<g:hiddenField name="project.id" value="${story?.project?.id}" />

<table>

  <tr>
    <th> <label for="summary" class="required">Summary</label> </th>
    <td>
      <g:field name="summary" size="55" maxlength="255" value="${story?.summary}" class="${hasErrors(bean: story, field: 'summary', 'error')}" />
    </td>
  </tr>

  <tr>
    <th> Dimensions </th>
    <td>
      <g:each var="dimension" in="${story.project.dimensions}">
        <g:set var="noSelectionLabel" value="${dimension.name.capitalize()}: ???" />
        <g:select name="${dimension.name}" class="many-to-one"
              from="${dimension.elements}" optionKey="value" value="${story.valueFor(dimension)?.value}" noSelection="['': noSelectionLabel]" />
      </g:each>
    </td>
  </tr>

  <tr>
    <th> <label for="detail">Detail</label> </th>
    <td> <g:textArea name="detail" cols="52" rows="5" maxlength="4095" value="${story?.detail}" class="${hasErrors(bean: story, field: 'detail', 'error')}" /> </td>
  </tr>

  <tr>
    <th > <label for="estimate" class="required">Estimate</label> </th>
    <td>
      <g:field name="estimate" size="8" maxlength="8" type="text" value="${story?.estimate}" class="${hasErrors(bean: story, field: 'estimate', 'error')}" />
      ${story.project.estimateUnits}
    </td>
  </tr>

</table>
