package com.onetribeyoyo.mtm.domain

class Project {

    String name

    SortedSet dimensions

    static hasMany = [
        stories : Story,
        dimensions: Dimension,
        permanentDimensions: Dimension
    ]

    static constraints = {
        name unique: true, nullable:false, blank:false, maxSize:255
    }

    Dimension dimensionFor(String name) {
        dimensions.find { d -> (d.name?.toLowerCase() == name?.toLowerCase()) }
    }

    Element lastStatus() {
        def statusElements = dimensionFor("status")?.elements as List
        def lastStatus = statusElements ? statusElements[-1] : null
        return lastStatus
    }

    def storymapFor(Dimension xAxis, Dimension yAxis) {
        if (!xAxis && !yAxis) {
            return [:]
        }
        // first initialize a map that can be used to look up lists of stories by x and y points...
        def storymap = [:]
        storymap[null] = [:]
        storymap[null][null] = []
        xAxis?.elements?.each { x ->
            storymap[x] = [:]
            storymap[x][null] = []
            yAxis?.elements?.each { y ->
                storymap[x][y] = []
                storymap[null][y] = []
            }
        }
        // then walk through all the stories and place them in the approriate bucket...
        stories.each { story ->
            def x = story.valueFor(xAxis)?.element
            def y = story.valueFor(yAxis)?.element
            storymap[x][y] << story
        }
        return storymap
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
