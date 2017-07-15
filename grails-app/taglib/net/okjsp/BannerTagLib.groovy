package net.okjsp

import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest
import org.codehaus.groovy.grails.web.util.WebUtils
import org.springframework.mobile.device.Device

class BannerTagLib {

    def randomService
    def deviceResolver

    /**
     * @attr type REQUIRED
     */
    def banner = { attrs, body ->

        def bannerType = attrs.type

        if(!(bannerType instanceof BannerType)) bannerType = BannerType.valueOf(bannerType)

        def banners = Banner.where {
            type == bannerType && visible == true
        }.list()

        def banner = banners ? randomService.draw(banners) : null
        def bannerHTML = ""


        GrailsWebRequest webUtils = WebUtils.retrieveGrailsWebRequest()
        def request = webUtils.getCurrentRequest()

        Device device =  deviceResolver.resolveDevice(request)


        if(banner) {

            def target = """target=\"${banner.target}\""""
            switch (bannerType) {

                case BannerType.MAIN_RIGHT :
                case BannerType.SUB_RIGHT :
                    bannerHTML = """<div class="right-banner">
                                    <a href="${request.contextPath}/banner/stats/${banner.id}" ${banner.target ? target : ''}><img src="${banner.image}" style="width:160px;"/></a>
                                </div>"""

                    break
                case BannerType.MAIN :
                case BannerType.JOBS_TOP :
                    bannerHTML = """<div class="main-banner-wrapper">
                                        <div class="main-banner"><a href="${request.contextPath}/banner/stats/${banner.id}" ${banner.target ? target : ''}><img src="${banner.image}" /></a></div>
                                    </div>"""
                    break
                case BannerType.CONTENT :
                    bannerHTML = """<div class="sub-banner-wrapper">
                                        <div class="sub-banner"><a href="${request.contextPath}/banner/stats/${banner.id}" ${banner.target ? target : ''}><img src="${banner.image}" /></a></div>
                                    </div>"""
                    break
            }
        } else {

            if(!device.isMobile()) {
                switch (bannerType) {
                    case BannerType.MAIN:
                        bannerHTML += """
                            <div class="main-banner-wrapper">
                            <style type="text/css">
                               .adslot_2 {display:inline-block; width: 728px; height: 90px; }
                               @media (max-width:1025px) { .adslot_2 { display: none; } }
                            </style>
                            <ins class="adsbygoogle adslot_2"
                                data-ad-client="ca-pub-1191230850516122"
                                data-ad-slot="6710356887"></ins>
                            <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
                            <script>(adsbygoogle = window.adsbygoogle || []).push({});</script>
                            </div>"""
                        break
                    case BannerType.CONTENT:
                        bannerHTML += """
                            <div class="sub-banner-wrapper">
                            <style type="text/css">
                               .adslot_2 {display:inline-block; width: 728px; height: 90px; }
                               @media (max-width:1025px) { .adslot_2 { display: none; } }
                            </style>
                            <ins class="adsbygoogle adslot_2"
                                data-ad-client="ca-pub-1191230850516122"
                                data-ad-slot="6710356887"></ins>
                            <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
                            <script>(adsbygoogle = window.adsbygoogle || []).push({});</script>
                            </div>"""
                        break
                }
            }
        }

        if(bannerType == BannerType.MAIN_RIGHT || bannerType == BannerType.SUB_RIGHT) {

            if(!device.isMobile()) {

                bannerHTML += """<div class="google-ad">
                        <style type="text/css">
                           .adslot_1 {display:inline-block; width: 160px; height: 600px; }
                           @media (max-width:1240px) { .adslot_1 { display: none; } }
                        </style>
                        <ins class="adsbygoogle adslot_1"
                            data-ad-client="ca-pub-1191230850516122"
                            data-ad-slot="9306413607"></ins>
                        <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
                        <script>(adsbygoogle = window.adsbygoogle || []).push({});</script>
                     </div>"""

            }
        }

        out << bannerHTML
    }
}
