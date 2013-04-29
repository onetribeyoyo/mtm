<g:hiddenField name="project.id" value="${story?.project?.id}" />

<dl>

  <dt> <label for="summary" class="required">Summary</label> </dt>
  <dd> <g:field name="summary" size="103" maxlength="255" value="${story?.summary}" class="${hasErrors(bean: story, field: 'summary', 'error')}" /> </dd>

  <dt> Dimensions </dt>
  <dd>
    <g:each var="dimension" in="${story.project.dimensions}">
      <span class="nowrap">
        <label for="${dimension.id}.id"><strong>${dimension.name.capitalize()}</strong></label>:
        <g:select name="${dimension.name}" class="many-to-one"
              from="${dimension.elements}" optionKey="value" value="${story.valueFor(dimension)?.value}"noSelection="['': '???']" />
      </span>
    </g:each>
  </dd>

  <dt> <label for="detail">Detail</label> </dt>
  <dd> <g:textArea name="detail" cols="100" rows="5" maxlength="4095" value="${story?.detail}" class="${hasErrors(bean: story, field: 'detail', 'error')}" /> </dd>

  <dt> <label for="estimate" class="required">Estimate</label> </dt>
  <dd>
    <g:field name="estimate" size="8" maxlength="8" type="text" value="${story?.estimate}" class="${hasErrors(bean: story, field: 'estimate', 'error')}" />
    ${story.project.estimateUnits}
  </dd>

</dl>
