<div class="section float-left">
  <div>
    <div class="card-actions float-right">
      <mtm:dialogLink controller="dimension" action="edit" id="${dimension.id}" title="Edit ${dimension}"><img src="${fam.icon(name: 'page_edit')}" /></mtm:dialogLink>
      <g:if test="${!dimension.basis}">
        <mtm:dialogLink controller="dimension" action="confirmDelete" id="${dimension.id}" title="Delete ${dimension}"><img src="${fam.icon(name: 'delete')}" /></mtm:dialogLink>
      </g:if>
    </div>
    <h2>${dimension}</h2>
  </div>
  <g:if test="${dimension.basis}">
    <p class="narrow hint">
      This is one of the fundamental dimension (the basis) for the project.  As such, it cannot be deleted or renamed.
    </p>
  </g:if>
  <p class="narrow">
    ${dimension.description ?: "paragraph explaining what ${dimension.name} are used for..."}
  </p>
  <div class="grid">
    <div class="dimension-list" dimensionId="${dimension.id}">
      <g:each var="element" in="${dimension.elements}" status="rowNumber">
        <g:set var="rowStyle" value="${((rowNumber % 2) == 0) ? 'odd' : 'even'}" />
        <div id="element-${element.id}" class="card ${rowStyle} ${element.colour ?: dimension.colour} light-margin heavy-padding">
          <div class="card-actions">
            <mtm:dialogLink controller="element" action="edit" id="${element.id}" title="Edit ${element}"><img src="${fam.icon(name: 'page_edit')}" /></mtm:dialogLink>
            <mtm:dialogLink controller="element" action="confirmDelete" id="${element.id}" title="Delete ${element}"><img src="${fam.icon(name: 'delete')}" /></mtm:dialogLink>
          </div>
          <%-- TODO: how many stories? --%>
          ## ${element.value}
        </div>
      </g:each>
    </div>
  </div>
  <p class="hint narrow">Drag these up/down to adjust the order.</p>
  <div class="buttonset">
    <mtm:dialogLink controller="element" action="create" id="${dimension.id}" title="New ${dimension}">New ${dimension}</mtm:dialogLink>
  </div>
</div>
