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
        "/user/privacy"(view: '/user/privacy')
        "/user/agreement"(view: '/user/agreement')

        "/find/user?/$action"(controller: "findUser")

        "/articles/$code/$action?(.$format)?"(controller: "article")

        "/article/$id(.$format)?"(controller: "article", action: "show")
        "/article/$action/$id(.$format)?"(controller: "article")
        "/article/$id/$action/$contentId(.$format)?"(controller: "article")


        "/content/$id(.$format)?"(controller: "content", action: "show")
        "/content/$action/$id(.$format)?"(controller: "content")

        "/notification(.$format)?"(controller: "notification", action: "index")

        "/notification/$action/$id?(.$format)?"(controller: "notification")

        "/"(controller: "main", action: 'index')


        /* Admin */

        "/_admin/banner/$action?/$id?(.$format)?"(controller: "banner")

        "/intro/about"(view: "/intro/about")

        "500"(view:'/error')
	}
}
