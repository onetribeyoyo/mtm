package com.onetribeyoyo.mtm.services

import com.onetribeyoyo.mtm.domain.*

import spock.lang.Specification

class ImportServiceISpec extends Specification {

    def storyImportService
    def projectService

    def "extractData with missing header columns"() {
        when:
            Project project = Project.build()
            def lines = """id,release,feature,progress
,story1,,
,story2,,
"""
        then:
            try {
                storyImportService.extractData(project, new StringReader(lines))
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
            storyImportService.extractData(project, new StringReader(lines))

        then:
            project.stories?.size() == 2
            !project.dimensions
    }

    def "extractData with no dimensions"() {
        when:
            Project project = Project.build()

        then:
            !project.stories

        and:
            def lines = """id,summary
,story1
,story2
"""
            storyImportService.extractData(project, new StringReader(lines))

        then:
            project.stories?.size() == 2
            !project.dimensions
    }

    def "extractData with new feature and release names"() {
        when:
            Project project = Project.build()
            projectService.configureBasis(project)

        then:
            !project.stories
            project.dimensions?.size() == 2
            project.dimensionFor("release").elements.size() == 3
            project.dimensionFor("status").elements.size() == 5

        and:
            def lines = """id,summary,release,feature,status
,story1,,f1,new status
,story2,r99,,
,story3,r100,f1,
"""
            storyImportService.extractData(project, new StringReader(lines))

        then:
            project.stories?.size() == 3
            project.dimensions?.size() == 2
            project.dimensionFor("release").elements.size() == 5
            project.dimensionFor("status").elements.size() == 6
    }

    def "extractData from empty file"() {
        when:
            Project project = Project.build()
            projectService.configureBasis(project)

        then:
            try {
                storyImportService.extractData(project, new StringReader(""))
                fail()
            } catch (RuntimeException ex) {
                assert ex.message == "Empty file."
            }
    }

    def "extractData from non csv file"() {
        when:
            Project project = Project.build()
            projectService.configureBasis(project)
            def lines = """deadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeef
deadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeef
deadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeef
deadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeef
deadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeef
deadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeef
deadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeef
deadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeef
"""
        then:
            try {
                storyImportService.extractData(project, new StringReader(lines))
                fail()
            } catch (RuntimeException ex) {
                assert ex.message == "Invalid file format: File does not contain a proper header row.  It must at least contain columns for each of [summary]"
            }
    }

    def "extractData from file with bad header"() {
        when:
            Project project = Project.build()
            projectService.configureBasis(project)
            def lines = """foo,bar,baz,stuff
\"data\",\"data\",\"data\",\"data\"
\"moreData\",\"moreData\",\"moreData\",\"moreData\"
"""
        then:
            try {
                storyImportService.extractData(project, new StringReader(lines))
                fail()
            } catch (RuntimeException ex) {
                assert ex.message == "Invalid file format: File does not contain a proper header row.  It must at least contain columns for each of [summary]"
            }
    }

    def "extractData from file with only a header"() {
        when:
            Project project = Project.build()
            projectService.configureBasis(project)
            def lines = "id,summary"
        then:
            storyImportService.extractData(project, new StringReader(lines))
   }

}
