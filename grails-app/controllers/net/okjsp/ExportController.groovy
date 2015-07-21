package net.okjsp

import grails.transaction.Transactional
import grails.util.Environment;
import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class ExportController {
    
    EncryptService encryptService

    def index() {

        response.contentType = "text/csv"
        response.setHeader("Content-disposition", "attachment; filename=test.csv")
        
        def persons = Person.findAllByDmAllowed(true)
        
        StringBuilder sb = new StringBuilder()
        
        // 성능은?
        for(Person p : persons) {
            
            // 한글문제 처리
            String name = p.getFullName()
            String email = p.getEmail()
            String enc = new String(encryptService.encrypt(email.getBytes()))
            
            sb.append(String.format("%s,%s,%s%s", name, email, enc, System.lineSeparator()))
        }
        
        render sb.toString()
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
