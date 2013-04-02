<dl>

  <dt> <label for="name" class="required">Name</label> </dt>
  <dd> <g:field name="name" type="text" value="${project?.name}" class="${hasErrors(bean: project, field: 'name', 'error')}" /> </dd>

  <dt> <label for="estimateUnits" class="required">Estimate Units</label> </dt>
  <dd> <g:field name="estimateUnits" type="text" value="${project?.estimateUnits}" class="${hasErrors(bean: project, field: 'estimateUnits', 'error')}" /> </dd>

  <dt> <label for="colour">Show Estimates</label> </dt>
  <dd>
    <g:checkBox name="showEstimates" checked="${project.showEstimates}" value="true" class="${hasErrors(bean: project, field: 'showEstimates', 'error')}" />
    <span class="hint">Display row totals and story estimates in storymaps.</span>
    <br /><span class="hint">Uncheck this when you want to discuss the project without focusing on estimates!</span>
  </dd>

  <g:if test="${project.dimensions}">

    <dt> <label for="colour">Card Colour</label> </dt>
    <dd>
      <g:select name="colour" class="many-to-one" from="${project.dimensions}" optionKey="name" value="${project.colourDimension?.name}" noSelection="['': '']" />
      <span class="hint">The <em>card colour</em> dimension is used to set background colours on the cards.</span>
    </dd>

    <dt> <label for="highlight">Highlight</label> </dt>
    <dd>
      <g:select name="highlight" class="many-to-one" from="${project.dimensions}" optionKey="name" value="${project.highlightDimension?.name}" noSelection="['': '']" />
      <span class="hint">The <em>card highlight</em> dimension is used to show detail in each card's upper right corner.</span>
    </dd>

    <dt> <label for="primary">Primary Axis</label> </dt>
    <dd>
      <g:select name="primary" class="many-to-one" from="${project.dimensions}" optionKey="name" value="${project.primaryAxis?.name}" />
      <span class="hint">The project's <em>primary</em> dimension is used as the Y axis for all the default story maps.</span>
    </dd>

  </g:if>

</dl>
