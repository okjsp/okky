package net.okjsp

import grails.plugin.springsecurity.SpringSecurityUtils

class ManagedUser {
    
    transient springSecurityService
    
    User user

    static constraints = {
    }
    
    static mapping = {
        cache true
    }
}
