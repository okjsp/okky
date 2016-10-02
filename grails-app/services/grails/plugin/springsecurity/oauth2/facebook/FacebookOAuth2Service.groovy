package grails.plugin.springsecurity.oauth2.facebook

import com.github.scribejava.apis.FacebookApi
import com.github.scribejava.core.builder.api.DefaultApi20
import com.github.scribejava.core.model.OAuth2AccessToken
import grails.converters.JSON
import grails.plugin.springsecurity.oauth2.exception.OAuth2Exception
import grails.plugin.springsecurity.oauth2.service.OAuth2AbstractProviderService
import grails.plugin.springsecurity.oauth2.token.OAuth2SpringToken
import grails.transaction.Transactional

@Transactional
class FacebookOAuth2Service extends OAuth2AbstractProviderService {

    @Override
    String getScopeSeparator() {
        return ","
    }

    @Override
    String getProviderID() {
        return 'facebook'
    }

    @Override
    Class<? extends DefaultApi20> getApiClass() {
        FacebookApi.class
    }

    @Override
    String getProfileScope() {
        return 'https://graph.facebook.com/me?fields=email,name'
    }

    /**
     * The scopes that are at least required by the oauth2 provider, to get an email-address
     */
    @Override
    String getScopes() {
        return "email"
    }

    @Override
    OAuth2SpringToken createSpringAuthToken(OAuth2AccessToken accessToken) {
        def user
        def response = getResponse(accessToken)
        try {
            log.debug("JSON response body: " + accessToken.rawResponse)
            user = JSON.parse(response.body)
        } catch (Exception exception) {
            log.error("Error parsing response from " + getProviderID() + ". Response:\n" + response.body)
            throw new OAuth2Exception("Error parsing response from " + getProviderID(), exception)
        }
        if (!user?.email) {
            log.error("No user email from " + getProviderID() + ". Response was:\n" + response.body)
            throw new OAuth2Exception("No user email from " + getProviderID())
        }
        new FacebookOauth2SpringToken(accessToken, user?.email, user?.name, providerID)
    }

}
