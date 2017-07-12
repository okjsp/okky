package net.okjsp

class JobPositionDuty {

    String name

    static belongsTo = [group : JobPositionGroup]

    static constraints = {
    }
}
