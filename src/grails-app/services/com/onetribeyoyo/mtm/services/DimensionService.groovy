package com.onetribeyoyo.mtm.services

import com.onetribeyoyo.mtm.domain.*

class DimensionService {

    static transactional = true

    def deleteElement(Dimension dimension, Element element) {
        log.debug "deleteElement(${dimension}, ${element})"

        assert element.dimension == dimension

        dimension.project.stories.each { story ->
            if (element in story.vector) {
                story.removeFromVector(element)
            }
        }
        dimension.removeFromElements(element)
        element.delete()
    }

    def updateElementOrder(Dimension dimension, List<Long> sortedIds) {
        log.debug "updateSortOrder(${dimension}, ${sortedIds})"

        def sortMap = [:] // a map from ID to sort order.
        sortedIds.eachWithIndex { id, order -> sortMap[id] = order }

        dimension.elements.each { element ->
            Long newOrder = sortMap[element.id]
            log.trace "updateSortOrder(..) ${dimension}:${element} -- from ${element.order} to ${newOrder}"
            element.order = newOrder
        }
    }

}
