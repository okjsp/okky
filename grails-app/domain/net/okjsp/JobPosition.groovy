package net.okjsp

class JobPosition {

    JobPositionType jobPositionType

    JobPayType jobPayType
    String tagString

    static hasMany = [tags : Tag]

    static constraints = {
        tagString nullable: true
        tags maxSize: 10, nullable: true
    }
}