package net.okjsp

class ArticleNotice {

    Category category
    User user
    Date dateCreated

    static belongsTo = [article : Article]

    static constraints = {
    }
}
