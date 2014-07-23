package com.onetribeyoyo.mtm.project

import grails.plugin.springsecurity.annotation.Secured

@Secured(["isAuthenticated()"])
class ExportController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def exportService

    def grailsApplication

    def stories(String id, String format, String extension) {
        format = format ?: "csv"
        extension = extension ?: "csv"

        def project = Project.read(id)
        if (!project) {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])}"
            redirect controller: "project", action: "show", id: id
        } else {
            def data = []
            project.stories.each { story ->
                def storyData = [:]
                storyData.summary = story.summary
                storyData.detail = story.detail
                storyData.estimate = story.estimate
                project.dimensions.each { dimension ->
                    storyData[dimension.name] = story.valueFor(dimension)
                }
                data << storyData
            }
            List fields = [ "summary", "detail", "estimate" ]
            project.dimensions.each { dimension ->
                fields << dimension.name
            }
            Map labels = [:]
            Map formatters = [:]

            String filename = "project.${project.id}.stories.${extension}"
            response.setHeader("Content-disposition", "attachment; filename=${filename}")
            response.contentType = grailsApplication.config.grails.mime.types[format]
            exportService.export(format, response.outputStream, data, fields, labels, formatters, params)
        }
    }

    def order(String id, String format, String extension) {
        format = format ?: "csv"
        extension = extension ?: "csv"

        def project = Project.read(id)
        if (!project) {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])}"
            redirect controller: "project", action: "show", id: id
        } else {
            def data = []
            project.stories.each { story ->
                story.ordering.each { ordering ->
                    if (ordering.order != null) {
                        data << [
                            story: ordering.story.summary,
                            xAxis: ordering.x?.dimension,
                            x: ordering.x,
                            yAxis: ordering.y?.dimension,
                            y: ordering.y,
                            order: ordering.order
                        ]
                    }
                }
            }
            List fields = ["story","xAxis","x","yAxis","y","order"]
            Map labels = [:]
            Map formatters = [:]

            String filename = "project.${project.id}.ordering.${extension}"
            response.setHeader("Content-disposition", "attachment; filename=${filename}")
            response.contentType = grailsApplication.config.grails.mime.types[format]
            exportService.export(format, response.outputStream, data, fields, labels, formatters, params)
        }
    }

    def structure(String id, String format, String extension) {
        format = format ?: "csv"
        extension = extension ?: "csv"

        def project = Project.read(id)
        if (!project) {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), id])}"
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
                        primaryXAxis: (element.dimension.isPrimaryXAxis()),
                        primaryYAxis: (element.dimension.isPrimaryYAxis()),
                        colourDimension: (element.dimension.isColourDimension()),
                        highlightDimension: (element.dimension.isHighlightDimension()),
                    ]
                    data << elementData
                }
            }
            List fields = [ "dimension", "value", "order", "colour", "description", "primaryXAxis", "primaryYAxis", "colourDimension", "highlightDimension" ]
            Map labels = [:]
            Map formatters = [:]

            String filename = "project.${project.id}.structure.${extension}"
            response.setHeader("Content-disposition", "attachment; filename=${filename}")
            response.contentType = grailsApplication.config.grails.mime.types[format]
            exportService.export(format, response.outputStream, data, fields, labels, formatters, params)
        }
    }

}
