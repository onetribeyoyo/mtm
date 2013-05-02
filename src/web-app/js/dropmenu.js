$(document).ready(function() {
    $(".menu").click(function() {
        //$(this).parent().find(".submenu").toggle();

        var x = $(this).attr("id");
        if (x == 1) {
            $(this).parent().find(".submenu").hide();
            $(this).attr("id", "0");
        } else {
            // first make sure any other submenus are closed,
            $(".submenu").hide();
            $(".menu").attr("id", "");
            // then show this one...
            $(this).parent().find(".submenu").show();
            $(this).attr("id", "1");
        }
    });

    $(".submenu").mouseup(function() {
        return false
    });
    $(".menu").mouseup(function() {
        return false
    });

    $(document).mouseup(function() {
        $(".submenu").hide();
        $(".menu").attr("id", "");
    });

});
