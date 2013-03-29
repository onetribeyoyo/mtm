<%-- If complete is set, then show the abbreviated card. --%>
<g:set var="abbrevStyle" value="${complete ? '' : 'hidden'}" />
<g:set var="detailStyle" value="${complete ? 'hidden' : ''}" />

<div id="${story.id}" class="card ${layoutStyle} ${story.blocked ? 'blocked' : ''} ${story.valueFor(story.project.colourDimension)?.element?.colour}">

  <div id="story-abbrev-${id}" class="story-abbrev ${abbrevStyle}">
    <g:render template="/project/cardActions" />
    <span class="name">${story.id}</span>
  </div>

  <div id="story-${story.id}" class="story ${detailStyle}">
    <div class="summary">
      <g:render template="/project/cardActions" />

      <g:set var="highlight" value="${story.valueFor(story.project.highlightDimension)?.element}" />
      <g:if test="${highlight}">
        <span class="highlight float-right">${highlight}</span>
      </g:if>

      <div class="summary">${story.id}: ${story.summary}</div>

      <g:if test="${story.estimate}">
        <span class="estimate float-right">${story.estimate}<g:if test="${story.project.estimateUnits}">${story.project.estimateUnits[0].toLowerCase()}</g:if></span>
      </g:if>

      <div class="vector">
        <g:each var="dimension" in="${story.project.dimensions}">
          <g:if test="${!(dimension in [xAxis, yAxis, story.project.colourDimension, story.project.highlightDimension])}">
            <g:set var="point" value="${story.valueFor(dimension)}" />
              <g:if test="${point}">
                <span class="tag">${point.element}</span>
            </g:if>
          </g:if>
        </g:each>
      </div>
    </div>
  </div>

</div>
