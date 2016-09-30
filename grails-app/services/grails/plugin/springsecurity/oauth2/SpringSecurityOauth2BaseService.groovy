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
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.oauth2.exception.OAuth2Exception
import grails.plugin.springsecurity.oauth2.service.OAuth2AbstractProviderService
import grails.plugin.springsecurity.oauth2.service.OAuth2ProviderService
import grails.plugin.springsecurity.oauth2.token.OAuth2SpringToken
import grails.plugin.springsecurity.oauth2.util.OAuth2ProviderConfiguration
import grails.plugin.springsecurity.userdetails.GormUserDetailsService
import grails.plugin.springsecurity.userdetails.GrailsUser
import grails.transaction.Transactional
import org.apache.commons.lang.exception.ExceptionUtils
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.SimpleGrantedAuthority

@Transactional
class SpringSecurityOauth2BaseService {

/**
 * Map for storing the different OAuth2Provider
 */
    Map<String, OAuth2AbstractProviderService> providerServiceMap = new HashMap<>()
    private Map<String, OAuth2ProviderConfiguration> _providerConfigurationMap = new HashMap<>()

    def grailsApplication
    AuthenticationManager authenticationManager

    OAuth2SpringToken createAuthToken(String providerName, OAuth2AccessToken scribeToken) {
        def providerService = getProviderService(providerName)
        OAuth2SpringToken oAuthToken = providerService.createSpringAuthToken(scribeToken)
        Class<?> OAuthID = lookupOAuthIdClass()
        def oAuthID = OAuthID.findByProviderAndAccessToken(oAuthToken.providerName, oAuthToken.socialId)
        if (oAuthID) {
            updateOAuthToken(oAuthToken, oAuthID.user)
        }
        return oAuthToken
    }

    /**
     * @param providerName
     * @return The authorization url for the provider
     */
    String getAuthorizationUrl(String providerName) {
        OAuth2AbstractProviderService providerService = getProviderService(providerName)
        providerService.getAuthUrl(['scope': _providerConfigurationMap.get(providerName).scope])
    }

    /**
     * @param username
     * @param password
     * @return Whether the authentication is valid or not
     */
    boolean authenticationIsValid(String username, String password) {
        boolean valid = true
        try {
            authenticationManager.authenticate new UsernamePasswordAuthenticationToken(username, password)
        } catch (AuthenticationException exception) {
            log.warn("Authentication is invalid")
            log.trace(ExceptionUtils.getStackTrace(exception))
            valid = false
        }
        return valid
    }

    /**
     * Update the oAuthToken
     * @param oAuthToken
     * @param user
     * @return A current OAuth2SpringToken
     */
    OAuth2SpringToken updateOAuthToken(OAuth2SpringToken oAuthToken, user) {
        def conf = SpringSecurityUtils.securityConfig

        // user

        String usernamePropertyName = conf.userLookup.usernamePropertyName
        String passwordPropertyName = conf.userLookup.passwordPropertyName
        String enabledPropertyName = conf.userLookup.enabledPropertyName
        String accountExpiredPropertyName = conf.userLookup.accountExpiredPropertyName
        String accountLockedPropertyName = conf.userLookup.accountLockedPropertyName
        String passwordExpiredPropertyName = conf.userLookup.passwordExpiredPropertyName

        String username = user."${usernamePropertyName}"
        String password = user."${passwordPropertyName}"
        boolean enabled = enabledPropertyName ? user."${enabledPropertyName}" : true
        boolean accountExpired = accountExpiredPropertyName ? user."${accountExpiredPropertyName}" : false
        boolean accountLocked = accountLockedPropertyName ? user."${accountLockedPropertyName}" : false
        boolean passwordExpired = passwordExpiredPropertyName ? user."${passwordExpiredPropertyName}" : false

        // authorities

        String authoritiesPropertyName = conf.userLookup.authoritiesPropertyName
        String authorityPropertyName = conf.authority.nameField
        Collection<?> userAuthorities = user."${authoritiesPropertyName}"
        def authorities = userAuthorities.collect { new SimpleGrantedAuthority(it."${authorityPropertyName}") }

        oAuthToken.principal = new GrailsUser(username, password, enabled, !accountExpired, !passwordExpired,
                !accountLocked, authorities ?: [GormUserDetailsService.NO_ROLE], user.id)
        oAuthToken.authorities = authorities
        oAuthToken.authenticated = true

        return oAuthToken
    }

