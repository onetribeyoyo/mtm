package com.onetribeyoyo.simplemodal

class SimpleModalController {

    def confirm = {
        render template: "confirm", model: [ : ]
    }

    def confirmRemote = {
        render template: "confirmRemote", model: [ : ]
    }

}
