package com.onetribeyoyo.mtm.project

class DomainTestUtil {

    void validateConstraints(obj, field, error) {
        def validated = obj.validate()
        if (error && error != 'valid') {
            assert !validated
            assert obj.errors[field]
            assert error == obj.errors[field]
        } else {
            assert !obj.errors[field]
        }
    }

    String stringWithLength(int length) {
        String str = ""
        length.times {
            str += "a"
        }
        return str
    }

}
