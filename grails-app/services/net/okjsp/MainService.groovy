package net.okjsp

import grails.plugin.cache.CacheEvict
import grails.plugin.cache.Cacheable
import grails.plugin.springsecurity.SpringSecurityService
import grails.transaction.Transactional

@Transactional(readOnly = true)
class MainService {
    
    SpringSecurityService springSecurityService

    @Cacheable(value="choiceArticlesCache")
    def getChoiceArticles() {
        Article.where {
            choice == true && enabled == true
        }.list(max: 5, sort: 'id', order: 'desc')
    }

    @Cacheable("techArticlesCache")
    def getTechArticles() {
        Article.where {
            category in Category.get('tech').children && enabled == true
        }.list(max: 10, sort: 'id', order: 'desc')
    }

    @Cacheable("qnaArticlesCache")
    def getQnaArticles() {
        
        Article.where {
            category in Category.get('questions') && enabled == true
        }.list(max: 10, sort: 'id', order: 'desc')
    }

    @Cacheable("communityArticlesCache")
    def getCommunityArticles() {
        Article.where {
            category in Category.get('community').children && enabled == true
        }.list(max: 10, sort: 'id', order: 'desc')
    }

    @Cacheable("columnsArticlesCache")
    def getColumnsArticles() {
        Article.where {
            category in Category.get('columns') && enabled == true
        }.list(max: 10, sort: 'id', order: 'desc')
    }

}
