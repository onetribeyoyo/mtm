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

}
