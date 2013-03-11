package com.onetribeyoyo.mtm.controllers

class InfoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect action: "storymap"
    }

    def estimation = { }

    def faq = { }

    def storymap = { }

}
