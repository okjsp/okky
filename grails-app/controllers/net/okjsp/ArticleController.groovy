package net.okjsp

import grails.plugin.springsecurity.SpringSecurityService
import grails.validation.ValidationException
import org.springframework.http.HttpStatus

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ArticleController {

    ArticleService articleService
    SpringSecurityService springSecurityService

    static allowedMethods = [save: "POST", update: ["PUT","POST"], delete: ["DELETE","POST"], scrap: "POST",
                             addNote: "POST", assent: ["PUT","POST"], dissent: ["PUT","POST"]]

    def index(String code, Integer max) {
        params.max = Math.min(max ?: 20, 100)
        params.sort = params.sort ?: 'id'
        params.order = params.order ?: 'desc'

        def category = Category.get(code)

        if(category == null) {
            notFound()
            return
        }

        def managedAvatar = ManagedUser.findAll()*.user*.avatar

        if(springSecurityService.loggedIn) {
            managedAvatar.remove(springSecurityService.currentUser.avatar)
        }

        def categories = category.children ?: [category]

        def articlesQuery = Article.where {
            and {
                category in categories
                enabled
                not {
                    author in managedAvatar
                }
            }
        }

        respond articlesQuery.list(params), model:[articlesCount: articlesQuery.count(), category: category]
    }

    @Transactional
    def show(Long id) {

        def contentVotes = [], notes, scrapped

        Article article = Article.get(id)

        if(article == null) {
            notFound()
            return
        }

        article.updateViewCount(1)

        if(springSecurityService.loggedIn) {
            Avatar avatar = Avatar.get(springSecurityService.principal.avatarId)
            contentVotes = ContentVote.findAllByArticleAndVoter(article, avatar)
            scrapped = Scrap.findByArticleAndAvatar(article, avatar)
        }

        if(article.category.useEvaluate) {
            def notesCriteria = Content.createCriteria()
            notes = notesCriteria {
                and {
                    eq("article", article)
                    eq("type", ContentType.NOTE)
                }
                and {
                    order("selected", "desc")
                    order("voteCount", "desc")
                    order("id", "asc")
                }
            }
        } else {
            notes = Content.findAllByArticleAndType(article, ContentType.NOTE)
        }

        respond article, model: [contentVotes: contentVotes, notes: notes, scrapped: scrapped]
    }

    def create(String code) {

        def category = Category.get(code)

        if(category == null) {
            notFound()
            return
        }

        def categories = category.children ?: category.parent?.children ?: [category]

        respond new Article(params), model: [categories: categories, category: category]
    }

    @Transactional
    def save(String code) {

        Article article = new Article(params)

        Category category = Category.get(params.categoryCode)

        try {

            withForm {
                Avatar author = Avatar.get(springSecurityService.principal.avatarId)

                articleService.save(article, author, category)

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

        } catch (ValidationException e) {

            category = Category.get(code)

            def categories = category.children ?: category.parent?.children ?: [category]

            respond article.errors, view: 'create', model: [categories: categories, category: category]
        }
    }

    def edit(Long id) {

        Article article = Article.get(id)

        if(article == null) {
            notFound()
            return
        }

        if(article.authorId != springSecurityService.principal.avatarId) {
            notAcceptable()
            return
        }

        def categories = article.category.children ?: article.category.parent?.children ?: [article.category]
        respond article, model: [categories: categories]
    }

    @Transactional
    def update(Article article) {

        if(article.authorId != springSecurityService.principal.avatarId) {
            notAcceptable()
            return
        }

        try {

            withForm {

                Avatar editor = Avatar.get(springSecurityService.principal.avatarId)

                Category category = Category.get(params.categoryCode)

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
            respond article.errors, view: 'edit'
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

        if(article.authorId != springSecurityService.principal.avatarId) {
            notAcceptable()
            return
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

        try {

            Avatar avatar = Avatar.get(springSecurityService.principal.avatarId)

            Content content = new Content()
            bindData(content, params, 'note')

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
