if (typeof jQuery !== 'undefined') {
	(function($) {
		$('#spinner').ajaxStart(function() {
			$(this).fadeIn();
		}).ajaxStop(function() {
			$(this).fadeOut();
		});
	})(jQuery);
}



// TODO: declare a global that is bound to the app context (used for createing dynamic links.)



$(document).ready(function() {

    $("tr").hover(function() {
        $(this).find(".actions").show();
    }, function() {
        $(this).find(".actions").hide();
    });

});
