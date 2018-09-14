package net.okjsp.api;

import org.grails.plugin.springsecurity.oauth.GoogleApi20Token;
import org.scribe.builder.api.DefaultApi20;
import org.scribe.exceptions.OAuthException;
import org.scribe.extractors.AccessTokenExtractor;
import org.scribe.model.*;
import org.scribe.oauth.OAuth20ServiceImpl;
import org.scribe.oauth.OAuthService;
import org.scribe.utils.OAuthEncoder;
import org.scribe.utils.Preconditions;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Google OAuth2.0
 * Released under the same license as scribe (MIT License)
 *
 * @author houman001
 *         This code borrows from and modifies changes made by @yincrash and @donbeave
 * @author yincrash
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
public class GoogleApi20 extends DefaultApi20 {

    private static final String AUTHORIZE_URL = "https://accounts.google.com/o/oauth2/auth?response_type=code&client_id=%s&redirect_uri=%s";
    private static final String SCOPED_AUTHORIZE_URL = AUTHORIZE_URL + "&scope=%s";
    private static final String SUFFIX_OFFLINE = "&access_type=offline&approval_prompt=force";

    private boolean offline = false;

    @Override
    public String getAccessTokenEndpoint() {
        return "https://accounts.google.com/o/oauth2/token";
    }

    @Override
    public AccessTokenExtractor getAccessTokenExtractor() {
        return new AccessTokenExtractor() {
            @Override
            public Token extract(String response) {
                Preconditions.checkEmptyString(response, "Response body is incorrect. Can't extract a token from an empty string");

                Matcher matcher = Pattern.compile("\"access_token\"\\s*: \"([^&\"]+)\"").matcher(response);
                if (matcher.find()) {
                    String token = OAuthEncoder.decode(matcher.group(1));
                    String refreshToken = "";
                    Matcher refreshMatcher = Pattern.compile("\"refresh_token\"\\s*: \"([^&\"]+)\"").matcher(response);
                    if (refreshMatcher.find())
                        refreshToken = OAuthEncoder.decode(refreshMatcher.group(1));
                    Date expiry = null;
                    Matcher expiryMatcher = Pattern.compile("\"expires_in\"\\s*: ([^,&\"]+)").matcher(response);
                    if (expiryMatcher.find()) {
                        int lifeTime = Integer.parseInt(OAuthEncoder.decode(expiryMatcher.group(1)));
                        expiry = new Date(System.currentTimeMillis() + lifeTime * 1000);
                    }
                    return new GoogleApi20Token(token, refreshToken, expiry, response);
                } else {
                    throw new OAuthException("Response body is incorrect. Can't extract a token from this: '" + response + "'", null);
                }
            }
        };

    }

    @Override
    public String getAuthorizationUrl(OAuthConfig config) {
        // Append scope if present
        if (config.hasScope()) {
            return String.format(offline ?
                            SCOPED_AUTHORIZE_URL + SUFFIX_OFFLINE : SCOPED_AUTHORIZE_URL, config.getApiKey(),
                    OAuthEncoder.encode(config.getCallback()),
                    OAuthEncoder.encode(config.getScope())
            );
        } else {
            return String.format(offline ?
                            AUTHORIZE_URL + SUFFIX_OFFLINE : AUTHORIZE_URL, config.getApiKey(),
                    OAuthEncoder.encode(config.getCallback())
            );
        }
    }

    @Override
    public Verb getAccessTokenVerb() {
        return Verb.POST;
    }

    @Override
    public OAuthService createService(OAuthConfig config) {
        return new Service(this, config);
    }

    public class Service extends OAuth20ServiceImpl {

        private static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
        private static final String GRANT_TYPE = "grant_type";
        private DefaultApi20 api;
        private OAuthConfig config;

        public Service(DefaultApi20 api, OAuthConfig config) {
            super(api, config);
            this.api = api;
            this.config = config;
        }

        public void setOffline(boolean offline) {
            GoogleApi20.this.offline = offline;
        }

        public boolean isOffline() {
            return offline;
        }

        @Override
        public Token getAccessToken(Token requestToken, Verifier verifier) {
            OAuthRequest request = new OAuthRequest(api.getAccessTokenVerb(), api.getAccessTokenEndpoint());
            switch (api.getAccessTokenVerb()) {
                case POST:
                    request.addBodyParameter(OAuthConstants.CLIENT_ID, config.getApiKey());
                    request.addBodyParameter(OAuthConstants.CLIENT_SECRET, config.getApiSecret());
                    request.addBodyParameter(OAuthConstants.CODE, verifier.getValue());
                    request.addBodyParameter(OAuthConstants.REDIRECT_URI, config.getCallback());
                    request.addBodyParameter(GRANT_TYPE, GRANT_TYPE_AUTHORIZATION_CODE);
                    break;
                case GET:
                default:
                    request.addQuerystringParameter(OAuthConstants.CLIENT_ID, config.getApiKey());
                    request.addQuerystringParameter(OAuthConstants.CLIENT_SECRET, config.getApiSecret());
                    request.addQuerystringParameter(OAuthConstants.CODE, verifier.getValue());
                    request.addQuerystringParameter(OAuthConstants.REDIRECT_URI, config.getCallback());
                    if (config.hasScope()) request.addQuerystringParameter(OAuthConstants.SCOPE, config.getScope());
            }
            Response response = request.send();
            return api.getAccessTokenExtractor().extract(response.getBody());
        }
    }

}