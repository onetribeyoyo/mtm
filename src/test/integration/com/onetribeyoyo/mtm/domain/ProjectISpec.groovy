package com.onetribeyoyo.mtm.domain

import grails.plugin.spock.IntegrationSpec

class ProjectISpec extends IntegrationSpec {

    def projectService

    def "lastStatus"() {
        when:
            Project project = Project.build()
            projectService.configureDimensionAndElements(project, projectService.STATUS_DIMENSION_DATA)
            projectService.configureDefaults(project)

        then:
            project.lastStatus().value == "done"
    }

    def "lastStatus for default project"() {
        when:
            Project project = Project.build()
            projectService.configureDefaults(project)

        then:
            project.lastStatus().value == "done"
    }

    def "no lastStatus when elements have not been configured"() {
        when:
            Project project = Project.build()
            projectService.configureDimension(project, projectService.STATUS_DIMENSION_DATA)

        then:
            !project.lastStatus()
    }

    def "storymapfor empty project"() {
        when:
            Project project = Project.build()

        then:
            Dimension release = project.dimensionFor("release")
            Dimension status = project.dimensionFor("status")
            def map = project.storymapFor(release, status)
            map == [:]
    }

    def "storymapfor project with just dimensions, no elements"() {
        when:
            Project project = Project.build()
            projectService.configureDimension(project, projectService.RELEASE_DIMENSION_DATA)
            projectService.configureDimension(project, projectService.STATUS_DIMENSION_DATA)

        then:
            Dimension release = project.dimensionFor("release")
            Dimension status = project.dimensionFor("status")
            def map = project.storymapFor(release, status)
            map == [null:[null:[]]]
    }

    def "storymapfor default project with no stories"() {
        when:
            Project project = Project.build()
            projectService.configureDefaults(project)

        then:
            Dimension release = project.dimensionFor("release")
            Dimension status = project.dimensionFor("status")
            def map = project.storymapFor(release, status)
            map == [null:[null:[]]]
    }

    def "storymapfor default project with some stories"() {
        when:
            Project project = Project.build()
            projectService.configureDefaults(project)
            projectService.createStory(project, [summary: "story 1"])
            projectService.createStory(project, [summary: "story 2"])

        then:
            Dimension release = project.dimensionFor("release")
            Dimension status = project.dimensionFor("status")
            def map = project.storymapFor(release, status)
            map == [null:[null:[]]]
    }

}
