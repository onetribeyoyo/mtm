package com.onetribeyoyo.mtm.controllers

import com.onetribeyoyo.mtm.domain.Project

class InfoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect action: "faq"
    }

    def estimation(Long id) {
        id ? [ project : Project.read(params.id) ] : [:]
    }

    def faq(Long id) {
        id ? [ project : Project.read(params.id) ] : [:]
    }

    def process(Long id) {
        id ? [ project : Project.read(params.id) ] : [:]
    }

}
