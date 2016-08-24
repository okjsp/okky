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

        "/article/$id(.$format)?"(controller: "article", action: "show")
        "/article/$action/$id(.$format)?"(controller: "article")
        "/article/$id/$action/$contentId(.$format)?"(controller: "article")
        
        "/articles/tagged/$tag(.$format)?"(controller: "article", action: "tagged")

        "/changes/$id(.$format)?"(controller: "article", action: "changes")

        "/content/$id(.$format)?"(controller: "content", action: "show")
        "/content/$action/$id(.$format)?"(controller: "content")

        "/notification(.$format)?"(controller: "notification", action: "index")

        "/notification/$action/$id?(.$format)?"(controller: "notification")
        
        "/seq/$id"(controller: "article", action: "seq")

        "/"(controller: "main", action: 'index')

        "/flush"(controller: "main", action: 'flush')
            
        "/file/image"(controller: "file", action: "image")
        
        "/bbs"(redirect: "/")

        "/banner/stats/$id(.$format)?"(controller: "banner", action: "stats")

        /* Admin */

        "/_admin/banner/$action?/$id?(.$format)?"(controller: "banner")
        "/_admin/spamWord/$action?/$id?(.$format)?"(controller: "spamWord")
        "/_admin/user/$action?/$id?(.$format)?"(controller: "adminUser")
        "/_admin/dm/export"(controller: "dm", action: 'export')
        "/_admin/dm/reject"(controller: "dm", action: 'reject')
        "/_admin/dm/updateReject"(controller: "dm", action: 'updateReject')
        "/_admin"(controller: "statistic")

        "/intro/about"(view: "/intro/about")

        "500"(view:'/error')
	}
}
