package net.okjsp

import com.megatome.grails.RecaptchaService
import grails.plugin.mail.MailService
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.annotation.Secured
import grails.util.Environment
import grails.validation.ValidationException

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class UserController {

    UserService userService
    RecaptchaService recaptchaService
    SpringSecurityService springSecurityService
    MailService mailService
    EncryptService encryptService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def beforeInterceptor = [action:this.&notLoggedIn,
                             except: ['edit', 'update', 'index', 'rejectDM', 'withdrawConfirm', 'withdraw', 'passwordChange', 'updatePasswordChange']]

    private notLoggedIn() {
        if(springSecurityService.loggedIn) {
            redirect uri: '/'
            return false
        }
    }

    def index(Integer id, Integer max) {
        params.max = Math.min(max ?: 20, 100)
        params.sort = params.sort ?: 'id'
        params.order = params.order ?: 'desc'

        Avatar currentAvatar = Avatar.get(id)
        User user = User.findByAvatar(currentAvatar)

        if(user.withdraw) {
            redirect uri: '/'
            return
        }

        def activitiesQuery

        if(params.category == 'activity' || (!params.category && !currentAvatar.official)) {
            activitiesQuery= Activity.where {
                avatar == currentAvatar
            }
        } else {
            def category

            if(params.category == 'solved') category = ActivityType.SOLVED
            else if(params.category == 'scrapped') category = ActivityType.SCRAPED
            else {
                params.category = 'articles'
                category = ActivityType.POSTED
            }

            activitiesQuery= Activity.where {
                avatar == currentAvatar
                type == category
            }
        }

        def counts = [
            postedCount: Activity.countByAvatarAndType(currentAvatar, ActivityType.POSTED),
            solvedCount: Activity.countByAvatarAndType(currentAvatar, ActivityType.SOLVED),
            followerCount : Follow.countByFollowing(currentAvatar),
            followingCount : Follow.countByFollower(currentAvatar),
            scrappedCount : Scrap.countByAvatar(currentAvatar)
        ]

        respond user, model: [avatar: currentAvatar, activities: activitiesQuery.list(params), activitiesCount: activitiesQuery.count(), counts: counts]
    }

    def register() {
        recaptchaService.cleanUp session
        respond new User(params)
    }

    @Transactional
    def save(User user) {

        try {

            def realIp = userService.getRealIp(request)

            def reCaptchaVerified = recaptchaService.verifyAnswer(session, realIp, params)

            if(!reCaptchaVerified) {
                respond user.errors, view: 'register'
                return
            }

            user.createIp = realIp

            userService.saveUser user

            recaptchaService.cleanUp session

            def key = userService.createConfirmEmail(user)

            mailService.sendMail {
                async true
                to user.person.email
                subject message(code:'email.join.subject')
                body(view:'/email/join_confirm', model: [user: user, key: key, grailsApplication: grailsApplication] )
            }

            session['confirmSecuredKey'] = key

            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), user.id])
                    redirect action: 'complete'
                }
                '*' { respond user, [status: CREATED] }
            }

        } catch (ValidationException e) {
            respond user.errors, view: 'register'
        }
    }

    def complete() {

        if(springSecurityService.isLoggedIn()) {
            redirect uri: "/"
            return
        }

        def confirmEmail = ConfirmEmail.where {
            securedKey == session['confirmSecuredKey'] &&
                dateExpired > new Date()
        }.get()

        if(!confirmEmail) {
            flash.message = message(code: 'default.expired.link.message')
            redirect uri: '/login/auth'
            return
        }

        render view: 'complete', model: [email: confirmEmail.email]
    }

    @Transactional
    def confirm(String key) {

        if(springSecurityService.isLoggedIn()) {
            redirect uri: "/"
            return
        }

        session.invalidate()

        def confirmEmail = ConfirmEmail.where {
            securedKey == key &&
                dateExpired > new Date()
        }.get()

        if(!confirmEmail) {
            flash.message = message(code: 'default.expired.link.message')
            redirect uri: '/login/auth'
            return
        }

        User user = confirmEmail.user

        user.person.email = confirmEmail.email
        user.person.save()

        user.enabled = true
        user.save()

        confirmEmail.delete(flush: true)

        render view: 'confirm'
    }

    def edit() {
        User user = springSecurityService.currentUser

        Company company = Company.findByManager(user.person)

        respond user, model: [company: company]
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
        
        try {

            userService.updateUser user

            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.updated.message', args: [message(code: 'User.label', default: 'User'), user.id])
                    redirect action: 'edit'
                }
                '*'{ respond user, [status: OK] }
            }

        } catch (ValidationException e) {
            user.oAuthIDs = OAuthID.findAllByUser(user)
            respond user.errors, view: 'edit'
        }
    }

    def info(String token) {
        String [] decoded = new String(token.decodeBase64()).split(":")

        String username = decoded[0]
        String hashed = decoded[1]


    }

    def password(String key) {

        if(springSecurityService.isLoggedIn()) {
            redirect uri: "/"
            return
        }

        def confirmEmail = ConfirmEmail.where {
            securedKey == key &&
                dateExpired > new Date()
        }.get()

        if(!confirmEmail) {
            flash.message = message(code: 'default.expired.link.message')
            redirect uri: '/login/auth'
            return
        }

        render view: 'password', model: [key: key]

    }
    
    @Transactional
    def updatePassword(String password, String passwordConfirm, String key) {

        if(springSecurityService.isLoggedIn()) {
            redirect uri: "/"
            return
        }

        def confirmEmail = ConfirmEmail.where {
            securedKey == key &&
                dateExpired > new Date()
        }.get()

        if(!confirmEmail) {
            flash.message = message(code: 'default.expired.link.message')
            redirect uri: '/login/auth'
            return
        }

        if(password != passwordConfirm) {
            flash.message = message(code: 'user.password.not.equal.message')
            render view: 'password', model: [key: key]
            return
        }

        def user = confirmEmail.user

        user.password = password
        user.enabled = true
        user.save()

        if(user.hasErrors()) {
            flash.message = message(code: 'user.password.matches.error', args: [message(code: 'user.password.label')])
            render view: 'password', model: [key: key]
            return
        }

        confirmEmail.delete(flush: true)

        flash.message = message(code: 'user.password.updated.message')
        redirect uri: '/login/auth'
    }
    
    @Transactional
    def rejectDM(String k) {
        
        String email = "", result = "실패"
        
        try {
            email = new String(encryptService.decrypt(k.getBytes()))
        } catch(Exception e) {
            e.printStackTrace()
        }
        
        Person p = Person.findByEmail(email)
        if(p != null) {
            p.setDmAllowed(false)
            p.save()
            result = "성공"
        }
        
        render "수신거부에 ${result}하였습니다."
    }

    def withdrawConfirm() {
        render view: "withdrawConfirm"
    }

    @Transactional
    def withdraw() {
        User user = springSecurityService.currentUser

        userService.withdraw(user)

        session.invalidate()

        redirect controller: 'user', action: 'withdrawComplete'

    }

    def withdrawComplete() {
        render view: "withdrawComplete"
    }

    def passwordChange() {
        render view: "passwordChange"
    }

    @Transactional
    def updatePasswordChange(String oldPassword, String password, String passwordConfirm, String key) {

        User user = springSecurityService.currentUser

        if(user.password != springSecurityService.encodePassword(oldPassword)) {
            flash.message = message(code: 'user.oldPassword.not.equal.message')
            render view: 'passwordChange'
            return
        }

        if(password != passwordConfirm) {
            flash.message = message(code: 'user.password.not.equal.message')
            render view: 'passwordChange', model: [key: key]
            return
        }

        user.password = password
        user.save()

        if(user.hasErrors()) {
            flash.message = message(code: 'user.password.matches.error', args: [message(code: 'user.password.label')])
            render view: 'passwordChange', model: [key: key]
            return
        }

        redirect controller: 'user', action: 'edit'
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
                redirect uri: '/'
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
