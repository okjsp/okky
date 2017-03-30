package net.okjsp.api;


/**
 * Created by langerhans on 2017. 3. 31..
 */

import org.grails.plugin.springsecurity.oauth.GoogleApi20Token;
import org.scribe.builder.api.DefaultApi20;
import org.scribe.exceptions.OAuthException;
import org.scribe.extractors.AccessTokenExtractor;
import org.scribe.extractors.TokenExtractor20Impl;
import org.scribe.model.OAuthConfig;
import org.scribe.model.Token;
import org.scribe.utils.OAuthEncoder;
import org.scribe.utils.Preconditions;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FacebookApi extends DefaultApi20 {
    private static final String AUTHORIZE_URL = "https://www.facebook.com/dialog/oauth?client_id=%s&redirect_uri=%s";
    private static final String SCOPED_AUTHORIZE_URL = "https://www.facebook.com/dialog/oauth?client_id=%s&redirect_uri=%s&scope=%s";

    public FacebookApi() {
    }

    @Override
    public AccessTokenExtractor getAccessTokenExtractor() {
        return new AccessTokenExtractor() {
            @Override
            public Token extract(String response) {
                Preconditions.checkEmptyString(response, "Response body is incorrect. Can't extract a token from an empty string");

                Matcher matcher = Pattern.compile("\"access_token\":\"([^&\"]+)\"").matcher(response);
                if (matcher.find()) {
                    String token = OAuthEncoder.decode(matcher.group(1));
                    return new Token(token, "", response);
                } else {
                    throw new OAuthException("Response body is incorrect. Can't extract a token from this: '" + response + "'", null);
                }
            }
        };

    }

    public String getAccessTokenEndpoint() {
        return "https://graph.facebook.com/oauth/access_token";
    }

    public String getAuthorizationUrl(OAuthConfig config) {
        Preconditions.checkValidUrl(config.getCallback(), "Must provide a valid url as callback. Facebook does not support OOB");
        return config.hasScope()?String.format("https://www.facebook.com/dialog/oauth?client_id=%s&redirect_uri=%s&scope=%s", config.getApiKey(), OAuthEncoder.encode(config.getCallback()), OAuthEncoder.encode(config.getScope())):String.format("https://www.facebook.com/dialog/oauth?client_id=%s&redirect_uri=%s", config.getApiKey(), OAuthEncoder.encode(config.getCallback()));
    }
}
