package net.okjsp

class Tag {

    String name
    Integer taggedCount = 1
    String description

    Date dateCreated

    static hasMany = [similarTexts: TagSimilarText]

    static constraints = {
        name blank: false, unique: true
        description nullable: true
        similarTexts nullable: true
    }

    static mapping = {
        version false
    }

    String toString() { name }

}
