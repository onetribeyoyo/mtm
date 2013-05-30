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

  <dt> <label for="primaryX">Primary X</label> </dt>
  <dd>
    <g:if test="${dimension?.isPrimaryXAxis()}">
      <span class="hint">This is the project's <em>primary X</em> dimension (the one used by the default story maps use for the X axis.)</span>
    </g:if><g:else>
      <g:checkBox name="primaryX" checked="${dimension?.isPrimaryXAxis()}" value="true" class="${hasErrors(bean: dimension, field: 'primaryXAxis', 'error')}" />
      <span class="hint">The project's <em>primary X</em> dimension is used as the default for story maps.</span>
    </g:else>
  </dd>

  <dt> <label for="primaryY">Primary Y</label> </dt>
  <dd>
    <g:if test="${dimension?.isPrimaryYAxis()}">
      <span class="hint">This is the project's <em>primary Y</em> dimension (the one used by the default story maps use for the Y axis.)</span>
    </g:if><g:else>
      <g:checkBox name="primaryY" checked="${dimension?.isPrimaryYAxis()}" value="true" class="${hasErrors(bean: dimension, field: 'primaryYAxis', 'error')}" />
      <span class="hint">The project's <em>primary Y</em> dimension is used as the default for story maps.</span>
    </g:else>
  </dd>

</dl>
