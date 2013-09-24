<g:set var="updateDiv" value="element-${element?.id}" />

<g:formRemote name="delete-element" url="[controller:'element', action:'delete']" asynchronous="false">

  <div class="simplemodal-content">
    <g:render contextPath="/layouts" template="messages" model="[instance: element]" />
    <fieldset>
      <g:hiddenField name="id" value="${element.id}" />
      <p>Are you sure you want to delete the <strong><em>${element.dimension}</em></strong> element
      <strong><em>${element}</em></strong>?</p>
      <p>This action cannot be reversed.</p>
    </fieldset>
  </div>

  <div class="buttonset">
    <button class="delete" id="delete">Delete</button>
    <simplemodal:closeButton>Cancel</simplemodal:closeButton>
  </div>

</g:formRemote>
