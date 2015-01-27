package net.okjsp

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

class UserSpec extends Specification {

    void setup() {
    }

    void "유저 생성"() {

        when: "Person 과 Avatar 가 먼저 생성된 후"
        def person = new Person(fullName: '테스트다', email: 'test2@test.com')
        def avatar = new Avatar(nickname: '테스트아바타다', picture: '1234', pictureType: Avatar.PictureType.GRAVATAR)

        then:
        person.save()
        avatar.save()
        !person.hasErrors()
        !avatar.hasErrors()
        person instanceof Person
        avatar instanceof Avatar

        when: "Person 과 Avatar 가 먼저 생성된 후 User 저장"
        def user = new User(username: 'test1234', password: 'a1231234', person: person, avatar: avatar)
        user.createIp = "0.0.0.0"

        then:
        user.save()
        user instanceof User


        when: "아이디가 영어로만 되어 있고, 10 자리일 경우 정상"
        user.username = 'testadvrcf'

        then: "정상"
        user.save()
        !user.hasErrors()


        when: "아이디가 4자리인 경우 경우 에러"
        user.username = 'test'

        then: "에러"
        !user.save()
        user.hasErrors()
        user.errors['username'].code == 'size.toosmall'


        when: "아이디가 16자리인 경우 에러"
        user.username = 'test123test12345'

        then: "에러"
        !user.save()
        user.hasErrors()
        user.errors['username'].code == 'size.toobig'


        when: "아이디가 숫자만 있는 경우 경우 에러"
        user.username = '1234567'

        then: "에러"
        !user.save()
        user.errors['username'].code == 'matches.invalid'
        user.hasErrors()


        when: "아이디에 특수문자가 있는 경우 경우 에러"
        user.username = 'te@st1234'

        then: "에러"
        !user.save()
        user.hasErrors()
        user.errors['username'].code == 'matches.invalid'


        when: "아이디에 대문자가 포함되 있는 경우 에러"
        user.username = 'tEst1234'

        then: "에러"
        !user.save()
        user.hasErrors()
        user.errors['username'].code == 'matches.invalid'


        when: "아이디에 스페이스가 포함되 있는 경우 에러"
        user.username = 't est1234'

        then: "에러"
        !user.save()
        user.hasErrors()
        user.errors['username'].code == 'matches.invalid'


        when: "아이디가 금칙어인 경우 에러"
        user.username = 'administrator'

        then: "에러"
        !user.save()
        user.hasErrors()
        user.errors['username'].code == 'default.invalid.disallow.message'


        when: "비밀번호가 영어로만 이루어진 경우 에러"
        user.username = 'test1234'
        user.password = 'testtest'

        then: "에러"
        !user.save()
        user.hasErrors()
        user.errors['password'].code == 'matches.invalid'


        when: "비밀번호가 숫자로만 이루어진 경우 에러"
        user.password = '12341234'

        then: "에러"
        !user.save()
        user.hasErrors()
        user.errors['password'].code == 'matches.invalid'


        when: "비밀번호가 5자인 경우 에러"
        user.password = 'a2341'

        then: "에러"
        !user.save()
        user.hasErrors()
        user.errors['password'].code == 'minSize.notmet'


        when: "비밀번호에 특수문자가 포함되면 무방"
        user.password = '!test$1234'

        then: "정상"
        user.save()
        !user.hasErrors()


        when: "비밀번호에 스페이스가 포함되도 무방"
        user.password = '!test $1234'

        then: "정상"
        user.save()
        !user.hasErrors()


        when: "아바타와 개인정보가 없을 경우 에러"
        user = new User(username: 'test2', password: 'a1231234')

        then: "에러"
        !user.save()
        user.hasErrors()
        user.errors['avatar'].code == 'nullable'
        user.errors['person'].code == 'nullable'

    }
}
