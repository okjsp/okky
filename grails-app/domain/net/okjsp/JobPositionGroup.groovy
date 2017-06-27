package net.okjsp

class JobPositionGroup {

    String name

    static hasMany = [duties : JobPositionDuty]

    static constraints = {
    }
}
