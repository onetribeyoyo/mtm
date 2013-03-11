<g:hiddenField name="dimension.id" value="${element?.dimension?.id}" />

<dl>

  <dt> <label for="dimension">Dimension</label> </dt>
  <dd> ${element.dimension.name} </dd>

  <dt> <label for="value" class="required">Value</label> </dt>
  <dd> <g:field name="value" type="text" value="${element?.value}" class="${hasErrors(bean: element, field: 'value', 'error')}" /> </dd>

  <dt> <label for="description">Description</label> </dt>
  <dd> <g:textArea name="description" cols="50" rows="5" maxlength="4095" value="${element?.description}" class="${hasErrors(bean: element, field: 'description', 'error')}" /> </dd>

  <dt> <label for="order">Sort Order</label> </dt>
  <dd> <g:field name="order" type="number" value="${element.order}" class="${hasErrors(bean: element, field: 'order', 'error')}" /> </dd>

</dl>
