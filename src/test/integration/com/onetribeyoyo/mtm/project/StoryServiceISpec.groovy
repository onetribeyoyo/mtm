package com.onetribeyoyo.mtm.project

import spock.lang.Specification
import spock.lang.Unroll

class StoryServiceISpec extends Specification {

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
            def a = story.valueFor(axis)
            a?.value == fromA
        and: "slide to point 'B'"
            def b = axis.elementFor(toB)
            b.value == toB
            storyService.slide(story, axis, b)
            story.valueFor(axis) == b
        and: "slide to point 'B'"
            def c = axis.elementFor(toC)
            c.value == toC
            storyService.slide(story, axis, c)
            story.valueFor(axis) == c
        and: "finally, slide back to point 'A'"
            a?.value == fromA
            storyService.slide(story, axis, a)
            story.valueFor(axis) == a

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
            def xFrom = story.valueFor(xAxis)
            def xTo = xAxis.elementFor("done")

            def yAxis = project.dimensionFor("release")
            def yFrom = story.valueFor(yAxis)
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
            story.valueFor(xAxis)?.value == xTo.value
            story.valueFor(yAxis)?.value == yTo.value
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
            !story.valueFor("release")
            !story.valueFor("status")
        and:
            def params = [release: "r0.1", status: "done"]
            storyService.setVector(story, params)
            story.valueFor(release).value == "r0.1"
            story.valueFor(status).value == "done"
    }

}
