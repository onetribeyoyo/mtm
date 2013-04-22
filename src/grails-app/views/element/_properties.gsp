<g:hiddenField name="dimension.id" value="${element?.dimension?.id}" />

<dl>

  <dt> <label for="value" class="required">Value</label> </dt>
  <dd> <g:field name="value" type="text" value="${element?.value}" class="${hasErrors(bean: element, field: 'value', 'error')}" /> </dd>

  <dt> <label for="colour" class="required">Colour</label> </dt>
  <dd> <g:field name="colour" type="text" value="${element?.colour}" class="${hasErrors(bean: element, field: 'colour', 'error')}" /> </dd>

  <dt> <label for="description">Description</label> </dt>
  <dd> <g:textArea name="description" cols="50" rows="5" maxlength="4095" value="${element?.description}" class="${hasErrors(bean: element, field: 'description', 'error')}" /> </dd>

</dl>
