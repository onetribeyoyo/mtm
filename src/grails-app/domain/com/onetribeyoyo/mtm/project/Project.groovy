package com.onetribeyoyo.mtm.project

class Project {

    String name

    SortedSet dimensions
    static hasMany = [
        stories : Story,
        dimensions: Dimension
    ]

    String estimateUnits = ""
    Boolean showEstimates = true

    Dimension primaryXAxis       // used to configure default storymaps
    Dimension primaryYAxis       // used to configure default storymaps
    Dimension colourDimension    // used to determine card display colour
    Dimension highlightDimension // used to determine card highlight content

    static constraints = {
        name unique: true, nullable:false, blank:false, maxSize:255
        estimateUnits nullable: true, blank: true, maxSize:15
        primaryXAxis nullable: true // but only to make it easier to construct projects!
        primaryYAxis nullable: true // but only to make it easier to construct projects!
        colourDimension nullable: true
        highlightDimension nullable: true
        showEstimates nullable: true
    }

    Dimension dimensionFor(String name) {
        dimensions.find { d -> (d.name?.toLowerCase() == name?.toLowerCase()) }
    }

    Element lastStatus() {
        def statusElements = dimensionFor("status")?.elements as List
        def lastStatus = statusElements ? statusElements[-1] : null
        return lastStatus
    }

    String toString() {
        name ? name : "project[id:${id}]"
    }

}
