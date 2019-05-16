package net.okjsp

import grails.transaction.Transactional

class RecruitService {

    def articleService

    def userService

    @Transactional
    def create(Article article, Recruit recruit, Category category, Company company, Avatar author) {

        article.isRecruit = true

        articleService.save(article, author, category)

        recruit.article = article
        recruit.company = company

        recruit.save(flush: true, failOnError: true)

    }
}
