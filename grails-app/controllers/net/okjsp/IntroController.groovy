package net.okjsp

import grails.transaction.Transactional

@Transactional(readOnly = true)
class IntroController {

    def about() {

        def categories = Category.get('questions').children

        List<Category> categoryList = new ArrayList<>(categories)

        int userCount = User.count()
        int techQnaCount = Article.countByCategoryInList(categoryList)

        Date now = new Date()

        int year = now[Calendar.YEAR]

        render view: "about", model : [userCount : StringUtils.getCountShorten(userCount), qnaCount: StringUtils.getCountShorten(techQnaCount), ago: year - 2001]
    }

    def ad() {

        int count = User.count()

        render view: "ad", model : [resultString : StringUtils.getCountShorten(count)]
    }
}
