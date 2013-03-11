package com.onetribeyoyo.mtm.domain

import grails.buildtestdata.mixin.Build

import grails.test.mixin.TestFor

import spock.lang.Specification
import spock.lang.Unroll

@TestFor(Project)
@Build(Project)
class ProjectSpec extends Specification {

    static DomainTestUtil testUtil = new DomainTestUtil()

    def setup() {
        // mock a form with some proprties set for checking unique constraints
        mockForConstraintsTests(Project, [new Project(name: "project-1")])
    }

    @Unroll("project constraints: #field is #error.")
    def "project constraints"() {
        when:
           def obj = new Project("$field": value)

        then:
            testUtil.validateConstraints(obj, field, error)

        where:
            field  | error      | value

            "name" | "nullable" | null
            "name" | "blank"    | ""
            "name" | "unique"   | "project-1"
            "name" | "valid"    | "project-2"
            "name" | "valid"    | testUtil.stringWithLength(255)
            "name" | "maxSize"  | testUtil.stringWithLength(256)
    }

}
