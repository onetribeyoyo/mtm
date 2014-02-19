package com.onetribeyoyo.mtm.project

import spock.lang.Specification

class StoryImportServiceSpec extends Specification {

    def "test extractField"() {
        when:
            def storyImportService = new StoryImportService()

        then:
            !storyImportService.extractString( [], 1 )
            "a" == storyImportService.extractString( ["a", "b", "c"], 0 )
            "b" == storyImportService.extractString( ["a", "b", "c"], 1 )
            !storyImportService.extractString( ["a", "b", "c"], 4 )
        and:
            !storyImportService.extractBoolean( [], 0 )
            !storyImportService.extractBoolean( [""], 0 )
            !storyImportService.extractBoolean( ["    "], 0 )
            !storyImportService.extractBoolean( ["false"], 0 )
            !storyImportService.extractBoolean( ["False"], 0 )
            !storyImportService.extractBoolean( ["FaLse"], 0 )
            !storyImportService.extractBoolean( ["FALSE"], 0 )
            storyImportService.extractBoolean( ["True"], 0 )
            storyImportService.extractBoolean( ["non-false is true!"], 0 )
        and:
            storyImportService.extractLong( [], 0 ) == null
            storyImportService.extractLong( [""], 0 ) == null
            storyImportService.extractLong( [" "], 0 ) == null
            storyImportService.extractLong( ["-1"], 0 ) == -1
            storyImportService.extractLong( ["0"], 0 ) == 0
            storyImportService.extractLong( ["42"], 0 ) == 42
    }

}
