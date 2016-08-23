package net.okjsp

class LoggedIn {

    User user
    Date dateCreated
    String remoteAddr

    static constraints = {
        remoteAddr nullable: true
    }

    static mapping = {
        sort dateCreated: 'desc'
    }

    @Override
    String toString() {
        "${remoteAddr} | ${dateCreated}"
    }
}
