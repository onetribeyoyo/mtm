package com.onetribeyoyo.mtm.services

import com.onetribeyoyo.mtm.domain.*

//import grails.buildtestdata.mixin.Build
import grails.plugin.spock.*

import spock.lang.*
import spock.lang.Specification
import spock.lang.Unroll

@TestFor(StoryService)
//@Mock([Dimension, Element, OrderedElement, Story])
class StoryServiceSpec extends Specification {

    @Unroll("slide from #from tp #from")
    def "slide along a dimension"() {
    when:

    then:

    where:
        from | to
        "a"  | "b"
        "a"  | "a"
        "a"  | null
        null | null
        null | "a"
    }

    def "slide must be along a single axis"() {
        //when:

        //then:

    }

}
