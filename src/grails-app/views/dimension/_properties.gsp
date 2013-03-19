<g:hiddenField name="project.id" value="${dimension?.project?.id}" />

<dl>

  <dt> <label for="name" class="required">Dimension</label> </dt>
  <dd>
    <g:if test="${dimension.basis}">
      ${dimension?.name}
      <p class="hint">(need a note here explaining why the name can't be changed.)</p>
    </g:if><g:else>
      <g:field name="name" type="text" value="${dimension?.name}" class="${hasErrors(bean: dimension, field: 'name', 'error')}" />
    </g:else>
  </dd>

  <dt> <label for="description">Description</label> </dt>
  <dd> <g:textArea name="description" cols="50" rows="5" maxlength="4095" value="${dimension?.description}" class="${hasErrors(bean: dimension, field: 'description', 'error')}" /> </dd>

</dl>
