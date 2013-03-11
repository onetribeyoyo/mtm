package com.onetribeyoyo.mtm.domain

class OrderedElement implements Comparable {

    static belongsTo = [
        story: Story
    ]

    Element element
    // TODO: needs two elements, one for x, one for 7!

    Long order = 9999 // elements start at the end of the list.

    static mapping = {
        order column:"order_" // "order" is an sql keyword!
    }

    static constraints = {
        story nullable: false
        element nullable: false
        order nullable: false
    }

    int compareTo(that) {
        (this.order <=> that.order) ?: (this.id <=> that.id)
    }

    String toString() {
        "${element}:${order}"
    }

}
