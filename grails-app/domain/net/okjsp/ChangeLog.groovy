package net.okjsp

class ChangeLog {

    Article article
    Content content

    ChangeLogType type
    String md5
    String text

    int revision

    Date dateCreated

    static constraints = {
        content nullable: true
    }

    static mapping = {
        text type: 'text'
    }
}
