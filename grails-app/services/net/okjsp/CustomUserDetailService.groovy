package net.okjsp

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.userdetails.GrailsUserDetailsService
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.util.WebUtils
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException

import javax.servlet.http.HttpSession

class CustomUserDetailService implements GrailsUserDetailsService {

    static final List NO_ROLES = [new SimpleGrantedAuthority(SpringSecurityUtils.NO_ROLE)]

    UserDetails loadUserByUsername(String username, boolean loadRoles)
        throws UsernameNotFoundException {
        return loadUserByUsername(username)
    }

    @Transactional
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = User.findByUsername(username)
            if (!user || user.withdraw) throw new UsernameNotFoundException(
                'User not found', username)

            def authorities = user.authorities.collect {
                new SimpleGrantedAuthority(it.authority)
            }

            return new CustomUserDetail(user.username, user.password, user.enabled,
                !user.accountExpired, !user.passwordExpired,
                !user.accountLocked, authorities ?: NO_ROLES, user.id,
                user.avatarId, user.personId)
    }

}
