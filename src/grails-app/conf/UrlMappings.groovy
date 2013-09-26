class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.${format})?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller:"project")
        "500"(view:'/error')
    }
}
