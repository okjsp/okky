package net.okjsp

import grails.plugin.springsecurity.userdetails.GrailsUser
import org.springframework.security.core.GrantedAuthority

/**
 * Created by langerhans on 2014. 7. 17..
 */
class CustomUserDetail extends GrailsUser {

    final long avatarId
    final long personId

    CustomUserDetail(String username, String password, boolean enabled,
            boolean accountNonExpired, boolean credentialsNonExpired,
            boolean accountNonLocked,
            Collection<GrantedAuthority> authorities,
            long id, long avatarId, long personId) {
        super(username, password, enabled, accountNonExpired,
            credentialsNonExpired, accountNonLocked, authorities, id)

        this.avatarId = avatarId
        this.personId = personId
    }
}
