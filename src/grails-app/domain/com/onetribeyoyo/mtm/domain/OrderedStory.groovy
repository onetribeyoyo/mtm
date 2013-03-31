package com.onetribeyoyo.mtm.domain

class OrderedStory implements Comparable {

    static belongsTo = [
        story: Story
    ]

    Element x
    Element y

    Long order = 9999 // "unsorted" stories start at the end of the list.

    static mapping = {
        order column:"order_" // "order" is an sql keyword!
    }

    static constraints = {
        story nullable: false
        x nullable: true
        y nullable: true
        order nullable: false
    }

    int compareTo(that) {
        (this.order <=> that.order) ?: (this.story.id <=> that.story.id)
    }

    String toString() {
        "story[id:${story.id}].(${x},${y}):${order}"
    }

}
