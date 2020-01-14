import grails.plugin.springsecurity.SpringSecurityUtils
import net.okjsp.CustomUserDetailService
import net.okjsp.encoding.OldPasswordEncoder
import net.okjsp.handler.TokenStrategySuccessHandler
import net.okjsp.listeners.CustomSecurityEventListener

// Place your Spring DSL code here
beans = {

    def conf = SpringSecurityUtils.securityConfig

    userDetailsService(CustomUserDetailService)
    securityEventListener(CustomSecurityEventListener)
    passwordEncoder(OldPasswordEncoder)
    authenticationSuccessHandler(TokenStrategySuccessHandler) {
        requestCache = ref('requestCache')
        defaultTargetUrl = conf.successHandler.defaultTargetUrl // '/'
        alwaysUseDefaultTargetUrl = conf.successHandler.alwaysUseDefault // false
        targetUrlParameter = conf.successHandler.targetUrlParameter // 'spring-security-redirect'
        ajaxSuccessUrl = conf.successHandler.ajaxSuccessUrl // '/login/ajaxSuccess'
        useReferer = conf.successHandler.useReferer // false
        redirectStrategy = ref('redirectStrategy')
    }

    /*userDetailsServiceWrapper(UserDetailsByNameServiceWrapper) {
        userDetailsService = ref('userDetailsService')
    }
    cookiePreAuthFilter(CookiePreAuthFilter) {
        authenticationManager = ref("authenticationManager")
        springSecurityService = ref("springSecurityService")
        cookieService = ref("cookieService")
        grailsApplication = ref("grailsApplication")
    }
    preAuthProvider(PreAuthenticatedAuthenticationProvider) {
        preAuthenticatedUserDetailsService = ref("userDetailsServiceWrapper")
    }*/

}
