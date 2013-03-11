package com.onetribeyoyo.mtm.services

import com.onetribeyoyo.mtm.domain.*

import grails.plugin.spock.IntegrationSpec

class ImportServiceISpec extends IntegrationSpec {

    def importService

    def "extractData with missing header columns"() {
        when:
            Project project = Project.build()
            def lines = """id,release,feature,progress
,story1,,
,story2,,
"""
        then:
            try {
                importService.extractData(project, new StringReader(lines))
                fail()
            } catch (Exception ex) {
                assert ex.message == "Invalid file format: File does not contain a proper header row.  It must at least contain columns for each of [summary]"
            }
    }

    def "extractData with extra header columns"() {
        when:
            Project project = Project.build()
            def lines = """id,summary,release,feature,progress,extraData
,story1,,
,story2,,
"""
            importService.extractData(project, new StringReader(lines))

        then:
            project.stories?.size() == 2
            !project.dimensions
    }

    def "extractData with no dimensions"() {
        when:
            Project project = Project.build()
            def lines = """id,summary
,story1
,story2
"""
            importService.extractData(project, new StringReader(lines))

        then:
            project.stories?.size() == 2
            !project.dimensions
    }

    def "extractData with new feature and release names"() {
        when:
            Project project = Project.build()
            def lines = """id,summary,release,feature,progress
,story1,,f1
,story2,r1,
,story3,r1,f1
"""
            importService.extractData(project, new StringReader(lines))

        then:
            project.stories?.size() == 3
            project.dimensions?.size() == 2
    }

    /*
    def "extractData with existing feature and release names"() {
        //project.addToFeatures(new Feature(name: "f1"))
        //project.addToReleases(new Release(name: "r0"))
        project.save(flush:true)

        def lines = """id,summary,release,feature,progress
,story1,,
,story2,,
,story3,r1,
,story4,r1,f1
"""
        importService.extractData(project, new StringReader(lines))

        assert project.stories?.size() == 4
        assert project.features?.size() == 2
        assert project.releases?.size() == 2
    }

    def "extractData update existing stories"() {
        //project.addToFeatures(new Feature(name: "f1"))
        //project.addToReleases(new Release(name: "r0"))

        def lines = """id,summary,release,feature,progress
,story1,,
,story2,,
,story3,r1,
,story4,r1,f1
"""
        importService.extractData(project, new StringReader(lines))
    }

    def "extractData from empty file"() {
        assert "Empty file." == shouldFail {
            importService.extractData(project, new StringReader(""))
        }
    }

    def "extractData from non csv file"() {
        def lines = """deadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeef
deadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeef
deadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeef
deadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeef
deadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeef
deadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeef
deadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeef
deadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeef
"""
        assert "Invalid file format: File does not contain a proper header row.  It must at least contain columns for each of [summary]" == shouldFail {
            importService.extractData(project, new StringReader(lines))
        }
    }

    def "extractData from file with bad header"() {
        def lines = """foo,bar,baz,stuff
\"data\",\"data\",\"data\",\"data\"
\"moreData\",\"moreData\",\"moreData\",\"moreData\"
"""
        assert "Invalid file format: File does not contain a proper header row.  It must at least contain columns for each of [summary]" == shouldFail {
            importService.extractData(project, new StringReader(lines))
        }
    }

    def "extractData from file with only a header"() {
        def lines = "id,summary"
        importService.extractData(project, new StringReader(lines))
   }
    */
}
