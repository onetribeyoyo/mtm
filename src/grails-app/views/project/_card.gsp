<%-- If complete is set, then show the abbreviated card. --%>
<g:set var="abbrevStyle" value="${complete ? '' : 'hidden'}" />
<g:set var="detailStyle" value="${complete ? 'hidden' : ''}" />

<div id="${story.id}" class="card ${layoutStyle} ${story.blocked ? 'blocked' : ''} ${story.valueFor(story.project.colourDimension)?.colour}">

  <div id="story-abbrev-${id}" class="story-abbrev ${abbrevStyle}">
    <%-- <g:render template="/project/cardActions" /> --%>
    <span class="name">...${story.id.substring(story.id.length() - 4, story.id.length())}</span>
  </div>

  <div id="story-${story.id}" class="story ${detailStyle}">
    <div class="summary">
      <g:render template="/project/cardActions" />

      <g:if test="${story.project.highlightDimension}">
        <g:set var="highlight" value="${story.valueFor(story.project.highlightDimension)}" />
        <g:if test="${highlight}">
          <span class="highlight float-right">${highlight}</span>
        </g:if>
      </g:if>

      <div class="summary">${story.summary}</div>

      <g:if test="${story.project.showEstimates && story.estimate}">
        <span class="estimate float-right">${story.estimate}<g:if test="${story.project.estimateUnits}">${story.project.estimateUnits[0].toLowerCase()}</g:if></span>
      </g:if>

      <g:if test="${story.project.showExtraDimensions}">
        <g:render template="/project/cardDimensions" />
      </g:if>
    </div>
  </div>

</div>
