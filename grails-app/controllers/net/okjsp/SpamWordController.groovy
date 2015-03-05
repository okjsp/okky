package net.okjsp


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class SpamWordController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
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
            '*' { respond spamWord, [status: CREATED] }
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
            '*' { respond spamWord, [status: OK] }
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
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'spamWord.label', default: 'SpamWord'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
