package com.onetribeyoyo.mtm.services

import com.onetribeyoyo.mtm.domain.*

import grails.plugin.spock.IntegrationSpec
import spock.lang.Unroll

class ProjectServiceISpec extends IntegrationSpec {

    def projectService
    def storyService

    def "configureDimensionAndElements"() {
        given:
            Project project = Project.build()
            !project.dimensions
        and:
            def data = [ name: "yAxis", elements: ["a", "b", "c"] ]

        when:
            projectService.configureDimensionAndElements(project, data)
        then:
            project.dimensions.size() == 1
            project.dimensions.iterator()[0].name == data.name
            project.dimensions.iterator()[0].elements.size() == 3

        when:
            projectService.configureDimensionAndElements(project, data)
        then: "configureDimensionAndElements is idempotent"
            project.dimensions.size() == 1
            project.dimensions.iterator()[0].elements.size() == 3
    }

    @Unroll("configureBasis: dimension:'#dimensionName'.")
    def "configureBasis"() {
        when:
            Project project = Project.build()
            projectService.configureBasis(project)

        then:
            project.dimensions.size() == 2
            (project.primaryXAxis?.name == dimensionName) == primaryXAxis
            (project.primaryYAxis?.name == dimensionName) == primaryYAxis

        where:
            dimensionName | basis | primaryXAxis | primaryYAxis
            "release"     | true  | false        | true
            "status"      | true  | true         | false
            "feature"     | false | false        | false
            "strategy"    | false | false        | false
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
            "status"      | ["on deck", "blocked", "in progress", "ready to test", "done"]
            "feature"     | ["feature 1", "feature 2", "feature 3"]
            "strategy"    | ["strengths", "weaknesses", "opportunities", "threats"]
            "assigned to" | ["you", "me", "them", "everybody"]
    }

    def "createDimension"() {
        when:
            Project project = Project.build()
            project.dimensions?.size() == 0
            projectService.createDimension(project, [name: "5th"])

        then:
            project.dimensions?.size() == 1

        and: "can't add a second one with the same name"
            try {
                projectService.createDimension(project, [name: "5th"])
                fail()
            } catch (Exception ex) {
                // should fail with an error on the dimension
            }
    }

    def "createStory"() {
        when:
            Project project = Project.build()
            project.stories?.size() == 0
            projectService.createStory(project, [summary: "story 1"])

        then:
            project.stories?.size() == 1
    }

    def "test updateStoryOrder"() {
        given: "a project and a couple stories"
            Project project = Project.build()
            projectService.configureBasis(project)
            def axis = project.dimensionFor("status")
            def element = axis.elementFor("done")
        and:
            def story1 = projectService.createStory(project, [summary: "story 1"])
            def story2 = projectService.createStory(project, [summary: "story 2"])
            project.save(flush: true)
            storyService.slide(story1, axis, element)
            storyService.slide(story2, axis, element)
            project.save(flush: true)

        and: "the initial sort order is still the default..."
            !story1.orderingFor(element, null)
            !story2.orderingFor(element, null)

        when:
            projectService.updateStoryOrder(project, element, null, [story1.id, story2.id])
        then:
            story1.orderingFor(element, null).order == 0
            story2.orderingFor(element, null).order == 1

        when:
            projectService.updateStoryOrder(project, element, null, [story2.id, story1.id])
        then:
            story1.orderingFor(element, null).order == 1
            story2.orderingFor(element, null).order == 0
    }

}
