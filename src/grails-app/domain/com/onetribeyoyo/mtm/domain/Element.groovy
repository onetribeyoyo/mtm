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

    /**
     *  Returns true iff for all stories with a vector containing this element, the vector also contains
     *  thatElement.
     */
    // TODO: got to be a better method name!
    boolean onPoint(Element thatElement) {
        def orderedStories = this.dimension.project.orderedStoriesFor(this)
        if (!orderedStories) {
            return false
        } else {
            //orderedStories.each { orderedElement ->
            //    println "   . . .     ${orderedElement}"
            //    println "   . . .         ${orderedElement.story}"
            //    println "   . . .         ${orderedElement.element}"
            //    println "   . . .         ${orderedElement.story.valueFor(thatElement.dimension)}"
            //    println "   . . .         ${orderedElement.story.valueFor(thatElement.dimension)?.element}"
            //}
            def result = orderedStories.find { orderedElement -> (orderedElement.story.valueFor(thatElement.dimension)?.element != thatElement) }
            //println "   . . .     thatElement: ${thatElement}"
            //println "   . . . <-- ${result}"
            //println ""
            return result
        }
    }

    boolean inProgress() {
        def lastStatus = this.dimension.project.lastStatus()
        onPoint(lastStatus)
    }

    boolean complete() {
        !inProgress()
    }

    int compareTo(that) {
        (this.order <=> that.order) ?: (this.value <=> that.value) ?: (this.id <=> that.id)
    }

    String toString() {
        value ? value : id
    }

}
