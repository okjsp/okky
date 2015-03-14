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
        
        def promoteArticles = mainService.getPromoteArticles().unique { a, b -> a.author <=> b.author }.sort { Math.random() }

//        promoteArticles = promoteArticles.unique { a, b -> a.createIp <=> b.createIp }

        if(promoteArticles?.size() > 3) promoteArticles = promoteArticles.subList(0, 5)
        
        return [
            isIndex: true,
            choiceArticles: mainService.getChoiceArticles(),
            questionsArticles: mainService.getQnaArticles(),
            communityArticles: mainService.getCommunityArticles(),
            columnArticle: mainService.getColumnArticle(),
            techArticle: mainService.getTechArticle(),
            weeklyArticles: mainService.getWeeklyArticles(),
            promoteArticles: promoteArticles,
            mainBanner : mainBanner
        ]
    }
    
    def flush() {
        grailsCacheAdminService.clearAllCaches();
    }
}
