import net.okjsp.*
import grails.converters.JSON

class BootStrap {

    UserService userService

    def init = { servletContext ->

//        SpringSecurityUtils.clientRegisterFilter('cookiePreAuthFilter', SecurityFilterPosition.PRE_AUTH_FILTER)

        def adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true)
        def userRole = new Role(authority: 'ROLE_USER').save(flush: true)

        environments {
            development {
                if(!User.findByUsername('admin')) {

                    // 테스트 User 생성
                    def adminUser = new User(
                        username: 'admin',
                        password: 'password11',
                        person: new Person(fullName: '관리자', email: 'admin@okky.kr'),
                        avatar: new Avatar(nickname: '관리자')
                    )
                    adminUser.enabled = true
                    adminUser.createIp = '0.0.0.0'
                    userService.saveUser adminUser
                    UserRole.create(adminUser, adminRole, true)
                }

                if(!User.findByUsername('testuser')) {

                    // 테스트 User 생성
                    def testUser = new User(
                        username: 'testuser',
                        password: 'password11',
                        person: new Person(fullName: '테스트사용자', email: 'test@okky.kr'),
                        avatar: new Avatar(nickname: '테스트사용자')
                    )

                    testUser.enabled = true
                    testUser.createIp = '0.0.0.0'
                    userService.saveUser testUser
                    UserRole.create(testUser, userRole, true)
                }

                if(!User.findByUsername('testuser2')) {

                    // 테스트 User 생성
                    def testUser = new User(
                            username: 'testuser2',
                            password: 'password11',
                            person: new Person(fullName: '테스트사용자2', email: 'test2@okky.kr'),
                            avatar: new Avatar(nickname: '테스트사용자2')
                    )

                    testUser.enabled = true
                    testUser.createIp = '0.0.0.0'
                    userService.saveUser testUser
                    UserRole.create(testUser, userRole, true)
                }

                if(!User.findByUsername('testuser3')) {

                    // 테스트 User 생성
                    def testUser = new User(
                            username: 'testuser3',
                            password: 'password11',
                            person: new Person(fullName: '테스트사용자3', email: 'test3@okky.kr'),
                            avatar: new Avatar(nickname: '테스트사용자3')
                    )

                    testUser.enabled = true
                    testUser.createIp = '0.0.0.0'
                    userService.saveUser testUser
                    UserRole.create(testUser, userRole, true)
                }

                // 1 Level Category
                def questionsCategory = Category.get('questions') ?: new Category(code: 'questions', labelCode: 'questions.label', defaultLabel: 'Q&A', iconCssNames: 'fa fa-database', sortOrder: 0, writable: true, useNote: true, useOpinion: true, useEvaluate: true, useTag: true, requireTag: true).save(flush: true)
                def techCategory = Category.get('tech') ?: new Category(code: 'tech', labelCode: 'tech.label', defaultLabel: 'Tech', iconCssNames: 'fa fa-code', sortOrder: 1, writable: false, useNote: true, useOpinion: false, useEvaluate: false, useTag: true).save(flush: true)
                def communityCategory = Category.get('community') ?: new Category(code: 'community', labelCode: 'community.label', defaultLabel: '커뮤니티', iconCssNames: 'fa fa-comments', sortOrder: 2, writable: false, useNote: true, useOpinion: false, useEvaluate: false, useTag: false).save(flush: true)
                def columnsCategory = Category.get('columns') ?: new Category(code: 'columns', labelCode: 'columns.label', defaultLabel: '칼럼', iconCssNames: 'fa fa-quote-left', sortOrder: 3, writable: true, useNote: true, useOpinion: false, useEvaluate: false, useTag: true).save(flush: true)
                def jobsCategory = Category.get('jobs') ?: new Category(code: 'jobs', labelCode: 'jobs.label', defaultLabel: 'Jobs', iconCssNames: 'fa fa-group', sortOrder: 4, writable: false, useNote: true, useOpinion: false, useEvaluate: false, useTag: true).save(flush: true)

                // 2 Level Category

                // Tech
                def newsCategory = Category.get('news') ?: new Category(code: 'news', parent: techCategory, labelCode: 'news.label', defaultLabel: 'IT News & 정보', iconCssNames: 'fa fa-code', sortOrder: 0, useNote: true, useOpinion: false, useEvaluate: false, useTag: true).save(flush: true)
                def tipsCategory = Category.get('tips') ?: new Category(code: 'tips', parent: techCategory, labelCode: 'tips.label', defaultLabel: 'Tips & Tricks', iconCssNames: 'fa fa-code', sortOrder: 1, useNote: true, useOpinion: false, useEvaluate: false, useTag: true).save(flush: true)

                // Community
                def noticeCategory = Category.get('notice') ?: new Category(code: 'notice', parent: communityCategory, labelCode: 'notice.label', defaultLabel: '공지사항', iconCssNames: 'fa fa-comments', sortOrder: 0, useNote: true, useOpinion: false, useEvaluate: false, useTag: true, adminOnly: true).save(flush: true)
                def lifeCategory = Category.get('life') ?: new Category(code: 'life', parent: communityCategory, labelCode: 'life.label', defaultLabel: '사는얘기', iconCssNames: 'fa fa-comments', sortOrder: 1, useNote: true, useOpinion: false, useEvaluate: false, useTag: false).save(flush: true)
                def forumCategory = Category.get('forum') ?: new Category(code: '포럼', parent: communityCategory, labelCode: 'forum.label', defaultLabel: 'Forum', iconCssNames: 'fa fa-code', sortOrder: 1, useNote: true, useOpinion: false, useEvaluate: false, useTag: true).save(flush: true)
                def gatheringCategory = Category.get('gathering') ?: new Category(code: 'gathering', parent: communityCategory, labelCode: 'gathering.label', defaultLabel: '정기모임/스터디', iconCssNames: 'fa fa-comments', sortOrder: 2, useNote: true, useOpinion: false, useEvaluate: false, useTag: true).save(flush: true)
                def promoteCategory = Category.get('promote') ?: new Category(code: 'promote', parent: communityCategory, labelCode: 'gathering.label', defaultLabel: '학원', iconCssNames: 'fa fa-comments', sortOrder: 3, useNote: true, useOpinion: false, useEvaluate: false).save(flush: true)
                def eventCategory = Category.get('event') ?: new Category(code: 'event', parent: communityCategory, labelCode: 'event.label', defaultLabel: 'OKKY 행사', iconCssNames: 'fa fa-comments', sortOrder: 4, useNote: true, useOpinion: false, useEvaluate: false, url: '/event').save(flush: true)

                // Job
                def evalcomCategory = Category.get('evalcom') ?: new Category(code: 'evalcom', parent: jobsCategory, labelCode: 'gathering.label', defaultLabel: '좋은회사/나쁜회사', iconCssNames: 'fa fa-group', sortOrder: 0, useNote: true, useOpinion: false, useEvaluate: false, useTag: false, anonymity: true).save(flush: true)
                def recruitCategory = Category.get('recruit') ?: new Category(code: 'recruit', parent: jobsCategory, labelCode: 'recruit.label', defaultLabel: '구인', iconCssNames: 'fa fa-group', sortOrder: 1, useNote: true, useOpinion: false, useEvaluate: false, useTag: true).save(flush: true)
                def resumesCategory = Category.get('resumes') ?: new Category(code: 'resumes', parent: jobsCategory, labelCode: 'resumes.label', defaultLabel: '구직', iconCssNames: 'fa fa-group', sortOrder: 3, useNote: true, useOpinion: false, useEvaluate: false, useTag: true).save(flush: true)

            }
        }
        /**
         * Managed User
         */

