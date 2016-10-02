/* Copyright 2006-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package grails.plugin.springsecurity.oauth2

import com.github.scribejava.core.model.OAuth2AccessToken
import com.megatome.grails.RecaptchaService
import com.sun.istack.internal.Nullable
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.annotation.Secured
import grails.plugin.springsecurity.oauth2.exception.OAuth2Exception
import grails.plugin.springsecurity.oauth2.facebook.FacebookOauth2SpringToken
import grails.plugin.springsecurity.oauth2.token.OAuth2SpringToken
import grails.plugin.springsecurity.userdetails.GrailsUser
import grails.validation.ValidationException
import net.okjsp.AvatarPictureType
import net.okjsp.Person
import net.okjsp.User
import org.apache.commons.lang.StringUtils
import org.apache.commons.lang.exception.ExceptionUtils
import org.grails.validation.routines.UrlValidator
import org.springframework.security.core.context.SecurityContextHolder

/**
 * Controller for handling OAuth authentication request and
 * integrating it into SpringSecurity
 *
 * Based on SpringSecurityOAuthController:2.1.0.RC4
 */
@Secured('permitAll')
class SpringSecurityOAuth2Controller {

    public static final String SPRING_SECURITY_OAUTH_TOKEN = 'springSecurityOAuthToken'

    SpringSecurityOauth2BaseService springSecurityOauth2BaseService
    SpringSecurityService springSecurityService
    def userService
    RecaptchaService recaptchaService

    def oauthService

    /**
     * Authenticate
     */
    def authenticate() {

        String providerName = params.provider

        if (StringUtils.isBlank(providerName)) {
            throw new OAuth2Exception("No provider defined")
        }

        log.debug "authenticate ${providerName}"
        String url = springSecurityOauth2BaseService.getAuthorizationUrl(providerName)
        log.debug "redirect url from s2oauthservice=${url}"

        if (!UrlValidator.instance.isValid(url)) {
            flash.message = "Authorization url for provider '${providerName}' is invalid."
            redirect(controller: 'login', action: 'index')
        }

        redirect(url: url)

    }

    /**
     * Default callback function for first OAuth2 Step
     */
    def callback() {
        String providerName = params.provider
        log.debug("Callback for " + providerName)

        // Check if we got an AuthCode from the server query
        String authCode = params.code

        log.debug("AuthCode: " + authCode)
        if (!authCode || authCode.isEmpty()) {
            throw new OAuth2Exception("No AuthCode in callback for provider '${providerName}'")
        }

        def providerService = springSecurityOauth2BaseService.getProviderService(providerName)
        OAuth2AccessToken accessToken
        try {
            accessToken = providerService.getAccessToken(authCode)
        } catch (Exception exception) {
            log.error("Could not authenticate with oAuth2. " + ExceptionUtils.getMessage(exception), exception)
            log.debug(ExceptionUtils.getStackTrace(exception))
            redirect(uri: springSecurityOauth2BaseService.getFailureUrl(providerName))
            return
        }
        session[springSecurityOauth2BaseService.sessionKeyForAccessToken(providerName)] = accessToken
        redirect(uri: springSecurityOauth2BaseService.getSuccessUrl(providerName))
    }

    def onFailure(String provider) {
        flash.error = "Error authenticating with ${provider}"
        log.warn("Error authentication with OAuth2Provider ${provider}")
        authenticateAndRedirect(null, getDefaultTargetUrl())
    }

    def onSuccess(String provider) {
        if (!provider) {
            log.warn "The Spring Security OAuth callback URL must include the 'provider' URL parameter"
            throw new OAuth2Exception("The Spring Security OAuth callback URL must include the 'provider' URL parameter")
        }
        def sessionKey = springSecurityOauth2BaseService.sessionKeyForAccessToken(provider)
        if (!session[sessionKey]) {
            log.warn "No OAuth token in the session for provider '${provider}'"
            throw new OAuth2Exception("Authentication error for provider '${provider}'")
        }

        // Create the relevant authentication token and attempt to log in.
        OAuth2SpringToken oAuthToken = springSecurityOauth2BaseService.createAuthToken(provider, session[sessionKey])

        if (oAuthToken.principal instanceof GrailsUser) {
            authenticateAndRedirect(oAuthToken, getDefaultTargetUrl())
        } else {
            // This OAuth account hasn't been registered against an internal
            // account yet. Give the oAuthID the opportunity to create a new
            // internal account or link to an existing one.
            session[SPRING_SECURITY_OAUTH_TOKEN] = oAuthToken

            def redirectUrl = springSecurityOauth2BaseService.getAskToLinkOrCreateAccountUri()
            if (!redirectUrl) {
                log.warn "grails.plugin.springsecurity.oauth.registration.askToLinkOrCreateAccountUri configuration option must be set"
                throw new OAuth2Exception('Internal error')
            }
            log.debug "Redirecting to askToLinkOrCreateAccountUri: ${redirectUrl}"
            redirect(redirectUrl instanceof Map ? redirectUrl : [uri: redirectUrl])
        }
    }

