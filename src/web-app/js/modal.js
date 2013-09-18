/**
 *  Modal dialogs are based on SimpleModal http://www.ericmmartin.com/projects/simplemodal/
 *  which is licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */

function openSimpleModal(dialogId, minWidth) {
    $("#" + dialogId).modal({
        overlayId: "simplemodal-overlay",
        containerId: "simplemodal-container",
        closeHTML: null,
        escClose: true,
        minWidth: minWidth,
        minHeight: 80,
        maxHeight: $(window).height(),
        opacity: 65,
        position: ["0",],
        overlayClose: false,
        onOpen: _openSimpleModal,
        onClose: _closeSimpleModal,
    });
};

function closeSimpleModal() {
    $.modal.close();
};

function refreshSimpleModal(dialogId) {
    _resizeSimpleModalContent();
};

var modalContainer;
function _openSimpleModal(dlg) {

    var containerSlideDuration = 100;         // how long it will take to show the container (the title bar and the dialog border)
    var delayBetweenContainerAndContent = 0;  // a pause between showing the container and the content
    var contentSlideDuration = 300;           // how long it will take to show the contents of the dialog

    // make sure any drop menus get closed...
    $(".submenu").hide();

    modalContainer = dlg.container[0];

    dlg.overlay.fadeIn("fast", function () {
        $("#simplemodal-dialog", modalContainer).show();
        var title = $("#simplemodal-title", modalContainer);
        title.show();

        _resizeSimpleModalContent();

        dlg.container.slideDown(contentSlideDuration, function () {
            setTimeout(function () {
                var h = $("#simplemodal-data", modalContainer).height()
                    + title.height()
                    + 20; // padding
                dlg.container.animate(
                    {height: h},
                    contentSlideDuration,
                    function () {
                        $("div.close", modalContainer).show();
                        $("#simplemodal-data", modalContainer).show();
                    }
                );
            }, delayBetweenContainerAndContent);
        });
    });
};

function _closeSimpleModal(dlg) {
    //var self = this; // this = SimpleModal object
    dlg.container.animate(
        { top: "-" + (dlg.container.height() + 20) },
        500,
        function () {
            $.modal.close();
        }
    );
};

/**
 *  Resizes the .simplemodal-content in an attempt to keep the entire dialog visible.
 */
function _resizeSimpleModalContent() {

    // assuming we've got a buttonset on the bottom and a title bar on
    // the top, set the max-height of the contentDiv to show the entire
    // dialog while still leaving a bit of blank space underneath (for
    // visual appeal.)
    var maxHeight = $(window).height() - 150;
    $(".simplemodal-content").css("max-height", maxHeight);

    // check the min-height.  The default value is pleasing to the eye,
    // but it may be bigger than the max we just set!
    if (parseInt($(".simplemodal-content").css('min-height'), 10) > maxHeight) {
        $(".simplemodal-content").css("min-height", maxHeight);
    }
};
