package com.onetribeyoyo.mtm.controllers

import com.onetribeyoyo.mtm.domain.Project

class InfoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect action: "faq"
    }

    def estimation = {
        Project project = params.id ? Project.read(params.id) : null
        [ project: project ]
    }

    def faq = {
        Project project = params.id ? Project.read(params.id) : null
        [ project: project ]
    }

    def process = {
        Project project = params.id ? Project.read(params.id) : null
        [ project: project ]
    }

}
