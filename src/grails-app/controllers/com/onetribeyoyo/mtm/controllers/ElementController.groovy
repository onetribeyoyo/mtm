package com.onetribeyoyo.mtm.controllers

import com.onetribeyoyo.mtm.domain.*

class ElementController {

    def dimensionService
    def projectService

    def create = {
        def dimension = Dimension.read(params.id)
        def element = new Element(dimension: dimension)
        element.properties = params
        render template: "create", model:[element: element]
    }
    def save = {
        def dimension = Dimension.read(params.dimension.id)
        Element element = new Element(dimension: dimension, value: params.value, description: params.description)
        element.validate()

        if (element.hasErrors()) {
            render status: "400", template: "create", model: [element: element]
        }
        else {
            element.save(failOnError: true)
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'element.label', default: 'Element'), element.id])}"
            redirect controller: "project", action: "show", id: element.dimension.project.id
        }
    }

    def edit = {
        def element = Element.read(params.id)
        render template: "edit", model:[element: element]
    }
    def update = {
        def element = Element.get(params.id)
        if (!element) {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'element.label', default: 'Element'), params.id])}"
            render status: "404", flash.error
        } else {
            if (params.version) {
                def version = params.version.toLong()
                if (element.version > version) {
                    element.errors.rejectValue("version", "default.optimistic.locking.failure",
                                                 [message(code: 'element.label', default: 'Element')] as Object[],
                                                 "Another user has updated this Element while you were editing")
                    render status: "404", template: "edit", model: [element: element]
                    return
                }
            }
            element.value = params.value
            element.description = params.description
            if (!element.hasErrors() && element.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'element.label', default: 'Element'), element.id])}"
                render flash.message
            } else {
                render status: "400", template: "edit", model: [element: element]
            }
        }
    }

    def confirmDelete = {
        def element = Element.read(params.id)
        if (element.dimension.elements.size() == 1) {
            render template: "cantDelete", model:[element: element]
        } else {
            render template: "confirmDelete", model:[element: element]
        }
    }
    def delete = {
        def element = Element.get(params.id)
        if (element) {
            if (element.dimension.elements.size() == 1) {
                render status: "400", template: "cantDelete", model: [element: element]
            } else {
                try {
                    element.dimension.deleteElement(element)
                    flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'element.label', default: 'Element'), params.id])}"
                    render flash.message
                }
                catch (org.springframework.dao.DataIntegrityViolationException e) {
                    flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'element.label', default: 'Element'), params.id])}"
                    render status: "400", template: "confirmDelete", model: [element: element]
                }
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'element.label', default: 'Element'), params.id])}"
            render status: "404", template: "confirmDelete", model: [element: element]
        }
    }

}
