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

        if(banner) {
            def bannerHTML = ""

            def target = """target=\"${banner.target}\""""


            GrailsWebRequest webUtils = WebUtils.retrieveGrailsWebRequest()
            def request = webUtils.getCurrentRequest()

            Device device =  deviceResolver.resolveDevice(request)

            switch (bannerType) {
                
                case BannerType.MAIN_RIGHT :
                case BannerType.SUB_RIGHT :


                    if(device.isMobile()) {
                        bannerHTML = """
                            <script type="text/javascript" src="http://ad.appsary.com/ad/22019/tag.js"></script>
                        """
                    } else {

                        bannerHTML = """<div class="right-banner">
                                        <a href="${request.contextPath}/banner/stats/${banner.id}" ${banner.target ? target : ''}><img src="${banner.image}" style="width:160px;"/></a>
                                    </div>"""

                        bannerHTML += """<div class="google-ad">
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
                case BannerType.MAIN : 
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

            out << bannerHTML
        }
    }
}
