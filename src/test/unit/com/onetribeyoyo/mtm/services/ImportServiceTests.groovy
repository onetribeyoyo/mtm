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
        ImportService service = new ImportService()
        assert !service.extractField( [], 1 )
        assert "a" == service.extractField( ["a", "b", "c"], 0 )
        assert "b" == service.extractField( ["a", "b", "c"], 1 )
        assert !service.extractField( ["a", "b", "c"], 4 )
    }

}
