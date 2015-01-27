package net.okjsp

class ContentVote  {

    Integer point = 1

    Article article
    Content content
    Avatar voter

    Date dateCreated

    static constraints = {
        point bindable: false
        voter bindable: false
        voter unique: 'content'
    }

    static mapping = {
        version false
    }

    def afterInsert() {
        content.updateVoteCount(point)
    }

    def beforeDelete() {
        content.updateVoteCount(-point)
    }
}
