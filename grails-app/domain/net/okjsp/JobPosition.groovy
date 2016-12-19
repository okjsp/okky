package net.okjsp

class JobPosition {

    String title

    Integer minCareer
    Integer maxCareer

    JobPayType jobPayType
    String tagString

    String description

    static hasMany = [tags : Tag]

    static constraints = {
        tagString nullable: true
        tags maxSize: 10, nullable: true
    }
}