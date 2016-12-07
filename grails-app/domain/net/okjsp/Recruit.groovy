package net.okjsp

class Recruit {
    Article article
    String city
    String district

    JobType jobType

    Company company

    Date startDate
    Date endDate

    Integer workingMonth

    static hasMany = [jobPositions: JobPosition]

    static constraints = {
      company nullable: true
      startDate nullable: true
      endDate nullable: true
      workingMonth nullable: true
    }
}
