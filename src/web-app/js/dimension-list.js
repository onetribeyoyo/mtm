function moveCallback(dimensionId, sortOrder) {
    var callbackParams = dimensionId + "&sortOrder=" + sortOrder;
    //$("#callbackConsole").text(callbackParams);
    //alert("callbackParams: " + callbackParams);

    jQuery.ajax({
        type: "POST",
        url: "/mtm/dimension/updateElementOrder/", // TODO: don't hardcode the url!
        data: { "id" : dimensionId, "sortOrder": sortOrder },
        success: function(data, textStatus) {},
        error:   function(XMLHttpRequest, textStatus, errorThrown) {}
    });
}

$(function() {
    $(".dimension-list")
        .sortable({
            cursor: "move",
            placeholder: "placeholder",
            forcePlaceholderSize: true,
            opacity: 0.4,
            stop: function(event, ui) {
                var list = ui.item.closest(".dimension-list");
                var dimensionId = list.attr("dimensionId");
                var sortOrder = list.sortable("toArray").toString();
                moveCallback(dimensionId, sortOrder);
            }
        }).disableSelection();

});
