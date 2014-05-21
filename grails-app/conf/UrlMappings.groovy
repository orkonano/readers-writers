class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/telling/read/$id/$title"{
            controller = 'telling'
            action = 'read'
        }

        "/"(controller: "home", action: "index")
        "500"(view:'/error')
        "404"(view:'/pageNotFound')
	}
}
