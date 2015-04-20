package net.okjsp

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class StatisticController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {

        String diffDate = (new Date() - 30).clearTime().format('yyyy-MM-dd')

        println diffDate

        def userCounts = User.createCriteria().list {
            property('dateJoined')
            projections {
                count('id')
                groupProperty('dateJoined')
            }
            ge('dateJoined', diffDate)
            order('dateJoined', 'desc')
        }

        def totalCount = User.count()

        [userCounts : userCounts, totalCount: totalCount]
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
