package com.onetribeyoyo.mtm.controllers

import com.onetribeyoyo.mtm.domain.*

class DimensionController {

    def dimensionService
    def projectService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def create = {
        def project = Project.read(params.id)
        def dimension = new Dimension(project: project)
        dimension.properties = params
        render template: "create", model:[dimension: dimension]
    }
    def save = {
        def project = Project.read(params.project.id)
        Dimension dimension = new Dimension(project: project)
        dimension.properties = params
        dimension.validate()

        if (dimension.hasErrors()) {
            flash.error = "Please provide all required values."
            render status: "400", template: "create", model: [dimension: dimension]
        }
        else {
            dimension.save(failOnError: true)
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'dimension.label', default: 'Dimension'), dimension.id])}"
            redirect controller: "project", action: "show", id: dimension.project.id
        }
    }

    def edit = {
        def dimension = Dimension.read(params.id)
        render template: "edit", model:[dimension: dimension]
    }
    def update = {
        def dimension = Dimension.get(params.id)
        if (!dimension) {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'dimension.label', default: 'Dimension'), params.id])}"
            render status: "404", flash.error
        } else {
            if (params.version) {
                def version = params.version.toLong()
                if (dimension.version > version) {
                    dimension.errors.rejectValue("version", "default.optimistic.locking.failure",
                                                 [message(code: 'dimension.label', default: 'Dimension')] as Object[],
                                                 "Another user has updated this Dimension while you were editing")
                    render status: "404", template: "edit", model: [dimension: dimension]
                    return
                }
            }
            dimension.properties = params
            if (!dimension.hasErrors() && dimension.save(flush: true)) {
                if (params.primary) {
                    dimension.project.primary = dimension
                }
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'dimension.label', default: 'Dimension'), dimension.id])}"
                render flash.message
            } else {
                flash.error = "Please provide all required values."
                render status: "400", template: "edit", model: [dimension: dimension]
            }
        }
    }

    def confirmDelete = {
        def dimension = Dimension.read(params.id)
        if (dimension.isPrimary()) {
            render status: "400", template: "cantDeletePrimaryDimension", model:[dimension: dimension]
        } else if (dimension.project.dimensions.size() <= 2) {
            render status: "400", template: "cantDelete", model:[dimension: dimension]
        } else {
            render template: "confirmDelete", model:[dimension: dimension]
        }
    }
    def delete = {
        def dimension = Dimension.get(params.id)
        if (!dimension) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'dimension.label', default: 'Dimension'), params.id])}"
            render status: "404", template: "confirmDelete", model: [dimension: dimension]

        } else {
            if (dimension.isPrimary()) {
                render status: "400", template: "cantDeletePrimaryDimension", model: [dimension: dimension]
            } else if (dimension.project.dimensions.size() <= 2) {
                render status: "400", template: "cantDelete", model: [dimension: dimension]
            } else {
                try {
                    // TODO: implement dimension.delete that cleans up all elements and stories that use them (should be in a service)
                    dimension.elements.each { element ->
                        element.dimension.project.orderedStoriesFor(element).each { orderedStory ->
                            orderedStory.story.removeFromVector(orderedStory)
                            orderedStory.delete()
                        }
                        element.dimension.removeFromElements(element)
                        element.delete()
                    }
                    dimension.project.orderedStoriesFor(dimension).each { orderedStory ->
                        orderedStory.story.removeFromVector(orderedStory)
                        orderedStory.delete()
                    }
                    dimension.project.removeFromDimensions(dimension)
                    dimension.delete()

                    flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'dimension.label', default: 'Dimension'), params.id])}"
                    render flash.message
                }
                catch (org.springframework.dao.DataIntegrityViolationException e) {
                    flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'dimension.label', default: 'Dimension'), params.id])}"
                    render status: "400", template: "confirmDelete", model: [dimension: dimension]
                }
            }
        }
    }

    // TODO: rework this to take a project id and dimension name as args
    def updateElementOrder(Long id, String sortOrder) {
        Dimension dimension = Dimension.get(id)
        log.debug "updateSortOrder(${dimension}, sortOrder:${sortOrder})"
        List<Long> sortedIdList = sortOrder.split(",").collect { (it.replace("element-", "")) as Long }
        dimensionService.updateElementOrder(dimension, sortedIdList)
        render "sorted"
    }

}
