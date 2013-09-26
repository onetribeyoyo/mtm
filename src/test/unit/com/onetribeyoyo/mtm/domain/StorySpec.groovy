package com.onetribeyoyo.mtm.domain

import grails.test.mixin.TestFor
import spock.lang.Unroll
import spock.lang.Specification

@TestFor(Story)
class StorySpec extends Specification {

    static DomainTestUtil testUtil = new DomainTestUtil()

    def setup() {
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
            "summary" | "nullable" | "" // grails-2.3 binds empty strings to null.
            "summary" | "valid"    | "a summary"
            "summary" | "valid"    | testUtil.stringWithLength(255)
            "summary" | "maxSize"  | testUtil.stringWithLength(256)

            "detail" | "valid"   | null
            "detail" | "valid"   | ""
            "detail" | "valid"   | "some details"
            "detail" | "valid"   | testUtil.stringWithLength(4095)
            "detail" | "maxSize" | testUtil.stringWithLength(4096)

            "estimate" | "valid" | null
            "estimate" | "valid" | 0
            "estimate" | "valid" | 1
            "estimate" | "min"   | -1
    }

}
