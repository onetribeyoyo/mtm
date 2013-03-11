<g:hiddenField name="id" value="${project?.id}" />

<dl>

  <dt> <label for="name" class="required">Name</label> </dt>
  <dd> <g:field name="name" type="text" value="${project?.name}" class="${hasErrors(bean: project, field: 'name', 'error')}" /> </dd>

</dl>
