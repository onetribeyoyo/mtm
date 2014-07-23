package com.onetribeyoyo.mtm

import com.onetribeyoyo.mtm.project.*

class MtmTagLib {

    static namespace = "mtm"

    def springSecurityService
    def storymapService

    def format = { attrs ->
        def value = attrs.value
        if (value) {
            out << value
        } else {
            out << "<span class=\"hint\">not specified</span>"
        }
    }

    def tabs = { attrs, body ->
        Project project = attrs.project
        String selectedTab = attrs.selectedTab
        boolean includeFaqDetails = attrs.includeFaqDetails = attrs.includeFaqDetails ?: false

        out << "<ul class='tabrow non-printing'>\n"

        if (project) {
            renderTab((selectedTab == "map___"),    [controller: "project", action: "map",  id: project?.id ], "Story Map")
            renderTab((selectedTab == "config___"), [controller: "project", action: "show", id: project?.id ], "Project Config")
        }

        renderTab((selectedTab == "projects___"), [ controller: "project", action: "list", id: project?.id ], "<em>(projects)</em>")

        if (includeFaqDetails) {
            renderTab((selectedTab == "faq"),        [ controller: "info", action: "faq",        id: project?.id ], "FAQ")
            renderTab((selectedTab == "process"),    [ controller: "info", action: "process",    id: project?.id ], "Mapping Process")
            renderTab((selectedTab == "estimation"), [ controller: "info", action: "estimation", id: project?.id ], "Estimation")
        } else {
            renderTab((selectedTab == "info___"),    [ controller: "info", action: "faq",        id: project?.id ], "<em>FAQ</em>")
        }

        def user = springSecurityService.currentUser
        if (user) {
            renderTab((selectedTab == "user___"),    [ controller: "logout" ], "<em>${user} <small>(logout)</small></em>")
        }

        out << "</ul>\n"
    }

    private void renderTab(boolean selected, Map params, String label) {
        if (selected) {
            out << "  <li class='selected'><strong>${label}</strong></li>\n"
        } else {
            out << "  <li>"
            out << g.link(params, label)
            out << "</li>\n"
        }
    }

    def grid = { attrs, body ->
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

        out << "  <ul class='grid-row'>\n"

        // the dimension selector controls...
        out << "    <li class='grid-blank'>"
        out << menu(attrs)
        out << "</li>\n"

        // the column headings...
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

            out << "      <a href='#' class='y toggle-row row-${y.id} ${collapseStyle} non-printing' data-yId='${y.id}'><img src='../../images/arrow_collapse.png' /></a>\n"
            out << "      <a href='#' class='y toggle-row row-${y.id} ${expandStyle} non-printing' data-yId='${y.id}'><img src='../../images/arrow_expand.png' /></a>\n"
            out << "    </li>\n"

            // a cell for cards where (x == null)
            out << "    <li class='grid-cell ${rowStyle}' data-xAxisId='${xAxis.id}' data-xId='' data-yAxisId='${yAxis.id}' data-yId='${y.id}'>\n"
            storymap[y].columns[null].each { orderedStory ->
                out << g.render(template: '/project/card', model: [story:orderedStory.story, layoutStyle: layoutStyle, complete: complete, xAxis: xAxis, yAxis: yAxis])
            }
            out << "    </li>\n"

            // a cell for element on the x axis
            xAxis?.elements.each { x ->
                out << "    <li class='grid-cell ${rowStyle}' data-xAxisId='${xAxis.id}' data-xId='${x.id}' data-yAxisId='${yAxis.id}' data-yId='${y.id}'>\n"
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
        out << "      <a href='#' class='y toggle-row row-null' data-yId='null'><img src='../../images/arrow_collapse.png' /></a>\n"
        out << "      <a href='#' class='y toggle-row row-null hidden' data-yId='null'><img src='../../images/arrow_expand.png' /></a>\n"
        out << "    </li>\n"

        // a cell for cards where both (x == null) and  (y == null)
        out << "    <li class='grid-cell ${rowStyle}' data-xAxisId='${xAxis.id}' data-xId='' data-yAxisId='${yAxis.id}' data-yId=''>\n"
        storymap[null].columns[null].each { orderedStory ->
            out << g.render(template: '/project/card', model: [story:orderedStory.story, layoutStyle: layoutStyle])
        }
        out << "    </li>\n"

        // a cell for element on the x axis
        xAxis?.elements.each { x ->
            out << "    <li class='grid-cell ${rowStyle}' data-xAxisId='${xAxis.id}' data-xId='${x.id}' data-yAxisId='${yAxis.id}' data-yId=''>\n"
            storymap[null].columns[x].each { orderedStory ->
                out << g.render(template: '/project/card', model: [story:orderedStory.story, layoutStyle: layoutStyle])
            }
            out << "    </li>\n"
        }
        out << "  </ul>\n"
        out << "</div>"
        out << "<div class='clear non-printing' id='callbackConsole'></div>"
    }

    /**
     *  Note: Pairs of dimensions are only listed once (if x/y is listed, no need to list y/x.)
     *  You can always flip axes on the map grid to find what you need.
     */
    def menu = { attrs, body ->
        Project project = attrs.project
        Dimension xAxis = attrs.xAxis
        Dimension yAxis = attrs.yAxis

        out << "  <div class='dropdown non-printing'>\n"
        out << g.link(action: "map", id: project.id, params: [x: yAxis?.name, y: xAxis?.name], class: "non-printing",
                      "<img src='${fam.icon(name: 'arrow_refresh')}' title='flip axes' />")

        if (project.dimensions.size() > 2) {
            out << "    <a class='menu nowrap'><img src='${fam.icon(name: 'cog')}' title='change axes' /></a>"
            out << "    <div class='submenu' style='display: none;'>\n"
            out << "      <ul>\n"
            // only list the pair of dimensions once: if x/y is listed, no need to list y/x
            def done = []
            project.dimensions.each { x ->
                done << x
                project.dimensions.each { y ->
                    if ((x != y) && !(y in done)) {
                        out << "        <li class='nowrap'>"
                        out << g.link(controller: "project", action: "map", id: project.id, params: [x: x?.name, y: y?.name], "${x.name.capitalize()} by ${y.name.capitalize()}")
                        out << "</li>\n"
                    }
                }
            }
            out << "      </ul>\n"
            out << "    </div>\n"
        }
        out << "  </div>\n"
    }

    /**
     *  Renders a div with links to all possible maps.
     */
    def mapList = { attrs, body ->
        Project project = attrs.project
        Dimension xAxis = attrs.xAxis
        Dimension yAxis = attrs.yAxis

        out << "<div class='section float-left non-printing'>\n"
        out << "  <h2><img src='${fam.icon(name: 'map')}' title='map' /> All Maps</h2>\n"
        out << "  <hr />\n"
        out << " x/y = ${xAxis} / ${yAxis}"
        out << "  <ul>\n"
        // only list the pair of dimensions once: if x/y is listed, no need to list y/x
        def done = []
        project.dimensions.each { x ->
            done << x
            project.dimensions.each { y ->
                if ((x != y) && !(y in done)) {
                    def label = "${x.name.capitalize()} by ${y.name.capitalize()}"
                    //if ((x == xAxis) && (y == yAxis)) {
                    //    out << "    <li>${label.encodeAsHTML()}</li>\n"
                    //} else {
                        out << "    <li>"
                        out << g.link(controller: "project", action: "map", id: project.id, params: [x: x?.name, y: y?.name], label)
                        out << "</li>\n"
                    //}
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
