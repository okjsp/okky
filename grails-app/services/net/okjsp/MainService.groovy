package net.okjsp

import grails.plugin.cache.CacheEvict
import grails.plugin.cache.Cacheable
import grails.plugin.springsecurity.SpringSecurityService
import grails.transaction.Transactional
import groovy.time.TimeCategory

@Transactional(readOnly = true)
class MainService {
    
    SpringSecurityService springSecurityService

    @Cacheable(value="choiceArticlesCache")
    def getChoiceArticles() {
        Article.where {
            choice == true && enabled == true
        }.list(max: 5, sort: 'id', order: 'desc')
    }

    @Cacheable(value="weeklyArticlesCache")
    def getWeeklyArticles() {

        Integer.metaClass.mixin TimeCategory
        Date.metaClass.mixin TimeCategory

        def diff = new Date() - 7.days
        
        Article.where {
            enabled == true
            category != Category.get('promote') && category != Category.get('recruit')
            dateCreated > diff
        }.list(max: 5, sort: 'best', order: 'desc')
    }

    @Cacheable("techArticlesCache")
    def getTechArticle() {
        Article.createCriteria().get {
            'in'('category', Category.get('tech').children)
            eq('enabled', true)
            order('id', 'desc')
            maxResults(1)
        }
    }

    @Cacheable("qnaArticlesCache")
    def getQnaArticles() {
        
        Article.where {
            category == Category.get('questions') && enabled == true
        }.list(max: 10, sort: 'id', order: 'desc')
    }

    @Cacheable("communityArticlesCache")
    def getCommunityArticles() {
        
        def categories = Category.get('community').children.findAll { it.code != 'promote' }
        
        Article.where {
            category in categories && enabled == true
        }.list(max: 20, sort: 'id', order: 'desc')
    }

    @Cacheable("columnsArticlesCache")
    def getColumnArticle() {
        Article.createCriteria().get {
            eq('category', Category.get('columns'))
            eq('enabled', true)
            order('id', 'desc')
            maxResults(1)
        }
    }
    
    @Cacheable("promoteArticlesCache")
    def getPromoteArticles() {

        Integer.metaClass.mixin TimeCategory
        Date.metaClass.mixin TimeCategory

        def diff = new Date() - 7.days
        
        def category = Category.get('promote')

        Article.executeQuery(" from Article where category = :category and enabled = true and dateCreated > :diff order by rand()", [category: category, diff: diff])
    }

}
