package grails.plugin.springsecurity.oauth2.facebook

import com.github.scribejava.core.model.OAuth2AccessToken
import grails.plugin.springsecurity.oauth2.token.OAuth2SpringToken

/**
 * Always code as if the guy who ends up maintaining your code
 * will be a violent psychopath that knows where you live.
 *
 * - John Woods
 *
 * Created on 18.06.2016
 * @author J. Brunswicker
 */
class FacebookOauth2SpringToken extends OAuth2SpringToken{

    public static final String PROVIDER_NAME = 'facebook'

    private String email
    private String name
    private String providerId

    FacebookOauth2SpringToken(OAuth2AccessToken accessToken, String email, String name, String providerId) {
        super(accessToken)
        this.email = email
        this.name = name
        this.providerId = providerId
    }

    @Override
    String getProviderName() {
        return providerId
    }

    @Override
    String getSocialId() {
        return email
    }

    @Override
    String getScreenName() {
        return name
    }
}
