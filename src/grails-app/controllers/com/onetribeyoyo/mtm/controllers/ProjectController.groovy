package com.onetribeyoyo.mtm.controllers

import grails.converters.JSON
import grails.converters.XML

import groovy.json.JsonSlurper

import com.onetribeyoyo.mtm.domain.*

class ProjectController {

    static allowedMethods = [save: "POST", update: "POST"]

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

    def list() {
        Project project = params.id ? Project.read(params.id) : null
        params.max = Math.min(params.max ? params.int('max') : 20, 100)
        [
            project: project, 
            projectList: Project.list(params), 
            projectTotal: Project.count()
        ]
    }

    def map(Long id, String x, String y) {
        Project project = Project.get(id)
        if (!project) {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            redirect action: "list"
        } else {
            Dimension xAxis = project.dimensionFor(x) ?: project.dimensionFor(session.x) ?: project.primaryXAxis ?: project.dimensions.find { d -> d != project.primaryYAxis }
            Dimension yAxis = project.dimensionFor(y) ?: project.dimensionFor(session.y) ?: project.primaryYAxis ?: project.dimensions.find { d -> d != project.primaryXAxis }
            if (!xAxis || ! yAxis) {
                flash.error = "Cannot map unknown dimensions."
                redirect action: "show", params: params
            } else {
                session.x = xAxis.name
                session.y = yAxis.name
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


    //~ json export/import -------------------------------------------------------------------------

    def export(Long id) {
        Project project = Project.read(id)
        if (!project) {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            redirect action: "list"

        } else {
            def data = [
                name: project.name,
                estimateUnits: project.estimateUnits,
                showEstimates: project.showEstimates,
                colourDimension: project.colourDimension?.name,
                highlightDimension: project.highlightDimension?.name,
                primaryXAxis: project.primaryXAxis?.name,
                primaryYAxis: project.primaryYAxis?.name,

                dimensions: project.dimensions.collect { dimension ->
                    [
                        name: dimension.name,
                        description: dimension.description,
                        colour: dimension.colour,
                        layoutStyle: dimension.layoutStyle?.toString(),
                        elements: dimension.elements.collect { element ->
                            [
                                value: element.value,
                                order: element.order,
                                colour: element.colour,
                                description: element.description,
                            ]
                        }
                    ]
                },
                stories: project.stories.collect { story ->
                    [
                        summary: story.summary,
                        detail: story.detail,
                        estimate: story.estimate,
                        vector: story.vector.collect { element ->
                            [
                                dimension: element.dimension.name,
                                value: element.value,
                            ]
                        },
                        ordering: story.ordering.collect { ordering ->
                            [
                                xAxis: ordering.x?.dimension?.name,
                                x: ordering.x?.value,
                                yAxis: ordering.y?.dimension?.name,
                                y: ordering.y?.value,
                                order: ordering.order,
                            ]
                        },
                    ]
                },
            ]

            String filename = "project.${project.id}.mtm"
            response.setHeader("Content-disposition", "attachment; filename=${filename}")
            response.contentType = grailsApplication.config.grails.mime.types.text
            render data as JSON
        }
    }

    def projectFile = {
        render template: "chooseFile", model: [ filename: params.filename ]
    }

    def importProject(String projectName) {
        if (!projectName?.trim()) {
            flash.error = "Project name is required."
            redirect action: "list", model: params

        } else if (Project.countByName(projectName)) {
            flash.error = "Already have a project named \"${projectName}\"."
            redirect action: "list", model: params

        } else if (!params.projectFile) {
            flash.error = "Filename is required."
            redirect action: "list", model: params

        } else {
            def multipartFile = request.getFile("projectFile")
            if (!multipartFile || multipartFile.empty) {
                flash.error = "Import file is empty."
                redirect action: "list", model: params

            } else {
                try {
                    String pathname = multipartFile.originalFilename
                    def parts = [name: null, extension: null]
                    pathname.find(/^([^.]*)$|^(.*?)\.?([^.]*)$/) { full, noExtension, name, extension ->
                        parts.name = name ?: noExtension
                        parts.extension = extension
                    }
                    def savedFile = File.createTempFile("tmp_import_file_${parts.name}.", ".${parts.extension ?: 'mtm'}")
                    savedFile.deleteOnExit()
                    pathname = savedFile.absolutePath

                    multipartFile.transferTo(savedFile)
                    def json = new JsonSlurper().parse(new FileReader(new File(pathname)))
                    Project project = projectService.createFromJson(projectName, json)

                    redirect controller: "project", action: "show", id: project.id
                    //redirect action: "list"

                } catch (RuntimeException ex) {
                    flash.error = ex.message
                    redirect action: "list", model: params
                }
            }
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
        project.validate()

        if (project.hasErrors()) {
            flash.error = "Please provide all required values."
            render status: 400, template: "create", model: [project: project]
            flash.error = null // got to clear flash so it doesn't show up on page refresh!

        } else if (project.save(failOnError: true, flush: true)) {
            projectService.configureBasis(project)
            project.save(failOnError: true, flush: true)
            render text: g.createLink(controller: "project", action: "show", id: project.id)
            flash.message = null // got to clear flash so it doesn't show up on page refresh!

        } else {
            render status: 400, template: "create", model: [project: project]
            flash.error = null // got to clear flash so it doesn't show up on page refresh!
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
            flash.error = null // got to clear flash so it doesn't show up later when the dialog is dismissed!
        } else {
            if (params.version) {
                def version = params.version.toLong()
                if (project.version > version) {
                    project.errors.rejectValue("version", "default.optimistic.locking.failure",
                                                 [message(code: 'project.label', default: 'Project')] as Object[],
                                                 "Another user has updated this Project while you were editing")
                    render status: 404, template: "edit", model: [project: project]
                    flash.error = null // got to clear flash so it doesn't show up later when the dialog is dismissed!
                    return
                }
            }
            project.properties = params

            if (params.primaryX) {
                project.primaryXAxis = project.dimensionFor(params.primaryX)
            }

            if (params.primaryY) {
                project.primaryYAxis = project.dimensionFor(params.primaryY)
            }

            if (params.colour) {
                project.colourDimension = project.dimensionFor(params.colour)
            } else {
                project.colourDimension = null
            }

            if (params.highlight) {
                project.highlightDimension = project.dimensionFor(params.highlight)
            } else {
                project.highlightDimension = null
            }

            project.validate()
            if (project.hasErrors()) {
                flash.error = "Please provide all required values."
                render status: 400, template: "edit", model: [project: project]
                flash.error = null // got to clear flash so it doesn't show up on page refresh!

            } else if (project.save(failOnError: true, flush: true)) {
                projectService.configureBasis(project)
                project.save(failOnError: true, flush: true)
                render text: g.createLink(controller: "project", action: "show", id: project.id)
                flash.message = null // got to clear flash so it doesn't show up on page refresh!

            } else {
                render status: 400, template: "edit", model: [project: project]
                flash.error = null // got to clear flash so it doesn't show up on page refresh!
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
