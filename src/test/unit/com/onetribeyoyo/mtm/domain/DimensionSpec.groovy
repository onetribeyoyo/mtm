package com.onetribeyoyo.mtm.domain

import grails.test.mixin.TestFor
import spock.lang.Ignore
import spock.lang.Unroll
import spock.lang.Specification

@TestFor(Dimension)
@Mock([Dimension, Project])
class DimensionSpec extends Specification {

    static DomainTestUtil testUtil = new DomainTestUtil()

    def setup() {
        // mock a form with some proprties set for checking unique constraints
        mockForConstraintsTests(Dimension, [new Dimension()])
    }

    @Unroll("dimension constraints: #field is #error.")
    def "dimension constraints"() {
        when:
            def project = new Project()
            def obj = new Dimension("$field": value)

        then:
            testUtil.validateConstraints(obj, field, error)

        where:
            field  | error      | value

            "name" | "nullable" | null
            "name" | "blank"    | ""

            // TODO: name constraints will fail in unit tests, 'cause they call withNewSession!
            //"name" | "valid"    | "a name"
            //"name" | "maxSize"  | testUtil.stringWithLength(256)
            //"name" | "valid"    | testUtil.stringWithLength(255)

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

    @Ignore
    def "inProgress"() {
        when:
            def dimension = new Dimension(name: "foo")
        then:
            !dimension
            // TODO: implement this test!
    }

    @Ignore
    def "complete"() {
        when:
            def dimension = new Dimension(name: "foo")
        then:
            !dimension
            // TODO: implement this test!
    }

}
