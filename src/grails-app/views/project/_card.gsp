<%-- If complete is set, then show the abbreviated card. --%>
<g:set var="abbrevStyle" value="${complete ? '' : 'hidden'}" />
<g:set var="detailStyle" value="${complete ? 'hidden' : ''}" />

<div id="${story.id}" class="card ${layoutStyle} ${story.blocked ? 'blocked' : ''} ${story.valueFor('status')?.element?.colour}">

  <div id="story-abbrev-${id}" class="story-abbrev ${abbrevStyle}">
    <%-- <div class="card-estimate">4</div> --%>
    <g:render template="/project/cardActions" />
    <span class="name">${story.id}</span>
  </div>

  <div id="story-${story.id}" class="story ${detailStyle}">
    <div class="summary">
      <%-- <div class="card-estimate">4</div> --%>

      <g:set var="assignedTo" value="${story.valueFor('assigned to')?.element}" />
      <g:if test="${assignedTo}"> <div class="assignedTo ${assignedTo.colour ?: assignedTo.dimension.colour ?: ''}">${assignedTo}</div> </g:if>

      <g:render template="/project/cardActions" />
      <div class="summary">${story.id}: ${story.summary}</div>
      <div class="vector">
        <g:each var="dimension" in="${story.project.dimensions}">
          <g:if test="${!(dimension.name in [xAxis?.name, yAxis?.name, 'assigned to', 'status'])}">
            <g:set var="point" value="${story.valueFor(dimension)}" />
              <g:if test="${point}">
                <div class="tag ${point.element?.colour ?: point.element?.dimension.colour ?: 'wheat'}">${point.element}</div>
            </g:if>
          </g:if>
        </g:each>
      </div>
    </div>
  </div>

</div>
