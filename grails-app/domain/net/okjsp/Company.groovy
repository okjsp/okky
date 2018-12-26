package net.okjsp

class Company {

    String name
    String logo

    String registerNumber

    Person manager

    Date dateCreated = new Date()
    Date lastUpdated

    boolean enabled = false
    boolean locked = false

    static hasMany = [members : Person]

    static constraints = {
        logo nullable: true
        name blank: false, unique: true
    }
    
    static mapping = {
        cache true
    }
}
