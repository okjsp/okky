package net.okjsp

import grails.transaction.Transactional

@Transactional(readOnly = true)
class TagController {
    static scaffold = Tag
}
