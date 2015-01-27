package net.okjsp

import grails.transaction.Transactional
import sun.security.x509.AVA

class Content {
    transient springSecurityService

    ContentType type = ContentType.ARTICLE
    ContentTextType textType = ContentTextType.MD
    String text
    Integer voteCount = 0

    boolean selected = false

    Avatar author
    Avatar lastEditor

    boolean anonymity = false
    String aNickName

    Date dateCreated
    Date lastUpdated

    static hasMany = [files: File, contetnVotes : ContentVote]

    static belongsTo = [article: Article]

    static mapping = {
        text type: 'text'
        textType enumType: 'ordinal'
        type enumType: 'ordinal'
        sort id:'asc'
        contetnVotes cascade: 'all-delete-orphan'
    }

    static constraints = {
        text blank: false
        author bindable: false
        lastEditor nullable: true, bindable: false
        voteCount bindable: false
        type bindable: false
        article nullable: true
        aNickName nullable: true
    }

    def getDisplayAuthor() {
        if(anonymity) {
            return new Avatar(
                nickname: aNickName,
                picture: '',
                pictureType: AvatarPictureType.ANONYMOUSE,
                activityPoint: null
            )
        } else {
            return author
        }
    }

    def getAttachedFiles() {
        this.files.findAll {
            it.attachType == FileAttachType.ATTACHED
        }
    }

    @Transactional
    def updateVoteCount(def i) {
        if(id != null) {
            executeUpdate("update Content set voteCount = voteCount+:i where id = :id",[i:i, id: id])

            if(type == ContentType.ARTICLE) {
                article.updateVoteCount(i)
            }
        }
    }

    String toString() { text }
}
