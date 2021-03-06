package com.onetribeyoyo.mtm.project

import spock.lang.Specification

class ProjectISpec extends Specification {

    def projectService

    def "lastStatus"() {
        when:
            Project project = Project.build()
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
            projectService.configureDimension(project, DimensionData.STATUS)

        then:
            !project.lastStatus()
    }

}
