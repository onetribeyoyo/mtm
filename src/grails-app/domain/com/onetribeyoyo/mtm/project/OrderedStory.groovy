package com.onetribeyoyo.mtm.project

class OrderedStory implements Comparable {

    static belongsTo = [
        story: Story
    ]

    Element x
    Element y

    Long order

    static mapping = {
        order column:"order_" // "order" is an sql keyword!
    }

    static constraints = {
        story nullable: false
        x nullable: true
        y nullable: true
        order nullable: true
    }

    int compareTo(that) {
        (that == null) ? 1 : ((this.order <=> that.order) ?: (this.story.id <=> that.story.id))
    }

    String toString() {
        "story[id:${story.id}].(${x},${y}):${order}"
    }

}
