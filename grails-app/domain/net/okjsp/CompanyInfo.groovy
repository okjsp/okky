package net.okjsp

class CompanyInfo {

    Company company

    String description
    String welfare

    String tel
    String email

    String managerTel
    String managerEmail
    String managerName

    String homepageUrl
    Integer employeeNumber
    AttachedFile introFile

    static constraints = {
        company nullable: true
        description nullable: true
        welfare nullable: true
        homepageUrl nullable: true
        introFile nullable: true
    }

    static mapping = {
        description type: 'text'
        welfare type: 'text'
    }
}