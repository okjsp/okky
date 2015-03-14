package net.okjsp

import grails.transaction.Transactional

class Article {

    String title
    String tagString

    Integer viewCount = 0
    Integer voteCount = 0
    Integer noteCount = 0
    Integer scrapCount = 0
    boolean enabled = true
    boolean choice = false

    Category category

    Avatar author
    Avatar lastEditor

    boolean anonymity = false
    String aNickName

    Content selectedNote
    
    String createIp = ""

    Date dateCreated
    Date lastUpdated
    
    Integer best = 0

    static belongsTo = [content: Content]

    static hasMany = [tags : Tag, notes: Content]

    static mapping = {
        notes sort: 'id', order: 'asc'
        sort id: 'desc'
        best formula: "view_count + vote_count * 5"
    }

    static constraints = {
        title blank: false
        author bindable: false
        lastEditor nullable: true, bindable: false
        aNickName nullable: true
        viewCount bindable: false
        voteCount bindable: false
        noteCount bindable: false
        scrapCount bindable: false
        tags maxSize: 5, nullable: true
        tagString nullable: true
        notes bindable: false
        enabled bindable: false
        selectedNote nullable: true, bindable: false
        content nullable: true
        choice bindable: false, nullable: true
        createIp bindable: false, nullable: true
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

    def beforeInsert() {
        updateTag()
    }

    def beforeUpdate() {
        if(isDirty('tagString')) {
            updateTag()
        }
    }

    def beforeDelete() {
        /*if(tags) {
            tags.each { tag ->
                tag.taggedCount--
                tag.save()
            }
        }*/
    }

    void updateTag() {

        def removedTags = tags ?: []

        if(tagString) {
            def tagNames = tagString.split(/[,\s]+/).toList().unique().findAll { !it.isEmpty() }

            tagNames.each { tagName ->
                tagName = tagName.toLowerCase()
                def tag = TagSimilarText.findByText(tagName)?.tag ?: Tag.findByName(tagName)

                if(tag == null) {
                    tag =  new Tag(name: tagName).save()
                } else {
                    if(!tags?.contains(tag))
                        tag.taggedCount++
                }

                addToTags(tag)
                removedTags -= tag
                tag.save()
            }

            tagString = tagNames.join(',')
        }

        removedTags.each { tag ->
            removeFromTags(tag)
            tag.taggedCount--
            tag.save()
        }
    }

    /*
        Custom queries
     */
    @Transactional
    def updateViewCount(def i) {
        if(id != null) {
            executeUpdate("update Article set viewCount = viewCount+:i where id = :id",[i:i, id: id])
        }
    }

    @Transactional
    def updateNoteCount(def i) {
        if(id != null) {
            executeUpdate("update Article set noteCount = noteCount+:i where id = :id",[i:i, id: id])
        }
    }

    @Transactional
    def updateVoteCount(def i) {
        if(id != null) {
            executeUpdate("update Article set voteCount = voteCount+:i where id = :id",[i:i, id: id])
        }
    }

    @Transactional
    def updateScrapCount(def i) {
        if(id != null) {
            executeUpdate("update Article set scrapCount = scrapCount+:i where id = :id",[i:i, id: id])
        }
    }

    String toString() {
        return "#${id} - ${title}"
    }
}
