package com.onetribeyoyo.mtm.taglib

import com.onetribeyoyo.mtm.domain.Project

class TabsTagLib {

    static namespace = "tabs"

    def render = { attrs, body ->
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

}
