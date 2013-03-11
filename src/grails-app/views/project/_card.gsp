<%-- If complete is set, then show the abbreviated card. --%>
<g:set var="abbrevStyle" value="${complete ? '' : 'hidden'}" />
<g:set var="detailStyle" value="${complete ? 'hidden' : ''}" />

<%-- <div id="${story.id}" class="card ${layoutStyle} ${story.blocked ? 'blocked' : ''} ${story.valueFor('status')?.element?.colour}"> --%>
<div id="${story.id}" class="card ${layoutStyle} ${story.blocked ? 'blocked' : ''}">

  <div id="story-abbrev-${id}" class="story-abbrev ${abbrevStyle}">
    <%-- <div class="card-estimate">4</div> --%>
    <g:render template="/project/cardActions" />
    <span class="name">${story.id}</span>
  </div>

  <div id="story-${id}" class="story ${detailStyle}">
    <div class="summary">
      <%-- <div class="card-estimate">4</div> --%>
      <g:render template="/project/cardActions" />
      <div class="summary">${story.id}: ${story.summary}</div>
      <div class="vector">
        <g:each var="point" in="${story.vector}">
          <div class="tag ${point.element?.colour ?: point.element?.dimension.colour ?: 'wheat'}">${point.element}</div>
        </g:each>
      </div>
    </div>
  </div>

</div>
