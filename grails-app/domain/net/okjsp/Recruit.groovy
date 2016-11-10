package net.okjsp

class Recruit {
    Article article
    String city
    String district

    JobType jobType

    JobPositionType jobPositionType

    JobPayType jobPayType

    Company company

    Date startDate
    Date endDate

    Date workingMonth

    static constraints = {
      company nullable: true
      startDate nullable: true
      workingMonth nullable: true
    }
}
