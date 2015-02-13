package net.okjsp

import grails.plugin.cache.CacheEvict
import grails.plugin.cache.Cacheable
import grails.transaction.Transactional

@Transactional(readOnly = true)
class MainService {

    @Cacheable(value="choiceArticlesCache")
    def getChoiceArticles() {
        Article.where {
            choice
        }.list(max: 5, sort: 'id', order: 'desc')
    }

    @Cacheable("techArticlesCache")
    def getTechArticles() {
        Article.where {
            category in Category.get('tech').children
        }.list(max: 10, sort: 'id', order: 'desc')
    }

    @Cacheable("qnaArticlesCache")
    def getQnaArticles() {
        Article.where {
            category in Category.get('questions')
        }.list(max: 10, sort: 'id', order: 'desc')
    }

    @Cacheable("communityArticlesCache")
    def getCommunityArticles() {
        Article.where {
            category in Category.get('community').children
        }.list(max: 10, sort: 'id', order: 'desc')
    }

    @Cacheable("columnsArticlesCache")
    def getColumnsArticles() {
        Article.where {
            category in Category.get('columns')
        }.list(max: 10, sort: 'id', order: 'desc')
    }

}
