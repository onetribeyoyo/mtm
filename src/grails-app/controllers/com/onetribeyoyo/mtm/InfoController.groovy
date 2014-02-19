package com.onetribeyoyo.mtm

import com.onetribeyoyo.mtm.project.Project

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
