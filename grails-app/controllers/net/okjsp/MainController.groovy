package net.okjsp

class MainController {

    MainService mainService

    def index() {
        [
            isIndex: true,
            choiceArticles: mainService.getChoiceArticles(),
            articleBlocks: [
                [category: Category.get('questions'), articles: mainService.getQnaArticles()],
                [category: Category.get('tech'), articles: mainService.getTechArticles()],
                [category: Category.get('community'), articles: mainService.getCommunityArticles()],
                [category: Category.get('columns'), articles: mainService.getColumnsArticles()],
            ]
        ]
    }
}
