package com.onetribeyoyo.mtm.domain

class Project {

    String name
    String estimateUnits = ""

    SortedSet dimensions
    Dimension primary // used to configure default storymaps.

    static hasMany = [
        stories : Story,
        dimensions: Dimension,
        permanentDimensions: Dimension
    ]

    static constraints = {
        name unique: true, nullable:false, blank:false, maxSize:255
        estimateUnits nullable: true, blank: true, maxSize:15
        primary nullable: true // but only to make it easier to construct projects!
    }

    Dimension dimensionFor(String name) {
        dimensions.find { d -> (d.name?.toLowerCase() == name?.toLowerCase()) }
    }

    Element lastStatus() {
        def statusElements = dimensionFor("status")?.elements as List
        def lastStatus = statusElements ? statusElements[-1] : null
        return lastStatus
    }

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
