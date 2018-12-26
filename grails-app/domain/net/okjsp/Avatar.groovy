package net.okjsp

import grails.plugin.springsecurity.oauth.FacebookOAuthToken
import grails.plugin.springsecurity.oauth.GoogleOAuthToken
import grails.transaction.Transactional

class Avatar implements Comparable {

    static enum PictureType {
    }

    String nickname
    String picture
    AvatarPictureType pictureType = AvatarPictureType.GRAVATAR
    Integer activityPoint = 0
    Boolean official = false

    static hasMany = [activities: Activity, tags : Tag]

    static mapping = {
        pictureType enumType: 'ordinal'
        cache true
    }

    static constraints = {
        nickname blank: false, unique: true, maxSize: 20, validator: {
            if(User.disAllowUsernameFilter(it) || disAllowNicknameFilter(it))
                return ['default.invalid.disallow.message']
        }
        picture blank: false
        activityPoint bindable: false
        official nullable: true, bindable: false
    }

    def setPictureBySns(User user, AvatarPictureType type = pictureType) {

        pictureType = type

        switch (type) {
            case AvatarPictureType.FACEBOOK :
                picture = user.oAuthIDs.find { it.provider == FacebookOAuthToken.PROVIDER_NAME}.accessToken
                break
            case AvatarPictureType.GRAVATAR :
                picture = user.person.email.encodeAsMD5()
                break
            default:
                break
        }
    }

    static boolean disAllowNicknameFilter(nickname) {
        return ['OKJSP관리자','옥히','옥희','OKJSP','운영진','운영자'
                ,'OKJSP운영진','OKJSP운영자','부운영자','옥히관리자','옥희관리자'].contains(nickname)
    }

    @Transactional
    def updateActivityPoint(def i) {
        if(id != null && i != 0) {
            executeUpdate("update Avatar set activityPoint = activityPoint+:i where id = :id",[i:i, id: id])
        }
    }

    int compareTo(Object other) {
        id <=> other.id
    }

    String toString() {
        nickname
    }
}
