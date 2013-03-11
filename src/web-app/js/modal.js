/*
 * based on SimpleModal OSX Style Modal Dialog http://www.ericmmartin.com/projects/simplemodal/
 * which is licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */

function openMtmModal(dialogId) {
    $("#" + dialogId).modal({
        overlayId: 'mtm-overlay',
        containerId: 'mtm-container',
        closeHTML: null,
        minHeight: 80,
        opacity: 65,
        position: ['0',],
        overlayClose: false,
        onOpen: _openMtmModal,
        onClose: _closeMtmModal,
    });
};

function closeMtmModal(dialogId) {
    $.modal.close();
};

var modalContainer;
function _openMtmModal(d) {

    var containerSlideDuration = 100;         // how long it will take to show the container (the title bar and the dialog border)
    var delayBetweenContainerAndContent = 0;  // a pause between showing the container and the content
    var contentSlideDuration = 300;           // how long it will take to show the contents of the dialog

    modalContainer = d.container[0];

    d.overlay.fadeIn('fast', function () {
        $("#mtm-modal-content", modalContainer).show();
        var title = $("#mtm-modal-title", modalContainer);
        title.show();
        d.container.slideDown(contentSlideDuration, function () {
            setTimeout(function () {
                var h = $("#mtm-modal-data", modalContainer).height()
                    + title.height()
                    + 20; // padding
                d.container.animate(
                    {height: h},
                    contentSlideDuration,
                    function () {
                        $("div.close", modalContainer).show();
                        $("#mtm-modal-data", modalContainer).show();
                    }
                );
            }, delayBetweenContainerAndContent);
        });
    });
};

function _closeMtmModal(d) {
    //var self = this; // this = SimpleModal object
    d.container.animate(
        { top: "-" + (d.container.height() + 20) },
        500,
        function () {
            $.modal.close();
        }
    );
};