        [26163, 26660, 22488, 25959, 26838, 21356,

         27039, 27430, 28983, 30983,

         27238, 27183, 27354, 24552, 27453, 23889,
         28300, 26069, 28213, 27251, 28262, 28541,
         28752, 28828, 28837, 28838, 28654, 28272,
         29080, 8417, 27660, 8933, 21996, 26609,
         29972, 29982, 30361, 30362, 29897
        ].each {
            def user = User.get(it)
            if(user) ManagedUser.findOrSaveByUser(user)
        }

        /**
         * 기본 직종/직무 추가
         */

        if(JobPositionGroup.count() == 0) {
            def jobDeveloper = new JobPositionGroup(name : "개발").save(flush: true)
            def jobPlanner = new JobPositionGroup(name : "기획").save(flush: true)
            def jobDesigner = new JobPositionGroup(name : "디자인").save(flush: true)
            def jobMarketer = new JobPositionGroup(name : "마케팅").save(flush: true)
            def jobEtc = new JobPositionGroup(name : "기타").save(flush: true)

            jobDeveloper.addToDuties(name: "CTO")
            jobDeveloper.addToDuties(name: "개발팀장")
            jobDeveloper.addToDuties(name: "DBA")
            jobDeveloper.addToDuties(name: "서버개발")
            jobDeveloper.addToDuties(name: "웹개발")
            jobDeveloper.addToDuties(name: "모바일개발")
            jobDeveloper.addToDuties(name: "Full Stack")
            jobDeveloper.addToDuties(name: "QA")
            jobDeveloper.addToDuties(name: "PM-SI")
            jobDeveloper.addToDuties(name: "DS")
            jobDeveloper.addToDuties(name: "시스템엔지니어")
            jobDeveloper.addToDuties(name: "플랫폼개발")
            jobDeveloper.addToDuties(name: "임베디드개발")
            jobDeveloper.addToDuties(name: "솔루션개발")
            jobDeveloper.addToDuties(name: "클라이언트개발")
            jobDeveloper.addToDuties(name: "기타개발")

            jobPlanner.addToDuties(name: "PM")
            jobPlanner.addToDuties(name: "UI/UX 기획")
            jobPlanner.addToDuties(name: "사업기획")
            jobPlanner.addToDuties(name: "전략기획")
            jobPlanner.addToDuties(name: "서비스기획")

            jobDesigner.addToDuties(name: "디자인")
            jobDesigner.addToDuties(name: "UI 디자인")
            jobDesigner.addToDuties(name: "UX 디자인")
            jobDesigner.addToDuties(name: "웹디자인")
            jobDesigner.addToDuties(name: "모바일디자인")
            jobDesigner.addToDuties(name: "서비스디자인")

            jobMarketer.addToDuties(name: "마케팅")
            jobMarketer.addToDuties(name: "영업")
            jobMarketer.addToDuties(name: "Digital Marketer")
            jobMarketer.addToDuties(name: "Growth Hacker")
            jobMarketer.addToDuties(name: "전략수석")

            jobEtc.addToDuties(name: "기타")
            jobEtc.addToDuties(name: "MD")

        }

        /**
         * Register Custom Object Marshaller
         */
        JSON.registerObjectMarshaller(Notification) { notification ->
            def obj = [:]
            obj['type'] = notification.type.toString()
            obj['article'] = [id: notification.articleId]
            obj['content'] = [id: notification.contentId]
            obj['dateCreated'] = notification.dateCreated
            obj['lastUpdated'] = notification.lastUpdated
            obj['sender'] = notification.sender

            return obj
        }

        JSON.registerObjectMarshaller(Content) {
            def obj = [:]
            obj['type'] = it.type.toString()
            obj['textType'] = it.textType.toString()
            obj['text'] = it.text
            obj['voteCount'] = it.voteCount
            obj['selected'] = it.selected
            obj['lastEditor'] = it.lastEditor
            obj['dateCreated'] = it.dateCreated
            obj['lastUpdated'] = it.lastUpdated
            return obj
        }
    }

    def destroy = {
    }
}
