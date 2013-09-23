package com.onetribeyoyo.mtm.controllers

import com.onetribeyoyo.mtm.domain.*

class StoryController {

    def projectService
    def storyService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [storyInstanceList: Story.list(params), storyInstanceTotal: Story.count()]
    }

    def create() {
        def project = Project.read(params.id)
        def story = new Story(project: project)
        story.properties = params
        flash.error = null // got to clear flash so it doesn't show up!
        render template: "create", model:[story: story]
    }
    def save = {
        def project = Project.read(params.project.id)
        Story story = new Story(project: project, summary: params.summary, detail: params.detail, estimate: params.estimate as Long)
        storyService.setVector(story, params)
        story.validate()

        if (story.hasErrors()) {
            flash.error = "Please provide all required values."
            render status: "400", template: "create", model: [story: story]
            flash.error = null // got to clear flash so it doesn't show up on page refresh!
        } else {
            project.addToStories(story)
            story.save(failOnError: true)
            flash.message = "Created story: \"${story.summary}\"."
            render text: flash.message
        }
    }

    def edit = {
        def story = Story.read(params.id)
        render template: "edit", model:[story: story]
    }
    def update = {
        def story = Story.get(params.id)
        if (!story) {
            flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'story.label', default: 'Story'), params.id])}"
            render status: "404", flash.error
        } else {
            if (params.version) {
                def version = params.version.toLong()
                if (story.version > version) {
                    story.errors.rejectValue("version", "default.optimistic.locking.failure",
                                             [message(code: 'story.label', default: 'Story')] as Object[],
                                             "Another user has updated this Story while you were editing")
                    render status: "404", template: "edit", model: [story: story]
                    return
                }
            }
            story.properties = params
            storyService.setVector(story, params)

            if (!story.hasErrors() && story.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'story.label', default: 'Story'), story.id])}"
                render template: "/project/card", model: [story: story]
            } else {
                flash.error = "Please provide all required values."
                render status: "400", template: "edit", model: [story: story]
            }
        }
    }

    def confirmDelete = {
        def story = Story.read(params.id)
        render template: "confirmDelete", model:[story: story]
    }
    def delete = {
        def story = Story.get(params.id)
        if (story) {
            try {
                story.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'story.label', default: 'Story'), params.id])}"
                render flash.message
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'story.label', default: 'Story'), params.id])}"
                render status: "400", template: "confirmDelete", model: [story: story]
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'story.label', default: 'Story'), params.id])}"
            render status: "404", template: "confirmDelete", model: [story: story]
        }
    }

    def move(Long storyId, Long xAxisId, Long xId, Long yAxisId, Long yId, String sortOrder) {
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
            log.error "${messagePrefix} movement must be along the x axis."

        } else if (y && (y.dimension != yAxis)) {
            log.error "${messagePrefix} movement must be along the y axis."

        } else { // everything's ok...
            storyService.move(story, xAxis, x, yAxis, y)
            List<Long> sortedIds = sortOrder.split(",").collect { idStr -> idStr as Long }
            projectService.updateStoryOrder(story.project, x, y, sortedIds)
            render "moved"
        }
    }

}
