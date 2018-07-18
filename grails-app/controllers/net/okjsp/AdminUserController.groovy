package net.okjsp



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class AdminUserController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        params.order = params.order ?: 'desc'
        params.sort = params.sort ?: 'id'

        def userList, userCount

        if(params.where) {
            def users = User.where {
                username =~ "${params.where}" ||
                        person.fullName =~ "${params.where}"  ||
                        person.email =~ "${params.where}" ||
                        avatar.nickname =~ "${params.where}"
            }

            userList = users.list(params)
            userCount = users.count()

        } else {
            userList = User.list(params)
            userCount = User.count()
        }

        respond userList, model:[userCount: userCount]
    }

    def show(User userInstance) {

        def loggedIns = LoggedIn.where {
            user == userInstance
        }.list(max: 100, order: 'desc', sort: 'id')

        respond userInstance, model: [loggedIns: loggedIns]
    }

    def create() {
        respond new User(params)
    }

    @Transactional
    def save(User user) {
        if (user == null) {
            notFound()
            return
        }

        if (user.hasErrors()) {
            respond user.errors, view:'create'
            return
        }

        user.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), user.id])
                redirect user
            }
            '*' { respond user, [status: CREATED] }
        }
    }

    def edit(User user) {
        respond user
    }

    @Transactional
    def update(User user) {
        if (user == null) {
            notFound()
            return
        }

        if (user.hasErrors()) {
            respond user.errors, view:'edit'
            return
        }

        user.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'User.label', default: 'User'), user.id])
                redirect user
            }
            '*'{ respond user, [status: OK] }
        }
    }

    @Transactional
    def delete(User user) {

        if (user == null) {
            notFound()
            return
        }

        user.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'User.label', default: 'User'), user.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    @Transactional
    def disable(User user) {

        if (user == null) {
            notFound()
            return
        }

        user.accountLocked = true
        user.enabled = false
        user.accountExpired = true

        Article.executeUpdate("update Article set enabled = false where author = :author", [author : user.avatar])

        Content.executeUpdate("update Content set enabled = false where author = :author", [author : user.avatar])

        user.save(flush: true)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'User.label', default: 'User'), user.id])
                redirect user
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    @Transactional
    def enable(User user) {

        if (user == null) {
            notFound()
            return
        }

        user.accountLocked = false
        user.enabled = true
        user.accountExpired = false

        Article.executeUpdate("update Article set enabled = true where author = :author", [author : user.avatar])

        Content.executeUpdate("update Content set enabled = true where author = :author", [author : user.avatar])

        user.save(flush: true)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'User.label', default: 'User'), user.id])
                redirect user
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
