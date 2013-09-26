package com.onetribeyoyo.mtm.domain

import spock.lang.Specification

class DomainTestUtilSpec extends Specification {

    def "stringWithLength"() {
        when:
            def testUtil = new DomainTestUtil()

        then:
            testUtil.stringWithLength(0).size() == 0
            testUtil.stringWithLength(1).size() == 1
            testUtil.stringWithLength(2).size() == 2
            testUtil.stringWithLength(10).size() == 10
            testUtil.stringWithLength(100).size() == 100
    }

}
