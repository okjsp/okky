package net.okjsp

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.SpringSecurityUtils
import org.ocpsoft.prettytime.PrettyTime
import org.springframework.web.servlet.support.RequestContextUtils

class ArticleTagLib {

    SpringSecurityService springSecurityService
    ArticleService articleService

    /**
     * view Author
     * @attr size REQUIRED
     * @attr avatar
     * @attr dateCreated
     * @attr pictureOnly true false
     */
    def avatar = { attrs, body ->
        def url, s
        def size = attrs.size
        def result = ""
        def cssClass = attrs.class ?: ''

        Avatar avatar = attrs.avatar ?: Avatar.get(springSecurityService.principal.avatarId)

        switch (size) {
            case "x-small":
                s = '10'
                break
            case "small":
                s = '15'
                break
            case "medium":
                s = '40'
                break
            case "big":
                s = '150'
                break
        }

        switch (avatar.pictureType) {
            case AvatarPictureType.FACEBOOK:
                url = "//graph.facebook.com/${avatar.picture}/picture?width=${s}&height=${s}"
                break
            case AvatarPictureType.ANONYMOUSE:
                url = "//www.gravatar.com/avatar/00000000000000000000000000000000?d=mm&s=${s}"
                break
            case AvatarPictureType.GRAVATAR:
                url = "//www.gravatar.com/avatar/${avatar.picture}?d=identicon&s=${s}"
                break
        }

        out << "<div class='avatar avatar-${size} clearfix ${cssClass}'>"
        
        if(avatar.id)
            out << "<a href='${request.contextPath}/user/info/${avatar.id}' class='avatar-photo'><img src='${url}'/></a> "
        else
            out << "<span class='avatar-photo'><img src='${url}'/></span>"

        if (attrs.pictureOnly != 'true') {
            out << """<div class="avatar-info">"""
            
            if(avatar.id)
                out << """<a class="nickname" href="${request.contextPath}/user/info/${avatar.id}">${avatar.nickname}</a> """
            else
                out << """<span class="nickname"><strong>${avatar.nickname}</strong></span> """

            if (attrs.dateCreated != null) {
                if(avatar.id)
                    out << """<div class="activity"><span class="fa fa-flash"></span> ${shortenNumber(avatar.activityPoint)}</div>"""
                
                out << """<div class="date-created timeago" title="${attrs.dateCreated}">${attrs.dateCreated}</div>"""
            } else {
                if(avatar.id)
                    out << """<div class="activity ${size == 'medium' ? 'block' : ''}"><span class="fa fa-flash"></span> ${shortenNumber(avatar.activityPoint)}</div>"""
            }

            out << "</div>"
        }

        out << "</div>"
    }

    /**
     * Article Vote Buttons
     * @attr content REQUIRED content
     * @attr votes Content votes list
     * @attr category Category
     */
    def voteButtons = { attrs, body ->

        def content = attrs.content
        def category = attrs.category

        def vote = attrs.votes.find { it.content.id == content.id}

        if(category.useEvaluate) {

            def assentVotedClass = vote ? (vote?.point > 0 ? 'unvote' : 'disable') : 'assent'
            def dissentVotedClass = vote ? (vote?.point < 0 ? 'unvote' : 'disable') : 'dissent'

            if(!vote || vote?.point > 0) {
                out << """<a href="javascript://" class="note-vote-btn" role="button" data-type="${assentVotedClass}" data-eval="${category.useEvaluate}" data-id="${content.id}">"""
                out << """<i id="note-evaluate-assent-${content.id}" class="fa fa-angle-up note-evaluate-assent-${assentVotedClass}" data-placement="left" data-toggle="tooltip" title="${assentVotedClass == 'unvote' ? '추천 취소' : '추천'}"></i></a>"""
            } else {
                out << """<a href="javascript://" class="note-vote-btn" role="button" data-type="disabled" data-eval="${category.useEvaluate}" data-id="${content.id}">"""
                out << """<i id="note-evaluate-assent-${content.id}" class="fa fa-angle-up note-evaluate-assent-disabled"></i></a>"""
            }

            out << """<div id="content-vote-count-${content.id}" class="content-eval-count">${shortenNumber(content.voteCount)}</div>"""

            if(!vote || vote?.point < 0) {
                out << """<a href="javascript://" class="note-vote-btn" role="button" data-type="${dissentVotedClass}" data-eval="${category.useEvaluate}" data-id="${content.id}">"""
                out << """<i id="note-evaluate-dissent-${content.id}" class="fa fa-angle-down note-evaluate-dissent-${dissentVotedClass}" data-placement="left" data-toggle="tooltip" title="${dissentVotedClass == 'unvote' ? '반대 취소' : '반대'}"></i></a>"""
            } else {
                out << """<a href="javascript://" class="note-vote-btn" role="button" data-type="disabled" data-eval="${category.useEvaluate}" data-id="${content.id}">"""
                out << """<i id="note-evaluate-dissent-${content.id}" class="fa fa-angle-down note-evaluate-dissent-disabled"></i></a>"""

            }

        } else {

            def votedClass = vote ? 'unvote' : 'vote'

            out << """<a href="javascript://" class="note-vote-btn" role="button" data-type="${votedClass}" data-eval="${category.useEvaluate}" data-id="${content.id}">"""
                out << """<i  id="note-vote-${content.id}" class="fa fa-thumbs-up note-${votedClass}" data-toggle="tooltip" data-placement="left" title="${vote ? '추천 취소' : '좋아요'}"></i></a>"""
            out << """<div id="content-vote-count-${content.id}" class="content-count">${shortenNumber(content.voteCount)}</div>"""
        }
    }

