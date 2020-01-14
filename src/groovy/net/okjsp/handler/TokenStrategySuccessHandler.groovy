package net.okjsp.handler

import grails.plugin.springsecurity.SpringSecurityUtils
import net.okjsp.UserService
import net.okjsp.utils.TokenUtil
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.security.web.savedrequest.HttpSessionRequestCache
import org.springframework.security.web.savedrequest.RequestCache
import org.springframework.security.web.savedrequest.SavedRequest
import org.springframework.util.StringUtils

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class TokenStrategySuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    protected String ajaxSuccessUrl
    protected final Log logger = LogFactory.getLog(this.getClass())
    private RequestCache requestCache = new HttpSessionRequestCache()

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
        if (SpringSecurityUtils.isAjax(request)) {
            return ajaxSuccessUrl
        }
        return super.determineTargetUrl(request, response)
    }

    /**
     * Dependency injection for the Ajax success url, e.g. '/login/ajaxSuccess'
     * @param url the url
     */
    void setAjaxSuccessUrl(final String url) {
        ajaxSuccessUrl = url
    }

    void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        SavedRequest savedRequest = this.requestCache.getRequest(request, response)
        String targetUrlParameter = this.getTargetUrlParameter()
        def conf = SpringSecurityUtils.securityConfig
        if(request.getParameter("strategy") == 'token') {
            this.clearAuthenticationAttributes(request)
            String targetUrl = request.getParameter(targetUrlParameter)
            this.logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl)
            this.getRedirectStrategy().sendRedirect(request, response, targetUrl + "?token="+ TokenUtil.create(authentication.principal.username, conf.rememberMe.key))
        } else {
            if (savedRequest == null) {
                super.onAuthenticationSuccess(request, response, authentication)
            } else {
                if (!this.isAlwaysUseDefaultTargetUrl() && (targetUrlParameter == null || !StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
                    this.clearAuthenticationAttributes(request)
                    String targetUrl = savedRequest.getRedirectUrl()
                    this.logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl)
                    this.getRedirectStrategy().sendRedirect(request, response, targetUrl)
                } else {
                    this.requestCache.removeRequest(request, response)
                    super.onAuthenticationSuccess(request, response, authentication)
                }
            }
        }
    }

    void setRequestCache(RequestCache cache) {
        requestCache = cache
    }
}
