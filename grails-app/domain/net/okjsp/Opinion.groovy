package net.okjsp

class Opinion {

    String comment
    Integer voteCount = 0

    Avatar author
    Content content

    Date dateCreated
    Date lastUpdated

    static mapping = {
        comment type: 'text'
    }

    static constraints = {
        comment blank: false
    }
}
