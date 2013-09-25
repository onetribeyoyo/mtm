package com.onetribeyoyo.mtm.domain

class Element implements Comparable {

    static belongsTo = [
        dimension: Dimension
    ]

    String value
    String description
    String colour = ""
    Long order = 9999 // new elements start at the end of the list.

    static mapping = {
        order column:"order_" // "order" is an sql keyword!
    }

    static constraints = {
        dimension nullable: false
        value nullable: false, blank: false, maxSize: 255, validator: validateValueUniqueWithinDimension
        colour nullable: true, blank: true, maxSize: 63
        description nullable: true, blank: true, maxSize: 4095
        order nullable: false
    }

    protected static validateValueUniqueWithinDimension = { val, obj ->
        def result = true
        Element.withNewSession {
            def existingElement = obj.dimension.elementFor(val)
            if (existingElement && (existingElement != obj)) {
                result = "com.onetribeyoyo.mtm.domain.Element.value.unique"
            }
        }
        return result
    }

    boolean complete() {
        // for all stories with vectors containing this element...
        def stories = this.dimension.project.stories.findAll { story ->
            (this in story.vector)
        }

        // return true iff they're vectors also contain the "last" status element.
        def lastStatus = this.dimension.project.lastStatus()
        return stories.inject(true) { completeSoFar, story ->
            completeSoFar && (lastStatus in story.vector)
        }
    }

    boolean incomplete() {
        !complete()
    }

    int compareTo(that) {
        (this.order <=> that.order) ?: (this.value <=> that.value) ?: (this.id <=> that.id)
    }

    String toString() {
        value ? value : id
    }

}
