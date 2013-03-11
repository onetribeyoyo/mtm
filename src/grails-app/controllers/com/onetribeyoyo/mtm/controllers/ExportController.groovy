package com.onetribeyoyo.mtm.controllers

import com.onetribeyoyo.mtm.domain.*

class ExportController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def exportService

    def grailsApplication

    def exportStories = {
        String format = params.format ?: "csv"

        Project project = Project.read(params.id)

        def stories = []
        project.stories.each { story ->
            def storyData = [:]
            storyData.summary = story.summary
            storyData.detail = story.detail
            project.dimensions.each { dimension ->
                storyData[dimension.name] = story.valueFor(dimension)?.element
            }
            stories << storyData
        }
        List fields = []
        project.dimensions.each { dimension ->
            fields << dimension.name
        }
        fields << "summary"
        fields << "detail"
        Map labels = [:]
        Map formatters = [:]

        String filename = (project.name.endsWith(params.extension)) ? project.name : "project.${project.id}.stories.${params.extension}"
        response.setHeader("Content-disposition", "attachment; filename=${filename}")
        response.contentType = grailsApplication.config.grails.mime.types[format]
        exportService.export(format, response.outputStream, stories, fields, labels, formatters, params)
    }

}
