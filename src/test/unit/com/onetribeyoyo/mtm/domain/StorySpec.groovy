package com.onetribeyoyo.mtm.domain

import grails.test.mixin.TestFor
import spock.lang.Unroll
import spock.lang.Specification

@TestFor(Story)
class StorySpec extends Specification {

    static DomainTestUtil testUtil = new DomainTestUtil()

    def setup() {
        // mock a form with some proprties set for checking unique constraints
        mockForConstraintsTests(Story, [new Story()])
    }

    @Unroll("story constraints: #field is #error.")
    def "story constraints"() {
        when:
           def obj = new Story("$field": value)

        then:
            testUtil.validateConstraints(obj, field, error)

        where:
            field     | error      | value

            "summary" | "nullable" | null
            "summary" | "blank"    | ""
            "summary" | "valid"    | "a summary"
            "summary" | "valid"    | testUtil.stringWithLength(255)
            "summary" | "maxSize"  | testUtil.stringWithLength(256)

            "detail" | "valid"   | null
            "detail" | "valid"   | ""
            "detail" | "valid"   | "some details"
            "detail" | "valid"   | testUtil.stringWithLength(4095)
            "detail" | "maxSize" | testUtil.stringWithLength(4096)
    }

}
