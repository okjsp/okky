package net.okjsp

import com.memetix.random.RandomService
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.validation.ValidationException
import org.hibernate.FetchMode
import org.hibernate.type.StandardBasicTypes
import org.springframework.http.HttpStatus
import org.springframework.web.multipart.MultipartFile
import java.io.File

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class RecruitController {

    ArticleService articleService
    SpringSecurityService springSecurityService
    UserService userService
    RandomService randomService

    static responseFormats = ['html', 'json']

    static allowedMethods = [save: "POST", update: ["PUT","POST"], delete: ["DELETE","POST"], scrap: "POST",
                             addNote: "POST", assent: ["PUT","POST"], dissent: ["PUT","POST"]]

    def beforeInterceptor = {
        response.characterEncoding = 'UTF-8' //workaround for https://jira.grails.org/browse/GRAILS-11830
    }

    def index(Integer max) {
        redirect uri: "/articles/recruit"
    }

    def tagged(String tag, Integer max) {
        params.max = Math.min(max ?: 20, 100)
        params.sort = params.sort ?: 'id'
        params.order = params.order ?: 'desc'
        params.query = params.query?.trim()

        def category = Category.get('recruit')

        if(tag == null) {
            notFound()
            return
        }

        def articlesQuery = Article.where {
            category == category
            tagString =~ "%${tag}%"
            if(params.query && params.query != '')
                title =~ "%${params.query}%" || content.text =~ "%${params.query}%"

        }

        respond articlesQuery.list(params), model:[articlesCount: articlesQuery.count()]
    }

    def seq(Long id) {

        redirect uri:"/article/${id}"
    }

    @Transactional
    def show(Long id) {

        def contentVotes = [], scrapped

        Article article = Article.get(id)

        article.recruit = Recruit.findByArticle(article)

        if(article == null || (!article.enabled && SpringSecurityUtils.ifNotGranted("ROLE_ADMIN"))) {
            notFound()
            return
        }

        article.updateViewCount(1)

        if(springSecurityService.loggedIn) {
            Avatar avatar = Avatar.load(springSecurityService.principal.avatarId)
            contentVotes = ContentVote.findAllByArticleAndVoter(article, avatar)
            scrapped = Scrap.findByArticleAndAvatar(article, avatar)
        }

        def notes = Content.findAllByArticleAndType(article, ContentType.NOTE)

        def contentBanners = Banner.where {
            type == BannerType.CONTENT && visible == true
        }.list()

        def contentBanner = contentBanners ? randomService.draw(contentBanners) : null

        def changeLogs = ChangeLog.createCriteria().list {
            eq('article', article)
            projections {
                sqlGroupProjection 'article_id as articleId, max(date_created) as dateCreated, content_id as contentId', 'content_id', ['articleId', 'dateCreated', 'contentId'], [StandardBasicTypes.LONG, StandardBasicTypes.TIMESTAMP, StandardBasicTypes.LONG]
            }
        }

        def companyInfo = CompanyInfo.findByCompany(article.recruit.company)

        respond article, model: [contentVotes: contentVotes, notes: notes, scrapped: scrapped, contentBanner: contentBanner, changeLogs: changeLogs, companyInfo: companyInfo]
    }

    def create() {

        def category = Category.get('recruit')

        if(category == null) {
            notFound()
            return
        }

        Person person = Person.get(springSecurityService.principal.personId)

        if(!person.company) {
            redirect(url: '/company/create')
            return
        }
        if(!person.company) {
            redirect(url: '/company/create')
            return
        }

        if(!person.company.enabled) {
            redirect(url: '/company/wait')
            return
        }

        if(person.company.locked) {
            redirect(url: '/company/locked')
            return
        }

        params.category = category

        def categories
        def goExternalLink = false
        def parentCategories

        if(SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")) {
            categories = Category.findAllByWritableAndEnabled(true, true)
        } else {
            goExternalLink = category.writeByExternalLink
            categories = Category.findByParentAndWritableAndEnabled(category?.parent ?: category, true, true)
            params.anonymity = category?.anonymity ?: false
        }

        CompanyInfo companyInfo = CompanyInfo.findByCompany(person.company)

        def article = new Article(params)

        Recruit recruit = new Recruit(
                city: params.city,
                district: params.district,
                jobType: params.jobType ? JobType.valueOf(params.jobType) : null,
                workingMonth: params.workingMonth,
                startDate: params.startDate ? Date.parse("yyyy/MM", params.startDate) : null
        )

        article.recruit = recruit

        if(goExternalLink) {
            redirect(url: category.externalLink)
        } else {
            respond article, model: [categories: categories, category: category, recruit: new Recruit(params), company: person.company, companyInfo: companyInfo]
        }


    }

    @Transactional
    def save() {

        Person person = Person.get(springSecurityService.principal.personId)

        if(!person.company) {
            redirect(url: '/company/create')
            return
        }

        if(!person.company.enabled) {
            redirect(url: '/company/wait')
            return
        }

        if(person.company.locked) {
            redirect(url: '/company/locked')
            return
        }

        Article article = new Article(params)
        Recruit recruit = new Recruit()

        bindData(recruit, params, 'recruit')

        recruit.jobType = params.jobType

        def titles = params.list("recruit.jobPositions.title")
        def minCareers = params.list("recruit.jobPositions.minCareer")
        def maxCareers = params.list("recruit.jobPositions.maxCareer")
        def minPay = params.list("recruit.jobPositions.minPay")
        def maxPay = params.list("recruit.jobPositions.maxPay") ?: []
        def tagStrings = params.list("recruit.jobPositions.tagString")
        def descriptions = params.list("recruit.jobPositions.description")
        def jobGroups = params.list("recruit.jobPositions.group")
        def jobDuties = params.list("recruit.jobPositions.duty")

        titles.eachWithIndex { title, i ->

            if(recruit.jobType == JobType.CONTRACT) {
                maxPay << minPay[i]  + 10
            }

            recruit.addToJobPositions(new JobPosition(
                    title: title,
                    recruit: recruit,
                    minCareer: minCareers[i],
                    maxCareer: maxCareers[i],
                    minPay: minPay[i],
                    maxPay: maxPay[i],
                    tagString: tagStrings[i],
                    group: jobGroups[i],
                    duty: jobDuties[i],
                    description: descriptions[i]
            ))

        }


        def category = Category.get('recruit')

        try {

            withForm {
                Avatar author = Avatar.load(springSecurityService.principal.avatarId)

                if(SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")) {

                    article.choice = params.choice?:false

                    article.enabled = !params.disabled

                }

                article.isRecruit = true
                article.createIp = userService.getRealIp(request)

                articleService.save(article, author, category)

                recruit.article = article
                recruit.company = person.company

                recruit.save(flush: true, failOnError: true)

                withFormat {
                    html {
                        flash.message = message(code: 'default.created.message', args: [message(code: 'article.label', default: 'Article'), article.id])
                        redirect uri: "/recruits", method:"GET"
                    }
                    json { respond article, [status: CREATED] }
                }
            }.invalidToken {
                redirect uri: "/recruits", method:"GET"
            }

        } catch (ValidationException e) {

            def categories = category.children ?: category.parent?.children ?: [category]

            println recruit.errors

            CompanyInfo companyInfo = CompanyInfo.findByCompany(person.company)

            respond article.errors, view: 'create', model: [categories: categories, category: category, recruit: recruit, company: person.company, companyInfo: companyInfo]
        }
    }

    def edit(Long id) {

        Article article = Article.get(id)

        Recruit recruit = Recruit.findByArticle(article)

        if(article == null) {
            notFound()
            return
        }

        if(SpringSecurityUtils.ifNotGranted("ROLE_ADMIN")) {
            if (article.authorId != springSecurityService.principal.avatarId) {
                notAcceptable()
                return
            }
        }

        def categories

        if(SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")) {
            categories = Category.findAllByWritableAndEnabled(true, true)
        } else {
            categories = article.category.children ?: article.category.parent?.children ?: [article.category]
        }

        if(params.categoryCode) {
            article.category = Category.get(params.categoryCode)
        }

        respond article, model: [categories: categories, recruit: recruit]
    }

    @Transactional
    def update(Article article) {

        if(SpringSecurityUtils.ifNotGranted("ROLE_ADMIN")) {
            if (article.authorId != springSecurityService.principal.avatarId) {
                notAcceptable()
                return
            }
        }

        Recruit recruit = Recruit.findByArticle(article)

        bindData(recruit, params, 'recruit')

        recruit.jobPositions.collect().each {
            recruit.removeFromJobPositions(it)
        }


        def titles = params.list("recruit.jobPositions.title")
        def minCareers = params.list("recruit.jobPositions.minCareer")
        def maxCareers = params.list("recruit.jobPositions.maxCareer")
        def minPay = params.list("recruit.jobPositions.minPay")
        def maxPay = params.list("recruit.jobPositions.maxPay") ?: []
        def tagStrings = params.list("recruit.jobPositions.tagString")
        def descriptions = params.list("recruit.jobPositions.description")
        def jobGroups = params.list("recruit.jobPositions.group")
        def jobDuties = params.list("recruit.jobPositions.duty")

        titles.eachWithIndex { title, i ->

            if(recruit.jobType == JobType.CONTRACT) {
                maxPay << minPay[i]  + 10
            }

            recruit.addToJobPositions(new JobPosition(
                    title: title,
                    recruit: recruit,
                    minCareer: minCareers[i],
                    maxCareer: maxCareers[i],
                    minPay: minPay[i],
                    maxPay: maxPay[i],
                    tagString: tagStrings[i],
                    group: jobGroups[i],
                    duty: jobDuties[i],
                    description: descriptions[i]
            ))

        }

        Category category = Category.get("recruit")

        try {

            withForm {

                Avatar editor = Avatar.get(springSecurityService.principal.avatarId)

                if(SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")) {

                    article.choice = params.choice?:false

                    article.enabled = !params.disabled

                }

                recruit.save(failOnError: true, flush: true)

                articleService.update(article, editor, category)

                withFormat {
                    html {
                        flash.message = message(code: 'default.updated.message', args: [message(code: 'Article.label', default: 'Article'), article.id])
                        redirect article
                    }
                    json { respond article, [status: OK] }
                }

            }.invalidToken {
                redirect article
            }

        } catch (ValidationException e) {

            def categories = category.children ?: category.parent?.children ?: [category]

            println recruit.errors

            respond article.errors, view: 'edit', model: [categories: categories, category: category, recruit: recruit, company: person.company]
        }
    }

    @Transactional
    def delete(Long id) {

        Article article = Article.get(id)

        def categoryCode = article.category.code

        if (article == null) {
            notFound()
            return
        }

        if(SpringSecurityUtils.ifNotGranted("ROLE_ADMIN")) {
            if (article.authorId != springSecurityService.principal.avatarId) {
                notAcceptable()
                return
            }
        }

        Recruit recruit = Recruit.findByArticle(article)

        recruit.jobPositions.clear()

        recruit.delete()

        articleService.delete(article)

        withFormat {
            html {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Article.label', default: 'Article'), article.id])
                flash.status = "success"
                redirect uri: "/articles/${categoryCode}", method:"GET"
            }
            json { render status: NO_CONTENT }
        }
    }

    @Transactional
    def scrap(Long id) {

        Article article = Article.get(id)

        if (article == null) {
            notFound()
            return
        }

        try {

            Avatar avatar = Avatar.get(springSecurityService.principal.avatarId)

            if(Scrap.countByArticleAndAvatar(article, avatar) < 1) {
                articleService.saveScrap(article, avatar)
            } else {
                articleService.deleteScrap(article, avatar)
            }

            withFormat {
                html { redirect article }
                json {
                    article.refresh()
                    def result = [scrapCount: article.scrapCount]
                    respond result
                }
            }

        } catch (ValidationException e) {
            flash.error = e.message
            redirect article
        }

    }


    @Transactional
    def addNote(Long id) {

        Recruit recruit = Recruit.get(id)

        Article article = recruit.article

        try {

            Avatar avatar = Avatar.get(springSecurityService.principal.avatarId)

            Content content = new Content()
            bindData(content, params, 'note')

            content.createIp = userService.getRealIp(request)

            articleService.addNote(article, content, avatar)

            withFormat {
                html {
                    flash.message = message(code: 'default.created.message', args: [message(code: 'Note.label', default: 'Note'), article.id])
                    redirect article
                }
                json {
                    respond article, [status: OK]
                }
            }

        } catch (ValidationException e) {
            flash.error = e.message
            redirect article
        }

    }

    @Transactional
    def assent(Long id, Long contentId) {

        Article article = Article.get(id)

        Avatar avatar = Avatar.get(springSecurityService.principal.avatarId)
        Content content = Content.get(contentId)

        articleService.addVote(article, content, avatar, 1)

        withFormat {
            html { redirect article }
            json {
                content.refresh()
                def result = [voteCount: content.voteCount]
                respond result
            }
        }
    }

    @Transactional
    def dissent(Long id, Long contentId) {

        Article article = Article.get(id)

        Avatar avatar = Avatar.get(springSecurityService.principal.avatarId)
        Content content = Content.get(contentId)

        articleService.addVote(article, content, avatar, -1)

        withFormat {
            html { redirect article }
            json {
                content.refresh()
                def result = [voteCount: content.voteCount]
                respond result
            }
        }
    }

    @Transactional
    def unvote(Long id, Long contentId) {

        Article article = Article.get(id)

        Content content = Content.get(contentId)
        Avatar avatar = Avatar.get(springSecurityService.principal.avatarId)

        articleService.cancelVote(article, content, avatar)

        withFormat {
            html { redirect article }
            json {
                content.refresh()
                def result = [voteCount: content.voteCount]
                respond result
            }
        }

    }

    @Transactional
    def selectNote(Long id, Long contentId) {

        Article article = Article.get(id)

        if(article.authorId != springSecurityService.principal.avatarId) {
            notAcceptable()
            return
        }

        Content content = Content.get(contentId)

        if(article.selectedNote == null) {

            content.selected = true
            content.save()

            article.selectedNote = content
            article.save(flush: true)
        }

        withFormat {
            html { redirect article }
            json { respond article, [status: OK] }
        }
    }

    @Transactional
    def deselectNote(Long id) {

        Article article = Article.get(id)

        if(article.authorId != springSecurityService.principal.avatarId) {
            notAcceptable()
            return
        }

        if(article.selectedNote != null) {
            article.selectedNote.selected = false
            article.selectedNote.save()

            article.selectedNote = null
            article.save(flush: true)
        }

        withFormat {
            html { redirect article }
            json { respond article, [status: OK] }
        }
    }

    def changes(Long id) {

        Content content = Content.get(id)

        Article article = content.article

        def changeLogs = ChangeLog.where{
            eq('article', article)
            eq('content', content)
        }.list(sort: 'id', order: 'desc')


        def lastTexts = [:]

        changeLogs.each { ChangeLog log ->

            if(!lastTexts[log.type]) {
                if(log.type == ChangeLogType.TITLE) {
                    lastTexts[log.type] = article.title
                } else if(log.type == ChangeLogType.CONTENT) {
                    lastTexts[log.type] = content.text
                } else if(log.type == ChangeLogType.TAGS) {
                    lastTexts[log.type] = article.tagString
                }
            }

            def dmp = new diff_match_patch()

            LinkedList<diff_match_patch.Patch> patches = dmp.patch_fromText(log.patch)

            log.text = dmp.patch_apply(patches, lastTexts[log.type] as String)[0]

            lastTexts[log.type] = log.text

        }

        respond article, model: [content: content, changeLogs: changeLogs]
    }

    protected void notFound() {

        withFormat {
            html {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'article.label', default: 'Article'), params.id])
                redirect uri: '/'
            }
            json { render status: NOT_FOUND }
        }
    }

    protected void notAcceptable() {

        withFormat {
            html {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'article.label', default: 'Article'), params.id])
                redirect uri: '/'
            }
            json { render status: NOT_ACCEPTABLE }
        }
    }
}
