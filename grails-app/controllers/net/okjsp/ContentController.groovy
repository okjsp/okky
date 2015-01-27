package net.okjsp

import grails.plugin.springsecurity.SpringSecurityService
import grails.validation.ValidationException

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ContentController {

    SpringSecurityService springSecurityService
    ActivityService activityService
    NotificationService notificationService

    static allowedMethods = [update: ["POST","PUT"], delete: ["POST","DELETE"]]

    def show(Long id) {
        Content content = Content.get(id)
        respond content
    }

    def edit(Long id) {

        Content content = Content.get(id)

        if(content.authorId != springSecurityService.principal.avatarId) {
            notAcceptable()
            return
        }

        respond content
    }

    @Transactional
    def update(Content content) {

        if(content.authorId != springSecurityService.principal.avatarId) {
            notAcceptable()
            return
        }

        if (content == null) {
            notFound()
            return
        }

        if (content.hasErrors()) {
            respond content.errors, view: 'edit'
            return
        }

        content.lastEditor = Avatar.get(springSecurityService.principal.avatarId)
        content.save(flush: true)

        withFormat {
            html {
                println "form"
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Content.label', default: 'Content'), content.id])
                redirect content.article
            }
            json { respond content, [status: OK] }
        }
    }

    @Transactional
    def delete(Long id) {

        Content content = Content.get(id)

        if(content.authorId != springSecurityService.principal.avatarId) {
            notAcceptable()
            return
        }

        if (content == null) {
            notFound()
            return
        }

        Article article = content.article

        activityService.removeAllByContent(content)

        notificationService.removeFromNote(content)

        article.removeFromNotes(content)
        article.updateNoteCount(-1)

        content.delete flush: true

        withFormat {
            html {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Content.label', default: 'Content'), content.id])
                redirect article
            }
            json { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        withFormat {
            html {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'content.label', default: 'Content'), params.id])
                redirect action: "index", method: "GET"
            }
            json { render status: NOT_FOUND }
        }
    }

    protected void notAcceptable() {

        withFormat {
            html {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'article.label', default: 'Article'), params.id])
                redirect action: "index", method: "GET"
            }
            json { render status: NOT_ACCEPTABLE }
        }
    }
}
