package net.okjsp

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class CompanyController {

    def info(Company companyInstance) {
        def companyInfo = CompanyInfo.findByCompany(companyInstance)

        params.max = Math.min(params.max ?: 10, 100)
        params.sort = params.sort ?: 'id'
        params.order = params.order ?: 'desc'

        def recruitQuery = Recruit.where {
            company == companyInstance
            article.enabled == true
        }

        respond companyInstance, model:[companyInfo:companyInfo, recruits: recruitQuery.list(params), recruitCount: recruitQuery.count()]
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'company.label', default: 'Company'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
