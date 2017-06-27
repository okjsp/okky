package net.okjsp



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class JobPositionGroupController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond JobPositionGroup.list(params), model:[jobPositionGroupCount: JobPositionGroup.count()]
    }

    def show(JobPositionGroup jobPositionGroup) {
        respond jobPositionGroup
    }

    def create() {
        respond new JobPositionGroup(params)
    }

    @Transactional
    def save(JobPositionGroup jobPositionGroup) {
        if (jobPositionGroup == null) {
            notFound()
            return
        }

        if (jobPositionGroup.hasErrors()) {
            respond jobPositionGroup.errors, view:'create'
            return
        }

        jobPositionGroup.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'jobPositionGroup.label', default: 'JobPositionGroup'), jobPositionGroup.id])
                redirect jobPositionGroup
            }
            '*' { respond jobPositionGroup, [status: CREATED] }
        }
    }

    def edit(JobPositionGroup jobPositionGroup) {
        respond jobPositionGroup
    }

    @Transactional
    def update(JobPositionGroup jobPositionGroup) {
        if (jobPositionGroup == null) {
            notFound()
            return
        }

        if (jobPositionGroup.hasErrors()) {
            respond jobPositionGroup.errors, view:'edit'
            return
        }

        jobPositionGroup.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'JobPositionGroup.label', default: 'JobPositionGroup'), jobPositionGroup.id])
                redirect jobPositionGroup
            }
            '*'{ respond jobPositionGroup, [status: OK] }
        }
    }

    @Transactional
    def delete(JobPositionGroup jobPositionGroup) {

        if (jobPositionGroup == null) {
            notFound()
            return
        }

        jobPositionGroup.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'JobPositionGroup.label', default: 'JobPositionGroup'), jobPositionGroup.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'jobPositionGroup.label', default: 'JobPositionGroup'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
