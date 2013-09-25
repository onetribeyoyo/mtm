<div class="card ${element.colour ?: element.dimension.colour}">

  <div class="card-actions non-printing">
    <simplemodal:link controller="element" action="edit" id="${element.id}"
          title="Edit '${element}' element" width="narrow"><img src="${fam.icon(name: 'page_edit')}" title="edit element" /></simplemodal:link>


    <simplemodal:link controller="element" action="confirmDelete" id="${element.id}"
          title="Delete ${element}" width="narrow"><img src="${fam.icon(name: 'delete')}" title="delete element" /></simplemodal:link>
  </div>

  <%-- TODO: how many stories? --%>

  ${element.value}

</div>
