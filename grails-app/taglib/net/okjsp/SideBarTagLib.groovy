package net.okjsp

import grails.plugin.springsecurity.SpringSecurityService
import org.ocpsoft.prettytime.PrettyTime
import org.springframework.web.servlet.support.RequestContextUtils

class SideBarTagLib {

    SpringSecurityService springSecurityService
    NotificationService notificationService

    /**
     * sidebar
     *
     * @attr category current category
     */
    def sidebar = { attrs, body ->

        Category category = attrs.category
        int notificationCount = 0

        if(springSecurityService.loggedIn) {
            Avatar avatar = Avatar.get(springSecurityService.principal.avatarId)
            notificationCount = notificationService.count(avatar)
        }

        println category

        out << render(template: '/layouts/sidebar', model: [category: category, notificationCount: notificationCount])
    }

    def encodedURL = { attrs, body ->
        def redirectUrl = request.forwardURI.substring(request.contextPath.length())
        def queryString = request.queryString ? '?'+request.queryString : ''

        if(attrs.withDomain) redirectUrl = grailsApplication.config.grails.serverURL + redirectUrl

        out << URLEncoder.encode(redirectUrl+queryString, request.characterEncoding)
    }
}
