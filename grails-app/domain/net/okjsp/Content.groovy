package net.okjsp

import grails.transaction.Transactional

class Content {
    transient sanitizeService
    transient articleService

    ContentType type = ContentType.ARTICLE
    ContentTextType textType = ContentTextType.MD
    String text
    Integer voteCount = 0

    boolean selected = false

    Avatar author
    Avatar lastEditor

    boolean anonymity = false
    String aNickName
    String createIp = null

    boolean enabled = true

    Date dateCreated
    Date lastUpdated

    static hasMany = [files: AttachedFile, contetnVotes: ContentVote]

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
        author bindable: false, nullable: true
        lastEditor nullable: true, bindable: false
        voteCount bindable: false
        type bindable: false
        article nullable: true
        aNickName nullable: true
        createIp nullable: true
        enabled nullable: true
        text validator: { val ->
            def spam = SpamWord.findAll().find { word ->
                val.contains(word.text)
            }

            if(spam) return ["default.invalid.word.message"]
        }
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
            it.attachType == AttachedFileType.ATTACHED
        }
    }

    def beforeInsert() {
        if(text) {
            text = sanitizeService.sanitize(text)
        }

        if(anonymity) {
            anonymity = true
            author = null
        }
    }

    def beforeUpdate() {
        if(isDirty("text")) {
            text = sanitizeService.sanitize(text)

            articleService.changeLog(ChangeLogType.CONTENT, article, this,  this.getPersistentValue('text'), text)
        }

        if(anonymity) {
            anonymity = true
            lastEditor = null
            author = null
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
