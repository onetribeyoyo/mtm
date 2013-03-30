package com.onetribeyoyo.mtm.services

import com.onetribeyoyo.mtm.domain.*

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

        String msg = "slide(..) story:${story.id}"
        if (from != to) {
            msg << " from ${from?.dimension}.${from}"
            if (from) story.removeFromVector(from)
            if (to)   story.addToVector(to)
            log.debug "${msg} to ${story.valueFor(axis)}"
        } else {
            log.debug "${msg}: no change"
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
