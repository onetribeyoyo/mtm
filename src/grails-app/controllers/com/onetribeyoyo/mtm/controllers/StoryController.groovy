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

    def create = {
        def project = Project.read(params.id)
        def story = new Story(project: project)
        story.properties = params
        render template: "create", model:[story: story]
    }
    def save = {
        def project = Project.read(params.project.id)
        Story story = projectService.createStory(project, params)

        if (story.hasErrors()) {
            flash.error = "Please provide all required values."
            render status: "400", template: "create", model: [story: story]
        }
        else {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'story.label', default: 'Story'), story.id])}"
            redirect controller: "project", action: "show", id: story.project.id
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
        log.debug "move(story:${storyId}, x:${xAxisId}:${xId}, y:${yAxisId}:${yId}, sort:${sortOrder})"

        Story story = Story.get(storyId);
        Dimension xAxis = Dimension.get(xAxisId)
        Dimension yAxis = Dimension.get(yAxisId)
        Element x = Element.get(xId)
        Element y = Element.get(yId)

        if (
            (xAxis.project == story.project) && (yAxis.project == story.project) // got to move only along dimensions that make sense for the story
            && (!x || (x.dimension == xAxis)) // movement must be along the x axis
            && (!y || (y.dimension == yAxis)) // movement must be along the y axis
        ) {
            storyService.move(story, xAxis, x, yAxis, y)
            List<Long> sortedIds = sortOrder.split(",").collect { idStr -> idStr as Long }
            projectService.updateStoryOrder(story.project, x, sortedIds)
            render "moved"
        }
        else {
            log.error "move(story:${storyId}, x:${xAxisId}:${xId}, y:${yAxisId}:${yId}, sort:${sortOrder}) illegal attempt to move a story into dimensions outside of the project!"
        }
    }

}
