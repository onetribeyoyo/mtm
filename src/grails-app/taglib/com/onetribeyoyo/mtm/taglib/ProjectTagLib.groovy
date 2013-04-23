package com.onetribeyoyo.mtm.taglib

import com.onetribeyoyo.mtm.domain.*

import com.onetribeyoyo.mtm.util.LayoutStyle

class ProjectTagLib {

    static namespace = "project"

    def storymapService

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
        out << "    <li class='grid-blank'>"
        out << g.link(action: "map", id: project.id, params: [x: yAxis?.name, y: xAxis?.name], class: "non-printing",
                      "<img src='${fam.icon(name: 'arrow_refresh')}' title='flip axes' />")
        out << "</li>\n"
        out << "    <li class='grid-column-head ${xAxis?.colour}'>???</li>\n" // column for cards where (x == null)
        xAxis?.elements.each { x -> // one column for each element on the x axis
            out << "    <li class='grid-column-head ${x.colour ?: xAxis?.colour}'>${x.value.encodeAsHTML()}</li>\n"
        }
        out << "  </ul>\n"

        // one row for each element on the y axis
        def lastRowNumber = 0
        yAxis?.elements.eachWithIndex { y, rowNumber ->

            def rowStyle = ((rowNumber % 2) == 0) ? "odd" : "even\n"
            out << "  <ul class='grid-row'>\n"

            // the row heading...
            out << "    <li class='grid-row-head ${y.colour ?: yAxis?.colour} ${rowStyle}'>\n"
            out << "      ${y.value.encodeAsHTML()}\n"
            if (project.showEstimates && storymap[y].estimate) {
                out << "      <div class='estimate'>"
                out << storymap[y].estimate
                if (project.estimateUnits) {
                    out << "&nbsp;${project.estimateUnits.encodeAsHTML()}"
                }
                out << "</div>\n"
            }

            def complete = y.complete()
            def collapseStyle = complete ? "hidden" : ""
            def expandStyle = complete ? "" : "hidden"

            out << "      <a href='#' class='y toggle-row row-${y.id} ${collapseStyle} non-printing' yId='${y.id}'><img src='../../images/arrow_collapse.png' /></a>\n"
            out << "      <a href='#' class='y toggle-row row-${y.id} ${expandStyle} non-printing' yId='${y.id}'><img src='../../images/arrow_expand.png' /></a>\n"
            out << "    </li>\n"

            // a cell for cards where (x == null)
            out << "    <li class='grid-cell ${rowStyle}' xAxisId='${xAxis.id}' xId='' yAxisId='${yAxis.id}' yId='${y.id}'>\n"
            storymap[y].columns[null].each { orderedStory ->
                out << g.render(template: '/project/card', model: [story:orderedStory.story, layoutStyle: layoutStyle, complete: complete, xAxis: xAxis, yAxis: yAxis])
            }
            out << "    </li>\n"

            // a cell for element on the x axis
            xAxis?.elements.each { x ->
                out << "    <li class='grid-cell ${rowStyle}' xAxisId='${xAxis.id}' xId='${x.id}' yAxisId='${yAxis.id}' yId='${y.id}'>\n"
                storymap[y].columns[x].each { orderedStory ->
                    out << g.render(template: '/project/card', model: [story:orderedStory.story, layoutStyle: layoutStyle, complete: complete, xAxis: xAxis, yAxis: yAxis])
                }
                out << "    </li>\n"
            }
            out << "  </ul>\n"
            lastRowNumber = rowNumber
        }

        // one more row for cards where (y == null)...
        out << "  <ul class='grid-row'>\n"
        def rowStyle = ((++lastRowNumber % 2) == 0) ? "odd" : "even\n"
        out << "    <li class='grid-row-head ${yAxis?.colour} ${rowStyle}'>???\n"
        if (project.showEstimates && storymap[null].estimate) {
            out << "      <div class='estimate'>"
            out << storymap[null].estimate
            if (project.estimateUnits) {
                out << "&nbsp;${project.estimateUnits.encodeAsHTML()}"
            }
            out << "</div>\n"
        }
        out << "      <a href='#' class='y toggle-row row-null' yId='null'><img src='../../images/arrow_collapse.png' /></a>\n"
        out << "      <a href='#' class='y toggle-row row-null hidden' yId='null'><img src='../../images/arrow_expand.png' /></a>\n"
        out << "    </li>\n"

