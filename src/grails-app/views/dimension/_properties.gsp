<g:hiddenField name="project.id" value="${dimension?.project?.id}" />

<dl>

  <dt> <label for="name" class="required">Dimension</label> </dt>
  <dd> <g:field name="name" type="text" value="${dimension?.name}" class="${hasErrors(bean: dimension, field: 'name', 'error')}" /> </dd>

  <dt> <label for="description">Description</label> </dt>
  <dd> <g:textArea name="description" cols="50" rows="5" maxlength="4095" value="${dimension?.description}" class="${hasErrors(bean: dimension, field: 'description', 'error')}" /> </dd>

  <g:if test="${dimension?.isPrimaryAxis()}">
    <dt class="hint">This is the project's primary dimension (the one used by the default story maps use for the Y axis.)</dt>
    <dd></dd>
  </g:if><g:else>
    <dt> <label for="primary">Primary Dimension</label> </dt>
    <dd class="hint">
      <g:checkBox name="primary" checked="${dimension?.isPrimaryAxis()}" value="true" class="${hasErrors(bean: dimension, field: 'primary', 'error')}" />
      The project's primary dimension is used as the Y axis for all the default story maps.
    </dd>
  </g:else>

</dl>
