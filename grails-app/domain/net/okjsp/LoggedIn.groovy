package net.okjsp

class LoggedIn {

    User user
    Date dateCreated
    String remoteAddr

    static constraints = {
        remoteAddr nullable: true
    }
}
