package com.onetribeyoyo.mtm.domain

import grails.test.mixin.TestFor
import spock.lang.Unroll
import spock.lang.Specification

@TestFor(Dimension)
@Mock([Dimension, Project])
class DimensionSpec extends Specification {

    static DomainTestUtil testUtil = new DomainTestUtil()

    def setup() {
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
            "name" | "nullable" | "" // grails-2.3 binds empty strings to null.

            "name" | "valid"    | "a name"
            "name" | "maxSize"  | testUtil.stringWithLength(256)
            "name" | "valid"    | testUtil.stringWithLength(255)

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

    def "dimension name is unique within project"() {
        when: "a project has a dimension"
            def project1 = new Project(name: "xyzzy")
            project1.save(failOnError: true, flush: true)
            def dimension1 = new Dimension(project: project1, name: "1")
            dimension1.save(failOnError: true, flush: true)
        then: "the project can't have another dimension with the same name"
            def dimension2 = new Dimension(project: project1, name: "1")
            dimension1.name == dimension2.name
            !dimension2.validate()
            dimension2.errors["name"]
            "unique" == dimension2.errors["name"]

        when: "the two dimensions have different names"
            dimension2.name = "2"
        then:
            dimension1.name != dimension2.name
            dimension2.validate()
            !dimension2.errors["name"]

        when:
            def project2 = new Project(name: "xyzzy2")
            project2.save(failOnError: true, flush: true)
            def dimension3 = new Dimension(project: project2, name: "1")
        then: "a second project can have dimensions that have the same names as those in other projects"
            dimension3.validate()
    }

}
