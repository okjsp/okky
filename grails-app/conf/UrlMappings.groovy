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
        "/recruits/company"(controller: "recruit", action: 'createCompany')
        "/recruits/company/save"(controller: "recruit", action: 'saveCompany')
        "/recruits/company/registered"(view: "/recruit/registeredCompany")
        "/recruits/company/wait"(view: "/recruit/waitCompany")

        "/recruit/$id(.$format)?"(controller: "recruit", action: "show")
        "/recruit/$action/$id(.$format)?"(controller: "recruit")
        "/recruit/$id/$action/$contentId(.$format)?"(controller: "recruit")

        "/notification(.$format)?"(controller: "notification", action: "index")

        "/notification/$action/$id?(.$format)?"(controller: "notification")
        
        "/seq/$id"(controller: "article", action: "seq")

        "/"(controller: "main", action: 'index')

        "/flush"(controller: "main", action: 'flush')
            
        "/file/image"(controller: "file", action: "image")
        
        "/bbs"(redirect: "/")

        "/company/$action/$id(.$format)?"(controller: "company")

        "/banner/stats/$id(.$format)?"(controller: "banner", action: "stats")

        /* Admin */

        "/_admin/banner/$action?/$id?(.$format)?"(controller: "banner")
        "/_admin/spamWord/$action?/$id?(.$format)?"(controller: "spamWord")
        "/_admin/user/$action?/$id?(.$format)?"(controller: "adminUser")
        "/_admin/company/$action?/$id?(.$format)?"(controller: "adminCompany")
        "/_admin/dm/export"(controller: "dm", action: 'export')
        "/_admin/dm/reject"(controller: "dm", action: 'reject')
        "/_admin/dm/updateReject"(controller: "dm", action: 'updateReject')
        "/_admin"(controller: "statistic")

        "/intro/about"(view: "/intro/about")

        "500"(view:'/error')
	}
}
