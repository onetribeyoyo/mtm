package com.onetribeyoyo.mtm.services

import com.onetribeyoyo.mtm.domain.*

class StorymapService {

    static transactional = true

    /**
     *  Returns a map of maps of lists, one element for each row in the storymap.
     *  For example: the storymap for xAxis=[1,2,3] and yAxis={a,b,c] looks like
     *    [
     *        1: [columns: [a: [], b: [], c: [], null: []],
     *            estimate: 0,
     *            complete: false],
     *        2: [columns: [a: [], b: [], c: [], null: []],
     *            estimate: 0,
     *            complete: false],
     *        3: [columns: [a: [], b: [], c: [], null: []],
     *            estimate: 0,
     *            complete: false]
     *    ]
     *
     *  where
     *    columns is a map of story lists at the intersection of x and y,
     *    estimate is the total estimate of all the stories in the column lists,
     *    and complete is true only if all the stories are "done".
     */
    def storymapFor(Project project, Dimension xAxis, Dimension yAxis) {
        if (!xAxis && !yAxis) {
            return [:]
        }
        // first initialize the story map structure...
        def storymap = [:]
        yAxis?.elements?.each { row ->
            storymap[row] = rowMapFor(project, row, xAxis)
        }
        storymap[null] = rowMapFor(project, null, xAxis)

        // then walk through all the stories and place them in the approriate list...
        project.stories.each { story ->
            def x = story.valueFor(xAxis)?.element
            def y = story.valueFor(yAxis)?.element
            storymap[y].columns[x] << story
            if (story.estimate) {
                storymap[y].estimate += story.estimate
            }
        }
        return storymap
    }

    private def rowMapFor(Project project, Element row, Dimension columns) {
        def map = [:]
        def estimate = 0
        def complete = true
        map.columns = [:]
        columns.elements.each { x ->
            map.columns[x] = []
        }
        map.columns[null] = []

        map.estimate = estimate
        map.complete = (row?.complete() ? true : false)

        return map
    }

}
