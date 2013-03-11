package com.onetribeyoyo.mtm.domain

import com.onetribeyoyo.mtm.util.LayoutStyle

class Dimension implements Comparable {

    static belongsTo = [
        project: Project
    ]

    String name
    String description
    String colour = ""
    Boolean basis = false
    LayoutStyle layoutStyle = LayoutStyle.FLOW

    SortedSet elements
    static hasMany = [
        elements: Element
    ]

    static constraints = {
        name nullable: false, blank: false, maxSize: 255
        description nullable: true, blank: true, maxSize: 4095
        colour nullable: true, blank: true, maxSize: 63
        basis nullable: true
        //layoutStyle inList: LayoutStyle.values()
    }

    Element elementFor(String value) {
        elements.find { p -> (p.value?.toLowerCase() == value?.toLowerCase()) }
    }

    int compareTo(that) {
        (this.name <=> that.name) ?: (this.id <=> that.id)
    }

    String toString() {
        name ? name : id
    }

}
