package net.okjsp

class JobPosition {

    String title

    Integer minCareer
    Integer maxCareer

    JobPayType jobPayType
    String tagString

    String description

    static belongsTo = [recruit: Recruit]

    static hasMany = [tags : Tag]

    static constraints = {
        tagString nullable: true
        tags maxSize: 10, nullable: true
        maxCareer nullable: true
    }
}