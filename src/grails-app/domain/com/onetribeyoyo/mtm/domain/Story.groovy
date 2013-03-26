package com.onetribeyoyo.mtm.domain

class Story {

    static belongsTo = [
        project: Project
    ]

    String summary
    String detail
    Long estimate = 0

    SortedSet vector
    static hasMany = [
        vector : OrderedElement
    ]

    Boolean blocked = false

    static constraints = {
        summary nullable: false, blank: false, maxSize: 255
        detail nullable: true, blank: true, maxSize: 4095
        estimate nullable: true, min: 0 as Long
    }

    OrderedElement valueFor(String dimensionName) {
        vector.find { point -> (point.element.dimension.name == dimensionName) }
    }

    OrderedElement valueFor(Dimension dimension) {
        valueFor(dimension.name)
    }

    String toString() {
        id ? "${id}: ${summary}" : summary
    }

}
