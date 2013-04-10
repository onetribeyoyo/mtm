<div class="section">
  <div>
    <div class="card-actions float-right non-printing">
      <mtm:dialogLink controller="dimension" action="edit" id="${dimension.id}" title="Edit ${dimension}"><img src="${fam.icon(name: 'page_edit')}" title="edit dimension" /></mtm:dialogLink>
      <g:if test="${!dimension.isPrimaryAxis() && (dimension.project.dimensions.size() != 2)}">
        <mtm:dialogLink controller="dimension" action="confirmDelete" id="${dimension.id}" title="Delete ${dimension}"><img src="${fam.icon(name: 'delete')}" title="delete dimension" /></mtm:dialogLink>
      </g:if>
    </div>
    <h2>${dimension.name.capitalize()} Dimension</h2>
  </div>
  <g:if test="${dimension.isPrimaryAxis()}">
    <hr />
    <p class="narrow hint">
      This is the primary dimension for the project (the one uses as the Y axis on the default story maps.)  As such, it cannot be deleted.
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
    <div class="dimension-list" projectId="${dimension.project.id}" dimension="${dimension.name}">
      <g:each var="element" in="${dimension.elements}" status="rowNumber">
        <g:set var="rowStyle" value="${((rowNumber % 2) == 0) ? 'odd' : 'even'}" />
        <div id="element-${element.id}" style="width: 12em;" class="card ${rowStyle} ${element.colour ?: dimension.colour} light-margin heavy-padding">
          <div class="card-actions non-printing">
            <mtm:dialogLink controller="element" action="edit" id="${element.id}" title="Edit ${element}"><img src="${fam.icon(name: 'page_edit')}" title="edit element" /></mtm:dialogLink>
            <mtm:dialogLink controller="element" action="confirmDelete" id="${element.id}" title="Delete ${element}"><img src="${fam.icon(name: 'delete')}" title="delete element" /></mtm:dialogLink>
          </div>
          <%-- TODO: how many stories? --%>
          ${element.value}
        </div>
      </g:each>
    </div>
  </div>
  <hr class="non-printing" />
  <div class="buttonset non-printing">
    <mtm:dialogLink controller="element" action="create" id="${dimension.id}" title="New ${dimension}"><button>New <em>"${dimension}"</em></button></mtm:dialogLink>
  </div>
</div>
