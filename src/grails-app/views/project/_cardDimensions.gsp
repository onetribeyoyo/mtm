<div class="vector">
  <g:each var="dimension" in="${story.project.dimensions}">
    <%-- don't include x/y axis dimensions, and don't show the colourDimension or highlightDimension. --%>

    <%--
    <br />
    ${dimension}[
      x:${(dimension in [xAxis])},
      y:${(dimension in [yAxis])},
      c:${dimension.isColourDimension()},
      h:${dimension.isHighlightDimension()}
      ]
    --%>

    <g:if test="${!(dimension in [xAxis, yAxis]) && !dimension.isColourDimension() && !dimension.isHighlightDimension()}">
      <g:set var="point" value="${story.valueFor(dimension)}" />
      <g:if test="${point}">
        <span class="tag">${point}</span>
      </g:if>
    </g:if>
  </g:each>
</div>
