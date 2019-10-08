package net.okjsp

import grails.plugin.mail.MailService
import grails.plugin.springsecurity.SpringSecurityService
import grails.transaction.Transactional
import org.springframework.web.multipart.MultipartFile

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class CompanyController {

    SpringSecurityService springSecurityService

    MailService mailService

    def info(Company companyInstance) {
        def companyInfo = CompanyInfo.findByCompany(companyInstance)

        params.max = Math.min(Integer.parseInt(params.max ?: "10") ?: 10, 100)
        params.sort = params.sort ?: 'id'
        params.order = params.order ?: 'desc'

        def recruitQuery = Recruit.where {
            company == companyInstance
            article.enabled == true
        }

        respond companyInstance, model:[companyInfo:companyInfo, recruits: recruitQuery.list(params), recruitCount: recruitQuery.count()]
    }

    def create() {
        Person person = Person.get(springSecurityService.principal.personId)

        if(person.company)
            redirect uri: '/recruit/create'
        else
            respond new Company(params)
    }

    @Transactional
    def save(Company company) {

        Person person = Person.get(springSecurityService.principal.personId)

        if (company == null) {
            notFound()
            return
        }

        def year = Calendar.getInstance().get(Calendar.YEAR)

        MultipartFile logoFile = request.getFile("logoFile")
        MultipartFile introFile = request.getFile("introFile")

        if(!logoFile.empty) {
            def ext = logoFile.originalFilename.substring(logoFile.originalFilename.lastIndexOf('.'))
            def mil = System.currentTimeMillis()

            File directory = new File("${grailsApplication.config.grails.filePath}/logo/${year}")

            directory.mkdirs()

            logoFile.transferTo(new File(directory, "${mil}${ext}"))

            company.logo = "${year}/${mil}${ext}"
        }

        company.manager = person
        company.save()

        def companyInfo = new CompanyInfo()

        bindData(companyInfo, params, 'companyInfo')

        company.addToMembers(person)

        if (company.hasErrors()) {
            Company.withTransaction { it.setRollbackOnly() }
            respond company, model:[companyInfo: companyInfo], view:'create'
            return
        }

        if(!introFile.empty) {
            def ext = introFile.originalFilename.substring(introFile.originalFilename.lastIndexOf('.'))
            def mil = System.currentTimeMillis()

            File directory = new File("${grailsApplication.config.grails.filePath}/intro/${year}")
            directory.mkdirs()

            introFile.transferTo(new File("${grailsApplication.config.grails.filePath}/intro/${year}", "${mil}${ext}"))

            companyInfo.introFile = new AttachedFile(
                    name: "${year}/${mil}${ext}",
                    orgName: introFile.originalFilename,
                    byteSize: introFile.size,
                    mimeType: introFile.contentType,
                    type : AttachedFileType.ATTACHED).save()
        }

        companyInfo.company = company
        companyInfo.save()


        if (company.hasErrors() || companyInfo.hasErrors()) {

            Company.withTransaction { it.setRollbackOnly() }

            respond company, model:[companyInfo: companyInfo], view:'create'
            return
        }

        person.company = company
        person.save flush:true

        request.withFormat {
            form multipartForm {
                flash.tel = companyInfo.tel
                flash.email = companyInfo.email
                flash.name = company.name
                redirect uri: '/company/registered'
            }
            '*' { respond company, [status: CREATED] }
        }
    }

    def edit() {
        Person person = Person.get(springSecurityService.principal.personId)

        Company company = person.company

        CompanyInfo companyInfo = CompanyInfo.findByCompany(company)

        respond company, model: [companyInfo: companyInfo]
    }

    @Transactional
    def update() {

        def year = Calendar.getInstance().get(Calendar.YEAR)

        Person person = Person.get(springSecurityService.principal.personId)

        Company company = person.company

        if (company == null) {
            notFound()
            return
        }

        MultipartFile logoFile = request.getFile("logoFile")
        MultipartFile introFile = request.getFile("introFile")

        def prevEnabled = company.enabled

        if(!logoFile.empty) {
            def ext = logoFile.originalFilename.substring(logoFile.originalFilename.lastIndexOf('.'))
            def mil = System.currentTimeMillis()
            logoFile.transferTo(new java.io.File("${grailsApplication.config.grails.filePath}/logo", "${mil}${ext}"))

            company.logo = "${mil}${ext}"
        }
        company.registerNumber = params.registerNumber
        company.name = params.name
        company.enabled = false
        company.save()

        CompanyInfo companyInfo = CompanyInfo.findByCompany(company)

        bindData(companyInfo, params, 'companyInfo')


        if(!introFile.empty) {
            def ext = introFile.originalFilename.substring(introFile.originalFilename.lastIndexOf('.'))
            def mil = System.currentTimeMillis()

            File directory = new File("${grailsApplication.config.grails.filePath}/intro/${year}")
            directory.mkdirs()

            introFile.transferTo(new File("${grailsApplication.config.grails.filePath}/intro/${year}", "${mil}${ext}"))

            companyInfo.introFile = new AttachedFile(
                    name: "${year}/${mil}${ext}",
                    orgName: introFile.originalFilename,
                    byteSize: introFile.size,
                    mimeType: introFile.contentType,
                    type : AttachedFileType.ATTACHED).save()
        }

        companyInfo.save flush:true

        if (company.hasErrors() || companyInfo.hasErrors()) {
            respond company, model:[companyInfo: companyInfo], view:'create'
            return
        }

        if(prevEnabled) {

            mailService.sendMail {
                async true
                to "okky_jobs@ebrain.kr"
                subject "["+message(code:'email.company.enable.subject')+"] ${company.name}"
                body(view:'/email/company_request', model: [company: company, companyInfo: companyInfo, grailsApplication: grailsApplication] )
            }

        }

        request.withFormat {
            form multipartForm {
                redirect uri: '/company/updated'
            }
            '*' { respond company, [status: CREATED] }
        }
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
