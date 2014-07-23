package com.onetribeyoyo.mtm.project

import grails.converters.JSON
import grails.converters.XML

import groovy.json.JsonSlurper

import org.springframework.dao.DataIntegrityViolationException

import grails.plugin.springsecurity.annotation.Secured

@Secured(["isAuthenticated()"])
class ProjectController {

    static allowedMethods = [save: "POST", update: "POST"]

    def grailsApplication

    def projectService
    def storymapService

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

    def map(String id, String x, String y) {
        Project project = Project.get(id)
        if (!project) {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            redirect action: "list"
        } else {
            Dimension xAxis = storymapService.findXAxis(project, (x ?: session.x))
            Dimension yAxis = storymapService.findYAxis(project, (y ?: session.y))
            if (!xAxis || ! yAxis) {
                flash.error = "Cannot map unknown dimensions."
                redirect action: "show", params: params
            } else {
                session.x = xAxis.name
                session.y = yAxis.name
                [ project: project, xAxis: xAxis, yAxis: yAxis ]
            }
        }
    }

    def show(String id) {
        Project project = Project.read(id)
        if (!project) {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            redirect action: "list"
        } else {
            Dimension xAxis = storymapService.findXAxis(project, session.x)
            Dimension yAxis = storymapService.findYAxis(project, session.y)
            [ project: project, xAxis: xAxis, yAxis: yAxis ]
        }
    }


    //~ json export/import -------------------------------------------------------------------------

    def export(String id) {
        Project project = Project.read(id)
        if (!project) {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            redirect action: "list"

        } else {
            // TODO: move this to Map ProjectService.export(Project project) and then write some tests for it.
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

            // TODO: how should this be rewritten for grails 2.3?
            String filename = "${project.name.toLowerCase().replace(" ", "-")}.mtm" //"project.${project.id}.mtm"
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

    def json(String id) {
        Project project = Project.read(id)
        if (!project) {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            redirect action: "list"
        } else {
            JSON.use('deep')
            render project as JSON
        }
    }

    def xml(String id) {
        Project project = Project.read(id)
        if (!project) {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            redirect action: "list"
        } else {
            XML.use('deep')
            render project as XML
        }
    }


    //~ pre-defined dimensions ---------------------------------------------------------------------

    def addAssignedToDimension(String id) {
        def project = Project.get(id)
        if (!project) {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            redirect action: "list"
        } else {
            projectService.configureDimensionAndElements(project, DimensionData.ASSIGNED_TO)
            redirect action: "show", id: project.id
        }
    }

    def addBugDimension(String id) {
        def project = Project.get(id)
        if (!project) {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            redirect action: "list"
        } else {
            projectService.configureDimensionAndElements(project, DimensionData.BUG)
            redirect action: "show", id: project.id
        }
    }

    def addFeatureDimension(String id) {
        def project = Project.get(id)
        if (!project) {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            redirect action: "list"
        } else {
            projectService.configureDimensionAndElements(project, DimensionData.FEATURE)
            redirect action: "show", id: project.id
        }
    }

    def addReleaseDimension(String id) {
        def project = Project.get(id)
        if (!project) {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            redirect action: "list"
        } else {
            projectService.configureDimensionAndElements(project, DimensionData.RELEASE)
            redirect action: "show", id: project.id
        }
    }

    def addStatusDimension(String id) {
        def project = Project.get(id)
        if (!project) {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            redirect action: "list"
        } else {
            projectService.configureDimensionAndElements(project, DimensionData.STATUS)
            redirect action: "show", id: project.id
        }
    }

    def addStrategyDimension(String id) {
        def project = Project.get(id)
        if (!project) {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            redirect action: "list"
        } else {
            projectService.configureDimensionAndElements(project, DimensionData.STRATEGY)
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

    def edit(String id) {
        def project = Project.read(id)
        render template: "edit", model: [project: project]
    }
    def update(String id) {
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
            catch (DataIntegrityViolationException e) {
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
