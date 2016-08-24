package net.okjsp

import grails.plugin.cache.CacheEvict
import grails.plugin.cache.Cacheable
import grails.plugin.springsecurity.SpringSecurityService
import grails.transaction.Transactional
import groovy.time.TimeCategory
import org.hibernate.FetchMode
import org.hibernate.criterion.CriteriaSpecification

@Transactional(readOnly = true)
class MainService {
    
    SpringSecurityService springSecurityService

    @Cacheable(value="choiceArticlesCache")
    def getChoiceArticles() {

        Article.withCriteria() {
            fetchMode 'content', FetchMode.JOIN
            fetchMode 'author', FetchMode.JOIN
            eq('choice', true)
            eq('enabled', true)
            ne('category', Category.get('recruit'))
            ne('category', Category.get('resumes'))
            order('id', 'desc')
            maxResults(5)
        }.findAll()
    }

    @Cacheable(value="weeklyArticlesCache")
    def getWeeklyArticles() {

        def diff = new Date() - 7
        
        Article.withCriteria() {
            fetchMode 'content', FetchMode.JOIN
            fetchMode 'author', FetchMode.JOIN
            ne('category', Category.get('promote'))
            ne('category', Category.get('recruit'))
            eq('enabled', true)
            eq('choice', false)
            gt('dateCreated', diff)
            order('best', 'desc')
            maxResults(5)
        }.findAll()
    }

    @Cacheable("techArticleCache")
    def getTechArticle() {
        Article.withCriteria() {
            fetchMode 'content', FetchMode.JOIN
            fetchMode 'author', FetchMode.JOIN
            'in'('category', Category.get('tech').children)
            eq('enabled', true)
            order('id', 'desc')
            maxResults(1)
        }.find()
    }
    @Cacheable("techArticlesCache")
    def getTechArticles() {
        Article.withCriteria() {
            fetchMode 'content', FetchMode.JOIN
            fetchMode 'author', FetchMode.JOIN
            'in'('category', Category.get('tech').children)
            eq('enabled', true)
            order('id', 'desc')
            maxResults(5)
        }.findAll()
    }

    @Cacheable("qnaArticlesCache")
    def getQnaArticles() {
        Article.withCriteria() {
            fetchMode 'content', FetchMode.JOIN
            fetchMode 'author', FetchMode.JOIN
            eq('category', Category.get('questions'))
            eq('enabled', true)
            order('id', 'desc')
            maxResults(10)
        }.findAll()
    }

    @Cacheable("communityArticlesCache")
    def getCommunityArticles() {
        
        def categories = Category.get('community').children.findAll { it.code != 'promote' }

        Article.withCriteria() {
            fetchMode 'content', FetchMode.JOIN
            fetchMode 'author', FetchMode.JOIN
            'in'('category', categories)
            eq('enabled', true)
            order('id', 'desc')
            maxResults(20)
        }.findAll()
    }

    @Cacheable("columnsArticlesCache")
    def getColumnArticle() {
        Article.withCriteria() {
            fetchMode 'content', FetchMode.JOIN
            fetchMode 'author', FetchMode.JOIN
            eq('category', Category.get('columns'))
            eq('enabled', true)
            order('id', 'desc')
            maxResults(1)
        }.find()
    }
    
    @Cacheable("promoteArticlesCache")
    def getPromoteArticles() {

        def diff = new Date() - 7

        Article.withCriteria() {
            fetchMode 'content', FetchMode.JOIN
            fetchMode 'author', FetchMode.JOIN
            'in'('category', Category.get('promote'))
            eq('enabled', true)
            gt('dateCreated', diff)
            order('id', 'desc')
            maxResults(50)
        }.findAll()

    }

}
