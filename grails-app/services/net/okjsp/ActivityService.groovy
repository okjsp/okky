package net.okjsp

import grails.transaction.Transactional
import net.okjsp.*

@Transactional
class ActivityService {

    /**
     * Activity 찾기
     * @param article
     * @param content
     * @param avatar
     * @param activityType
     * @return
     */
    def find(Article article, Content content, Avatar avatar, ActivityType activityType) {

        Activity activity = Activity.createCriteria().get {
            and {
                eq('article', article)
                eq('avatar', avatar)
                eq('content', content)
                eq('type', activityType)
            }
        }

        activity
    }

    /**
     * Activity 생성
      * @param article
     * @param content
     * @param avatar
     * @param activityType
     * @return
     */
    def create(ActivityType activityType, Article article, Content content, Avatar avatar) {

        Activity activity = new Activity(article: article,
                avatar: avatar,
                content: content,
                type: activityType)
                .save(flush: true, failOnError: true)

        activity
    }

    def createByArticle(ActivityType activityType, Article article, Avatar avatar) {
        Activity activity = create(activityType, article,
            article.content, avatar)

        activity
    }

    def createByContent(ActivityType activityType, Content content, Avatar avatar) {
        Activity activity = create(activityType, content.article,
            content, avatar)

        activity
    }

    /**
     * Activity 삭제
     * @param article
     * @param content
     * @param avatar
     * @param activityType
     * @return
     */
    def remove(Activity activity) {

        activity.delete(flush: true, failOnError: true)
    }


    def removeByArticle(ActivityType activityType, Article article, Avatar avatar) {

        Activity activity = Activity.createCriteria().get {
            and {
                eq('type', activityType)
                eq('article', article)
                eq('content', article.content)
                eq('avatar', avatar)
            }
        }

        unsetPoint(activity, article.content)

        remove(activity)
    }


    def removeByContent(ActivityType activityType, Content content, Avatar avatar) {

        Activity activity = Activity.createCriteria().get {
            and {
                eq('type', activityType)
                eq('article', content.article)
                eq('content', content)
                eq('avatar', avatar)
            }
        }

        remove(activity)
    }

    def removeAllByArticle(Article article) {

        def activities = Activity.findAllByArticle(article)

        Activity.deleteAll(activities)
    }

    def removeAllByContent(Content content) {

        def activities = Activity.findAllByContent(content)

        Activity.deleteAll(activities)
    }

    def setPoint(Activity activityInstance, Content contentInstance) {

        int point = 0
        ActivityPointType pointType = ActivityPointType.NONE

        switch(activityInstance.type) {

        /*
            Take point
         */
            case ActivityType.POSTED:
                point = 10
                pointType = ActivityPointType.TAKE
                break

            case ActivityType.NOTED:
                if(activityInstance.article.author != activityInstance.avatar) {
                    point = 2
                    pointType = ActivityPointType.TAKE
                }
                break

            case ActivityType.SOLVED:
                if(activityInstance.article.author != activityInstance.avatar) {
                    point = 50
                    pointType = ActivityPointType.TAKE
                }
                break

        /*
            Give point
         */
            case ActivityType.ASSENTED_ARTICLE:
                if(contentInstance.author != activityInstance.avatar) {
                    point = 2
                    pointType = ActivityPointType.GIVE
                }
                break

            case ActivityType.DISSENTED_ARTICLE:
                if(contentInstance.author != activityInstance.avatar) {
                    point = -2
                    pointType = ActivityPointType.GIVE
                }
                break

            case ActivityType.ASSENTED_NOTE:
                if(contentInstance.author != activityInstance.avatar) {
                    point = 1
                    pointType = ActivityPointType.GIVE
                }
                break

            case ActivityType.DISSENTED_NOTE:
                if(contentInstance.author != activityInstance.avatar) {
                    point = -1
                    pointType = ActivityPointType.GIVE
                }
                break

            case ActivityType.SCRAPED:
                if(contentInstance.author != activityInstance.avatar) {
                    point = 5
                    pointType = ActivityPointType.GIVE
                }
                break
        }

        activityInstance.point = point
        activityInstance.pointType = pointType

        if(pointType == ActivityPointType.TAKE)
            activityInstance.avatar.updateActivityPoint(activityInstance.point)
        else if(pointType == ActivityPointType.GIVE)
            contentInstance.author.updateActivityPoint(activityInstance.point)
    }

    def unsetPoint(Activity activityInstance, Content contentInstance) {
        if(activityInstance.pointType == ActivityPointType.TAKE)
            activityInstance.avatar.updateActivityPoint(-activityInstance.point)
        else if(activityInstance.pointType == ActivityPointType.GIVE)
            contentInstance.author.updateActivityPoint(-activityInstance.point)
    }
}
