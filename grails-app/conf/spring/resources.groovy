import net.okjsp.CustomUserDetailService
import net.okjsp.encoding.OldPasswordEncoder
import net.okjsp.listeners.CustomSecurityEventListener

// Place your Spring DSL code here
beans = {
    userDetailsService(CustomUserDetailService)
    securityEventListener(CustomSecurityEventListener)
    passwordEncoder(OldPasswordEncoder)

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
