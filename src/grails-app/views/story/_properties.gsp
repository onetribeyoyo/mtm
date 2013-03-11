<g:hiddenField name="project.id" value="${story?.project?.id}" />

<dl>

  <dt> <label for="summary" class="required">Summary</label> </dt>
  <dd> <g:textArea name="summary" cols="50" rows="2" maxlength="255" value="${story?.summary}" class="${hasErrors(bean: story, field: 'summary', 'error')}" /> </dd>

  <dt> Dimensions </dt>
  <dd>
    <g:each var="dimension" in="${story.project.dimensions}">
      <span class="nowrap">
        <label for="${dimension.id}.id"><strong>${dimension.name.capitalize()}</strong></label>:
        <g:select name="dimension-${dimension.id}" from="${dimension.elements}" optionKey="id"
              value="${story.valueFor(dimension)?.element?.id}" class="many-to-one" noSelection="['': '???']" />
      </span>
    </g:each>
  </dd>

  <dt> <label for="detail">Detail</label> </dt>
  <dd> <g:textArea name="detail" cols="50" rows="5" maxlength="4095" value="${story?.detail}" class="${hasErrors(bean: story, field: 'detail', 'error')}" /> </dd>

</dl>
