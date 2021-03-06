modules = {

    "dropmenu" {
        resource url: "js/dropmenu.js"
        resource url: "css/dropmenu.css", attrs: [media: "screen,projection,print"]
    }

    "modal" {
        dependsOn "jquery"
        resource url: "js/jquery.simplemodal.1.4.4.min.js"
        resource url: "js/modal.js"
        resource url: "css/modal.css", attrs: [media: "screen,projection,print"]
    }

    "tabs" {
        resource url: "css/tabs.css", attrs: [media: "screen,projection,print"]
    }

    "mtm" {
        resource url: "js/application-vars.js.gsp"

        dependsOn "jquery"
        dependsOn "modal"

        resource url: "js/mtm.js"
        resource url: "css/mtm.css", attrs: [media: "screen,projection,print"]

        resource url: "css/buttons.css", attrs: [media: "screen,projection,print"]
        resource url: "css/card.css", attrs: [media: "screen,projection,print"]
        resource url: "css/forms.css", attrs: [media: "screen,projection,print"]
        resource url: "css/print.css", attrs: [media: "print"]

        resource url: "css/html-named-colors.css", attrs: [media: "screen,projection,print"]
        resource url: "css/rgb.txt-colors.css", attrs: [media: "screen,projection,print"]

        //resource url: "css/chalkboard.css", attrs: [media: "screen,projection,print"]
    }

    "project-config" {
        dependsOn "mtm"
        dependsOn "jquery-ui" // for card sorting
        resource url: "js/dimension-list.js"
    }

    "card-map" {
        dependsOn "mtm"
        dependsOn "jquery-ui" // for card sorting

        resource url: "js/grid.js"
        resource url: "css/grid.css", attrs: [media: "screen,projection,print"]

        resource url: "js/card.js"
        resource url: "css/card.css", attrs: [media: "screen,projection,print"]
    }

}
