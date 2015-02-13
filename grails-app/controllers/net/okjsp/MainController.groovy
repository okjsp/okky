package net.okjsp

class MainController {

    def mainService
    def springSecurityService

    def index() {

        def managedAvatar = ManagedUser.findAll()*.user*.avatar

        if(springSecurityService.loggedIn) {
            managedAvatar.remove(springSecurityService.currentUser.avatar)
        }
        
        def excludeManagedAvatar = {
            !managedAvatar.contains(it.author)
        }
        
        return [
            isIndex: true,
            choiceArticles: mainService.getChoiceArticles(),
            managedUsers: managedUsers,
            articleBlocks: [
                [category: Category.get('questions'), articles: mainService.getQnaArticles().findAll(excludeManagedAvatar)],
                [category: Category.get('tech'), articles: mainService.getTechArticles().findAll(excludeManagedAvatar)],
                [category: Category.get('community'), articles: mainService.getCommunityArticles().findAll(excludeManagedAvatar)],
                [category: Category.get('columns'), articles: mainService.getColumnsArticles().findAll(excludeManagedAvatar)],
            ]
        ]
    }
}
