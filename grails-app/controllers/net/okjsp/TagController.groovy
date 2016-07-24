package net.okjsp

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

@Transactional(readOnly = true)
@Secured("ROLE_USER")
class TagController {
    static scaffold = Tag
}
