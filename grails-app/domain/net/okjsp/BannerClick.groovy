package net.okjsp

import grails.util.Environment

class BannerClick {

    Banner banner
    Date dateCreated
    String dateString

    int clickCount = 0

    String ip

    static constraints = {
    }

    static mapping = {
        if (Environment.current == Environment.DEVELOPMENT)
            dateString formula: "FORMATDATETIME(date_created, 'yyyy-MM-dd')"

        if (Environment.current == Environment.PRODUCTION)
            dateString formula: "DATE_FORMAT(date_created, '%Y-%m-%d')"
    }
}
