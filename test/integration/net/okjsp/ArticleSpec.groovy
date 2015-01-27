package net.okjsp



import spock.lang.*

/**
 *
 */
class ArticleSpec extends Specification {

    def setup() {

    }

    def cleanup() {
    }

    void "등록"() {
        when:
        def avatar = User.findByUsername('testuser').avatar

        then:
        avatar != null
        avatar instanceof Avatar

        when:
        def content = new Content(author: avatar, text: 'test').save()
        def article = new Article(title: 'test', tagString: 'asdfasdf', content: content, category: Category.get('questions'))
        article.author = avatar

        then:
        article.save()
        !article.hasErrors()
    }
}
