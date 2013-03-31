package com.onetribeyoyo.mtm.domain

class Story implements Comparable {

    static belongsTo = [
        project: Project
    ]

    String summary
    String detail
    Long estimate = 0

    SortedSet vector
    static hasMany = [
        vector : Element,
        ordering : OrderedStory
    ]

    Boolean blocked = false

    static constraints = {
        summary nullable: false, blank: false, maxSize: 255
        detail nullable: true, blank: true, maxSize: 4095
        estimate nullable: true, min: 0 as Long
    }

    OrderedStory orderingFor(Element x, Element y) {
        ordering.find { os -> ((os.x == x) && (os.y == y)) || ((os.y == x) && (os.x == y)) }
    }

    Element valueFor(String dimensionName) {
        vector.find { point -> (point.dimension.name == dimensionName) }
    }

    Element valueFor(Dimension dimension) {
        dimension ? valueFor(dimension.name) : null
    }

    int compareTo(that) {
        (this.id <=> that.id)
    }
    String toString() {
        id ? "${id}: ${summary}" : summary
    }

}
