function moveCallback(projectId, dimensionName, sortOrder) {
    jQuery.ajax({
        type: "POST",
        url: "/mtm/dimension/updateElementOrder/", // TODO: don't hardcode the url!
        data: { "projectId" : projectId, "dimensionName": dimensionName, "sortOrder": sortOrder },
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
                var projectId = list.attr("projectId");
                var dimensionName = list.attr("dimension");
                var sortOrder = list.sortable("toArray").toString();
                moveCallback(projectId, dimensionName, sortOrder);
            }
        }).disableSelection();

});
