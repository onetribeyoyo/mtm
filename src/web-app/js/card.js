$(function() {
    $(".card").hover(
        function () {
            //$("#callbackConsole").text("in");
            $(this).addClass("selected-card");
            $(this).find(".card-actions").css("visibility", "visible");
        },
        function () {
            //$("#callbackConsole").text("out");
            $(this).removeClass("selected-card");
            $(this).find(".card-actions").css("visibility", "hidden");
        }
    );
    $(".card").find(".card-actions").css("visibility", "hidden");

});
