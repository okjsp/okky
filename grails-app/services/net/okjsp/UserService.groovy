package net.okjsp

import grails.transaction.Transactional

@Transactional
class UserService {

    def grailsApplication

    /**
     * Save User with relationship
     * @param userInstance
     * @return
     */
    def saveUser(User userInstance) {

        userInstance.avatar.setPictureBySns(userInstance)

        // 유저 연관 정보 선 저장
        userInstance.person.save(failOnError: true)
        userInstance.avatar.save(failOnError: true)

        def result = userInstance.save(failOnError: true)

        // 유저 권한 생성
        UserRole.create(userInstance, Role.findByAuthority('ROLE_USER'), true)

        result
    }

    def createConfirmEmail(User userInstance) {

        def now = new Date()

        ConfirmEmail.where { user == userInstance }.deleteAll()

        def secured = "${now.time}_${userInstance.username}_${userInstance.person.email}_${grailsApplication.config.grails.mail.key}".encodeAsSHA256().encodeAsBase64()

        new ConfirmEmail(user: userInstance, securedKey: secured, email: userInstance.person.email, dateExpired: new Date(now.time+(30*60*1000))).save(flush: true, failOnError: true)

        return secured

    }
    
    def getManaedAvatars(def user = null) {

        def managedAvatar = ManagedUser.findAll()*.user*.avatar

        if(user) {
            managedAvatar.remove(user.avatar)
        }

        managedAvatar
    }
}
