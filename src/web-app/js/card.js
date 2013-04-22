$(function() {
    $(".card").hover(
        function () {
            //$("#callbackConsole").text("in");
            $(this).addClass("selected-card");
            $(this).find(".card-actions").css("visibility", "visible");

            // TODO: $(this).find(".card-actions").delay(5000).fadeOut("slow");
        },
        function () {
            //$("#callbackConsole").text("out");
            $(this).removeClass("selected-card");
            $(this).find(".card-actions").css("visibility", "hidden");
        }
    );
    $(".card").find(".card-actions").css("visibility", "hidden");

});
