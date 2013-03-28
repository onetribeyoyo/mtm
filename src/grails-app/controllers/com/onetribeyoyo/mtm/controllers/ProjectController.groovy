package com.onetribeyoyo.mtm.controllers

import grails.converters.JSON

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
        [ project: Project.read(id) ]
    }

    def json(Long id) {
        render Project.read(id) as JSON
    }


    //~ dimensions ---------------------------------------------------------------------------------

    def addAssignedToDimension(Long id) {
        def project = Project.get(id)
        projectService.configureDimensionAndElements(project, projectService.ASSIGNED_TO_DIMENSION_DATA)
        render view: "show", model: [ project: project ]
    }

    def addBugDimension(Long id) {
        def project = Project.get(id)
        projectService.configureDimensionAndElements(project, projectService.BUG_DIMENSION_DATA)
        render view: "show", model: [ project: project ]
    }

    def addFeatureDimension(Long id) {
        def project = Project.get(id)
        projectService.configureDimensionAndElements(project, projectService.FEATURE_DIMENSION_DATA)
        render view: "show", model: [ project: project ]
    }

    def addReleaseDimension(Long id) {
        def project = Project.get(id)
        projectService.configureDimensionAndElements(project, projectService.RELEASE_DIMENSION_DATA)
        render view: "show", model: [ project: project ]
    }

    def addStatusDimension(Long id) {
        def project = Project.get(id)
        projectService.configureDimensionAndElements(project, projectService.STATUS_DIMENSION_DATA)
        render view: "show", model: [ project: project ]
    }

    def addStrategyDimension(Long id) {
        def project = Project.get(id)
        projectService.configureDimensionAndElements(project, projectService.STRATEGY_DIMENSION_DATA)
        render view: "show", model: [ project: project ]
    }


    //~ project crud -------------------------------------------------------------------------------

    def create = {
        def project = new Project(name: "New Project")
        render template: "create", model: [
            project: project
        ]
    }
    def save(String name, Long estimateUnits) {
        def project = new Project(name: name, estimateUnits: estimateUnits)
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

    def edit = {
        def project = Project.get(params.id)
        if (!project) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [project: project]
        }
    }
    def update = {
        def project = Project.get(params.id)
        if (project) {
            if (params.version) {
                def version = params.version.toLong()
                if (project.version > version) {

                    project.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'project.label', default: 'Project')] as Object[], "Another user has updated this Project while you were editing")
                    render(view: "edit", model: [project: project])
                    return
                }
            }
            project.properties = params
            if (!project.hasErrors() && project.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'project.label', default: 'Project'), project.id])}"
                redirect(action: "show", id: project.id)
            }
            else {
                render(view: "edit", model: [project: project])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            redirect(action: "list")
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
