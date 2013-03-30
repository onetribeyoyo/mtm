package com.onetribeyoyo.mtm.domain

import com.onetribeyoyo.mtm.util.LayoutStyle

class Dimension implements Comparable {

    static belongsTo = [
        project: Project
    ]

    String name
    String description
    String colour = ""
    LayoutStyle layoutStyle = LayoutStyle.FLOW

    SortedSet elements
    static hasMany = [
        elements: Element
    ]

    static constraints = {
        name nullable: false, blank: false, maxSize: 255, validator: validateNameUniqueWithinProject
        description nullable: true, blank: true, maxSize: 4095
        colour nullable: true, blank: true, maxSize: 63
        //layoutStyle inList: LayoutStyle.values()
    }

    protected static validateNameUniqueWithinProject = { val, obj ->
        def result = true
        Dimension.withNewSession {
            def existingDimension = obj.project.dimensionFor(val)
            if (existingDimension && (existingDimension != obj)) {
                result = "com.onetribeyoyo.mtm.domain.Dimension.name.unique"
            }
        }
        return result
    }

    Element elementFor(String value) {
        elements.find { p -> (p.value?.toLowerCase() == value?.toLowerCase()) }
    }

    def deleteElement(Element element) {
        log.debug "deleteElement(${element})"

        this.project.stories.each { story ->
            if (element in story.vector) {
                story.removeFromVector(element)
            }
        }
        element.delete()
    }

    Boolean isColourDimension() {
        this.project.colourDimension == this
    }

    Boolean isHighlightDimension() {
        this.project.highlightDimension == this
    }

    Boolean isPrimaryAxis() {
        this.project.primaryAxis == this
    }

    int compareTo(that) {
        (this.name <=> that.name) ?: (this.id <=> that.id)
    }

    String toString() {
        name ? name : id
    }

}
