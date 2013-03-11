package com.onetribeyoyo.mtm.taglib

class MtmTagLib {

    static namespace = "mtm"

    /** Defines the divs required by mtm:dialogLink.  Make sure pages that use mtm:dialogLink also include mtm:dialogDiv. */
    def dialogDiv = { attrs, body ->
        out << '<div id="mtm-modal-content">\n'
        out << '  <div id="mtm-modal-title">Edit Story</div>\n'
        out << '  <div class="close"><a href="#" class="simplemodal-close">x</a></div>\n'
        out << '  <div id="mtm-modal-data">???</div>\n'
        out << '</div>\n'
    }

    /**
     *  Expects controller, action and id attributes that are passed on to a remoteLink, the results of which
     *  are displayed in the divs defined by mtm:dialogDiv.
     *
     *  A title can be passed in as well (defaults to "MTM Dialog").
     */
    def dialogLink = { attrs, body ->

        def dialogContentId = attrs.dialogContentId ?: "mtm-modal-content"
        def dialogTitleId = attrs.dialogTitleId ?: "mtm-modal-title"
        def title = attrs.title ?: "MTM Dialog"

        def remoteLinkAttrs = [
            controller: attrs.controller ?: null,
            action: attrs.action,
            id: attrs.id ?: null,
            asynchronous: "false",
            before: "\$('#${dialogTitleId}').html('${title}')",
            after: "openMtmModal('${dialogContentId}')",
            update: "mtm-modal-data"
        ]
        out << g.remoteLink(remoteLinkAttrs, body)
    }

    /**
     *  Renders a link that closes the dialog
     */
    def closeDialogLink = { attrs, body ->
        def dialogContentId = attrs.dialogContentId ?: "mtm-modal-content"
        def onClick = "closeMtmModal('${dialogContentId}');"
        out << "<a class=\"close-dialog\" onClick=\"${onClick}\">"
        out << body()
        out << "</a>"
    }

}
