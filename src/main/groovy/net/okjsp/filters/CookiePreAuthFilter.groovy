package net.okjsp.filters

import grails.plugin.springsecurity.SpringSecurityUtils
import net.okjsp.User
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter

import javax.servlet.http.HttpServletRequest

/**
 * Created by langerhans on 2014. 8. 20..
 */
class CookiePreAuthFilter extends AbstractPreAuthenticatedProcessingFilter {

    def springSecurityService
    def grailsApplication
    def cookieService

    protected Object getPreAuthenticatedCredentials(HttpServletRequest arg0) {
        return null
    }
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest arg0) {
        def cookieKey = SpringSecurityUtils.securityConfig.rememberMe.key
        def cookieName = SpringSecurityUtils.securityConfig.rememberMe.cookieName + "_"

        def cookieValue = cookieService.get(cookieName)

        if(cookieValue) {
            def splitContent = new String(cookieValue.decodeBase64()).split(":")
            if(splitContent && splitContent.size() > 0) {
                def userInstance = User.findByUsername(splitContent[0])

                Integer expirationTime = -1

                def hashFromContent = "${userInstance.username}:${expirationTime}:${userInstance.password}:${cookieKey}".encodeAsSHA256()

                if(hashFromContent == splitContent[2]) {
                    return userInstance.username
                }
            }
        }
        return null
    }
}