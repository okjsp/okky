package net.okjsp

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import net.okjsp.ConfirmEmail
import net.okjsp.Person
import net.okjsp.User
import org.springframework.mail.MailException
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage

@Secured("permitAll")
class FindUserController {


    UserService userService
    SpringSecurityService springSecurityService
    MailSender mailSender
    SimpleMailMessage templateMessage

    def beforeInterceptor = [action:this.&notLoggedIn]

    private notLoggedIn() {
        if(springSecurityService.loggedIn) {
            redirect uri: '/'
            return false
        }
    }

    def index() {
        render view: 'index'
    }

    def send(String email) {

        if(!email || email.isEmpty()) {
            flash.message = message(code: 'default.blank.message', args: [message(code: 'person.email.label', default: 'email')])
            redirect action: 'index'
            return
        }

        def persons = Person.findAllByEmail(email)

        if(!persons) {
            flash.message = message(code: 'email.not.found.message')
            redirect action: 'index'
            return
        }

        if(persons.size() > 1) {
            flash.message = message(code: 'email.duplicate.found.message')
            redirect action: 'index'
            return
        }

        def person = persons[0]

        def user = User.findByPerson(person)

        def key = userService.createConfirmEmail(user)

        mailService.sendMail {
            async true
            to user.person.email
            subject message(code:'email.find.subject')
            body(view:'/email/find', model: [user: user, key: key, grailsApplication: grailsApplication] )
        }

        session['confirmSecuredKey'] = key

        redirect action: 'complete'
    }

    def complete() {

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
}
