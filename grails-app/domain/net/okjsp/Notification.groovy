package net.okjsp

class Notification {

    ActivityType type
    Avatar receiver
    Avatar sender
    Article article
    Content content

    Date dateCreated
    Date lastUpdated

    static constraints = {
    }

    static mapping = {
        sort lastUpdated : 'desc'
    }

    String toString() {
        return "${type} / receiver : ${receiver} / sender : ${sender} / article : ${article} / content : ${contentId}"
    }
}
