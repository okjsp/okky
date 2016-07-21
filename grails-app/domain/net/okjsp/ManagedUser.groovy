package net.okjsp

class ManagedUser {
    
    transient springSecurityService
    
    User user

    static constraints = {
    }
    
    static mapping = {
        cache true
    }
}
