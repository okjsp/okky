package net.okjsp

import grails.transaction.Transactional
import net.okjsp.*

@Transactional
class ArticleService {

    ActivityService activityService
    NotificationService notificationService

    def grailsApplication

    /**
     * 게시물 생성 (Article, Content, Activity 생성)
     * @param article
     * @param author
     * @param category
     * @return
     */
    def save(Article article, Avatar author, Category category) {

        User user = User.findByAvatar(author)

        article.category = category
        article.author = author

        article.anonymity = category?.anonymity ?: false
        article.aNickName = article.anonymity ? generateNickname(user, article.createIp) : author.nickname

        article.content.type = ContentType.ARTICLE
        article.content.author = author

        article.content.save(failOnError: true)

        article.save(failOnError: true)

        if(article.anonymity) {
            new Anonymous(
                    user: user,
                    article: article,
                    content: article.content,
                    type: ContentType.ARTICLE
            ).save(failOnError: true)
        } else {
            activityService.createByArticle(ActivityType.POSTED, article, author)
        }

    }

    /**
     * 게시물 업데이트 (Article, Content 업데이트)
     * @param article
     * @param editor
     * @param category
     * @return
     */
    def update(Article article, Avatar editor, Category category) {

        article.anonymity = category.anonymity

        article.category = category

        article.lastEditor = editor
        article.content.lastEditor = editor

        article.content.save(failOnError: true)

        article.save(failOnError: true, flush: true, deepValidate: false)
    }

    /**
     * 게시물 삭제 (Article, Content, Activity 삭제)
     * @param article
     * @return
     */
    def delete(Article article) {

        activityService.removeAllByArticle(article)

        notificationService.removeFromArticle(article)

        Scrap.where { eq('article', article) }.deleteAll()

        Content content = article.content

        article.content = null
        article.save(flush: true)

        ContentVote.where {
            eq('article', article)
        }.deleteAll()

        content.delete()

        if(article.anonymity) {
            Anonymous.where {
                eq('article', article)
                eq('content', article.content)
            }.deleteAll()
        }

        article.delete(flush: true)
    }

    /**
     * Add Note to Article
     * @param article
     * @param content
     * @param author
     * @return
     */
    def addNote(Article article, Content content, Avatar author) {

        User user = User.findByAvatar(author)

        content.type = ContentType.NOTE
        content.author = author
        content.anonymity = article.anonymity
        content.aNickName = article.anonymity ? generateNickname(user, article.createIp) : author.nickname
        content.save(failOnError: true)

        article.noteCount++

        article.addToNotes(content)

        article.save(failOnError: true, flush: true)

        if(article.anonymity) {
            new Anonymous(
                    user: user,
                    article: article,
                    content: content,
                    type: ContentType.NOTE
            ).save(failOnError: true)
        } else {
            activityService.createByContent(ActivityType.NOTED, content, author)
        }
    }

    /**
     * Save Scrap
     * @param article
     * @param avatar
     * @return
     */
    def saveScrap(Article article, Avatar avatar) {
        activityService.createByArticle(ActivityType.SCRAPED, article, avatar)
        new Scrap(article: article, avatar: avatar).save(flush: true, failOnError: true)
    }

    /**
     * Delete Scrap
     * @param article
     * @param avatar
     * @return
     */
    def deleteScrap(Article article, Avatar avatar) {
        activityService.removeByArticle(ActivityType.SCRAPED, article, avatar)
        Scrap.findByArticleAndAvatar(article, avatar).delete(flush: true, failOnError: true)
    }

    /**
     * 추천
     * @param article
     * @param content
     * @param avatar
     * @param point
     * @return
     */
    def addVote(Article article, Content content, Avatar avatar, Integer point) {

        if(ContentVote.countByContentAndVoter(content, avatar) < 1) {
            ContentVote contentVote = new ContentVote()
            contentVote.point = point
            contentVote.article = article
            contentVote.content = content
            contentVote.voter = avatar
            contentVote.save(flush: true)

            ActivityType activityType = null

            if(content.type == ContentType.ARTICLE)
                activityType = contentVote.point > 0 ? ActivityType.ASSENTED_ARTICLE : ActivityType.DISSENTED_ARTICLE
            else if(content.type == ContentType.NOTE)
                activityType = contentVote.point > 0 ? ActivityType.ASSENTED_NOTE : ActivityType.DISSENTED_NOTE

            activityService.createByContent(activityType, content, avatar)

            // Notification 은 스케쥴로 발송
        }

        content
    }

    def cancelVote(Article article, Content content, Avatar avatar) {

        def contentVote = ContentVote.findByArticleAndContentAndVoter(article, content, avatar)

        ActivityType activityType = null

        if(content.type == ContentType.ARTICLE)
            activityType = contentVote.point > 0 ? ActivityType.ASSENTED_ARTICLE : ActivityType.DISSENTED_ARTICLE
        else if(content.type == ContentType.NOTE)
            activityType = contentVote.point > 0 ? ActivityType.ASSENTED_NOTE : ActivityType.DISSENTED_NOTE

        activityService.removeByContent(activityType, content, avatar)

        notificationService.removeFromAccent(contentVote)

        contentVote.delete(flush: true)

    }

    def generateNickname(User user, String ip) {

        String md5 = "${ip}${grailsApplication.config.grails.encrypt.key}${user.id}".encodeAsMD5()

        println md5

        int startIndex = user.id % 10

        return "A${md5.substring(startIndex, startIndex+7)}"
    }

}
