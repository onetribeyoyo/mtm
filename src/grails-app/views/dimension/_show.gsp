<div class="section">
  <div>
    <div class="card-actions float-right non-printing">
      <simplemodal:link controller="dimension" action="edit" id="${dimension.id}"
            title="Edit ${dimension}" width="narrow"><img src="${fam.icon(name: 'page_edit')}" title="edit dimension" /></simplemodal:link>
      <g:if test="${!dimension.isPrimaryXAxis() && !dimension.isPrimaryYAxis() && (dimension.project.dimensions.size() != 2)}">
        <simplemodal:confirm controller="dimension" action="delete" id="${dimension.id}"
              confirmLabel="Delete" confirmClass="delete"
              message="Are you sure you want to delete the &quot;&lt;em&gt;${dimension.name}&lt;/em&gt;&quot; dimension?  This action cannot be reversed."
              title="Delete '${dimension.name}'"><img src="${fam.icon(name: 'delete')}" title="delete dimension" /></simplemodal:confirm>
      </g:if>
    </div>
    <h2>&quot;${dimension.name.capitalize()}&quot;</h2>
  </div>
  <g:if test="${dimension.isPrimaryXAxis() || dimension.isPrimaryYAxis()}">
    <hr />
    <p class="narrow hint">
      This is one of the primary dimensions for the project (the ones used as the X/Y axis on the default story maps.)  As such, it cannot be deleted.
    </p>
  </g:if> <g:elseif test="${dimension.project.dimensions.size() == 2}">
    <hr />
    <p class="narrow hint">
      There are only two dimensions, so neither can be deleted.
    </p>
  </g:elseif>
  <g:if test="${dimension.description}">
    <hr />
    <p class="narrow hint">${dimension.description}</p>
  </g:if>
  <hr />
  <p class="hint narrow">Drag these up/down to adjust the order.</p>
  <div class="grid">
    <div class="dimension-list" data-projectId="${dimension.project.id}" data-dimension="${dimension.name}">
      <g:each var="element" in="${dimension.elements}">
        <div id="element-${element.id}" style="min-width: 18.5em;">
          <g:render contextPath="/element" template="show" model="[element: element]" />
        </div>
      </g:each>
    </div>
  </div>
  <div class="buttonset non-printing">
    <simplemodal:link controller="element" action="create" id="${dimension.id}"
          title="New ${dimension} value" width="narrow"><button>New <em>"${dimension}"</em> value</button></simplemodal:link>
  </div>
</div>
