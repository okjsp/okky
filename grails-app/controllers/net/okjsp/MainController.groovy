package net.okjsp

import grails.plugin.springsecurity.annotation.Secured
import net.okjsp.Banner
import net.okjsp.BannerType

class MainController {

    def mainService
    def userService
    def springSecurityService
    def randomService

    def grailsCacheAdminService

    @Secured(["permitAll"])
    def index() {
        
        def mainBanners = Banner.where {
            type == BannerType.MAIN && visible == true
        }.list()

        def mainBanner = mainBanners ? randomService.draw(mainBanners) : null

        def promoteArticles = mainService.getPromoteArticles().clone().sort{ Math.random() * 1000 }.unique{ a, b -> a.authorId <=> b.authorId }
//        promoteArticles = promoteArticles.unique { a, b -> a.createIp <=> b.createIp }
        if(promoteArticles?.size() > 6) promoteArticles = promoteArticles.subList(0, 5)

//        def techArticles = mainService.getTechArticles().clone().sort { Math.random() }
//        if(techArticles?.size() > 2) techArticles = techArticles.subList(0, 2)

        return [
            isIndex: true,
            choiceArticles: mainService.getChoiceArticles(),
            weeklyArticles: mainService.getWeeklyArticles(),
            questionsArticles: mainService.getQnaArticles(),
            communityArticles: mainService.getCommunityArticles(),
            columnArticle: mainService.getColumnArticle(),
//            techArticle: mainService.getTechArticle(),
            techArticles: mainService.getTechArticles(),
            promoteArticles: promoteArticles,
            mainBanner : mainBanner
        ]
    }
    
    def flush() {
        grailsCacheAdminService.clearAllCaches();
    }
}
