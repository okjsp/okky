package net.okjsp

class JobPosition {

    String title

    Integer minCareer
    Integer maxCareer

    Integer minPay
    Integer maxPay

    String tagString

    String description


    JobPositionGroup group
    JobPositionDuty duty

    static belongsTo = [recruit: Recruit]

    static hasMany = [tags : Tag]

    static constraints = {
        tagString nullable: true
        tags maxSize: 10, nullable: true
        maxCareer nullable: true
        maxPay nullable: true
    }
}