package net.okjsp

class Activity {

    Avatar avatar
    ActivityType type
    ActivityPointType pointType = ActivityPointType.NONE
    Article article
    Content content
    Integer point = 0

    Date dateCreated
    Date lastUpdated

    static constraints = {
        point bindable:false
    }

    static mapping = {
        sort id:'desc'
    }

    def beforeInsert() {

        switch(this.type) {
        // Take point
            case ActivityType.POSTED:
                this.point = 10
                this.pointType = ActivityPointType.TAKE
                break
            case ActivityType.NOTED:
                if(this.article.author != this.avatar)
                    this.point = 2
                this.pointType = ActivityPointType.TAKE
                break
            case ActivityType.SOLVED:
                if(this.article.author != this.avatar)
                    this.point = 50
                this.pointType = ActivityPointType.TAKE
                break

        // Give point
            case ActivityType.ASSENTED_ARTICLE:
                if(this.content.author != this.avatar)
                    this.point = 2
                this.pointType = ActivityPointType.GIVE
                break

            case ActivityType.DISSENTED_ARTICLE:
                if(this.content.author != this.avatar)
                    this.point = -2
                this.pointType = ActivityPointType.GIVE
                break

            case ActivityType.ASSENTED_NOTE:
                if(this.content.author != this.avatar)
                    this.point = 1
                this.pointType = ActivityPointType.GIVE
                break

            case ActivityType.DISSENTED_NOTE:
                if(this.content.author != this.avatar)
                    this.point = -1
                this.pointType = ActivityPointType.GIVE
                break

            case ActivityType.SCRAPED:
                if(this.content.author != this.avatar)
                    this.point = 5
                this.pointType = ActivityPointType.GIVE
                break
        }

        if(this.pointType == ActivityPointType.TAKE)
            this.avatar.updateActivityPoint(this.point)
        else if(this.pointType == ActivityPointType.GIVE)
            this.content.author.updateActivityPoint(this.point)
    }

    def beforeDelete() {
        println this.pointType
        if(this.pointType == ActivityPointType.TAKE)
            this.avatar.updateActivityPoint(-this.point)
        else if(this.pointType == ActivityPointType.GIVE)
            this.content.author.updateActivityPoint(-this.point)
    }
}