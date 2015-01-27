package net.okjsp

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.oauth.OAuthToken
import grails.plugin.springsecurity.oauth.SpringSecurityOAuthService
import grails.plugin.springsecurity.userdetails.GormUserDetailsService
import grails.plugin.springsecurity.userdetails.GrailsUser
import grails.transaction.Transactional
import org.springframework.security.core.authority.SimpleGrantedAuthority

@Transactional
class CustomSecurityOAuthService {

    def userDetailsService
    def grailsApplication

    OAuthToken createAuthToken(providerName, scribeToken) {
        def providerService = grailsApplication.mainContext.getBean("${providerName}SpringSecurityOAuthService")
        OAuthToken oAuthToken = providerService.createAuthToken(scribeToken)
        def OAuthID = lookupOAuthIdClass()
        def oAuthID = OAuthID.findByProviderAndAccessToken(oAuthToken.providerName, oAuthToken.socialId)
        if (oAuthID) {
            updateOAuthToken(oAuthToken, oAuthID.user)
        }
        return oAuthToken
    }

    OAuthToken updateOAuthToken(OAuthToken oAuthToken, user) {

        oAuthToken.principal = userDetailsService.loadUserByUsername(user.username)
        oAuthToken.authorities = oAuthToken.principal.authorities
        oAuthToken.authenticated = true

        return oAuthToken
    }

    /**
     * Returns if a user with the given username exists in database.
     */
    boolean usernameTaken(String username) {
        def User = lookupUserClass()
        User.withNewSession { session ->
            if (username && User.countByUsername(username)) {
                return 'OAuthCreateAccountCommand.username.error.unique'
            }
        }
    }

    def getAskToLinkOrCreateAccountUri() {
        def askToLinkOrCreateAccountUri = grailsApplication.config.grails.plugin.springsecurity.oauth.registration.askToLinkOrCreateAccountUri ?: '/oauth/askToLinkOrCreateAccount'
        return askToLinkOrCreateAccountUri
    }

    def getRoleNames() {
        def roleNames = grailsApplication.config.grails.plugin.springsecurity.oauth.registration.roleNames ?: ['ROLE_USER']
        return roleNames
    }

    protected String lookupUserClassName() {
        SpringSecurityUtils.securityConfig.userLookup.userDomainClassName
    }

    protected Class<?> lookupUserClass() {
        grailsApplication.getDomainClass(lookupUserClassName()).clazz
    }

    protected String lookupRoleClassName() {
        SpringSecurityUtils.securityConfig.authority.className
    }

    protected Class<?> lookupRoleClass() {
        grailsApplication.getDomainClass(lookupRoleClassName()).clazz
    }

    protected String lookupUserRoleClassName() {
        SpringSecurityUtils.securityConfig.userLookup.authorityJoinClassName
    }

    protected Class<?> lookupUserRoleClass() {
        grailsApplication.getDomainClass(lookupUserRoleClassName()).clazz
    }

    protected String lookupOAuthIdClassName() {
        def domainClass = grailsApplication.config.grails.plugin.springsecurity.oauth.domainClass ?: 'OAuthID'
        return domainClass
    }

    protected Class<?> lookupOAuthIdClass() {
        grailsApplication.getDomainClass(lookupOAuthIdClassName()).clazz
    }
}