    /**
     * Register the provider into the service
     * @param providerService
     */
    def void registerProvider(OAuth2ProviderService providerService) throws OAuth2Exception {
        log.debug("Registering provider: " + providerService.getProviderID())
        if (providerServiceMap.containsKey(providerService.getProviderID())) {
            // There is already a provider under that name
            log.warn("There is already a provider with the name " + providerService.getProviderID() + " registered")
        } else {
            String baseURL = getBaseUrl()
            def callbackURL = getConfigValue(providerService.providerID, "callback") ? baseURL + getConfigValue(providerService.providerID, "callback") : baseURL + "/oauth2/" + providerService.getProviderID() + "/callback"
            log.debug("Callback URL: " + callbackURL)
            def successUrl = getConfigValue(providerService.providerID, "successUri") ? baseURL + getConfigValue(providerService.providerID, "successUri") : null
            log.debug("Success URL: " + successUrl)
            def failureUrl = getConfigValue(providerService.providerID, "failureUri") ? baseURL + getConfigValue(providerService.providerID, "failureUri") : null
            log.debug("Failure URL: " + failureUrl)
            def scopes = getConfigValue(providerService.providerID, "scopes") ?: null
            log.debug("Additional Scopes: " + scopes)
            def apiKey = System.getenv("${providerService.getProviderID().toUpperCase()}_API_KEY") ?: getConfigValue(providerService.providerID, "api_key")
            def apiSecret = System.getenv("${providerService.getProviderID().toUpperCase()}_API_SECRET") ?: getConfigValue(providerService.providerID, "api_secret")
            log.debug("API Key: " + apiKey + ", Secret: " + apiSecret)
            if (apiKey == null || apiKey.isEmpty()) {
                throw new OAuth2Exception("API Key for provider '" + providerService.providerID + "' is missing")
            }
            if (apiSecret == null || apiSecret.isEmpty()) {
                throw new OAuth2Exception("API Secret for provider '" + providerService.providerID + "' is missing")
            }
            _providerConfigurationMap.put(providerService.providerID, new OAuth2ProviderConfiguration(
                    apiKey: apiKey,
                    apiSecret: apiSecret,
                    callbackUrl: callbackURL,
                    successUrl: successUrl,
                    failureUrl: failureUrl,
                    scope: scopes ? providerService.getScopes() + providerService.scopeSeparator + scopes : providerService.getScopes(),
                    debug: grailsApplication.config.getProperty('oauth2.debug') ? grailsApplication.config.getProperty('oauth2.debug') : false
            ))
            providerService.init(_providerConfigurationMap.get(providerService.providerID))
            providerServiceMap.put(providerService.providerID, providerService)
        }
    }

    private def getConfigValue(String provider, String key) {
        grailsApplication.config.getAt("grails.plugin.springsecurity.oauth2.providers.${provider}.${key}") ?: null
    }
    /**
     * @return The base url
     */
    def String getBaseUrl() {
        grailsApplication.config.getProperty('grails.serverURL') ?: "http://localhost:${System.getProperty('server.port', '8080')}"
    }

    /**
     * @param providerName
     * @return The successurl for the provider service
     */
    def String getSuccessUrl(String providerName) {
        def providerService = getProviderService(providerName)
        providerService.successUrl ?: baseUrl + "/oauth2/" + providerName + "/success"
    }

    /**
     * @param providerName
     * @return The failureUrl for the provider service
     */
    def String getFailureUrl(String providerName) {
        def providerService = getProviderService(providerName)
        providerService.failureUrl ?: baseUrl + "/oauth2/" + providerName + "/success"
    }

    /**
     * @return The uri pointing to the page ask to link or create account
     */
    def getAskToLinkOrCreateAccountUri() {
        def askToLinkOrCreateAccountUri = grailsApplication.config.grails.plugin.springsecurity.oauth2.registration.askToLinkOrCreateAccountUri ?: '/oauth2/ask'
        return askToLinkOrCreateAccountUri
    }

    /**
     * Get OAuth2AbstractProviderService
     * @param providerID
     * @return An OAuth2AbstractProviderService implementation
     */
    def OAuth2AbstractProviderService getProviderService(String providerID) {
        if (!providerServiceMap.get(providerID)) {
            log.error("There is no providerService for " + providerID)
            throw new OAuth2Exception("No provider '${providerID}'")
        }
        providerServiceMap.get(providerID)
    }

    /**
     * @param providerName
     * @return The session key
     */
    String sessionKeyForAccessToken(String providerName) {
        return "OAuth2:access-t:${providerName}"
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

    /**
     * @return The OAuthID class name
     */
    protected String lookupOAuthIdClassName() {
        def domainClass = grailsApplication.config.grails.plugin.springsecurity.oauth2.domainClass ?: 'OAuthID'
        return domainClass
    }

    /**
     * @return The OAuthID class
     */
    protected Class<?> lookupOAuthIdClass() {
        grailsApplication.getDomainClass(lookupOAuthIdClassName()).clazz
    }

    /**
     * @return The user class name
     */
    protected String lookupUserClassName() {
        SpringSecurityUtils.securityConfig.userLookup.userDomainClassName
    }

    /**
     * @return The user class
     */
    protected Class<?> lookupUserClass() {
        grailsApplication.getDomainClass(lookupUserClassName()).clazz
    }

    /**
     * @return The UserRole class name
     */
    protected String lookupUserRoleClassName() {
        SpringSecurityUtils.securityConfig.userLookup.authorityJoinClassName
    }

    /**
     * @return The UserRole class
     */
    protected Class<?> lookupUserRoleClass() {
        grailsApplication.getDomainClass(lookupUserRoleClassName()).clazz
    }

    /**
     * @return The Role class name
     */
    protected String lookupRoleClassName() {
        SpringSecurityUtils.securityConfig.authority.className
    }

    /**
     * @return The Role class
     */
    protected Class<?> lookupRoleClass() {
        grailsApplication.getDomainClass(lookupRoleClassName()).clazz
    }

    /**
     * @return The role names for a newly registered user
     */
    def getRoleNames() {
        def roleNames = grailsApplication.config.grails.plugin.springsecurity.oauth2.registration.roleNames ?: ['ROLE_USER']
        return roleNames
    }
}
