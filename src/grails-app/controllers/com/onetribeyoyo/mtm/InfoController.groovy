package com.onetribeyoyo.mtm

import com.onetribeyoyo.mtm.project.Project

import grails.plugin.springsecurity.annotation.Secured

@Secured(["IS_AUTHENTICATED_ANONYMOUSLY"])
class InfoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect action: "process"
    }

    def estimation(String id) {
        id ? [ project : Project.read(params.id) ] : [:]
    }

    def faq(String id) {
        id ? [ project : Project.read(params.id) ] : [:]
    }

    def process(String id) {
        id ? [ project : Project.read(params.id) ] : [:]
    }

}
