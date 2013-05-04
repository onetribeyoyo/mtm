package com.onetribeyoyo.mtm.taglib

import com.onetribeyoyo.mtm.domain.*

class InfoTagLib {

    static namespace = "info"

    def tabs = { attrs, body ->
        String selectedTab = attrs.selectedTab

        out << "<ul class='tabrow non-printing'>\n"

        [
            "faq":        [ controller: "info",    action: "faq",        label: "FAQ" ],
            "storymaps":  [ controller: "info",    action: "storymap",   label: "Story Maps" ],
            "process":    [ controller: "info",    action: "process",    label: "Mapping Process" ],
            "estimation": [ controller: "info",    action: "estimation", label: "Estimation" ],
            "projects":   [ controller: "project", action: "list",       label: "Projects" ],
        ].each { key, params ->
            if (selectedTab == key) {
                out << "  <li class='selected'><strong>${params.label}</strong></li>\n"
            } else {
                out << "  <li>"
                out << g.link(controller: params.controller, action: params.action, id: params.id, params.label)
                out << "</li>\n"
            }
        }

        out << "</ul>\n"
    }

}
