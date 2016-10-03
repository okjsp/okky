package net.okjsp

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import net.okjsp.Avatar
import net.okjsp.Notification
import net.okjsp.NotificationRead

@Secured("ROLE_USER")
class NotificationController {
    SpringSecurityService springSecurityService
    NotificationService notificationService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        params.sort = params.sort ?: 'lastUpdated'
        params.order = params.order ?: 'desc'

        Avatar avatar = Avatar.get(springSecurityService.principal.avatarId)

        def query = Notification.where {
            and {
                eq('receiver', avatar)
            }
        }

        def notifications = query.list(params)
        def count = query.count()
        def lastRead = NotificationRead.findByAvatar(avatar).lastRead

        notificationService.read(avatar)

//        cache false 오류 나네

        def result = [notifications: notifications, count: count, lastRead: lastRead]

        respond result
    }

    def count() {

        Avatar avatar = Avatar.get(springSecurityService.principal.avatarId)

        def result = [count: notificationService.count(avatar)]

//        cache false   오류 나네
        respond result
    }
}
