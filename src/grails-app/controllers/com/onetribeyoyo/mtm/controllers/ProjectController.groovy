package com.onetribeyoyo.mtm.controllers

import grails.converters.JSON
import grails.converters.XML

import com.onetribeyoyo.mtm.domain.*

class ProjectController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def projectService

    def grailsApplication

    def index = {
        if (Project.count() == 1) {
            def project = Project.findAll()[0]
            params.id = project.id
            redirect(action: "show", params: params)
        } else {
            redirect(action: "list", params: params)
        }
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 20, 100)
        [projectList: Project.list(params), projectTotal: Project.count()]
    }

    def map(Long id, String x, String y) {
        Project project = Project.get(id)
        if (!project) {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            redirect action: "list"
        } else {
            Dimension xAxis = project.dimensionFor(x)
            Dimension yAxis = project.dimensionFor(y)
            if (!xAxis || ! yAxis) {
                flash.error = "Cannot map unknown dimensions."
                redirect action: "show", params: params
            } else {
                [project: project, xAxis: xAxis, yAxis: yAxis]
            }
        }
    }

    def show(Long id) {
        Project project = Project.read(id)
        if (!project) {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            redirect action: "list"
        } else {
            [ project: project ]
        }
    }

    def json(Long id) {
        Project project = Project.read(id)
        if (!project) {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            redirect action: "list"
        } else {
            JSON.use('deep')
            render project as JSON
        }
    }

    def xml(Long id) {
        Project project = Project.read(id)
        if (!project) {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            redirect action: "list"
        } else {
            XML.use('deep')
            render project as XML
        }
    }


    //~ dimensions ---------------------------------------------------------------------------------

    def addAssignedToDimension(Long id) {
        def project = Project.get(id)
        if (!project) {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            redirect action: "list"
        } else {
            projectService.configureDimensionAndElements(project, projectService.ASSIGNED_TO_DIMENSION_DATA)
            redirect action: "show", id: project.id
        }
    }

    def addBugDimension(Long id) {
        def project = Project.get(id)
        if (!project) {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            redirect action: "list"
        } else {
            projectService.configureDimensionAndElements(project, projectService.BUG_DIMENSION_DATA)
            redirect action: "show", id: project.id
        }
    }

    def addFeatureDimension(Long id) {
        def project = Project.get(id)
        if (!project) {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            redirect action: "list"
        } else {
            projectService.configureDimensionAndElements(project, projectService.FEATURE_DIMENSION_DATA)
            redirect action: "show", id: project.id
        }
    }

    def addReleaseDimension(Long id) {
        def project = Project.get(id)
        if (!project) {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            redirect action: "list"
        } else {
            projectService.configureDimensionAndElements(project, projectService.RELEASE_DIMENSION_DATA)
            redirect action: "show", id: project.id
        }
    }

    def addStatusDimension(Long id) {
        def project = Project.get(id)
        if (!project) {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            redirect action: "list"
        } else {
            projectService.configureDimensionAndElements(project, projectService.STATUS_DIMENSION_DATA)
            redirect action: "show", id: project.id
        }
    }

    def addStrategyDimension(Long id) {
        def project = Project.get(id)
        if (!project) {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            redirect action: "list"
        } else {
            projectService.configureDimensionAndElements(project, projectService.STRATEGY_DIMENSION_DATA)
            redirect action: "show", id: project.id
        }
    }


    //~ project crud -------------------------------------------------------------------------------

    def create = {
        def project = new Project(name: "New Project")
        render template: "create", model: [
            project: project
        ]
    }
    def save(String name, Long estimateUnits, Boolean showEstimates) {
        def project = new Project(name: name, estimateUnits: estimateUnits, showEstimates: showEstimates)
        project.save(flush:true, failOnError:true)
        projectService.configureBasis(project)
        if (project.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'project.label', default: 'Project'), project.id])}"
            redirect(action: "show", id: project.id)
        }
        else {
            render(view: "create", model: [project: project])
        }
    }

    def edit(Long id) {
        def project = Project.read(id)
        render template: "edit", model: [project: project]
    }
    def update(Long id) {
        def project = Project.get(id)
        if (!project) {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            render status: 404, text: flash.error
        } else {
            if (params.version) {
                def version = params.version.toLong()
                if (project.version > version) {
                    project.errors.rejectValue("version", "default.optimistic.locking.failure",
                                                 [message(code: 'project.label', default: 'Project')] as Object[],
                                                 "Another user has updated this Project while you were editing")
                    render status: 404, template: "edit", model: [project: project]
                    return
                }
            }
            project.properties = params
            if (params.primary) {
                project.primaryAxis = project.dimensionFor(params.primary)
            }
            if (params.colour) {
                project.colourDimension = project.dimensionFor(params.colour)
            }
            if (params.highlight) {
                project.highlightDimension = project.dimensionFor(params.highlight)
            }

            if (!project.hasErrors() && project.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'project.label', default: 'Project'), project.id])}"
                render text: flash.message
            } else {
                flash.error = "Please provide all required values."
                render status: 400, template: "edit", model: [project: project]
            }
        }
    }

    def delete = {
        def project = Project.get(params.id)
        if (project) {
            try {
                project.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            redirect(action: "list")
        }
    }

}
