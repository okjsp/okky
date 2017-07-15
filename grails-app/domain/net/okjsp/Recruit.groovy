package net.okjsp

class Recruit {
    Article article
    String city
    String district

    JobType jobType

    Company company

    String startDate

    Integer workingMonth

    String tel
    String email
    String name

    boolean closed = false

    static hasMany = [jobPositions: JobPosition]

    static mapping = {
        jobPositions sort: 'id', order: 'asc', cascade: "all-delete-orphan"
    }

    static constraints = {
      company nullable: true
      startDate nullable: true
      workingMonth nullable: true
    }
}
