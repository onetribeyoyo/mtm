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

        OrderedElement from = story.valueFor(axis)

        String msg = "slide(..) story:${story.id}"
        if (from?.element != to) {
            msg << " from ${from?.element?.dimension}.${from}"
            if (from) {
                story.removeFromVector(from)
                from.delete()
            }
            if (to) {
                def oe = new OrderedElement(story: story, element: to).save()
                if (from) {
                    oe.order = from.order
                }
                story.addToVector(oe)
            }
            log.debug "${msg} to ${story.valueFor(axis)}"
        } else {
            log.debug "${msg}: no change"
        }
    }

    void setVector(Story story, params) {
        story.project.dimensions.each { dimension ->
            // TODO: is there an easier way to get a long (or null) from the params map?
            String elementParam = params["dimension-${dimension.id}"]
            Long elementId = elementParam ? elementParam.toLong() : null
            Element element = elementId ? Element.get(elementId) : null
            //println "value for ${dimension} is ${element} with id of ${elementId}"
            slide(story, dimension, element)
        }
    }

}
