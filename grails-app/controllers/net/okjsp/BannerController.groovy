package net.okjsp

import org.hibernate.type.StandardBasicTypes
import org.springframework.web.multipart.MultipartFile

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class BannerController {

    UserService userService

    static allowedMethods = [save: "POST", update: ["PUT","POST"], delete: "DELETE"]

    @Transactional
    def stats(Banner banner) {

        if (banner == null) {
            notFound()
            return
        }

        String ip = userService.getRealIp(request)

        def bannerClick = BannerClick.findOrCreateWhere(banner: banner, ip: ip)

        bannerClick.clickCount++
        bannerClick.dateString = new Date().format("yyyy-MM-dd")
        bannerClick.save(flush: true)

        redirect url: banner.url
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        params.order = params.order ?: 'desc'
        params.sort = params.sort ?: 'id'
        respond Banner.list(params), model:[bannerCount: Banner.count()]
    }

    def show(Banner banner) {
        respond banner
    }

    def create() {
        respond new Banner(params)
    }

    @Transactional
    def save(Banner banner) {
        if (banner == null) {
            notFound()
            return
        }

        if (banner.hasErrors()) {
            respond banner.errors, view:'create'
            return
        }


        MultipartFile imageFile = request.getFile("imageFile")

        if(imageFile && !imageFile.empty) {
            def ext = imageFile.originalFilename.substring(imageFile.originalFilename.lastIndexOf('.'));
            def mil = System.currentTimeMillis()
            imageFile.transferTo(new java.io.File("${grailsApplication.config.grails.filePath}/banner/", "${mil}${ext}"))

            banner.image = "${grailsApplication.config.grails.fileURL}/banner/${mil}${ext}"
        }

        banner.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'banner.label', default: 'Banner'), banner.id])
                redirect banner
            }
            '*' { respond banner, [status: CREATED] }
        }
    }

    def edit(Banner banner) {
        respond banner
    }

    @Transactional
    def update(Banner banner) {
        if (banner == null) {
            notFound()
            return
        }

        if (banner.hasErrors()) {
            respond banner.errors, view:'edit'
            return
        }

        MultipartFile imageFile = request.getFile("imageFile")

        if(imageFile && !imageFile.empty) {
            def ext = imageFile.originalFilename.substring(imageFile.originalFilename.lastIndexOf('.'));
            def mil = System.currentTimeMillis()
            imageFile.transferTo(new java.io.File("${grailsApplication.config.grails.filePath}/banner/", "${mil}${ext}"))

            banner.image = "${grailsApplication.config.grails.fileURL}/banner/${mil}${ext}"
        }

        banner.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Banner.label', default: 'Banner'), banner.id])
                redirect banner
            }
            '*'{ respond banner, [status: OK] }
        }
    }

    @Transactional
    def delete(Banner banner) {

        if (banner == null) {
            notFound()
            return
        }

        banner.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Banner.label', default: 'Banner'), banner.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    def export(Banner banner) {

        def results = BannerClick.createCriteria().list {
            projections {
                groupProperty('dateString')
                count('clickCount')
                sum('clickCount')
            }
            eq('id', banner.id)
            order('dateString', 'desc')
        }

        String text = "일자,클릭회원수,전체클릭수\n"

        results.each { e ->
            text += e.join(',') +'\n'
        }

        response.contentType = "text/csv"
        response.setHeader("Content-disposition", String.format("attachment; filename=banner_%s_%s.csv", banner.name, new Date().format("yyyy-MM-dd")))

        render (text: text, encoding: "EUC-KR")
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'banner.label', default: 'Banner'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
