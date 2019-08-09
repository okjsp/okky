package net.okjsp

import grails.plugin.cache.CacheEvict
import grails.plugin.cache.CachePut
import grails.plugin.cache.Cacheable
import grails.transaction.Transactional

@Transactional
class BannerService {

    @Cacheable(value="banners", key="#type")
    def get(BannerType type) {
        def banners = Banner.where {
            type == type && visible == true
        }.list()

        banners
    }

    @CacheEvict(value='banners', allEntries = true)
    void save(Banner banner) {
        println "Saving message $banner"
        banner.save(flush:true)
    }

    @CacheEvict(value='banners', allEntries = true)
    void delete(Banner banner) {
        println "Deleting message $banner"
        banner.delete(flush:true)
    }
}
