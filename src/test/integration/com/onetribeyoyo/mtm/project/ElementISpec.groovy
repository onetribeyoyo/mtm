package com.onetribeyoyo.mtm.project

import spock.lang.Specification

class ElementISpec extends Specification {

    def projectService
    def storyService

    def "complete, incomplete, ..."() {
        given:
            Project project = Project.build()
            projectService.configureBasis(project)
            project.save(flush: true)
            def release = project.dimensionFor("release")
            def r1 = release.elementFor("r0.1")
            def r2 = release.elementFor("r0.2")
            def status = project.dimensionFor("status")
            def inProgress = status.elementFor("in progress")
            def done = status.elementFor("done")
            Story story1 = projectService.createStory(project, [summary: "story 1"])
            Story story2 = projectService.createStory(project, [summary: "story 2"])

        when: "two stories in same release, neither 'done'"
            storyService.slide(story1, release, r1)
            storyService.slide(story1, status, inProgress)
            storyService.slide(story2, release, r1)
            storyService.slide(story2, status, inProgress)
        then: "r1 is incomplete"
            r1.incomplete()
        and: "r2 is complete ('cause it has no stories)"
            r2.complete()
        and: "the inverse is true"
            !r1.complete()
            !r2.incomplete()

        when: "two stories in same release, one done, the other incomplete"
            storyService.slide(story1, status, done)
        then: "r1 is incomplete"
            r1.incomplete()
            r2.complete()

        when: "two stories in same release, both done"
            storyService.slide(story2, status, done)
        then: "r1 is complete"
            r1.complete()
            r2.complete()

        when: "two stories in different releases, both done"
            storyService.slide(story2, release, r2)
        then: "r1 is complete"
            r1.complete()
            r2.complete()
    }

}
