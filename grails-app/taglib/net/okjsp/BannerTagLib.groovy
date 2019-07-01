package net.okjsp

import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest
import org.codehaus.groovy.grails.web.util.WebUtils
import org.springframework.mobile.device.Device

class BannerTagLib {

    def randomService
    def deviceResolver
    def bannerService

    /**
     * @attr type REQUIRED
     */
    def banner = { attrs, body ->

        def bannerType = attrs.type

        if(!(bannerType instanceof BannerType)) bannerType = BannerType.valueOf(bannerType)


        def banners = bannerService.get(bannerType)

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
                    case BannerType.CONTENT_TOP :
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
                    case BannerType.MAIN_BLOCK:
                        if(banner.contentType == BannerContentType.TAG) {
                            if(device.isMobile() && banner.tagMobile) {
                                bannerHTML = """<div class="main-block main-block-banner">${banner.tagMobile}</div>"""
                            } else if(!device.isMobile() && banner.tagDesktop) {
                                bannerHTML = """<div class="main-block main-block-banner">${banner.tagDesktop}</div>"""
                            }
                        } else if(banner.contentType == BannerContentType.IMAGE_URL || banner.contentType == BannerContentType.IMAGE_FILE) {
                            bannerHTML = """<div class="main-block main-block-banner"><a href="${request.contextPath}/banner/stats/${banner.id}" ${banner.target ? target : ''}><img src="${banner.image}" /></a></div>"""
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
                case BannerType.JOBS_TOP :
                    bannerHTML = """
                    <div class="main-banner-wrapper">
                    <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
                    <!-- 728-90_Ressponsive -->
                    <ins class="adsbygoogle"
                         style="display:block"
                         data-ad-client="ca-pub-8103607814406874"
                         data-ad-slot="8622179990"
                         data-ad-format="auto"></ins>
                    <script>
                    (adsbygoogle = window.adsbygoogle || []).push({});
                    </script>
                    </div>"""
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

                case BannerType.MAIN_RIGHT_TOP :
                    if(!device.isMobile()) {
                        bannerHTML = """
                        <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
                        <!-- Main_250_600 -->
                        <ins class="adsbygoogle"
                             style="display:block"
                             data-ad-client="ca-pub-8103607814406874"
                             data-ad-slot="8985230627"
                             data-ad-format="auto"
                             data-full-width-responsive="true"></ins>
                        <script>
                             (adsbygoogle = window.adsbygoogle || []).push({});
                        </script>
                        """
                    }
                case BannerType.SUB_RIGHT_TOP :
                    if(!device.isMobile()) {
                        bannerHTML = """
                            <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
                            <!-- Content_250_600 -->
                            <ins class="adsbygoogle"
                                 style="display:block"
                                 data-ad-client="ca-pub-8103607814406874"
                                 data-ad-slot="5287913799"
                                 data-ad-format="auto"
                                 data-full-width-responsive="true"></ins>
                            <script>
                                 (adsbygoogle = window.adsbygoogle || []).push({});
                            </script>
"""
                    }
                    break
                case BannerType.MAIN_RIGHT_BOTTOM:
                case BannerType.SUB_RIGHT_BOTTOM:
                    if(!device.isMobile()) {
                        bannerHTML = """
                            <!-- Dable 우_EMPTY_250x250 위젯 시작/ 문의 http://dable.io -->
                            <div id="dablewidget_2XnEn2ld" data-widget_id="2XnEn2ld">
                            <script>
                            (function(d,a,b,l,e,_) {
                            if(d[b]&&d[b].q)return;d[b]=function(){(d[b].q=d[b].q||[]).push(arguments)};e=a.createElement(l);
                            e.async=1;e.charset='utf-8';e.src='//static.dable.io/dist/plugin.min.js';
                            _=a.getElementsByTagName(l)[0];_.parentNode.insertBefore(e,_);
                            })(window,document,'dable','script');
                            dable('setService', 'okky.kr');
                            dable('sendLogOnce');
                            dable('renderWidget', 'dablewidget_2XnEn2ld', {ignore_items: true});
                            </script>
                            </div>
                            <!-- Dable 우_EMPTY_250x250 위젯 종료/ 문의 http://dable.io -->
                        """
                    }
                    break
                case BannerType.CONTENT_TOP:
                    bannerHTML = """
                    <div class="sub-dable-banner-wrapper">
                        <!-- Dable 상_가로1_EMPTY_secret 위젯 시작/ 문의 http://dable.io -->
                        <div id="dablewidget_klrEYJ7m" data-widget_id="klrEYJ7m">
                        <script>
                        (function(d,a,b,l,e,_) {
                        if(d[b]&&d[b].q)return;d[b]=function(){(d[b].q=d[b].q||[]).push(arguments)};e=a.createElement(l);
                        e.async=1;e.charset='utf-8';e.src='//static.dable.io/dist/plugin.min.js';
                        _=a.getElementsByTagName(l)[0];_.parentNode.insertBefore(e,_);
                        })(window,document,'dable','script');
                        dable('setService', 'okky.kr');
                        dable('sendLogOnce');
                        dable('renderWidget', 'dablewidget_klrEYJ7m', {ignore_items: true});
                        </script>
                        </div>
                        <!-- Dable 상_가로1_EMPTY_secret 위젯 종료/ 문의 http://dable.io -->
                    </div>
                    """
                    break
            }
        }

        out << bannerHTML
    }
}
