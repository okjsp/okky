package net.okjsp

import grails.plugin.cache.Cacheable

class Category implements Serializable {

    String code
    String labelCode
    String defaultLabel
    String url
    String iconCssNames

    boolean writable = true
    boolean enabled = true
    boolean isURL = false
    boolean adminOnly = false
    Integer level = 1
    Integer sortOrder = 0

    boolean useTag
    boolean useEvaluate
    boolean useNote
    boolean useOpinion
    boolean useSelectSolution = false
    boolean requireTag = false
    Boolean anonymity = false
    
    Boolean writeByExternalLink = false
    String externalLink

    static belongsTo = [parent: Category]

    static hasMany = [children: Category]

    Date dateCreated
    Date lastUpdated

    static constraints = {
        code matches: /^[a-z_\-\d]+$/
        parent nullable: true
        url nullable: true
        useTag nullable: true
        useEvaluate nullable: true
        useNote nullable: true
        useOpinion nullable: true
        iconCssNames nullable: true
        anonymity nullable: true
        writeByExternalLink nullable: true
        externalLink nullable: true
        adminOnly nullable: true
    }

    static mapping = {
        id generator: "assigned", name: 'code', type: 'string'
        children sort: 'sortOrder', cache: true
        parent cache: true
        sort 'sortOrder'
        cache true
    }

    def getId() { code }

    def beforeInsert() {
        getLevelFromParent()
    }

    def beforeUpdate() {
        if (isDirty('parent')) {
            getLevelFromParent()
        }
    }

    static def getTopCategories() {
        Category.findAllByLevelAndEnabled(1, true)
    }

    protected void getLevelFromParent() {
        level = parent ? parent.level+1 : 1
    }

    String toString() { code }
}
