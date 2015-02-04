<table width="100%">

  <tr>
    <th class="nowrap"> <label for="name" class="required">Project Name</label> </th>
    <td valign="middle"> <g:field name="name" type="text" size="42" maxlength="255" value="${project?.name}" class="${hasErrors(bean: project, field: 'name', 'error')}" /> </td>
  </tr>

  <tr>
    <th class="nowrap"> <label for="estimateUnits" class="required">Estimate Units</label> </th>
    <td valign="middle"> <g:field name="estimateUnits" type="text" size="42" maxlength="15" value="${project?.estimateUnits}" class="${hasErrors(bean: project, field: 'estimateUnits', 'error')}" /> </td>
  </tr>

  <tr>
    <th class="nowrap"> <label for="colour">Show Estimates</label> </th>
    <td valign="middle">
      <g:checkBox name="showEstimates" checked="${project.showEstimates}" value="true" class="${hasErrors(bean: project, field: 'showEstimates', 'error')}" />
      <span class="hint">show story estimates and row totals on storymaps</span>
    </td>
  </tr>

  <tr>
    <th class="nowrap"> <label for="colour">Extra Dimensions</label> </th>
    <td valign="middle">
      <g:checkBox name="showExtraDimensions" checked="${project.showExtraDimensions}" value="true" class="${hasErrors(bean: project, field: 'showExtraDimensions', 'error')}" />
      <span class="hint">show extra dimensions on storymap cards</span>
    </td>
  </tr>

  <g:if test="${project.dimensions}">

    <tr>
      <th class="nowrap"> <label for="colour">Card Colour</label> </th>
      <td valign="middle">
        <g:select name="colour" class="many-to-one" from="${project.dimensions}" optionKey="name" value="${project.colourDimension?.name}" noSelection="['': '']" />
        <span class="hint">for background colours on the cards</span>
      </td>
    </tr>

    <tr>
      <th class="nowrap"> <label for="highlight">Highlight</label> </th>
      <td valign="middle">
        <g:select name="highlight" class="many-to-one" from="${project.dimensions}" optionKey="name" value="${project.highlightDimension?.name}" noSelection="['': '']" />
        <span class="hint">for upper right corner card detail</span>
      </td>
    </tr>

    <tr>
      <th class="nowrap"> <label for="primaryX">Primary X Axis</label> </th>
      <td valign="middle">
        <g:select name="primaryX" class="many-to-one" from="${project.dimensions}" optionKey="name" value="${project.primaryXAxis?.name}" />
        <span class="hint">the default X for story maps</span>
      </td>
    </tr>

    <tr>
      <th class="nowrap"> <label for="primaryY">Primary Y Axis</label> </th>
      <td valign="middle">
        <g:select name="primaryY" class="many-to-one" from="${project.dimensions}" optionKey="name" value="${project.primaryYAxis?.name}" />
        <span class="hint">the default Y for story maps</span>
      </td>
    </tr>

  </g:if>

</table>
