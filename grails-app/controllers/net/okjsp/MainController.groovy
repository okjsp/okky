package net.okjsp

class MainController {

    def mainService
    def userService
    def springSecurityService
    def randomService

    def grailsCacheAdminService

    def index() {
        
        def mainBanners = Banner.where {
            type == BannerType.MAIN && visible == true            
        }.list()

        def mainBanner = mainBanners ? randomService.draw(mainBanners) : null


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
            promoteArticles: mainService.getPromoteArticles(),
            mainBanner : mainBanner
        ]
    }
    
    def flush() {
        grailsCacheAdminService.clearAllCaches();
    }
}
