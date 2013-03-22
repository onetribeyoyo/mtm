package com.onetribeyoyo.mtm.services

import com.onetribeyoyo.mtm.domain.*

import grails.plugin.spock.IntegrationSpec
import spock.lang.Unroll

class StorymapServiceISpec extends IntegrationSpec {

    def projectService
    def storymapService

    static final Map X_AXIS_DATA = [ name: "xAxis", elements: ["1", "2"] ]
    static final Map Y_AXIS_DATA = [ name: "yAxis", elements: ["a", "b", "c"] ]

    def "storymapfor empty project"() {
        when:
            Project project = Project.build()

            Dimension xAxis = project.dimensionFor("xAxis")
            Dimension yAxis = project.dimensionFor("yAxis")
            def storymap = storymapService.storymapFor(project, xAxis, yAxis)

        then:
            storymap == [:]
    }

    def "storymapfor project with basis dimensions, no elements"() {
        when:
            Project project = Project.build()
            projectService.configureDimension(project, X_AXIS_DATA)
            projectService.configureDimension(project, Y_AXIS_DATA)

            Dimension xAxis = project.dimensionFor("xAxis")
            Dimension yAxis = project.dimensionFor("yAxis")
            def storymap = storymapService.storymapFor(project, xAxis, yAxis)

        then:
            storymap.keySet().size() == 1
            storymap.each { row, rowData ->
                assert (row && yAxis.elements.contains(row)) || (row == null)
                rowData.columns.each { x, cell ->
                    assert (x && xAxis.elements.contains(x)) || (x == null)
                    assert cell == []
                }
            }
    }

    def "storymapfor project with basis dimensions and elements no stories"() {
        when:
            Project project = Project.build()
            projectService.configureDimensionAndElements(project, X_AXIS_DATA)
            projectService.configureDimensionAndElements(project, Y_AXIS_DATA)

            Dimension xAxis = project.dimensionFor("xAxis")
            Dimension yAxis = project.dimensionFor("yAxis")
            def storymap = storymapService.storymapFor(project, xAxis, yAxis)

        then:
            storymap.keySet().size() == yAxis.elements.size() + 1
            storymap.each { row, rowData ->
                assert (row && yAxis.elements.contains(row)) || (row == null)
                rowData.columns.keySet().size() == xAxis.elements.size() + 1
                rowData.columns.each { x, cell ->
                    assert (x && xAxis.elements.contains(x)) || (x == null)
                    assert cell == []
                }
            }
    }

    def "storymapfor default project with some stories"() {
        when:
            Project project = Project.build()
            projectService.configureDefaults(project)
            projectService.configureDimensionAndElements(project, X_AXIS_DATA)
            projectService.configureDimensionAndElements(project, Y_AXIS_DATA)
            Story story1 = projectService.createStory(project, [summary: "story 1"])
            Story story2 = projectService.createStory(project, [summary: "story 2"])

            Dimension xAxis = project.dimensionFor("xAxis")
            Dimension yAxis = project.dimensionFor("yAxis")
            def storymap = storymapService.storymapFor(project, xAxis, yAxis)

        then:
            assert project.stories.size() == 2
            storymap.keySet().size() == yAxis.elements.size() + 1
            storymap.each { row, rowData ->
                assert (row && yAxis.elements.contains(row)) || (row == null)
                rowData.columns.keySet().size() == xAxis.elements.size() + 1
                rowData.columns.each { x, cell ->
                    assert (x && xAxis.elements.contains(x)) || (x == null)
                    if (!row && !x) {
                        assert cell.size() == 2
                        assert cell.contains(story1)
                        assert cell.contains(story2)
                    } else {
                        assert cell == []
                    }
                }
            }
    }

}
