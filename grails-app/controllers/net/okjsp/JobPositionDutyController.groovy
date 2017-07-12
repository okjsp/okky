package net.okjsp



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class JobPositionDutyController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond JobPositionDuty.list(params), model:[jobPositionDutyCount: JobPositionDuty.count()]
    }

    def show(JobPositionDuty jobPositionDuty) {
        respond jobPositionDuty
    }

    def create() {
        respond new JobPositionDuty(params)
    }

    @Transactional
    def save(JobPositionDuty jobPositionDuty) {
        if (jobPositionDuty == null) {
            notFound()
            return
        }

        if (jobPositionDuty.hasErrors()) {
            respond jobPositionDuty.errors, view:'create'
            return
        }

        jobPositionDuty.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'jobPositionDuty.label', default: 'JobPositionDuty'), jobPositionDuty.id])
                redirect jobPositionDuty
            }
            '*' { respond jobPositionDuty, [status: CREATED] }
        }
    }

    def edit(JobPositionDuty jobPositionDuty) {
        respond jobPositionDuty
    }

    @Transactional
    def update(JobPositionDuty jobPositionDuty) {
        if (jobPositionDuty == null) {
            notFound()
            return
        }

        if (jobPositionDuty.hasErrors()) {
            respond jobPositionDuty.errors, view:'edit'
            return
        }

        jobPositionDuty.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'JobPositionDuty.label', default: 'JobPositionDuty'), jobPositionDuty.id])
                redirect jobPositionDuty
            }
            '*'{ respond jobPositionDuty, [status: OK] }
        }
    }

    @Transactional
    def delete(JobPositionDuty jobPositionDuty) {

        if (jobPositionDuty == null) {
            notFound()
            return
        }

        jobPositionDuty.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'JobPositionDuty.label', default: 'JobPositionDuty'), jobPositionDuty.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'jobPositionDuty.label', default: 'JobPositionDuty'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
