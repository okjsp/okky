package net.okjsp

import com.megatome.grails.RecaptchaService
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class UserController {

    UserService userService
    RecaptchaService recaptchaService
    SpringSecurityService springSecurityService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer id, Integer max) {
        params.max = Math.min(max ?: 20, 100)
        params.sort = params.sort ?: 'id'
        params.order = params.order ?: 'desc'

        Avatar avatar = Avatar.get(id)
        User user = User.findByAvatar(avatar)

        def activitiesQuery = Activity.where {
            avatar == avatar
        }

        def counts = [
            postedCount: Activity.countByAvatarAndType(avatar, ActivityType.POSTED),
            solvedCount: Activity.countByAvatarAndType(avatar, ActivityType.SOLVED),
            followerCount : Follow.countByFollowing(avatar),
            followingCount : Follow.countByFollower(avatar),
            scrappedCount : Scrap.countByAvatar(avatar)
        ]

        respond user, model: [avatar: avatar, activities: activitiesQuery.list(params), activitiesCount: activitiesQuery.count(), counts: counts]
    }

    def register() {
        recaptchaService.cleanUp session
        respond new User(params)
    }

    @Transactional
    def save(User user) {

        try {

            def reCaptchaVerified = recaptchaService.verifyAnswer(session, request.getRemoteAddr(), params)

            if(!reCaptchaVerified) {
                respond user.errors, view: 'register'
                return
            }

            user.createIp = request.remoteAddr

            userService.saveUser user

            recaptchaService.cleanUp session

            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), user.id])
                    redirect uri: '/login/auth'
                }
                '*' { respond user, [status: CREATED] }
            }

        } catch (ValidationException e) {
            respond user.errors, view: 'register'
        }
    }

    def edit() {
        User user = springSecurityService.currentUser
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
                redirect action: 'edit'
            }
            '*'{ respond user, [status: OK] }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
                redirect view: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
