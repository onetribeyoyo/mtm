<g:hiddenField name="project.id" value="${dimension?.project?.id}" />

<dl>

  <dt> <label for="name" class="required">Dimension</label> </dt>
  <dd> <g:field name="name" type="text" value="${dimension?.name}" class="${hasErrors(bean: dimension, field: 'name', 'error')}" /> </dd>

  <dt> <label for="description">Description</label> </dt>
  <dd> <g:textArea name="description" cols="50" rows="5" maxlength="4095" value="${dimension?.description}" class="${hasErrors(bean: dimension, field: 'description', 'error')}" /> </dd>

  <dt> <label for="colour">Card</label> </dt>
  <dd>
    <g:checkBox name="colour" checked="${dimension?.isColourDimension()}" value="true" class="${hasErrors(bean: dimension, field: 'colourDimension', 'error')}" />
    <span class="hint">The <em>card colour</em> dimension is used to set background colours on the cards.</span>
  </dd>

  <dt> <label for="highlight">Highlight</label> </dt>
  <dd>
    <g:checkBox name="highlight" checked="${dimension?.isHighlightDimension()}" value="true" class="${hasErrors(bean: dimension, field: 'highlightDimension', 'error')}" />
    <span class="hint">The <em>card highlight</em> dimension is used to show detail in each card's upper right corner.</span>
  </dd>

  <dt> <label for="primary">Primary</label> </dt>
  <dd>
    <g:if test="${dimension?.isPrimaryAxis()}">
      <span class="hint">This is the project's <em>primary</em> dimension (the one used by the default story maps use for the Y axis.)</span>
    </g:if><g:else>
      <g:checkBox name="primary" checked="${dimension?.isPrimaryAxis()}" value="true" class="${hasErrors(bean: dimension, field: 'primaryAxis', 'error')}" />
      <span class="hint">The project's <em>primary</em> dimension is used as the Y axis for all the default story maps.</span>
    </g:else>
  </dd>

</dl>
