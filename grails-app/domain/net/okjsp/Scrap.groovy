package net.okjsp

class Scrap implements Serializable {

    Avatar avatar
    Article article

    Date dateCreated

    static constraints = {
    }

    static mapping = {
        id composite: ['article', 'avatar']
    }

    def beforeInsert() {
        article.updateScrapCount(1)
    }

    def beforeDelete() {
        article.updateScrapCount(-1)
    }
}
