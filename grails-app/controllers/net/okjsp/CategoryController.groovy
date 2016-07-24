package net.okjsp

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

@Transactional(readOnly = true)
@Secured("permitAll")
class CategoryController {
    static scaffold = Category
}
