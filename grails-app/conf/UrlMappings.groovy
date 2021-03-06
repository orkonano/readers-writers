class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/authors"(resources:'author') {
            "/followers"(resources:"follower")
        }

        "/tellings"(resources:"telling") {
            "/likes"(resources:"tellingLike")
        }

        "/tellings"(resources:"telling") {
            "/publications"(resources:"publication")
        }

        "/tellings"(resources:"telling")



        "/telling/read/$id/$title"{
            controller = 'telling'
            action = 'read'
        }

        "/user/show/$id/$nombre"{
            controller = 'user'
            action = 'show'
        }

        "/"(controller: "home", action: "index")
        "500"(view:'/error')
        "404"(view:'/pageNotFound')
	}
}
