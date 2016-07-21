package net.okjsp

import grails.transaction.Transactional
import net.okjsp.SpamWord
import org.springframework.http.HttpStatus

@Transactional(readOnly = true)
class SpamWordController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.sort = params.sort ?: 'id'
        params.order = params.order ?: 'desc'
        params.max = max ?: 100
        respond SpamWord.list(params), model: [spamWordCount: SpamWord.count()]
    }

    def show(SpamWord spamWord) {
        respond spamWord
    }

    def create() {
        respond new SpamWord(params)
    }

    @Transactional
    def save(SpamWord spamWord) {
        if (spamWord == null) {
            notFound()
            return
        }

        if (spamWord.hasErrors()) {
            respond spamWord.errors, view: 'create'
            return
        }

        spamWord.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'spamWord.label', default: 'SpamWord'), spamWord.id])
                redirect spamWord
            }
            '*' { respond spamWord, [status: HttpStatus.CREATED] }
        }
    }

    def edit(SpamWord spamWord) {
        respond spamWord
    }

    @Transactional
    def update(SpamWord spamWord) {
        if (spamWord == null) {
            notFound()
            return
        }

        if (spamWord.hasErrors()) {
            respond spamWord.errors, view: 'edit'
            return
        }

        spamWord.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'SpamWord.label', default: 'SpamWord'), spamWord.id])
                redirect spamWord
            }
            '*' { respond spamWord, [status: HttpStatus.OK] }
        }
    }

    @Transactional
    def delete(SpamWord spamWord) {

        if (spamWord == null) {
            notFound()
            return
        }

        spamWord.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'SpamWord.label', default: 'SpamWord'), spamWord.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: HttpStatus.NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'spamWord.label', default: 'SpamWord'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: HttpStatus.NOT_FOUND }
        }
    }
}
