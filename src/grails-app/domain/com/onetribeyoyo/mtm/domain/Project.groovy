package com.onetribeyoyo.mtm.domain

class Project {

    String name
    String estimateUnits = ""

    SortedSet dimensions
    Dimension primaryAxis        // used to configure default storymaps.
    Dimension colourDimension    // used to determine card display colour
    Dimension highlightDimension // used to determine card highlight content

    static hasMany = [
        stories : Story,
        dimensions: Dimension
    ]

    static constraints = {
        name unique: true, nullable:false, blank:false, maxSize:255
        estimateUnits nullable: true, blank: true, maxSize:15
        primaryAxis nullable: true // but only to make it easier to construct projects!
        colourDimension nullable: true
        highlightDimension nullable: true
    }

    Dimension dimensionFor(String name) {
        dimensions.find { d -> (d.name?.toLowerCase() == name?.toLowerCase()) }
    }

    Element lastStatus() {
        def statusElements = dimensionFor("status")?.elements as List
        def lastStatus = statusElements ? statusElements[-1] : null
        return lastStatus
    }

    // TODO: is this dead code?  If not, move it to projectService.
    List<OrderedElement> orderedStoriesFor(Dimension dimension) {
        if (!dimension) {
            return []
        } else {
            List<OrderedElement> list = OrderedElement.withCriteria {
                story {
                    eq("project", this)
                }
                element {
                    eq("dimension", dimension)
                }
            }
            return list
        }
    }

    // TODO: is this dead code?  If not, move it to projectService.
    List<OrderedElement> orderedStoriesFor(Element element) {
        if (!element) {
            return []
        } else {
            List<OrderedElement> list = OrderedElement.withCriteria {
                story {
                    eq("project", this)
                }
                eq("element", element)
            }
            return list
        }
    }

    String toString() {
        name ? name : "project[id:${id}]"
    }

}
