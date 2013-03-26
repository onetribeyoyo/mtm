package com.onetribeyoyo.mtm.services

import com.onetribeyoyo.mtm.domain.*

import grails.plugin.spock.IntegrationSpec
import spock.lang.Unroll

class StoryServiceISpec extends IntegrationSpec {

    def projectService
    def storyService

    @Unroll("slide along '#axisName' from A:'#fromA', to B:'#toB', to C:'#toC', and back to A.")
    def "slide"() {
         when:
            Project project = Project.build()
            projectService.configureBasis(project)
            project.save(flush: true)
            Story story = projectService.createStory(project, [summary: "slide(${axisName}, ${fromA}, ${toB}, ${toC})"])

        then: "starting at point 'A'"
            def axis = project.dimensionFor(axisName)
            def a = story.valueFor(axis)?.element
            a?.element?.value == fromA
        and: "slide to point 'B'"
            def b = axis.elementFor(toB)
            b.value == toB
            storyService.slide(story, axis, b)
            story.valueFor(axis)?.element == b
        and: "slide to point 'B'"
            def c = axis.elementFor(toC)
            c.value == toC
            storyService.slide(story, axis, c)
            story.valueFor(axis)?.element == c
        and: "finally, slide back to point 'A'"
            a?.value == fromA
            storyService.slide(story, axis, a)
            story.valueFor(axis)?.element == a

        where:
            axisName  | fromA  | toB       | toC
            "release" | null   | "r0.1"    | "r0.2"
            "status"  | null   | "on deck" | "done"
    }

    def "move"() {
         when:
            Project project = Project.build()
            projectService.configureBasis(project)
            project.save(flush: true)
            Story story = projectService.createStory(project, [summary: "move story"])

            def xAxis = project.dimensionFor("status")
            def xFrom = story.valueFor(xAxis)?.element
            def xTo = xAxis.elementFor("done")

            def yAxis = project.dimensionFor("release")
            def yFrom = story.valueFor(yAxis)?.element
            def yTo = yAxis.elementFor("r0.1")

        then: "target dimensions and elements are not null"
            xAxis
            xTo
            yAxis
            yTo
        and: "from is not the same as to"
            xFrom != xTo
            yFrom != yTo
        then: "the move does what we expect!"
            storyService.move(story, xAxis, xTo, yAxis, yTo)
            story.valueFor(xAxis)?.element?.value == xTo.value
            story.valueFor(yAxis)?.element?.value == yTo.value
    }

    def "setVector(..)"() {
        when:
            Project project = Project.build()
            projectService.configureBasis(project)
            project.save(flush: true)
            Story story = projectService.createStory(project, [summary: "setVector story"])

            def release = project.dimensionFor("release")
            def status = project.dimensionFor("status")


        then:
            !story.valueFor("release")?.element
            !story.valueFor("status")?.element
        and:
            def params = [release: "r0.1", status: "done"]
            storyService.setVector(story, params)
            story.valueFor(release).element.value == "r0.1"
            story.valueFor(status).element.value == "done"
    }

}
