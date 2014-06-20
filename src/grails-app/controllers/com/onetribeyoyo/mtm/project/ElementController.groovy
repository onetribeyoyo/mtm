package com.onetribeyoyo.mtm.project

class ElementController {

    def dimensionService
    def projectService

    def create() {
        def dimension = Dimension.read(params.id)
        def element = new Element(dimension: dimension)
        element.properties = params
        flash.error = null // got to clear flash so it doesn't show up!
        render template: "create", model:[element: element]
    }
    def save() {
        def dimension = Dimension.read(params.dimension.id)
        Element element = new Element(dimension: dimension, value: params.value, description: params.description)
        element.validate()
        if (element.hasErrors()) {
            flash.error = "Please provide all required values."
            render status: 400, template: "create", model: [element: element]
            flash.error = null // got to clear flash so it doesn't show up on page refresh!

        } else {
            if (element.save(failOnError: true, flush: true)) {
                render text: g.createLink(controller: "project", action: "show", id: element.dimension.project.id)
                flash.message = null // got to clear flash so it doesn't show up on page refresh!

            } else {
                render status: 400, template: "create", model: [dimension: dimension]
                flash.error = null // got to clear flash so it doesn't show up on page refresh!
            }
        }
    }

    def edit(String id) {
        def element = Element.read(id)
        render template: "edit", model:[element: element]
    }
    def update(String id) {
        def element = Element.get(id)
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
            element.colour = params.colour
            element.description = params.description
            element.validate()
            if (!element.hasErrors() && element.save(flush: true)) {
                render template: "show", model: [element: element]
            } else {
                flash.error = "Please provide all required values."
                render status: "400", template: "edit", model: [element: element]
            }
        }
    }

    def delete(String id) {
        def element = Element.get(id)
        if (element) {
            def projectId = element.dimension.project.id

            if (element.dimension.elements.size() == 1) {
                flash.error = "You can't delete this.  It's the last ${element.dimension} value and there's got to be at least one."

            } else {
                try {
                    projectService.delete(element)
                    flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'element.label', default: 'Element'), params.id])}"
                } catch (org.springframework.dao.DataIntegrityViolationException e) {
                    flash.error = "${message(code: 'default.not.deleted.message', args: [message(code: 'element.label', default: 'Element'), params.id])}"
                }
            }
            redirect controller: "project", action: "show", id: projectId

        } else {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'element.label', default: 'Element'), params.id])}"
            redirect controller: "project", action: "list"
        }
    }

}
