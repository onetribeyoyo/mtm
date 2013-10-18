package com.onetribeyoyo.mtm.taglib

class MtmTagLib {

    static namespace = "mtm"

    def format = { attrs ->
        def value = attrs.value
        if (value) {
            out << value
        } else {
            out << "<span class=\"hint\">not specified</span>"
        }
    }

}
