/*
 * based on SimpleModal OSX Style Modal Dialog http://www.ericmmartin.com/projects/simplemodal/
 * which is licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */

var containerSlideDuration = 100;         // how long it will take to show the container (the title bar and the dialog border)
var delayBetweenContainerAndContent = 0;  // a pause between showing the container and the content
var contentSlideDuration = 200;           // how long it will take to show the contents of the dialog
var container;

function openDialog(d) {
    container = d.container[0];
    d.overlay.fadeIn('fast', function () {
	$("#mtm-modal-content", self.container).show();
	var title = $("#mtm-modal-title", self.container);
	title.show();
	d.container.slideDown(contentSlideDuration, function () {
	    setTimeout(function () {
		var h = $("#mtm-modal-data", self.container).height()
		    + title.height()
		    + 20; // padding
		d.container.animate(
		    {height: h},
		    contentSlideDuration,
		    function () {
			$("div.close", self.container).show();
			$("#mtm-modal-data", self.container).show();
		    }
		);
	    }, delayBetweenContainerAndContent);
	});
    })
};

function closDialog(d) {
    var self = this; // this = SimpleModal object
    d.container.animate(
	{top:"-" + (d.container.height() + 20)},
	500,
	function () {
	    self.close(); // or $.modal.close();
	}
    );
};
