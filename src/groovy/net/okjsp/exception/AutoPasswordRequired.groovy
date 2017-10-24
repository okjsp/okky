package net.okjsp.exception

import org.springframework.security.core.AuthenticationException

class AutoPasswordRequired extends AuthenticationException {
    AutoPasswordRequired(String msg) {
        super(msg)
    }

    AutoPasswordRequired(String msg, Throwable t) {
        super(msg, t)
    }

    @Deprecated
    AutoPasswordRequired(String msg, Object extraInformation) {
        super(msg, extraInformation)
    }
}
