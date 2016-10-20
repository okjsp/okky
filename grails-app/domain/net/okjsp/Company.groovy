package net.okjsp

class Company {

    String name
    String logo

    Person manager

    boolean enabled = true

    static hasMany = [members : Person]

    static constraints = {
        logo nullable: true
        name blank: false, unique: true
    }
}
