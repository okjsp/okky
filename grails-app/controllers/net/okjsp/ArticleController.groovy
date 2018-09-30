package net.okjsp

import com.megatome.grails.RecaptchaService
import com.memetix.random.RandomService
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.validation.ValidationException
import org.hibernate.FetchMode
import org.hibernate.type.StandardBasicTypes
import org.springframework.http.HttpStatus

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ArticleController {

    ArticleService articleService
    SpringSecurityService springSecurityService
    UserService userService
    RandomService randomService
    RecaptchaService recaptchaService

    static responseFormats = ['html', 'json']

    static allowedMethods = [save: "POST", update: ["PUT","POST"], delete: ["DELETE","POST"], scrap: "POST",
                             addNote: "POST", assent: ["PUT","POST"], dissent: ["PUT","POST"]]
    
    def beforeInterceptor = {
        response.characterEncoding = 'UTF-8' //workaround for https://jira.grails.org/browse/GRAILS-11830
    }

    def index(String code, Integer max) {
        params.max = Math.min(max ?: 20, 100)
        params.sort = params.sort ?: 'id'
        params.order = params.order ?: 'desc'
        params.query = params.query?.trim()

        def category = Category.get(code)

        if(category == null) {
            notFound()
            return
        }

        def choiceJobs

        if(category.code == 'jobs' || category.parent?.code == 'jobs') {

            def diff = new Date() - 30

            choiceJobs = Article.withCriteria() {
                eq('choice', true)
                eq('enabled', true)
                'in'('category', [Category.get('recruit'), Category.get('resumes'), Category.get('evalcom')])
                gt('dateCreated', diff)
                order('id', 'desc')

                maxResults(5)
            }.findAll()


            choiceJobs.each {
                if(it.isRecruit) {
                    Recruit recruit = Recruit.findByArticle(it)
                    it.recruit = recruit
                }
            }
        }

        def notices = articleService.getNotices(category)

//        def managedAvatar = userService.getManaedAvatars(springSecurityService?.currentUser)
        def categories = category.children ?: [category]
        
        if(category.code == 'community') 
            categories = categories.findAll { it.code != 'promote' }

        def recruits
        def recruitFilter = false

        if(category.code == 'recruit') {

            def jobTypes = params.list('filter.jobType').collect { JobType.valueOf(it as String) }
            def jobDuties = params.list('filter.jobDuty').collect { JobPositionDuty.get(it as Long) }
            def cities = params.list('filter.city').collect { it as String }
            def minCareer = params['filter.minCareer']
            def maxCareer = params['filter.maxCareer']

            def jobPositions = JobPosition.createCriteria().list {
                if(jobDuties)
                    'in'('duty', jobDuties)
                if(minCareer)
                    ge('minCareer', minCareer as Integer)
                if(maxCareer)
                    le('maxCareer', maxCareer as Integer)
            }

            def jobPositionFilter = (jobDuties || minCareer || maxCareer)

            recruitFilter = (jobTypes || cities || jobPositionFilter)

            recruits = Recruit.createCriteria().list {
                if(jobTypes)
                    'in'('jobType' , jobTypes)
                if(jobPositionFilter) {
                    if(jobPositions) {
                        'in'('id' , jobPositions*.recruitId)
                    } else {
                        'in'('id' , Long.MAX_VALUE)
                    }
                }
                if(cities)
                    'in'('city', cities)
            }
        }

        def articlesQuery = Article.where {
            category in categories
            if (SpringSecurityUtils.ifNotGranted("ROLE_ADMIN"))
                enabled == true
            if (params.query && params.query != '')
                title =~ "%${params.query}%" || content.text =~ "%${params.query}%"

            if(recruitFilter) {
                if(recruits)
                    id in recruits*.article*.id
                else
                    id in [Long.MAX_VALUE]
            }
        }

        def articles = articlesQuery.list(params)

        articles.each {
            if(it.isRecruit) {
                Recruit recruit = Recruit.findByArticle(it)
                it.recruit = recruit
            }
        }

        respond articles, model:[articlesCount: articlesQuery.count(), category: category, choiceJobs: choiceJobs, notices: notices]
    }



    def tagged(String tag, Integer max) {
        params.max = Math.min(max ?: 20, 100)
        params.sort = params.sort ?: 'id'
        params.order = params.order ?: 'desc'
        params.query = params.query?.trim()

        if(tag == null) {
            notFound()
            return
        }
        
        def articlesQuery = Article.where {
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


        if(article == null || (!article.enabled && SpringSecurityUtils.ifNotGranted("ROLE_ADMIN"))) {
            notFound()
            return
        }

        if(article.isRecruit) {
            redirect uri: "/recruit/$article.id"
        }

        article.updateViewCount(1)

        if(springSecurityService.loggedIn) {
            Avatar avatar = Avatar.load(springSecurityService.principal.avatarId)
            contentVotes = ContentVote.findAllByArticleAndVoter(article, avatar)
            scrapped = Scrap.findByArticleAndAvatar(article, avatar)
        }

        def notes = Content.findAllByArticleAndTypeAndEnabled(article, ContentType.NOTE, true)

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

        respond article, model: [contentVotes: contentVotes, notes: notes, scrapped: scrapped, contentBanner: contentBanner, changeLogs: changeLogs]
    }

    def create(String code) {

        def category = Category.get(code)

        recaptchaService.cleanUp session

        User user = springSecurityService.loadCurrentUser()

        if(category == null) {
            notFound()
            return
        }

        if(category.code == 'recruit') {
            redirect uri: '/recruits/create'
            return
        }

        if(user.accountLocked || user.accountExpired) {
            forbidden()
            return
        }

        params.category = category

        def categories
        def goExternalLink = false
        
        if(SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")) {
            categories = Category.findAllByWritableAndEnabled(true, true)
        } else {
            goExternalLink = category.writeByExternalLink
            categories = Category.findAllByParentAndWritableAndEnabledAndAdminOnly(category?.parent ?: category, true, true, false) ?: [category]
            params.anonymity = category?.anonymity ?: false
        }

        def notices = params.list('notices') ?: []

        if(goExternalLink) {
            redirect(url: category.externalLink)
        } else {
            respond new Article(params), model: [categories: categories, category: category, notices: notices]
        }

        
    }

    @Transactional
    def save(String code) {

        Article article = new Article(params)

        Category category = Category.get(params.categoryCode)

        User user = springSecurityService.loadCurrentUser()

        if(category?.code == 'recruit') {
            redirect uri: '/recruits/create'
            return
        }

        if(user.accountLocked || user.accountExpired) {
            forbidden()
            return
        }

        try {

            def realIp = userService.getRealIp(request)
            def reCaptchaVerified = recaptchaService.verifyAnswer(session, realIp, params)

            if(!reCaptchaVerified) {
                throw new Exception("invalid captcha")
            }

            recaptchaService.cleanUp session

            withForm {
                Avatar author = Avatar.load(springSecurityService.principal.avatarId)

                if(SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")) {
                    article.choice = params.choice?:false
                    article.enabled = !params.disabled
                    article.ignoreBest = params.ignore ?: false
                }

                article.createIp = userService.getRealIp(request)
                
                articleService.save(article, author, category)

                articleService.saveNotices(article, user, params.list('notices'))

                withFormat {
                    html {
                        flash.message = message(code: 'default.created.message', args: [message(code: 'article.label', default: 'Article'), article.id])
                        redirect article
                    }
                    json { respond article, [status: CREATED] }
                }
            }.invalidToken {
                redirect uri: "/articles/${code}", method:"GET"
            }

        } catch (Exception e) {

            category = Category.get(code)
            def categories = category?.children ?: category?.parent?.children ?: [category]
            def notices = params.list('notices') ?: []
            article.category = category

            respond article.errors, view: 'create', model: [categories: categories, category: category, notices: notices]
        }
    }

    def edit(Long id) {

        Article article = Article.get(id)

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

        if(article.category.code == 'recruit') {
            redirect uri: "/recruit/edit/$article.id"
            return
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

        def notices = ArticleNotice.findAllByArticle(article)
        
        respond article, model: [categories: categories, notices: notices]
    }

    @Transactional
    def update(Article article) {

        User user = springSecurityService.loadCurrentUser()

        if(SpringSecurityUtils.ifNotGranted("ROLE_ADMIN")) {
            if (article.authorId != springSecurityService.principal.avatarId) {
                notAcceptable()
                return
            }
        }

        if(article.category.code == 'recruit') {
            redirect uri: '/recruits/create'
            return
        }

        if(user.accountLocked || user.accountExpired) {
            forbidden()
            return
        }

        try {

            withForm {

                Avatar editor = Avatar.get(springSecurityService.principal.avatarId)

                Category category = Category.get(params.categoryCode)
                
                if(SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")) {
                    article.choice = params.choice?:false
                    article.enabled = !params.disabled
                    article.ignoreBest = params.ignore ?: false
                }

                articleService.update(article, editor, category)

                articleService.removeNotices(article)

                articleService.saveNotices(article, user, params.list('notices'))

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
            respond article.errors, view: 'edit'
        }
    }

    @Transactional
    def delete(Long id) {

        Article article = Article.get(id)

        User user = springSecurityService.loadCurrentUser()

        def categoryCode = article.category.code

        if (article == null) {
            notFound()
            return
        }

        if(user.accountLocked || user.accountExpired) {
            forbidden()
            return
        }

        if(SpringSecurityUtils.ifNotGranted("ROLE_ADMIN")) {
            if (article.authorId != springSecurityService.principal.avatarId) {
                notAcceptable()
                return
            }
        }

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

        Article article = Article.get(id)

        User user = springSecurityService.loadCurrentUser()

        if(user.accountLocked || user.accountExpired) {
            forbidden()
            return
        }

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

    protected void forbidden() {

        withFormat {
            html {
                flash.message = message(code: 'default.forbidden.message', args: [message(code: 'article.label', default: 'Article'), params.id])
                redirect uri: '/'
            }
            json { render status: FORBIDDEN }
        }
    }
}