    /**
     * Current user is Author
     * @attr author Author REQUIRED
     */
    def isAuthor = { attrs, body ->
        if(springSecurityService.isLoggedIn() && attrs.author && springSecurityService.principal.avatarId == attrs.author.id) {
            out << body()
        }
    }

    /**
     * Current user is Author
     * @attr author Author REQUIRED
     */
    def isAuthorOrAdmin = { attrs, body ->
        if((springSecurityService.isLoggedIn() && attrs.author && springSecurityService.principal.avatarId == attrs.author.id)
            || SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")) {
            out << body()
        }
    }

    /**
     * Current user is Author
     * @attr author Author REQUIRED
     */
    def isNotAuthor = { attrs, body ->
        if(!attrs.author || !springSecurityService.isLoggedIn() || springSecurityService.principal.avatarId != attrs.author.id) {
            out << body()
        }
    }

    /**
     * Shorten Number
     * @attr number Original Number REQUIRED
     */
    def shorten = { attrs, body ->
        out << shortenNumber(attrs.number)
    }


    /**
     * HTML Filter
     * @attr text Original Text REQUIRED
     */
    def filterHtml = { attrs, body ->
        def text = attrs.text ?: body()
        out << text;
    }

    /**
     * Line to br
     * @attr text Original Text REQUIRED
     */
    def lineToBr = { attrs, body ->
        def text = attrs.text ?: body()
        text = text.encodeAsHTML()
        out << text.replaceAll("\n", '<br/>');
    }

    /**
     *
     */
    def description = { attrs, body ->
        String text = attrs.text ?: body()
        def length = attrs.length ?: 0
        text = text.replaceAll("<(style|script)(.*?)>(.*?)</(.*?)>", '');
        text = text.replaceAll("&(.*?);", ' ');
        text = text.replaceAll("<(.*?)>", '');
        text = text.replaceAll("\n", ' ');

        if(length > 0 && length < text.size()) {
            text = text.substring(0, length)
        }

        out << text
    }


    /**
     * Tag List
     * @attr tags REQUIRED
     * @attr class class names
     */
    def tags = { attrs, body ->
        def tags = attrs.tags
        def classNames = attrs.class ?: 'label-gray'

        if(tags instanceof String)
            tags = tags.split(',')

        tags?.each {
            out << """<span class="list-group-item-text item-tag label ${classNames}">${it}</span> """
        }
    }

    /**
     * Category Label
     * @attr category REQUIRED
     */
    def categoryLabel = { attrs, body ->
        def category = attrs.category

        out << """<a href="${request.contextPath}/articles/${category.code}" class="list-group-item-text item-tag label label-info"><i class="${category.iconCssNames}"></i> ${g:message([code: category.labelCode, default: category.defaultLabel])}</a>"""
    }

    private def shortenNumber(def orgNumber) {

        def unit = ['','k','m','b','t']
        int count = 0
        int number = orgNumber > 0 ? orgNumber : orgNumber*-1
        while (number >= 1000) {
            number /= 1000
            count++
        }
        
        if(orgNumber < 0) number *= -1

        "${number}${unit[count]}"
    }


}
