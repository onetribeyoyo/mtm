package com.onetribeyoyo.mtm.taglib

import com.onetribeyoyo.mtm.domain.*

import com.onetribeyoyo.mtm.util.LayoutStyle

class ProjectTagLib {

    static namespace = "project"

    def storymapService

    def tabs = { attrs, body ->
        Project project = attrs.project
        String selectedTab = attrs.selectedTab

        def tabDefs = [:]

        Dimension release = project.dimensionFor("release")
        project.dimensions.each { dimension ->
            if ((dimension != release) && dimension.elements) {
                tabDefs."${dimension.name}" = [
                    controller: "project", action: "map", id: project?.id,
                    x: dimension,
                    y: release,
                    label: "${dimension.name.capitalize()} by Release"
                ]
            }
        }

        tabDefs.config___   = [ controller: "project", label: "Dimensions",     action: "show", id: project?.id ]
        tabDefs.projects___ = [ controller: "project", label: "Switch Project", action: "list" ]
        tabDefs.info___     = [ controller: "info",    label: "FAQ" ]

        out << "<ul class='tabrow'>\n"
        tabDefs.each { key, params ->
            if (selectedTab == key) {
                out << "<li class='selected'>${params.label}</li>"
            } else {
                out << "<li>"
                out << g.link(controller: params.controller, action: params.action, id: params.id,
                              params: [x: params.x?.name, y: params.y?.name],
                              params.label)
                out << "</li>\n"
            }
        }
        out << "</ul>\n"
    }

    def cardGrid = { attrs, body ->
        Project project = attrs.project
        Dimension xAxis = attrs.xAxis
        Dimension yAxis = attrs.yAxis

        assert xAxis
        assert xAxis.project == project
        assert yAxis
        assert yAxis.project == project

        def storymap = storymapService.storymapFor(project, xAxis, yAxis)

        // the default layout is linear (stories stacked one on top of the other) but either dimension can
        // request a flow layout.
        String layoutStyle = ((xAxis.layoutStyle == LayoutStyle.FLOW) || (yAxis.layoutStyle == LayoutStyle.FLOW)) ? "float-left" : ""

        out << "<div class='grid section'>\n"

        // the column headings...
        out << "  <ul class='grid-row'>\n"
        out << "    <li class='grid-blank'></li>\n"
        out << "    <li class='grid-column-head ${xAxis?.colour}'>???</li>\n" // column for cards where (x == null)
        xAxis?.elements.each { x -> // one column for each element on the x axis
            out << "    <li class='grid-column-head ${x.colour ?: xAxis?.colour}'>${x.value}</li>\n"
        }
        out << "  </ul>\n"

        // one row for each element on the y axis
        def lastRowNumber = 0
        yAxis?.elements.eachWithIndex { y, rowNumber ->

            def rowStyle = ((rowNumber % 2) == 0) ? "odd" : "even\n"
            out << "  <ul class='grid-row'>\n"

            // the row heading...
            out << "    <li class='grid-row-head ${y.colour ?: yAxis?.colour} ${rowStyle}'>\n"
            out << "      ${y.value}\n"

            def complete = y.complete()
            def collapseStyle = complete ? "hidden" : ""
            def expandStyle = complete ? "" : "hidden"

            out << "      <a href='#' class='y release-toggle release-toggle-${y.id} ${collapseStyle}' yId='${y.id}'><img src='../../images/arrow_collapse.gif' /></a>\n"
            out << "      <a href='#' class='y release-toggle release-toggle-${y.id} ${expandStyle}' yId='${y.id}'><img src='../../images/arrow_expand.gif' /></a>\n"
            out << "    </li>\n"

            // a cell for cards where (x == null)
            out << "    <li class='grid-cell ${rowStyle}' xAxisId='${xAxis.id}' xId='' yAxisId='${yAxis.id}' yId='${y.id}'>\n"
            storymap[null][y].each { story ->
                out << g.render(template: '/project/card', model: [story:story, layoutStyle: layoutStyle, complete: complete, xAxis: xAxis, yAxis: yAxis])
            }
            out << "    </li>\n"

            // a cell for element on the x axis
            xAxis?.elements.each { x ->
                out << "    <li class='grid-cell ${rowStyle}' xAxisId='${xAxis.id}' xId='${x.id}' yAxisId='${yAxis.id}' yId='${y.id}'>\n"
                storymap[x][y].each { story ->
                    out << g.render(template: '/project/card', model: [story:story, layoutStyle: layoutStyle, complete: complete, xAxis: xAxis, yAxis: yAxis])
                }
                out << "    </li>\n"
            }
            out << "  </ul>\n"
            lastRowNumber = rowNumber
        }

        // one more row for cards where (y == null)...
        out << "  <ul class='grid-row'>\n"
        def rowStyle = ((++lastRowNumber % 2) == 0) ? "odd" : "even\n"
        out << "    <li class='grid-row-head ${yAxis?.colour} ${rowStyle}'>???"
        out << "      <a href='#' class='y release-toggle release-toggle-null' yId='null'><img src='../../images/arrow_collapse.gif' /></a>\n"
        out << "      <a href='#' class='y release-toggle release-toggle-null hidden' yId='null'><img src='../../images/arrow_expand.gif' /></a>\n"
        out << "    </li>\n"

        // a cell for cards where both (x == null) and  (y == null)
        out << "    <li class='grid-cell ${rowStyle}' xAxisId='${xAxis.id}' xId='' yAxisId='${yAxis.id}' yId=''>\n"
        storymap[null][null].each { story ->
            out << g.render(template: '/project/card', model: [story:story, layoutStyle: layoutStyle])
        }
        out << "    </li>\n"

        // a cell for element on the x axis
        xAxis?.elements.each { x ->
            out << "    <li class='grid-cell ${rowStyle}' xAxisId='${xAxis.id}' xId='${x.id}' yAxisId='${yAxis.id}' yId=''>\n"
            storymap[x][null].each { story ->
                out << g.render(template: '/project/card', model: [story:story, layoutStyle: layoutStyle])
            }
            out << "    </li>\n"
        }
        out << "  </ul>\n"
        out << "</div>"
        out << "<div class='clear non-printing' id='callbackConsole'></div>"
    }

}
