package net.okjsp

import grails.transaction.Transactional

@Transactional(readOnly = true)
class CategoryController {
    static scaffold = Category
}
