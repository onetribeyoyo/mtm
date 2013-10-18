<g:hiddenField name="id" value="${element?.id}" />
<g:hiddenField name="dimension.id" value="${element?.dimension?.id}" />

<table>

  <tr>
    <th> <label for="value" class="required">Value</label> </th>
    <td> <g:field name="value" type="text" size="52" maxlength="255" value="${element?.value}" class="${hasErrors(bean: element, field: 'value', 'error')}" /> </td>
  </tr>

  <tr>
    <th> <label for="colour" class="required">Colour</label> </th>
    <td> <g:field name="colour" type="text" size="52" maxlength="63" value="${element?.colour}" class="${hasErrors(bean: element, field: 'colour', 'error')}" /> </td>
  </tr>

  <tr>
    <th> <label for="description">Description</label> </th>
    <td> <g:textArea name="description" cols="50" rows="5" maxlength="4095" value="${element?.description}" class="${hasErrors(bean: element, field: 'description', 'error')}" /> </td>
  </tr>

</table>
