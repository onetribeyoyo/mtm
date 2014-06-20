package com.onetribeyoyo.mtm.project

class DimensionController {

    def dimensionService
    def projectService

    static allowedMethods = [save: "POST", update: "POST"]

    def create(String id) {
        def project = Project.get(id)
        def dimension = new Dimension(project: project)
        dimension.properties = params
        flash.error = null // got to clear flash so it doesn't show up!
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

    def edit(String id) {
        def dimension = Dimension.read(id)
        render template: "edit", model:[dimension: dimension]
    }
    def update(String id) {
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

                render template: "show", model: [dimension: dimension]

            } else {
                flash.error = "Please provide all required values."
                render status: 400, template: "edit", model: [dimension: dimension]
            }
        }
    }

    def delete(String id) {
        def dimension = Dimension.get(id)
        def projectId = dimension.project.id
        if (dimension) {
            if (dimension.isPrimaryXAxis() ||dimension.isPrimaryYAxis()) {
                flash.error = "You can't delete the '${dimension}' dimension.  It's a primary dimension for the project (the ones all the default story maps use for the X/Y axes.)  If you really want to delete it you'll first have to assign another dimension as the primary."
            } else if (dimension.project.dimensions.size() <= 2) {
                flash.error = "You can't delete the '${dimension}' dimension.  The project has to have at least two dimensions."
            } else {
                try {
                    projectService.delete(dimension)
                    flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'dimension.label', default: 'Dimension'), params.id])}"
                } catch (org.springframework.dao.DataIntegrityViolationException e) {
                    flash.error = "${message(code: 'default.not.deleted.message', args: [message(code: 'dimension.label', default: 'Dimension'), params.id])}"
                }
            }
        } else {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'dimension.label', default: 'Dimension'), params.id])}"
        }

        redirect controller: "project", action: "show", id: projectId
    }

    def updateElementOrder(String projectId, String dimensionName, String sortOrder) {
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
                List<String> sortedIdList = sortOrder.split(",").collect { it.replace("element-", "") }
                dimensionService.updateElementOrder(dimension, sortedIdList)
                render status: 400, text: "sorted"
            }
        }
    }

}
