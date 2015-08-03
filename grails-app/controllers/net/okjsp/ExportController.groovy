package net.okjsp

import grails.transaction.Transactional
import grails.util.Environment;
import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class ExportController {
    
    def userService

    def index() {

        response.contentType = "text/csv"
        response.setHeader("Content-disposition", String.format("attachment; filename=dm_%s.csv", new Date().format("yyyy-MM-dd")))
        
        render (text: userService.getDmUserCvsString(), encoding: "EUC-KR")
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
