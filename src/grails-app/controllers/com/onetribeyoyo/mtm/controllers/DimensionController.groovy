package com.onetribeyoyo.mtm.controllers

import com.onetribeyoyo.mtm.domain.*

class DimensionController {

    def dimensionService
    def projectService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def create(Long id) {
        def project = Project.get(id)
        def dimension = new Dimension(project: project)
        dimension.properties = params
        render template: "create", model: [dimension: dimension]
    }
    def save() {
        def project = Project.read(params.project.id)
        Dimension dimension = new Dimension(project: project, name: params.name, description: params.description)
        dimension.validate()
        if (dimension.hasErrors()) {
            flash.error = "Please provide all required values."
            render status: 400, template: "create", model: [dimension: dimension]
            flash.error = null // got to clear flash so it doesn't show up on page refresh!

        } else {
            project.addToDimensions(dimension)
            if (dimension.save(failOnError: true, flush: true)) {
                render text: g.createLink(controller: "project", action: "show", id: dimension.project.id)
                flash.message = null // got to clear flash so it doesn't show up on page refresh!

            } else {
                render status: 400, template: "create", model: [dimension: dimension]
                flash.error = null // got to clear flash so it doesn't show up on page refresh!
            }
        }
    }

    def edit(Long id) {
        def dimension = Dimension.read(id)
        render template: "edit", model:[dimension: dimension]
    }
    def update(Long id) {
        def dimension = Dimension.get(id)
        if (!dimension) {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'dimension.label', default: 'Dimension'), params.id])}"
            render status: 404, flash.error
        } else {
            if (params.version) {
                def version = params.version.toLong()
                if (dimension.version > version) {
                    dimension.errors.rejectValue("version", "default.optimistic.locking.failure",
                                                 [message(code: 'dimension.label', default: 'Dimension')] as Object[],
                                                 "Another user has updated this Dimension while you were editing")
                    render status: 404, template: "edit", model: [dimension: dimension]
                    return
                }
            }
            dimension.name = params.name
            dimension.description = params.description
            dimension.validate()
            if (!dimension.hasErrors() && dimension.save(flush: true)) {
                if (params.primaryX) {
                    dimension.project.primaryXAxis = dimension
                }
                if (params.primaryY) {
                    dimension.project.primaryYAxis = dimension
                }

                if (params.colour) {
                    dimension.project.colourDimension = dimension
                } else if (dimension.project.colourDimension == dimension) {
                    dimension.project.colourDimension = null
                }

                if (params.highlight) {
                    dimension.project.highlightDimension = dimension
                } else if (dimension.project.highlightDimension == dimension) {
                    dimension.project.highlightDimension = null
                }

                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'dimension.label', default: 'Dimension'), dimension.id])}"
                render text: g.createLink(controller: "project", action: "show", id: dimension.project.id)
                flash.message = null // got to clear flash so it doesn't show up on page refresh!

            } else {
                flash.error = "Please provide all required values."
                render status: 400, template: "edit", model: [dimension: dimension]
            }
        }
    }

    def delete = {
        def dimension = Dimension.get(params.id)
        if (!dimension) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'dimension.label', default: 'Dimension'), params.id])}"
            render status: 404, template: "confirmDelete", model: [dimension: dimension]

        } else {
            if (dimension.isPrimaryXAxis()) {
                render status: 400, template: "cantDeletePrimaryDimension", model: [dimension: dimension]
            } else if (dimension.isPrimaryYAxis()) {
                render status: 400, template: "cantDeletePrimaryDimension", model: [dimension: dimension]
            } else if (dimension.project.dimensions.size() <= 2) {
                render status: 400, template: "cantDelete", model: [dimension: dimension]
            } else {
                try {
                    projectService.deleteDimension(dimension.project, dimension)
                    flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'dimension.label', default: 'Dimension'), params.id])}"
                    render flash.message
                }
                catch (org.springframework.dao.DataIntegrityViolationException e) {
                    flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'dimension.label', default: 'Dimension'), params.id])}"
                    render status: 400, template: "confirmDelete", model: [dimension: dimension]
                }
            }
        }
    }

    def updateElementOrder(Long projectId, String dimensionName, String sortOrder) {
        log.debug "updateSortOrder(${projectId}, ${dimensionName}, sortOrder:${sortOrder})"
        Project project = Project.read(projectId)
        if (!project) {
            render status: 404, text: "unknown project ID:${projectId}"
        } else {
            Dimension dimension = project.dimensionFor(dimensionName)
            if (!dimension) {
                render status: 404, text: "unknown dimension: ${dimensionName}"
            } else if (dimension.project != project) {
                render status: 404, text: "unknown dimension: ${dimensionName} for project ID:${projectId}"
            } else {
                List<Long> sortedIdList = sortOrder.split(",").collect { (it.replace("element-", "")) as Long }
                dimensionService.updateElementOrder(dimension, sortedIdList)
                render status: 400, text: "sorted"
            }
        }
    }

}
