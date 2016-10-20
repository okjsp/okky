package net.okjsp

class Recruit {
    Article article
    String city
    String district

    JobType jobType

    JobPositionType jobPositionType

    JobPayType jobPayType

    Company company

    static constraints = {
      company nullable: true
    }
}
