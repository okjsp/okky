package net.okjsp

class Banner {

    BannerType type
    String name
    String image
    String url
    String target
    int clickCount = 0
    boolean visible = true

    Date dateCreated
    Date lastUpdated

    static constraints = {
        target nullable: true
    }

    static mapping = {
        cache true
    }
}
