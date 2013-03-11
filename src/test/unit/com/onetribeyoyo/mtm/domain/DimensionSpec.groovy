package com.onetribeyoyo.mtm.domain

import grails.test.mixin.TestFor
import spock.lang.Unroll
import spock.lang.Specification

@TestFor(Dimension)
class DimensionSpec extends Specification {

    static DomainTestUtil testUtil = new DomainTestUtil()

    def setup() {
        // mock a form with some proprties set for checking unique constraints
        mockForConstraintsTests(Dimension, [new Dimension()])
    }

    @Unroll("dimension constraints: #field is #error.")
    def "dimension constraints"() {
        when:
           def obj = new Dimension("$field": value)

        then:
            testUtil.validateConstraints(obj, field, error)

        where:
            field  | error      | value

            "name" | "nullable" | null
            "name" | "blank"    | ""
            "name" | "valid"    | "a name"
            "name" | "valid"    | testUtil.stringWithLength(255)
            "name" | "maxSize"  | testUtil.stringWithLength(256)

            "description" | "valid"   | null
            "description" | "valid"   | ""
            "description" | "valid"   | "some description"
            "description" | "valid"   | testUtil.stringWithLength(4095)
            "description" | "maxSize" | testUtil.stringWithLength(4096)

            "colour" | "valid"   | null
            "colour" | "valid"   | ""
            "colour" | "valid"   | "colour-style"
            "colour" | "valid"   | testUtil.stringWithLength(63)
            "colour" | "maxSize" | testUtil.stringWithLength(64)

            "basis" | "valid" | null
            "basis" | "valid" | true
            "basis" | "valid" | false

            // "layoutStyle"
    }

    def "inProgress"() {
        when:
            def dimension = new Dimension(name: "foo")
        then:
            !dimension
    }

    def "complete"() {
        when:
            def dimension = new Dimension(name: "foo")
        then:
            !dimension
    }

}
