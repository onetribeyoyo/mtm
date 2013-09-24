<g:hiddenField name="project.id" value="${dimension?.project?.id}" />

<table width="100%">

  <tr>
    <th class="nowrap"> <label for="name" class="required">Dimension</label> </th>
    <td>
      <g:field name="name" type="text" value="${dimension?.name}" class="${hasErrors(bean: dimension, field: 'name', 'error')}" />
    </td>
  </tr>

  <tr>
    <th class="nowrap"> <label for="description">Description</label> </th>
    <td>
      <g:textArea name="description" cols="50" rows="5" maxlength="4095" value="${dimension?.description}" class="${hasErrors(bean: dimension, field: 'description', 'error')}" />
    </td>
  </tr>

  <tr>
    <th class="nowrap"> <label for="colour">Card</label> </th>
    <td>
      <g:checkBox name="colour" checked="${dimension?.isColourDimension()}" value="true" class="${hasErrors(bean: dimension, field: 'colourDimension', 'error')}" />
      <span class="hint">use this dimension to set card background colours</span>
    </td>
  </tr>

  <tr>
    <th class="nowrap"> <label for="highlight">Highlight</label> </th>
    <td>
      <g:checkBox name="highlight" checked="${dimension?.isHighlightDimension()}" value="true" class="${hasErrors(bean: dimension, field: 'highlightDimension', 'error')}" />
      <span class="hint">use this dimension for highlighting card detail</span>
    </td>
  </tr>

  <tr>
    <th class="nowrap"> <label for="primaryX">Primary X</label> </th>
    <td>
      <g:checkBox name="primaryX" checked="${dimension?.isPrimaryXAxis()}" value="true"
            disabled="${dimension?.isPrimaryXAxis() ? 'disablesd' : 'false'}"
            class="${hasErrors(bean: dimension, field: 'primaryXAxis', 'error')}" />
      <span class="hint">this is the project's primary X axis</span>
    </td>
  </tr>

  <tr>
    <th class="nowrap"> <label for="primaryY">Primary Y</label> </th>
    <td>
      <g:checkBox name="primaryY" checked="${dimension?.isPrimaryYAxis()}" value="true"
            disabled="${dimension?.isPrimaryYAxis() ? 'disablesd' : 'false'}"
            class="${hasErrors(bean: dimension, field: 'primaryYAxis', 'error')}" />
      <span class="hint">this is the project's primary Y axis</span>
    </td>
  </tr>

</table>
