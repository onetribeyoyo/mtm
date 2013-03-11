package com.onetribeyoyo.mtm.services

import com.onetribeyoyo.mtm.domain.*

import grails.buildtestdata.mixin.Build
import grails.plugin.spock.*

import spock.lang.*

@TestFor(DimensionService)
//@Mock([Status])
//@Build([Status])
class DimensionServiceSpec extends Specification {

    def "test updateSortOrder"() {
        given: "a few stories with unspecified sort order"
            //Status s0 = Status.build(sortOrder: 0)
            //Status s1 = Status.build(sortOrder: 1)
            //Status s2 = Status.build(sortOrder: 2)
            //s0.sortOrder == 0
            //s1.sortOrder == 1
            //s2.sortOrder == 2

        when: "I update the sort order"
            //List<Long> sortedIds = [ s1.id, s2.id, s0.id ]
            //service.updateSortOrder(Status.class, sortedIds)

        then: "the sortOrder of the objects is now specified"
            //s0.sortOrder == 2
            //s1.sortOrder == 0
            //s2.sortOrder == 1
            assert false
    }

}
