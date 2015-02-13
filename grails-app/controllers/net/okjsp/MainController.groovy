package net.okjsp

class MainController {

    def mainService
    def userService
    def springSecurityService

    def index() {

        def managedAvatar = userService.getManaedAvatars(springSecurityService?.currentUser)
        
        def excludeManagedAvatarClosure = {
            !managedAvatar.contains(it.author)
        }
        
        return [
            isIndex: true,
            choiceArticles: mainService.getChoiceArticles(),
            articleBlocks: [
                [category: Category.get('questions'), articles: mainService.getQnaArticles().findAll(excludeManagedAvatarClosure)],
                [category: Category.get('tech'), articles: mainService.getTechArticles().findAll(excludeManagedAvatarClosure)],
                [category: Category.get('community'), articles: mainService.getCommunityArticles().findAll(excludeManagedAvatarClosure)],
                [category: Category.get('columns'), articles: mainService.getColumnsArticles().findAll(excludeManagedAvatarClosure)],
            ]
        ]
    }
}
