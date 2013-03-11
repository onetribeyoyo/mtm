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
        [projectInstanceList: Project.list(params), projectInstanceTotal: Project.count()]
    }

    def map(Long id, Long x, Long y) {
        Project project = Project.get(id)
        if (!project) {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            redirect action: "list"
        } else {
            Dimension xAxis = Dimension.get(x)
            Dimension yAxis = Dimension.get(y)
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


    //~ project crud -------------------------------------------------------------------------------

    def create = {
        def project = new Project(name: "New Project")
        render template: "create", model: [
            project: project
        ]
    }

    def save = {
        def projectInstance = new Project(params)
        projectService.configureDefaults(projectInstance)
        if (projectInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'project.label', default: 'Project'), projectInstance.id])}"
            redirect(action: "show", id: projectInstance.id)
        }
        else {
            render(view: "create", model: [projectInstance: projectInstance])
        }
    }

    def edit = {
        def projectInstance = Project.get(params.id)
        if (!projectInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [projectInstance: projectInstance]
        }
    }

    def update = {
        def projectInstance = Project.get(params.id)
        if (projectInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (projectInstance.version > version) {

                    projectInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'project.label', default: 'Project')] as Object[], "Another user has updated this Project while you were editing")
                    render(view: "edit", model: [projectInstance: projectInstance])
                    return
                }
            }
            projectInstance.properties = params
            if (!projectInstance.hasErrors() && projectInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'project.label', default: 'Project'), projectInstance.id])}"
                redirect(action: "show", id: projectInstance.id)
            }
            else {
                render(view: "edit", model: [projectInstance: projectInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def projectInstance = Project.get(params.id)
        if (projectInstance) {
            try {
                projectInstance.delete(flush: true)
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
