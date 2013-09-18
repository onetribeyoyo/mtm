package com.onetribeyoyo.simplemodal.controllers

//import grails.plugins.springsecurity.Secured

class SimpleModalController {

    //@Secured(['ROLE_USER'])
    def confirm = {
        render template: "confirm", model: [ : ]
    }

    //@Secured(['ROLE_USER'])
    def confirmRemote = {
        render template: "confirmRemote", model: [ : ]
    }

}
