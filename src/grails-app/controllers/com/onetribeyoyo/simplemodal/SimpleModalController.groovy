package com.onetribeyoyo.simplemodal

import grails.plugin.springsecurity.annotation.Secured

@Secured(["isAuthenticated()"])
class SimpleModalController {

    def confirm = {
        render template: "confirm", model: [ : ]
    }

    def confirmRemote = {
        render template: "confirmRemote", model: [ : ]
    }

}
