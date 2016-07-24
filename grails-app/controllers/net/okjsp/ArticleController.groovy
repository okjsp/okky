package net.okjsp

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import grails.validation.ValidationException
import org.springframework.http.HttpStatus

@Transactional(readOnly = true)
class ArticleController {

    ArticleService articleService
    SpringSecurityService springSecurityService
    UserService userService

    static allowedMethods = [save: "POST", update: ["PUT","POST"], delete: ["DELETE","POST"], scrap: "POST",
                             addNote: "POST", assent: ["PUT","POST"], dissent: ["PUT","POST"]]

    @Secured("permitAll")
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

//        def managedAvatar = userService.getManaedAvatars(springSecurityService?.currentUser)
        def categories = category.children ?: [category]
        
        if(category.code == 'community') 
            categories = categories.findAll { it.code != 'promote' }

        def articlesQuery = Article.where {
            category in categories && enabled == true
            if(params.query && params.query != '')
                title =~ "%${params.query}%" || content.text =~ "%${params.query}%"

        }

        respond articlesQuery.list(params), model:[articlesCount: articlesQuery.count(), category: category]
    }

    @Secured("permitAll")
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

    @Secured("permitAll")
    def seq(Long id) {
        
        redirect uri:"/article/${id}"
    }

    @Transactional
    @Secured("permitAll")
    def show(Long id) {

        def contentVotes = [], scrapped

        Article article = Article.get(id)

        if(article == null) {
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

        Random randomizer = new Random();
        def contentBanner = contentBanners.get(randomizer.nextInt(contentBanners.size()));

        respond article, model: [contentVotes: contentVotes, notes: notes, scrapped: scrapped, contentBanner: contentBanner]
    }

    @Secured("ROLE_USER")
    def create(String code) {

        def category = Category.get(code)

        if(category == null) {
            notFound()
            return
        }

        params.category = category

        def categories
        def goExternalLink = false
        
        if(SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")) {
            categories = Category.findAllByWritableAndEnabled(true, true)
        } else {
            goExternalLink = category.writeByExternalLink
            categories = category.children ?: category.parent?.children ?: [category]
            params.anonymity = category?.anonymity ?: false
        }

        if(goExternalLink) {
            redirect(url: category.externalLink)
        } else {
            respond new Article(params), model: [categories: categories, category: category]
        }

        
    }

    @Transactional
    @Secured("ROLE_USER")
    def save(String code) {

        Article article = new Article(params)

        Category category = Category.get(params.categoryCode)

        try {

            withForm {
                Avatar author = Avatar.load(springSecurityService.principal.avatarId)

                if(SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")) {

                    article.choice = params.choice?:false

                }

                article.createIp = userService.getRealIp(request)
                
                articleService.save(article, author, category)

                withFormat {
                    html {
                        flash.message = message(code: 'default.created.message', args: [message(code: 'article.label', default: 'Article'), article.id])
                        redirect article
                    }
                    json { respond article, [status: HttpStatus.CREATED] }
                }
            }.invalidToken {
                redirect uri: "/articles/${code}", method:"GET"
            }

        } catch (ValidationException e) {

            category = Category.get(code)

            def categories = category.children ?: category.parent?.children ?: [category]

            respond article.errors, view: 'create', model: [categories: categories, category: category]
        }
    }

    @Secured("ROLE_USER")
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

        def categories

        if(SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")) {
            categories = Category.findAllByWritableAndEnabled(true, true)
        } else {
            categories = article.category.children ?: article.category.parent?.children ?: [article.category]
        }

        if(params.categoryCode) {
            article.category = Category.get(params.categoryCode)
        }
        
        respond article, model: [categories: categories]
    }

    @Transactional
    @Secured("ROLE_USER")
    def update(Article article) {

        if(SpringSecurityUtils.ifNotGranted("ROLE_ADMIN")) {
            if (article.authorId != springSecurityService.principal.avatarId) {
                notAcceptable()
                return
            }
        }

        try {

            withForm {

                Avatar editor = Avatar.get(springSecurityService.principal.avatarId)

                Category category = Category.get(params.categoryCode)
                
                if(SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")) {
                    
                    article.choice = params.choice?:false
                    
                }

                articleService.update(article, editor, category)

                withFormat {
                    html {
                        flash.message = message(code: 'default.updated.message', args: [message(code: 'Article.label', default: 'Article'), article.id])
                        redirect article
                    }
                    json { respond article, [status: HttpStatus.OK] }
                }

            }.invalidToken {
                redirect article
            }

        } catch (ValidationException e) {
            respond article.errors, view: 'edit'
        }
    }

    @Transactional
    @Secured("ROLE_USER")
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

        articleService.delete(article)

        withFormat {
            html {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Article.label', default: 'Article'), article.id])
                flash.status = "success"
                redirect uri: "/articles/${categoryCode}", method:"GET"
            }
            json { render status: HttpStatus.NO_CONTENT }
        }
    }

    @Transactional
    @Secured("ROLE_USER")
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
    @Secured("ROLE_USER")
    def addNote(Long id) {

        Article article = Article.get(id)

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
                    respond article, [status: HttpStatus.OK]
                }
            }

        } catch (ValidationException e) {
            flash.error = e.message
            redirect article
        }

    }

    @Transactional
    @Secured("ROLE_USER")
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
    @Secured("ROLE_USER")
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
    @Secured("ROLE_USER")
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
    @Secured("ROLE_USER")
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
            json { respond article, [status: HttpStatus.OK] }
        }
    }

    @Transactional
    @Secured("ROLE_USER")
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
            json { respond article, [status: HttpStatus.OK] }
        }
    }

    protected void notFound() {

        withFormat {
            html {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'article.label', default: 'Article'), params.id])
                redirect uri: '/'
            }
            json { render status: HttpStatus.NOT_FOUND }
        }
    }

    protected void notAcceptable() {

        withFormat {
            html {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'article.label', default: 'Article'), params.id])
                redirect uri: '/'
            }
            json { render status: HttpStatus.NOT_ACCEPTABLE }
        }
    }
}
