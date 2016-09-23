package net.okjsp

import grails.transaction.Transactional
import grails.util.Environment
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class DmController {
    
    def userService
    static allowedMethods = [updateReject: ["PUT","POST"]]

    def export() {

        response.contentType = "text/csv"
        response.setHeader("Content-disposition", String.format("attachment; filename=dm_%s.csv", new Date().format("yyyy-MM-dd")))
        
        render (text: userService.getDmUserCvsString(), encoding: "EUC-KR")
    }

    def reject() {


        def rejectCount = Person.where { dmAllowed == false }.count()

        render view: "reject", model: ['rejectCount' : rejectCount]
    }

    def updateReject() {

        MultipartFile rejectFile = request.getFile("rejectFile")

        def rejectList = []

        if(!rejectFile.empty) {
            def lines = rejectFile.inputStream.readLines()

            for(def email : lines) {
                if(email && !email.empty) {
                    rejectList << email
                    println "rejected : ${email}"
                }
            }

            println "total rejected : ${rejectList.size()}"


            Person.executeUpdate("update Person set dmAllowed = false where email in :rejectList", [rejectList: rejectList])
        }

        redirect action: 'reject'
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
