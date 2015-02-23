package net.okjsp

class MainController {

    def mainService
    def userService
    def springSecurityService
    def randomService

    def index() {

        def managedAvatar = userService.getManaedAvatars(springSecurityService?.currentUser)
        
        def excludeManagedAvatarClosure = {
            it
        }
        
        def mainBanners = Banner.where {
            type == BannerType.MAIN && visible == true            
        }.list()

        def mainBanner = mainBanners ? randomService.draw(mainBanners) : null
        
        return [
            isIndex: true,
            choiceArticles: mainService.getChoiceArticles(),
            mainBanner : mainBanner,
            articleBlocks: [
                [category: Category.get('questions'), articles: mainService.getQnaArticles().findAll(excludeManagedAvatarClosure)],
                [category: Category.get('tech'), articles: mainService.getTechArticles().findAll(excludeManagedAvatarClosure)],
                [category: Category.get('community'), articles: mainService.getCommunityArticles().findAll(excludeManagedAvatarClosure)],
                [category: Category.get('columns'), articles: mainService.getColumnsArticles().findAll(excludeManagedAvatarClosure)],
            ]
        ]
    }
}
