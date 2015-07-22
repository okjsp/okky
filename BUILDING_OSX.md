# OS X 에서 OKKY 개발 시작하기

이 문서는 OS X에서 OKKY 개발을 시작하려는 사람들을 위한 가이드 입니다.

- 기준환경
  - OS X 10.10 Yosemite
  - iTerm2

## Grails 기본 환경 설정하기

1. 다음 프로그램들을 설치합니다.
  - Java SDK:
    http://www.oracle.com/technetwork/java/javase/downloads/index.html
  - GVM:
    http://gvmtool.net/

2. Grails를 설치합니다.
  ```sh
    gvm install grails 2.4.5
  ```

3. 선호하는 IDE 나 편집기를 사용하시면 됩니다.
  - GGTS:
    http://spring.io/tools/ggts
  - IntelliJ IDEA Community Edition:
    https://www.jetbrains.com/idea/download/
  - ATOM:
    https://atom.io/
  - Brackets:
    http://brackets.io/

## OKKY 환경 설정

1. 설정 파일 복사
  ```sh
    cp grails-app/conf/Config.sample.groovy grails-app/conf/Config.groovy
  ```

2. 기본 설정하기

  1. recaptcha 설정:
    recaptcha.publicKey , recaptcha.privateKey 부분을 [Google reCAPTCHA](http://www.google.com/recaptcha/intro/index.html) 에서 발급 받은 Key로 교체합니다.
  2. Facebook 설정:
    environments > development > oauth > providers > facebook 에 있는 
    key, secret 을 facebook 에서 발급 받은 내용으로 교체합니다.
  3. Google 설정:
    environments > development > oauth > providers > google 에 있는 key, 
    secret 을 google 에서 발급 받은 내용으로 교체합니다.
  4. Email 설정:
    로컬 개발용으로 gmail 설정을 해 줍니다.
    
```groovy
    grails.mail.host="smtp.gmail.com"
    grails.mail.port=587
    grails.mail.username="yourUsernameHere"
    grails.mail.password="yourPwdHere"
    grails.mail.from="defaultMailFromHere"
    grails.mail.props = ['mail.smtp.auth': "true",
                       "mail.smtp.starttls.enable": "true",
                       "mail.from":"defaultMailFromHere"]
    grails.mail.javaMailProperties = ['mail.smtp.auth': "true",
                                    "mail.smtp.starttls.enable": "true",
                                    "mail.from":"defaultMailFromHere"]
```
