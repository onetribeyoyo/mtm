package com.onetribeyoyo.simplemodal.taglib

/**
 *  Modal dialogs are based on SimpleModal http://www.ericmmartin.com/projects/simplemodal/
 *  which is licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
class SimpleModalTagLib {

    static namespace = "simplemodal"

    static String DEFAULT_TITLE = "Simple Modal Dialog"

    /** Defines the divs required by dialog:link tags.  Make sure pages that use dialog:link also includes dialog:div. */
    def div = { attrs, body ->
        out << '<div id="simplemodal-dialog">\n'
        out << '  <div id="simplemodal-title"></div>\n'
        //out << '  <div class="close"><a href="#" class="simplemodal-close">x</a></div>\n'
        out << '  <div id="simplemodal-data"></div>\n'
        out << '</div>\n'
    }

    /**
     *  Expects controller, action and id attributes that are passed on to a remoteLink, the results of which
     *  are displayed in the divs defined by dialog:div.
     *
     *  Optional attributes:
     *    title - defaults to DEFAULT_TITLE
     *    width - one of "narrow"|"normal"|"wide"|###, defaults to "normal"
     */
    def link = { attrs, body ->

        def dialogContentId = attrs.dialogContentId ?: "simplemodal-dialog"
        def dialogTitleId = attrs.dialogTitleId ?: "simplemodal-title"
        def title = attrs.title.encodeAsHTML() ?: DEFAULT_TITLE

        def width = attrs.width ?: "normal"
        switch (width) {
            case "narrow": width = 470; break
            case "normal": width = 670; break
            case "wide"  : width = 970; break
        }

        def remoteLinkAttrs = [
            action: attrs.action,
            asynchronous: "false",
            before: "\$('#${dialogTitleId}').html('${title}')".encodeAsHTML(),
            after: "openSimpleModal('${dialogContentId}', '${width}')",
            update: "simplemodal-data"
        ]
        if (attrs.controller) remoteLinkAttrs.controller = attrs.controller
        if (attrs.id) remoteLinkAttrs.id = attrs.id
        if (attrs.params) remoteLinkAttrs.params = attrs.params

        out << g.remoteLink(remoteLinkAttrs, body)
    }

    /**
     *  Displays a confirm dialog, that when accepted triggers a call to controller+action+id.
     *
     *  The tag's body will be used as the dialog message.
     *
     *  Accepts the same attributes and events as g.link plus the following
     *    title (optional)         -  defaults to "Please Confirm"
     *    width (optional)         -  defaults to "narrow"
     *    message (optional)       -  defaults to "Are you sure?"
     *
     *    remote (optional)        -  when true, uses a remote link to process the confirmed action (dialog will be dismissed on success.)
     *
     *    confirmLabel (optional)  -  label for the confirm button defaults to "Confirm"
     *    confirmClass (optional)  -  used to decorate the confirm button
     *
     *    cancelLabel (optional)   -  defaults to "Cancel"
     *
     */
    def confirmLink = { attrs, body ->

        def linkAttrs = [
            controller: "simpleModal",
            action: attrs.remote ? "confirmRemote" : "confirm",
            title: attrs.title ?: "Please Confirm",
            width: attrs.width ?: "narrow",
            params: attrs.params ?: [:]
        ]
        def confirmAttrs = [
            confirm_confirmLabel: attrs.confirmLabel ?: "Confirm",
            confirm_confirmClass: attrs.confirmClass,
            confirm_cancelLabel: attrs.cancelLabel ?: "Cancel",
            confirm_message: attrs.message ?: "Are you sure?",
        ]

        if (attrs.remote) {
            // g.remoteLink atributes...
            if (attrs.action)     confirmAttrs.confirm_action     = attrs.action
            if (attrs.after)      confirmAttrs.confirm_after      = attrs.after
            if (attrs.before)     confirmAttrs.confirm_before     = attrs.before
            if (attrs.controller) confirmAttrs.confirm_controller = attrs.controller
            if (attrs.id)         confirmAttrs.confirm_id         = attrs.id

            // g.remoteLink events...
            if (attrs.on_ERROR_CODE)   confirmAttrs.confirm_on_ERROR_CODE   = attrs.on_ERROR_CODE
            if (attrs.onUninitialized) confirmAttrs.confirm_onUninitialized = attrs.onUninitialized
            if (attrs.onLoading)       confirmAttrs.confirm_onLoading       = attrs.onLoading
            if (attrs.onLoaded)        confirmAttrs.confirm_onLoaded        = attrs.onLoaded
            if (attrs.onComplete)      confirmAttrs.confirm_onComplete      = attrs.onComplete

            confirmAttrs.confirm_asynchronous = "true"
            confirmAttrs.confirm_onSuccess    = attrs.onSuccess ? "${attrs.onSuccess};closeSimpleModal()" : "closeSimpleModal()"
            //confirmAttrs.confirm_onFailure    = ""
            confirmAttrs.confirm_update       = "${attrs.update}"

        } else {
            // g.link atributes...
            if (attrs.absolute)   confirmAttrs.confirm_absolute   = attrs.absolute
            if (attrs.action)     confirmAttrs.confirm_action     = attrs.action
            if (attrs.base)       confirmAttrs.confirm_base       = attrs.base
            if (attrs.controller) confirmAttrs.confirm_controller = attrs.controller
            if (attrs.elementId)  confirmAttrs.confirm_elementId  = attrs.elementId
            if (attrs.event)      confirmAttrs.confirm_event      = attrs.event
            if (attrs.fragment)   confirmAttrs.confirm_fragment   = attrs.fragment
            if (attrs.id)         confirmAttrs.confirm_id         = attrs.id
            if (attrs.mapping)    confirmAttrs.confirm_mapping    = attrs.mapping
            if (attrs.params)     confirmAttrs.confirm_params     = attrs.params
            if (attrs.plugin)     confirmAttrs.confirm_plugin     = attrs.plugin
            if (attrs.url)        confirmAttrs.confirm_url        = attrs.url
        }
        linkAttrs.params = confirmAttrs

        out << dialog.link(linkAttrs, body)
    }

    /**
     *  Renders a button that closes the dialog
     */
    def closeButton = { attrs, body ->
        def dialogContentId = attrs.dialogContentId ?: "simplemodal-dialog"
        def onClick = "closeSimpleModal('${dialogContentId}');"
        out << "<a class=\"close-dialog button\" onClick=\"${onClick}\">"
//        out << "<button type='button'>"
        out << body()
//        out << "</button>"
        out << "</a>"
    }
    /**
     *  Renders a link that closes the dialog
     */
    def closeLink = { attrs, body ->
        def dialogContentId = attrs.dialogContentId ?: "simplemodal-dialog"
        def onClick = "closeSimpleModal('${dialogContentId}');"
        out << "<a class=\"close-dialog\" onClick=\"${onClick}\">"
        out << body()
        out << "</a>"
    }
    /**
     *  Renders a input button that closes the dialog.
     *  Use this one if you're trying to get links/buttons to render the same size as input buttons.
     */
    def closeInputButton = { attrs ->
        def label = attrs.label ?: "Close"

        def dialogContentId = attrs.dialogContentId ?: "simplemodal-dialog"
        def onClick = "closeSimpleModal('${dialogContentId}');"
        out << "<a class=\"close-dialog\" onClick=\"${onClick}\">"
        out << "<input type='button' class='button' value='${label}' />"
        out << "</a>"
    }

}
