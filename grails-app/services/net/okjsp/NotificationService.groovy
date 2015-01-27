package net.okjsp

import grails.transaction.Transactional

@Transactional
class NotificationService {

    def createFromNote(Content content) {

        if(content.article.author != content.author) {

            Notification notification =
                Notification.findOrCreateWhere(type: ActivityType.NOTED, article: content.article,
                    sender: content.author, receiver: content.article.author)

            notification.content = content

            notification.save(failOnError: true)

            println "Note Notification : ${notification}"

        }

        // 이전 댓글 작성자 3명 에게 노티 보냄
        def latestNotes = Content.where {
            and {
                eq('article', content.article)
                eq('type', ContentType.NOTE)
                lt('id', content.id)
            }
        }.list(sort: 'id', order:'desc', max: 3)

        def uniqAuthors = latestNotes.collect { it.author }.unique()

        println uniqAuthors

        uniqAuthors.each { noteAuthor ->
            println "Note Authors ${noteAuthor} / ${content.author} / ${content.article.author} : ${noteAuthor != content.author}"
            if(noteAuthor != content.article.author &&
                noteAuthor != content.author) {

                Notification notificationAfter =
                    Notification.findOrCreateWhere(type: ActivityType.NOTED, article: content.article,
                        sender: content.author, receiver: noteAuthor)

                notificationAfter.content = content

                notificationAfter.save(flush: true)

                println "Note Notification After : ${notificationAfter}"

            }
        }
    }

    def createFromAccent(ContentVote contentVote) {

        if(contentVote.content.author == contentVote.voter) return

        ActivityType activityType
        Notification notification

        if(contentVote.content.type == ContentType.ARTICLE) activityType = ActivityType.ASSENTED_ARTICLE
        if(contentVote.content.type == ContentType.NOTE) activityType = ActivityType.ASSENTED_NOTE

        if(activityType) {
            notification = new Notification(type: activityType, article: contentVote.article, content: contentVote.content,
                sender: contentVote.voter,receiver: contentVote.content.author)

            notification.save(failOnError: true)
        }
    }

    def removeFromArticle(Article article) {

        def query = Notification.where {
            eq('article', article)
        }

        query.deleteAll()
    }

    def removeFromNote(Content content) {

        def query = Notification.where {
            eq('content', content)
        }

        query.deleteAll()
    }

    def removeFromAccent(ContentVote contentVote) {

        if(contentVote.content.author == contentVote.voter) return

        ActivityType activityType

        if(contentVote.content.type == ContentType.ARTICLE) activityType = ActivityType.ASSENTED_ARTICLE
        if(contentVote.content.type == ContentType.NOTE) activityType = ActivityType.ASSENTED_NOTE

        def query = Notification.where {
            and {
                eq('type', activityType)
                eq('sender', contentVote.voter)
                eq('content', contentVote.content)
            }
        }

        query.deleteAll()
    }

    def count(Avatar avatar) {

        NotificationRead notificationRead = NotificationRead.findOrSaveByAvatar(avatar)

        def query = Notification.where {
            and {
                eq('receiver', avatar)
                gt('lastUpdated', notificationRead.lastRead)
            }
        }

        query.count()
    }

    def read(Avatar avatar) {

        NotificationRead notificationRead = NotificationRead.findOrCreateByAvatar(avatar)

        notificationRead.lastRead = new Date()

        notificationRead.save(flush: true, failOnError: true)

    }
}
