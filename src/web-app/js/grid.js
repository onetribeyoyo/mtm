function moveCallback(storyId, xAxisId, xId, yAxisId, yId, sortOrder) {

    //var callbackParams
    //    = "story=" + storyId
    //    + "&xAxisId=" + xAxisId + "&xId=" + xId
    //    + "&yAxisId=" + yAxisId + "&yId=" + yId
    //    + "&sortOrder=" + sortOrder;
    //$("#callbackConsole").text(callbackParams);
    //alert("callbackParams: " + callbackParams);

    jQuery.ajax({
        type:"POST",
        url: appContext + "/story/move",
        data:{
            "storyId": storyId,
            "xAxisId": xAxisId, "xId": xId,
            "yAxisId": yAxisId, "yId": yId,
            "sortOrder": sortOrder
        },
        success:function(data, textStatus){},
        error:function(XMLHttpRequest, textStatus, errorThrown){}
    });

    // hide all the card actions...
    $(".card-actions").css("visibility", "hidden");
}

$(function() {
    $(".toggle-detail")
        .each(function() {
            $(this).click(function() {
                $(this).parent().siblings(".detail").toggle();
                $(this).parent().siblings(".footer").toggle()
            });
        });

    $("#story-edit-dialog")
        .dialog({
            autoOpen: false,
            modal: true,
            resizable: false,
            position: "top",
            //height: 300,
            width: 550,
        });

    $(".toggle-row")
        .each(function() {
            $(this)
                .click(function() {
                    var rowId = $(this).attr("yId");
                    $(".row-" + rowId).toggle();
                    $(".row-" + rowId).parent().parent().find(".story, .story-abbrev").toggle();
                })
                .end()
        });

    $(".grid-cell").sortable({
        connectWith: ".grid-cell",
        //cursor: "move",
        placeholder: "grid-placeholder",
        forcePlaceholderSize: true,
        opacity: 0.4,
        stop: function(event, ui) {
            var storyId = ui.item.closest(".card").attr("id");
            var cell = ui.item.closest(".grid-cell");
            var xAxisId = cell.attr("xAxisId");
            var xId = cell.attr("xId");
            var yAxisId = cell.attr("yAxisId");
            var yId = cell.attr("yId");
            var sortOrder = cell.sortable("toArray").toString();
            moveCallback(storyId, xAxisId, xId, yAxisId, yId, sortOrder);
        }
    }).disableSelection();

});
