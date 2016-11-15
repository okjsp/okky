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

    Integer workingMonth

    static constraints = {
      company nullable: true
      startDate nullable: true
      endDate nullable: true
      workingMonth nullable: true
    }
}
