package net.okjsp

class Company {

    String name

    static hasMany = [members : Person]

    static constraints = {
        name blank: false, unique: true
    }
}
