class UrlMappings {

	static mappings = {

        /*"/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }*/

        "/login?/$action"(controller: "login")
        "/logout?/$action"(controller: "logout")
        "/user/$action"(controller: "user")
        "/user/info/$id"(controller: "user", action: "index")
        "/user/info/$id/$category?"(controller: "user", action: "index")
        "/user/privacy"(view: '/user/privacy')
        "/user/agreement"(view: '/user/agreement')

        "/find/user?/$action"(controller: "findUser")

        "/articles/$code/$action?(.$format)?"(controller: "article")
        "/articles/tagged/$tag(.$format)?"(controller: "article", action: "tagged")

        "/article/$id(.$format)?"(controller: "article", action: "show")
        "/article/$action/$id(.$format)?"(controller: "article")
        "/article/$id/$action/$contentId(.$format)?"(controller: "article")

        "/changes/$id(.$format)?"(controller: "article", action: "changes")

        "/content/$id(.$format)?"(controller: "content", action: "show")
        "/content/$action/$id(.$format)?"(controller: "content")

        "/recruits/$action?(.$format)?"(controller: "recruit")
        "/recruits/tagged/$tag(.$format)?"(controller: "recruit", action: "tagged")

        "/recruit/$id(.$format)?"(controller: "recruit", action: "show")
        "/recruit/$action/$id(.$format)?"(controller: "recruit")
        "/recruit/delete/$id(.$format)?"(controller: "recruit", action: "delete")
        "/recruit/$id/$action/$contentId(.$format)?"(controller: "recruit")

        "/notification(.$format)?"(controller: "notification", action: "index")

        "/notification/$action/$id?(.$format)?"(controller: "notification")
        
        "/seq/$id"(controller: "article", action: "seq")


        "/"(controller: "main", action: 'index')

        "/flush"(controller: "main", action: 'flush')
            
        "/file/image"(controller: "file", action: "image")
        
        "/bbs"(redirect: "/")

        "/company/$action/$id(.$format)?"(controller: "company")

        "/company/create"(controller: "company", action: "create")
        "/company/save"(controller: "company", action: "save")
        "/company/edit"(controller: "company", action: "edit")
        "/company/update"(controller: "company", action: "update")
        "/company/registered"(view: "/company/registered")
        "/company/updated"(view: "/company/updated")
        "/company/wait"(view: "/company/wait")

        "/banner/stats/$id(.$format)?"(controller: "banner", action: "stats")

        /* Admin */

        "/_admin/banner/$action?/$id?(.$format)?"(controller: "banner")
        "/_admin/spamWord/$action?/$id?(.$format)?"(controller: "spamWord")
        "/_admin/user/$action?/$id?(.$format)?"(controller: "adminUser")
        "/_admin/company/$action?/$id?(.$format)?"(controller: "adminCompany")
        "/_admin/job/group/$action?/$id?(.$format)?"(controller: "jobPositionGroup")
        "/_admin/job/duty/$action?/$id?(.$format)?"(controller: "jobPositionDuty")
        "/_admin/dm/export"(controller: "dm", action: 'export')
        "/_admin/dm/reject"(controller: "dm", action: 'reject')
        "/_admin/dm/updateReject"(controller: "dm", action: 'updateReject')
        "/_admin"(controller: "statistic")

        "/intro/about"(view: "/intro/about")
        "/intro/ad"(controller: "intro", action: "ad")

        "/event"(controller: "event", action: "index")
        "/event/$id(.$format)?"(controller: "event", action: "show")
        "/event/$id/request"(controller: "event", action: "request")

        "/autoPassword/$action" (controller: "autoPassword")

        "/grails-errorhandler" (redirect: "/")


        "500"(view:'/error')
	}
}
