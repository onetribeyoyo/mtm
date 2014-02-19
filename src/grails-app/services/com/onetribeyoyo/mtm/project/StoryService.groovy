package com.onetribeyoyo.mtm.project

class StoryService {

    static transactional = true

    void move(Story story, Dimension xAxis, Element x, Dimension yAxis, Element y) {
        assert xAxis
        assert yAxis
        slide(story, xAxis, x)
        slide(story, yAxis, y)
        story.save()
    }

    void slide(Story story, Dimension axis, Element to) {
        assert axis
        assert !to || (to.dimension == axis)

        Element from = story.valueFor(axis)
        if (from != to) {
            if (from) story.removeFromVector(from)
            if (to)   story.addToVector(to)
            log.debug "slide(..) story:${story.id} from ${from?.dimension}.${from} to ${story.valueFor(axis)}"
        } else {
            log.debug "slide(..) story:${story.id} - no change"
        }
    }

    void setVector(Story story, params) {
        story.project.dimensions.each { dimension ->
            String elementName = params[dimension.name]
            Element element = dimension.elementFor(elementName)
            slide(story, dimension, element)
        }
    }

}
