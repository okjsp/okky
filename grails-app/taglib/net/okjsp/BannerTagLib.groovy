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

                    case BannerType.MAIN_RIGHT_TOP :
                    case BannerType.SUB_RIGHT_TOP :
                        if(banner.contentType == BannerContentType.TAG) {
                            if(device.isMobile() && banner.tagMobile) {
                                bannerHTML = """<div class="right-banner">${banner.tagMobile}</div>"""
                            } else if(!device.isMobile() && banner.tagDesktop) {
                                bannerHTML = """<div class="right-banner">${banner.tagDesktop}</div>"""
                            }
                        } else if(banner.contentType == BannerContentType.IMAGE_URL || banner.contentType == BannerContentType.IMAGE_FILE) {
                            bannerHTML = """<div class="right-banner">
                                        <a href="${request.contextPath}/banner/stats/${banner.id}" ${banner.target ? target : ''}><img src="${banner.image}" style="width:160px;"/></a>
                                    </div>"""
                        }
                        break
                    case BannerType.MAIN :
                    case BannerType.JOBS_TOP :
                        if(banner.contentType == BannerContentType.TAG) {
                            if(device.isMobile() && banner.tagMobile) {
                                bannerHTML = """<div class="main-banner-wrapper"><div class="main-banner">${banner.tagMobile}</div></div>"""
                            } else if(!device.isMobile() && banner.tagDesktop) {
                                bannerHTML = """<div class="main-banner-wrapper"><div class="main-banner">${banner.tagDesktop}</div></div>"""
                            }
                        } else if(banner.contentType == BannerContentType.IMAGE_URL || banner.contentType == BannerContentType.IMAGE_FILE) {
                            bannerHTML = """<div class="main-banner-wrapper">
                                            <div class="main-banner"><a href="${request.contextPath}/banner/stats/${banner.id}" ${banner.target ? target : ''}><img src="${banner.image}" /></a></div>
                                        </div>"""
                        }

                        break
                    case BannerType.CONTENT :
                        if(banner.contentType == BannerContentType.TAG) {
                            if(device.isMobile() && banner.tagMobile) {
                                bannerHTML = """<div class="sub-banner-wrapper"><div class="sub-banner">${banner.tagMobile}</div></div>"""
                            } else if(!device.isMobile() && banner.tagDesktop) {
                                bannerHTML = """<div class="sub-banner-wrapper"><div class="sub-banner">${banner.tagDesktop}</div></div>"""
                            }
                        } else if(banner.contentType == BannerContentType.IMAGE_URL || banner.contentType == BannerContentType.IMAGE_FILE) {
                            bannerHTML = """<div class="sub-banner-wrapper">
                                            <div class="sub-banner"><a href="${request.contextPath}/banner/stats/${banner.id}" ${banner.target ? target : ''}><img src="${banner.image}" /></a></div>
                                        </div>"""
                        }
                        break
                    case BannerType.MAIN_RIGHT_BOTTOM :
                    case BannerType.SUB_RIGHT_BOTTOM :
                        if(banner.contentType == BannerContentType.TAG) {
                            if(device.isMobile() && device.isMobile() && banner.tagMobile) {
                                bannerHTML = """<div class="google-ad">${banner.tagMobile}</div>"""
                            } else if(!device.isMobile() && banner.tagDesktop) {
                                bannerHTML = """<div class="google-ad">${banner.tagDesktop}</div>"""
                            }
                        } else if(banner.contentType == BannerContentType.IMAGE_URL || banner.contentType == BannerContentType.IMAGE_FILE) {
                            if(!device.isMobile()) {
                                bannerHTML = """<div class="google-ad"><a href="${request.contextPath}/banner/stats/${
                                    banner.id
                                }" ${banner.target ? target : ''}><img src="${banner.image}" /></a></div>"""
                            }
                        }

            }
        } else {

            switch (bannerType) {
                case BannerType.MAIN:
                case BannerType.CONTENT:
                    if(!device.isMobile()) {
                        bannerHTML = """
                        <div class="sub-banner-wrapper">
                        <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
                        <!-- okkyad_728x90 -->
                        <ins class="adsbygoogle"
                             style="display:inline-block;width:728px;height:90px"
                             data-ad-client="ca-pub-8103607814406874"
                             data-ad-slot="4317461060"></ins>
                        <script>
                        (adsbygoogle = window.adsbygoogle || []).push({});
                        </script>
                        </div>"""
                    }
                    break
                case BannerType.MAIN_BLOCK:
                    if(!device.isMobile()) {
                        bannerHTML = """
                            <div class="main-block">
                            <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
                            <!-- okkyad_250x250 -->
                            <ins class="adsbygoogle"
                                 style="display:inline-block;width:250px;height:250px"
                                 data-ad-client="ca-pub-8103607814406874"
                                 data-ad-slot="4095178752"></ins>
                            <script>
                            (adsbygoogle = window.adsbygoogle || []).push({});
                            </script>
                            </div>
                          """
                    }
                    break
                case BannerType.MAIN_RIGHT_BOTTOM:
                    if(device.isMobile()) {
                        bannerHTML = """
                            <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
                            <script>
                              (adsbygoogle = window.adsbygoogle || []).push({
                                google_ad_client: "ca-pub-8103607814406874",
                                enable_page_level_ads: true
                              });
                            </script>
                            """
                    }

                case BannerType.SUB_RIGHT_BOTTOM:

                    if(!device.isMobile()) {
                        bannerHTML = """<div class="google-ad">
                            <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
                            <!-- okjspad_160x600 -->
                            <ins class="adsbygoogle"
                                 style="display:inline-block;width:160px;height:600px"
                                 data-ad-client="ca-pub-8103607814406874"
                                 data-ad-slot="6573675943"></ins>
                            <script>
                                (adsbygoogle = window.adsbygoogle || []).push({});
                            </script>
                        </div>"""
                    }
                    break
            }
        }

        out << bannerHTML
    }
}
