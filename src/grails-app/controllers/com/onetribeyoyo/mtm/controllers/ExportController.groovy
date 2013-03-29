package com.onetribeyoyo.mtm.controllers

import com.onetribeyoyo.mtm.domain.*

class ExportController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def exportService

    def grailsApplication

    def exportStories(Long id, String format ) {
        format = format ?: "csv"

        def project = Project.read(id)
        if (!project) {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            redirect controller: "project", action: "show", id: id
        } else {
            def data = []
            project.stories.each { story ->
                def storyData = [:]
                storyData.summary = story.summary
                storyData.detail = story.detail
                project.dimensions.each { dimension ->
                    storyData[dimension.name] = story.valueFor(dimension)?.element
                }
                data << storyData
            }
            List fields = []
            project.dimensions.each { dimension ->
                fields << dimension.name
            }
            fields << "summary"
            fields << "detail"
            fields << "estimate"
            Map labels = [:]
            Map formatters = [:]

            String filename = (project.name.endsWith(params.extension)) ? project.name : "project.${project.id}.stories.${params.extension}"
            response.setHeader("Content-disposition", "attachment; filename=${filename}")
            response.contentType = grailsApplication.config.grails.mime.types[format]
            exportService.export(format, response.outputStream, data, fields, labels, formatters, params)
        }
    }

    def exportStructure(Long id, String format) {
        format = params.format ?: "csv"

        def project = Project.read(id)
        if (!project) {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            redirect controller: "project", action: "show", id: id
        } else {
            def data = []
            project.dimensions.each { dimension ->
                dimension.elements.each { element ->
                    def elementData = [
                        dimension: element.dimension,
                        value: element.value,
                        order: element.order,
                        colour: element.colour,
                        description: element.description,
                        primaryAxis: (element.dimension.project.primaryAxis == dimension),
                        colourDimension: (element.dimension.project.colourDimension == dimension),
                        highlightDimension: (element.dimension.project.highlightDimension == dimension),
                    ]
                    data << elementData
                }
            }
            List fields = [ "dimension", "value", "order", "colour", "description", "primaryAxis", "colourDimension", "highlightDimension" ]
            Map labels = [:]
            Map formatters = [:]

            String filename = "project.${project.id}.structure.${params.extension}"
            response.setHeader("Content-disposition", "attachment; filename=${filename}")
            response.contentType = grailsApplication.config.grails.mime.types[format]
            exportService.export(format, response.outputStream, data, fields, labels, formatters, params)
        }
    }

}