        // a cell for cards where both (x == null) and  (y == null)
        out << "    <li class='grid-cell ${rowStyle}' xAxisId='${xAxis.id}' xId='' yAxisId='${yAxis.id}' yId=''>\n"
        storymap[null].columns[null].each { orderedStory ->
            out << g.render(template: '/project/card', model: [story:orderedStory.story, layoutStyle: layoutStyle])
        }
        out << "    </li>\n"

        // a cell for element on the x axis
        xAxis?.elements.each { x ->
            out << "    <li class='grid-cell ${rowStyle}' xAxisId='${xAxis.id}' xId='${x.id}' yAxisId='${yAxis.id}' yId=''>\n"
            storymap[null].columns[x].each { orderedStory ->
                out << g.render(template: '/project/card', model: [story:orderedStory.story, layoutStyle: layoutStyle])
            }
            out << "    </li>\n"
        }
        out << "  </ul>\n"
        out << "</div>"
        out << "<div class='clear non-printing' id='callbackConsole'></div>"
    }

    def tabs = { attrs, body ->
        Project project = attrs.project
        String selectedTab = attrs.selectedTab

        out << "<ul class='tabrow non-printing'>\n"

        def primaryAxis = project.primaryAxis
        project.dimensions.each { dimension ->
            if (!dimension.isPrimaryAxis() && dimension.elements) {
                def label = "${dimension.name.capitalize()} by ${primaryAxis?.name?.capitalize()}"
                if (selectedTab == dimension.name) {
                    out << "  <li class='selected'>${label.encodeAsHTML()}</li>"
                } else {
                    out << "  <li>"
                    out << g.link(controller: "project", action: "map", id: project.id, params: [x: dimension?.name, y: primaryAxis?.name], label)
                    out << "</li>\n"
                }
            }
        }

        [
            "config___":   [ controller: "project", label: "Project",        action: "show", id: project?.id ],
            "projects___": [ controller: "project", label: "Switch Project", action: "list" ],
            "info___":     [ controller: "info",    label: "FAQ" ]
        ].each { key, params ->
            if (selectedTab == key) {
                out << "  <li class='selected'>${params.label.encodeAsHTML()}</li>"
            } else {
                out << "  <li>"
                out << g.link(controller: params.controller, action: params.action, id: params.id, params.label)
                out << "</li>\n"
            }
        }

        out << "</ul>\n"
    }

    /**
     *  Renders a div with links to all possiblt maps.
     */
    def maps = { attrs, body ->
        Project project = attrs.project
        Dimension xAxis = attrs.xAxis
        Dimension yAxis = attrs.yAxis

        out << "<div class='section float-left non-printing'>\n"
        out << "  <h2>All Maps</h2>\n"
        out << "  <hr />\n"
        out << "  <ul>\n"
        // only list the pair of dimensions once: if x/y is listed, no need to list y/x
        def done = []
        project.dimensions.each { x ->
            done << x
            project.dimensions.each { y ->
                if ((x != y) && !(y in done)) {
                    def label = "${x.name.capitalize()} by ${y.name.capitalize()}"
                    if ((x == xAxis) && (y == yAxis)) {
                        out << "    <li>${label.encodeAsHTML()}</li>\n"
                    } else {
                        out << "    <li>"
                        out << g.link(controller: "project", action: "map", id: project.id, params: [x: x?.name, y: y?.name], label)
                        out << "</li>\n"
                    }
                }
            }
        }
        out << "  </ul>\n"
        out << "  <hr />\n"
        out << "  <p class='narrow hint'>Note: Pairs of dimensions are only listed once (if x/y is listed, no need to list y/x.)  You can always flip axes"
        out << " <img src='${fam.icon(name: 'arrow_refresh')}' title='flip axes' /> on the map grid to find what you need.</p>\n"
        out << "</div>\n"
    }

}
