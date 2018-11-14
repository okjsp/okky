package net.okjsp

import com.megatome.grails.RecaptchaService
import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.oauth.FacebookOAuthToken
import grails.plugin.springsecurity.oauth.GoogleOAuthToken
import grails.plugin.springsecurity.oauth.OAuthLoginException
import grails.plugin.springsecurity.oauth.OAuthToken
import grails.plugin.springsecurity.userdetails.GrailsUser
import grails.validation.ValidationException
import org.codehaus.groovy.grails.web.util.WebUtils
import org.grails.plugin.springsecurity.oauth.GoogleApi20Token
import org.springframework.web.servlet.ModelAndView
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
/**
 * Simple controller for handling OAuth authentication and integrating it
 * into Spring Security.
 */
class SpringSecurityOAuthController {

    public static final String SPRING_SECURITY_OAUTH_TOKEN = 'springSecurityOAuthToken'

    def oauthService
    def springSecurityService
    def customSecurityOAuthService
    def authenticationManager
    def userService
    RecaptchaService recaptchaService

    /**
     * This can be used as a callback for a successful OAuth authentication
     * attempt.
     * It logs the associated user in if he or she has an internal
     * Spring Security account and redirects to <tt>targetUri</tt> (provided as a URL
     * parameter or in the session). Otherwise it redirects to a URL for
     * linking OAuth identities to Spring Security accounts. The application must implement
     * the page and provide the associated URL via the <tt>oauth.registration.askToLinkOrCreateAccountUri</tt>
     * configuration setting.
     *
     * "/oauth/$provider/success"(controller: "springSecurityOAuth", action: "onSuccess")
     */
    def onSuccess(String provider) {
        // Validate the 'provider' URL. Any errors here are either misconfiguration
        // or web crawlers (or malicious users).
        if (!provider) {
            log.warn "The Spring Security OAuth callback URL must include the 'provider' URL parameter"
            throw new OAuthLoginException("The Spring Security OAuth callback URL must include the 'provider' URL parameter")
        }

        def sessionKey = oauthService.findSessionKeyForAccessToken(provider)
        if (!session[sessionKey]) {
            log.warn "No OAuth token in the session for provider '${provider}'"
            throw new OAuthLoginException("Authentication error for provider '${provider}'")
        }
        // Create the relevant authentication token and attempt to log in.
        OAuthToken oAuthToken = customSecurityOAuthService.createAuthToken(provider, session[sessionKey])

        if (oAuthToken.principal instanceof GrailsUser) {
            authenticateAndRedirect(oAuthToken, getDefaultTargetUrl())
        } else {
            // This OAuth account hasn't been registered against an internal
            // account yet. Give the oAuthID the opportunity to create a new
            // internal account or link to an existing one.
            session[SPRING_SECURITY_OAUTH_TOKEN] = oAuthToken

            def redirectUrl = customSecurityOAuthService.getAskToLinkOrCreateAccountUri()
            if (!redirectUrl) {
                log.warn "grails.plugin.springsecurity.oauth.registration.askToLinkOrCreateAccountUri configuration option must be set"
                throw new OAuthLoginException('Internal error')
            }
            log.debug "Redirecting to askToLinkOrCreateAccountUri: ${redirectUrl}"
            redirect(redirectUrl instanceof Map ? redirectUrl : [uri: redirectUrl])
        }
    }

    def onFailure(String provider) {
        // TODO: put it in i18n messages file
        //flash.message = "book.delete.message"
        //flash.args = ["The Stand"]
        flash.default = "Error authenticating with ${provider}"
        log.warn "Error authenticating with external provider ${provider}"
        authenticateAndRedirect(null, getDefaultTargetUrl())
    }

    def askToLinkOrCreateAccount() {
        OAuthToken oAuthToken = session[SPRING_SECURITY_OAUTH_TOKEN]

        if (!oAuthToken) {
            println "askToLinkOrCreateAccount: OAuthToken not found in session"
            throw new OAuthLoginException('Authentication error')
        }

        User user = new User()

        if (springSecurityService.isLoggedIn()) {
            def currentUser = springSecurityService.getCurrentUser()
            currentUser.addToOAuthIDs(provider: oAuthToken.providerName, accessToken: oAuthToken.socialId, user: currentUser)
            if (currentUser.validate() && currentUser.save()) {
                oAuthToken = customSecurityOAuthService.updateOAuthToken(oAuthToken, currentUser)
                authenticateAndRedirect(oAuthToken, getDefaultTargetUrl())
                return
            }
        } else {

            Person person = new Person()

            if(oAuthToken.providerName == FacebookOAuthToken.PROVIDER_NAME) {
                def response = oauthService.getFacebookResource(oAuthToken.accessToken, 'https://graph.facebook.com/me')
                def info = JSON.parse(response.body)
                println "info ==="
                println info

                person.fullName = info.name
                person.email = info.email
            } else if(oAuthToken.providerName == GoogleOAuthToken.PROVIDER_NAME) {

                def response = oauthService.getGoogleResource(oAuthToken.accessToken, 'https://www.googleapis.com/oauth2/v1/userinfo')
                def info = JSON.parse(response.body)
                println "info ==="
                println info

                person.fullName = info.name
                person.email = info.email
            }

            user.person = person
        }

        println user.person.email

        render view: 'askToLinkOrCreateAccount', model: [userInstance: user]
    }

