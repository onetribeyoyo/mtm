package com.onetribeyoyo.mtm.controllers

import com.onetribeyoyo.mtm.domain.*

class DimensionController {

    def dimensionService
    def projectService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def create =  {
        def project = Project.get(params.id)
        def dimension = new Dimension(project: project)
        dimension.properties = params
        render template: "create", model: [dimension: dimension]
    }
    def save = {
        def project = Project.read(params.project.id)
        Dimension dimension = projectService.createDimension(project: project, params)

        if (dimension.hasErrors()) {
            flash.error = "Please provide all required values."
            render status: 400, template: "create", model: [dimension: dimension]
        }
        else {
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
            dimension.properties = params
            if (!dimension.hasErrors() && dimension.save(flush: true)) {
                if (params.primary) {
                    dimension.project.primaryAxis = dimension
                }
                if (params.colour) {
                    dimension.project.colourDimension = dimension
                }
                if (params.highlight) {
                    dimension.project.highlightDimension = dimension
                }
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'dimension.label', default: 'Dimension'), dimension.id])}"
                render flash.message
            } else {
                flash.error = "Please provide all required values."
                render status: 400, template: "edit", model: [dimension: dimension]
            }
        }
    }

    def confirmDelete = {
        def dimension = Dimension.read(params.id)
        if (dimension.isPrimaryAxis()) {
            render status: 400, template: "cantDeletePrimaryDimension", model:[dimension: dimension]
        } else if (dimension.project.dimensions.size() <= 2) {
            render status: 400, template: "cantDelete", model:[dimension: dimension]
        } else {
            render template: "confirmDelete", model:[dimension: dimension]
        }
   }
    def delete = {
        def dimension = Dimension.get(params.id)
        if (!dimension) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'dimension.label', default: 'Dimension'), params.id])}"
            render status: 404, template: "confirmDelete", model: [dimension: dimension]

        } else {
            if (dimension.isPrimaryAxis()) {
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

    // TODO: rework this to take a project id and dimension name as args
    def updateElementOrder(Long projectId, String dimensionName, String sortOrder) {
        log.debug "updateSortOrder(${projectId} ${dimensionName}, sortOrder:${sortOrder})"
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
