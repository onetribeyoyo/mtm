package com.onetribeyoyo.mtm.project

class Dimension implements Comparable {

    static belongsTo = [
        project: Project
    ]

    String name
    String description

    SortedSet elements
    static hasMany = [
        elements: Element
    ]

    String colour = ""
    LayoutStyle layoutStyle = LayoutStyle.FLOW

    static constraints = {
        name nullable: false, blank: false, maxSize: 255, unique:"project"
        description nullable: true, blank: true, maxSize: 4095
        colour nullable: true, blank: true, maxSize: 63
        //layoutStyle inList: LayoutStyle.values()
    }

    Element elementFor(String value) {
        elements.find { p -> (p.value?.toLowerCase() == value?.toLowerCase()) }
    }

    Boolean isColourDimension() {
        this.project.colourDimension == this
    }

    Boolean isHighlightDimension() {
        this.project.highlightDimension == this
    }

    Boolean isPrimaryXAxis() {
        this.project.primaryXAxis == this
    }

    Boolean isPrimaryYAxis() {
        this.project.primaryYAxis == this
    }

    int compareTo(that) {
        (this.name <=> that.name) ?: (this.id <=> that.id)
    }

    String toString() {
        name ? name : id
    }

}
