package com.onetribeyoyo.mtm.project

import org.springframework.dao.DataIntegrityViolationException

import grails.plugin.springsecurity.annotation.Secured

@Secured(["isAuthenticated()"])
class StoryController {

    def projectService
    def storyService

    static allowedMethods = [save: "POST", update: "POST"]

    def show(Story story) {
        [ story: story ]
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [stories: Story.list(params), storyTotal: Story.count()]
    }

    def create(String id) {
        def project = Project.read(id)
        def story = new Story(project: project)
        story.properties = params
        flash.error = null // got to clear flash so it doesn't show up!
        render template: "create", model:[story: story]
    }
    def save() {
        def project = Project.read(params.project.id)
        Story story = new Story(project: project, summary: params.summary, detail: params.detail, estimate: params.estimate as String)
        storyService.setVector(story, params)
        story.validate()
        if (story.hasErrors()) {
            flash.error = "Please provide all required values."
            render status: 400, template: "create", model: [story: story]
            flash.error = null // got to clear flash so it doesn't show up on page refresh!

        } else {
            project.addToStories(story)
            if (story.save(failOnError: true, flush: true)) {
                render text: g.createLink(controller: "project", action: "map", id: story.project.id)

            } else {
                render status: 400, template: "create", model: [story: story]
                flash.error = null // got to clear flash so it doesn't show up on page refresh!
            }
        }
    }

    def edit() {
        def story = Story.read(params.id)
        render template: "edit", model:[story: story]
    }
    def update(String id) {
        def story = Story.get(id)
        if (!story) {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'story.label', default: 'Story'), params.id])}"
            render status: 404, flash.error
        } else {
            if (params.version) {
                def version = params.version.toLong()
                if (story.version > version) {
                    story.errors.rejectValue("version", "default.optimistic.locking.failure",
                                                 [message(code: 'story.label', default: 'Story')] as Object[],
                                                 "Another user has updated this Story while you were editing")
                    render status: 404, template: "edit", model: [story: story]
                    return
                }
            }
            story.properties = params
            storyService.setVector(story, params)
            story.validate()
            if (!story.hasErrors() && story.save(flush: true)) {
                render text: g.createLink(controller: "project", action: "map", id: story.project.id)
                flash.message = null // got to clear flash so it doesn't show up on page refresh!

            } else {
                flash.error = "Please provide all required values."
                render status: 400, template: "edit", model: [dimension: dimension]
            }
        }
    }

    def delete(String id) {
        def story = Story.get(id)
        if (story) {
            try {
                story.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'story.label', default: 'Story'), params.id])}"
            } catch (DataIntegrityViolationException e) {
                flash.error = "${message(code: 'default.not.deleted.message', args: [message(code: 'story.label', default: 'Story'), params.id])}"
            }
            redirect controller: "project", action: "map", id: story.project.id

        } else {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'story.label', default: 'Story'), params.id])}"
            redirect controller: "project", action: "list"
        }
    }

    def move(String storyId, String xAxisId, String xId, String yAxisId, String yId, String sortOrder) {
        def messagePrefix = "move(story:${storyId}, x:${xAxisId}:${xId}, y:${yAxisId}:${yId}, sort:${sortOrder})"
        log.debug messagePrefix

        Story story = Story.get(storyId);
        Dimension xAxis = Dimension.get(xAxisId)
        Dimension yAxis = Dimension.get(yAxisId)
        Element x = Element.get(xId)
        Element y = Element.get(yId)

        if ((xAxis.project?.id != story.project?.id) || (yAxis.project?.id != story.project?.id)) {
            log.error "${messagePrefix} attempt to move story into dimensions outside of project."

        } else if (x && (x.dimension != xAxis)) {
            log.error "${messagePrefix} attempt to move story off of the X axis."

        } else if (y && (y.dimension != yAxis)) {
            log.error "${messagePrefix} attempt to move story off of the Y axis."

        } else { // everything's ok...
            storyService.move(story, xAxis, x, yAxis, y)
            List<Long> sortedIds = sortOrder.split(",")
            projectService.updateStoryOrder(story.project, x, y, sortedIds)
            render "moved"
        }
    }

}
