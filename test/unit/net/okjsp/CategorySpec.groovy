package net.okjsp

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Category)
class CategorySpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "생성"() {
        when:
        def qnaCategory = new Category(code: 'qna', labelCode: 'qna.label', defaultLabel: 'Q&A', useNote: true, useOpinion: true, useEvaluate: true, useTag: true)

        then:
        qnaCategory.save()
        !qnaCategory.hasErrors()
    }
}
