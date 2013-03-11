package com.onetribeyoyo.mtm.services

import com.onetribeyoyo.mtm.domain.*

import grails.plugin.spock.IntegrationSpec
import spock.lang.Unroll

class ProjectServiceISpec extends IntegrationSpec {

    def projectService

    @Unroll("configureDefaultDimensions: dimension:'#dimensionName'.")
    def "configureDefaultDimensions"() {
        when:
            Project project = Project.build()
            projectService.configureDefaultDimensions(project)

        then:
            project.dimensions.size() == 4
            Dimension dimension = project.dimensionFor(dimensionName)
            dimension.basis == basis

        where:
            dimensionName | basis
            "release"     | true
            "status"      | true
            "feature"     | false
            "strategy"    | false
    }


    @Unroll("configureDefaultElements: dimension:'#dimensionName' elements are #elementNames.")
    def "configureDefaultDimensions"() {
        when:
            Project project = Project.build()
            projectService.configureDefaultDimensions(project)
            projectService.configureDefaultElements(project)

        then:
            project.dimensions.size() == 4
            Dimension dimension = project.dimensionFor(dimensionName)
            dimension.elements?.size() == elementNames.size()
            println "${dimension}"
            elementNames.each { name ->
                assert dimension.elementFor(name)
                //println "    ${dimension.elementFor(name)}"
            }

        where:
            dimensionName | elementNames
            "release"     | ["r0.1", "r0.2", "r0.3"]
            "status"      | ["on deck", "in progress", "ready to test", "done"]
            "feature"     | ["feature 1", "feature 2", "feature 3"]
            "strategy"    | ["strengths", "weaknesses", "opportunities", "threats"]
    }

}
