<div class="card ${element.colour ?: element.dimension.colour}">

  <div class="card-actions non-printing">
    <simplemodal:link controller="element" action="edit" id="${element.id}"
          title="Edit '${element}' element" width="narrow"><img src="${fam.icon(name: 'page_edit')}" title="edit element" /></simplemodal:link>
    <g:if test="${element.dimension.elements.size() > 1}">
      <simplemodal:confirm controller="element" action="delete" id="${element.id}"
            confirmLabel="Delete" confirmClass="delete"
            message="Are you sure you want to delete the &quot;&lt;em&gt;${element}&lt;/em&gt;&quot; value?  This action cannot be reversed."
            title="Delete '${element.dimension}' '${element}'"
            width="narrow"><img src="${fam.icon(name: 'delete')}" title="delete element" /></simplemodal:confirm>
    </g:if>
  </div>

  <%-- TODO: how many stories? --%>

  ${element.value}

</div>
