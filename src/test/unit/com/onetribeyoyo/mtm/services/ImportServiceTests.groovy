package com.onetribeyoyo.mtm.services

import grails.test.GrailsUnitTestCase


class ImportServiceTests extends GrailsUnitTestCase {

    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    public void testExtractField() {
        ImportService service = new StoryImportService()

        assert !service.extractString( [], 1 )
        assert "a" == service.extractString( ["a", "b", "c"], 0 )
        assert "b" == service.extractString( ["a", "b", "c"], 1 )
        assert !service.extractString( ["a", "b", "c"], 4 )

        assert !service.extractBoolean( [], 0 )
        assert !service.extractBoolean( [""], 0 )
        assert !service.extractBoolean( ["    "], 0 )
        assert !service.extractBoolean( ["false"], 0 )
        assert !service.extractBoolean( ["False"], 0 )
        assert !service.extractBoolean( ["FaLse"], 0 )
        assert !service.extractBoolean( ["FALSE"], 0 )
        assert service.extractBoolean( ["True"], 0 )
        assert service.extractBoolean( ["non-false is true!"], 0 )

        assert service.extractLong( [], 0 ) == null
        assert service.extractLong( [""], 0 ) == null
        assert service.extractLong( [" "], 0 ) == null
        assert service.extractLong( ["-1"], 0 ) == -1
        assert service.extractLong( ["0"], 0 ) == 0
        assert service.extractLong( ["42"], 0 ) == 42
    }

}