    /**
     * Associates an OAuthID with an existing account. Needs the user's password to ensure
     * that the user owns that account, and authenticates to verify before linking.
     */
    def linkAccount(User command) {
        OAuthToken oAuthToken = session[SPRING_SECURITY_OAUTH_TOKEN]

        if (!oAuthToken) {
            log.warn "linkAccount: OAuthToken not found in session"
            throw new OAuthLoginException('Authentication error')
        }
        if (request.post) {
            if (!authenticationIsValid(command.username, command.password)) {
                log.info "Authentication error for use ${command.username}"
                command.errors.rejectValue("username", "OAuthLinkAccountCommand.authentication.error")
                render view: 'askToLinkOrCreateAccount', model: [linkAccountCommand: command]
                return
            }
//            def commandValid = command.validate()
            def User = customSecurityOAuthService.lookupUserClass()
            boolean linked = User.withTransaction { status ->
                //def user = User.findByUsernameAndPassword(command.username, springSecurityService.encodePassword(command.password))
                def user = User.findByUsernameAndEnabled(command.username, true)
                if (user) {
                    user.addToOAuthIDs(provider: oAuthToken.providerName, accessToken: oAuthToken.socialId, user: user)
                    if (user.validate() && user.save()) {
                        oAuthToken = customSecurityOAuthService.updateOAuthToken(oAuthToken, user)
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
                authenticateAndRedirect(oAuthToken, getDefaultTargetUrl())
                return
            }
        }
        render view: 'askToLinkOrCreateAccount', model: [userInstance: command]
        return
    }

    private boolean authenticationIsValid(String username, String password) {
        boolean valid = true
        try {
            authenticationManager.authenticate new UsernamePasswordAuthenticationToken(username, password)
        } catch (AuthenticationException e) {
            valid = false
        }
        return valid
    }

    def createAccount() {
        OAuthToken oAuthToken = session[SPRING_SECURITY_OAUTH_TOKEN]
        if (!oAuthToken) {
            log.warn "createAccount: OAuthToken not found in session"
            throw new OAuthLoginException('Authentication error')
        }

        User user = new User(params)

        if (request.post) {
            if (!springSecurityService.loggedIn) {

                try {

                    def realIp = userService.getRealIp(request)

                    def reCaptchaVerified = recaptchaService.verifyAnswer(session, realIp, params)

                    user.createIp = realIp

                    if(user.hasErrors() || !reCaptchaVerified) {
                        respond user.errors, view: 'askToLinkOrCreateAccount', model: [userInstance: user]
                        return
                    }

                    user.addToOAuthIDs(provider: oAuthToken.providerName, accessToken: oAuthToken.socialId, user: user)
                    user.enabled = true
                    if(oAuthToken.providerName == FacebookOAuthToken.PROVIDER_NAME)
                        user.avatar.pictureType = AvatarPictureType.FACEBOOK

                    def created = userService.saveUser user

                    recaptchaService.cleanUp session

                    if (created) {
                        authenticateAndRedirect(oAuthToken, getDefaultTargetUrl())
                        return
                    }

                } catch (ValidationException e) {
                    respond user.errors, view: 'askToLinkOrCreateAccount'
                }
            }
        }
        render view: 'askToLinkOrCreateAccount', model: [userInstance: user]
    }

    protected Map getDefaultTargetUrl() {
        def config = SpringSecurityUtils.securityConfig
        def savedRequest = SpringSecurityUtils.getSavedRequest(session)
        def defaultUrlOnNull = '/'
        if (savedRequest && !config.successHandler.alwaysUseDefault) {
            return [url: (savedRequest.redirectUrl && !savedRequest.redirectUrl.endsWith('.json') ? savedRequest.redirectUrl : defaultUrlOnNull)]
        }
        return [uri: (config.successHandler.defaultTargetUrl ?: defaultUrlOnNull)]
    }

    protected void authenticateAndRedirect(OAuthToken oAuthToken, redirectUrl) {
        session.removeAttribute SPRING_SECURITY_OAUTH_TOKEN
        SecurityContextHolder.context.authentication = oAuthToken

        def remoteAddress =  userService.getRealIp(WebUtils.retrieveGrailsWebRequest().request)

        GrailsUser grailsUser = oAuthToken.principal

        User user = User.findByUsername(grailsUser.getUsername())

        // Login Log 저장
        new LoggedIn(user: user, remoteAddr: remoteAddress).save(flush: true)

        redirect(redirectUrl instanceof Map ? redirectUrl : [uri: redirectUrl])
    }

}
