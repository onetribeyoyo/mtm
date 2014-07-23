class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller:"info")
        "500"(view:'/error')


        //"/projectss"(resources:"project")
        "/projects"(resources:"project") {
            "/story"(resources:"story")
            "/dimension"(resources:"dimension") {
                "/element"(resources:"element")
            }
        }

    }

}
