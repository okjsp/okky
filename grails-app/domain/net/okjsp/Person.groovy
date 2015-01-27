package net.okjsp

class Person {

    String fullName
    String email
    String homepageUrl

    Company company

    Resume resume

    boolean dmAllowed = true

    Date dateCreated
    Date lastUpdated

    static constraints = {
        fullName blank: false, minSize: 2
        email blank: false, email: true, unique: true
        homepageUrl nullable: true, url: true
        company nullable: true
        resume nullable: true
    }

    String toString(){
        fullName
    }
}
