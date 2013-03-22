package com.onetribeyoyo.mtm.services

import com.onetribeyoyo.mtm.domain.*

import grails.plugin.spock.IntegrationSpec
import spock.lang.Unroll

class ProjectServiceISpec extends IntegrationSpec {

    def projectService

    @Unroll("configureBasis: dimension:'#dimensionName'.")
    def "configureBasis"() {
        when:
            Project project = Project.build()
            projectService.configureBasis(project)

        then:
            project.dimensions.size() == 2
            Dimension dimension = project.dimensionFor(dimensionName)
            (project.primary?.name == dimensionName) == primary

        where:
            dimensionName | basis | primary
            "release"     | true  | true
            "status"      | true  | false
            "feature"     | false | false
            "strategy"    | false | false
    }


    @Unroll("configureDefaults: dimension:'#dimensionName' elements are #elementNames.")
    def "configureDefaults"() {
        when:
            Project project = Project.build()
            projectService.configureDefaults(project)

        then:
            project.dimensions?.size() == 5
            Dimension dimension = project.dimensionFor(dimensionName)
            dimension.elements?.size() == elementNames.size()
            elementNames.each { name ->
                assert dimension.elementFor(name)
            }

        where:
            dimensionName | elementNames
            "release"     | ["r0.1", "r0.2", "r0.3"]
            "status"      | ["on deck", "in progress", "ready to test", "done"]
            "feature"     | ["feature 1", "feature 2", "feature 3"]
            "strategy"    | ["strengths", "weaknesses", "opportunities", "threats"]
            "assigned to" | ["you", "me", "them", "everybody"]
    }

    def "createStory"() {
        when:
            Project project = Project.build()
            //project.addToStories(projectService.createStory(project, [summary: "story 1"]))
            projectService.createStory(project, [summary: "story 1"])

        then:
            project.stories?.size() == 1
    }

}