    def ask() {

        OAuth2SpringToken oAuth2SpringToken = session[SPRING_SECURITY_OAUTH_TOKEN]

        // Check for token in session
        if (!oAuth2SpringToken) {
            println "askToLinkOrCreateAccount: OAuthToken not found in session"
            throw new OAuth2Exception('Authentication error')
        }

        User user = new User()

        if (springSecurityService.isLoggedIn()) {
            def currentUser = springSecurityService.currentUser

            // Try to add the token to the OAuthID's
            currentUser.addTooAuthIDs(
                    provider: oAuth2SpringToken.providerName,
                    accessToken: oAuth2SpringToken.socialId,
                    user: currentUser
            )
            if (currentUser.validate() && currentUser.save()) {
                // Could assign the token to the OAuthIDs. Login and redirect
                oAuth2SpringToken = springSecurityOauth2BaseService.updateOAuthToken(oAuth2SpringToken, currentUser)
                authenticateAndRedirect(oAuth2SpringToken, getDefaultTargetUrl())
                return
            }
        } else {
            Person person = new Person()

            if(oAuth2SpringToken.providerName == FacebookOauth2SpringToken.PROVIDER_NAME) {

                person.fullName = oAuth2SpringToken.screenName
                person.email = oAuth2SpringToken.socialId

//            } else if(providerName == GoogleOAuthToken.PROVIDER_NAME) {
//
//                def response = oauthService.getGoogleResource(oAuthToken.accessToken, 'https://www.googleapis.com/oauth2/v1/userinfo')
//                def info = JSON.parse(response.body)
//                println "info ==="
//                println info
//
//                person.fullName = info.name
//                person.email = info.email
            }

            user.person = person
        }



        // There seems to be a new one in the town aka 'There is no one logged in'
        // Ask to create a new account or link an existing user to it
        render view: '/springSecurityOAuth/askToLinkOrCreateAccount', model: [userInstance: user]
    }

    /**
     * Associates an OAuthID with an existing account. Needs the user's password to ensure
     * that the user owns that account, and authenticates to verify before linking.
     */
    def linkAccount(User command) {
        OAuth2SpringToken oAuth2SpringToken = session[SPRING_SECURITY_OAUTH_TOKEN] as OAuth2SpringToken
        if (!oAuth2SpringToken) {
            log.warn "linkAccount: OAuthToken not found in session"
            throw new OAuth2Exception('Authentication error')
        }
        if (request.post) {
            if (!springSecurityOauth2BaseService.authenticationIsValid(command.username, command.password)) {
                log.info "Authentication error for use ${command.username}"
                command.errors.rejectValue("username", "OAuthLinkAccountCommand.authentication.error")
                render view: 'ask', model: [linkAccountCommand: command]
                return
            }
            //def commandValid = command.validate()
            def User = springSecurityOauth2BaseService.lookupUserClass()
            boolean linked = User.withTransaction { status ->
                def user = User.findByUsername(command.username)
                if (user) {
                    user.addTooAuthIDs(provider: oAuth2SpringToken.providerName, accessToken: oAuth2SpringToken.socialId, user: user)
                    if (user.validate() && user.save()) {
                        oAuth2SpringToken = springSecurityOauth2BaseService.updateOAuthToken(oAuth2SpringToken, user)
                        return true
                    } else {
                        return false
                    }
                } else {
                    command.errors.rejectValue("username", "OAuthLinkAccountCommand.username.not.exists")
                }
                status.setRollbackOnly()
                return false
            }
            if (linked) {
                authenticateAndRedirect(oAuth2SpringToken, getDefaultTargetUrl())
                return
            }
        }
        render view: '/springSecurityOAuth/askToLinkOrCreateAccount', model: [userInstance: command]
    }

    /**
     * Create ne account and associate it with the oAuthID
     */
    def createAccount() {
        OAuth2SpringToken oAuth2SpringToken = session[SPRING_SECURITY_OAUTH_TOKEN] as OAuth2SpringToken
        if (!oAuth2SpringToken) {
            log.warn "createAccount: OAuthToken not found in session"
            throw new OAuth2Exception('Authentication error')
        }

        User user = new User(params)

        if (request.post) {
            if (!springSecurityService.loggedIn) {

                try {

                    def realIp = userService.getRealIp(request)
                    def reCaptchaVerified = recaptchaService.verifyAnswer(session, realIp, params)

                    user.createIp = realIp

                    if(user.hasErrors() || !reCaptchaVerified) {
                        respond user.errors, view: '/springSecurityOAuth/askToLinkOrCreateAccount', model: [userInstance: user]
                        return
                    }

                    user.addTooAuthIDs(provider: oAuth2SpringToken.providerName, accessToken: oAuth2SpringToken.socialId, user: user)

                    if(oAuth2SpringToken.providerName == FacebookOauth2SpringToken.PROVIDER_NAME) {
                        user.avatar.pictureType = AvatarPictureType.FACEBOOK
                    }

                    def created = userService.saveUser user

                    recaptchaService.cleanUp session

                    if (created) {
                        authenticateAndRedirect(oAuth2SpringToken, getDefaultTargetUrl())
                        return
                    }
                } catch (ValidationException e) {
                    respond user.errors, view: '/springSecurityOAuth/askToLinkOrCreateAccount'
                }

            }
        }
        render view: '/springSecurityOAuth/askToLinkOrCreateAccount', model: [userInstance: user]
    }

    /**
     * Set authentication token and redirect to the page we came from
     * @param oAuthToken
     * @param redirectUrl
     */
    protected void authenticateAndRedirect(@Nullable OAuth2SpringToken oAuthToken, redirectUrl) {
        session.removeAttribute SPRING_SECURITY_OAUTH_TOKEN
        SecurityContextHolder.context.authentication = oAuthToken
        redirect(redirectUrl instanceof Map ? redirectUrl : [uri: redirectUrl])
    }

    /**
     * Get default Url
     * @return
     */
    protected Map getDefaultTargetUrl() {
        def config = SpringSecurityUtils.securityConfig
        def savedRequest = SpringSecurityUtils.getSavedRequest(session)
        def defaultUrlOnNull = '/'
        if (savedRequest && !config.successHandler.alwaysUseDefault) {
            return [url: (savedRequest.redirectUrl ?: defaultUrlOnNull)]
        }
        return [uri: (config.successHandler.defaultTargetUrl ?: defaultUrlOnNull)]
    }
}
