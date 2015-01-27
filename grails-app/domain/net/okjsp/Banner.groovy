package net.okjsp

class Banner {

    BannerType type
    String name
    String image
    String url
    int clickCount = 0
    boolean visible = true

    Date dateCreated
    Date lastUpdated

    static constraints = {
    }

    static mapping = {
        cache true
    }
}
