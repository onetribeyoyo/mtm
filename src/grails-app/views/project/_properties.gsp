<g:hiddenField name="id" value="${project?.id}" />

<dl>

  <dt> <label for="name" class="required">Name</label> </dt>
  <dd> <g:field name="name" type="text" value="${project?.name}" class="${hasErrors(bean: project, field: 'name', 'error')}" /> </dd>

  <dt> <label for="estimateUnits" class="required">Estimate Units</label> </dt>
  <dd> <g:field name="estimateUnits" type="text" value="${project?.estimateUnits}" class="${hasErrors(bean: project, field: 'estimateUnits', 'error')}" /> </dd>

</dl>
