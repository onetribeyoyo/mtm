package com.onetribeyoyo.mtm.project

class DimensionService {

    static transactional = true

    def updateElementOrder(Dimension dimension, List<String> sortedIds) {
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
