package com.onetribeyoyo.mtm.services

import com.onetribeyoyo.mtm.domain.*

class StorymapService {

    static transactional = true

    /**
     *  Returns a map of maps of lists.  Example: the storymap xAxis=[1,2,3] and yAxis={a,b,c] looks like
     *    [
     *        1: [a: [], b: [], c: [], null: []],
     *        2: [a: [], b: [], c: [], null: []],
     *        3: [a: [], b: [], c: [], null: []],
     *        null: [a: [], b: [], c: [], null: []]
     *    ]
     *
     *  where each [] list contains the projects stories at the intersection of x and y.
     */
    def storymapFor(Project project, Dimension xAxis, Dimension yAxis) {
        if (!xAxis && !yAxis) {
            return [:]
        }
        // first create the map of map of lists...
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
        // then walk through all the stories and place them in the approriate list...
        project.stories.each { story ->
            def x = story.valueFor(xAxis)?.element
            def y = story.valueFor(yAxis)?.element
            storymap[x][y] << story
        }
        return storymap
    }

}
