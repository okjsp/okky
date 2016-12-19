package net.okjsp

class CompanyInfo {

    Company company

    String description
    String welfare

    String tel
    String email
    String homepageUrl
    Integer employeeNumber

    static constraints = {
        company nullable: true
        description nullable: true
        welfare nullable: true
    }

    static mapping = {
        description type: 'text'
        welfare type: 'text'
    }
}