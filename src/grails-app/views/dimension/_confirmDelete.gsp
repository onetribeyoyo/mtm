<g:if test="${flash.message}"><div class="message">${flash.message}</div></g:if>
<g:if test="${flash.error}"><div class="error">${flash.error}</div></g:if>

<g:hasErrors bean="${dimension}">
  <ul class="errors">
    <g:eachError bean="${dimension}" var="error">
      <li><g:message error="${error}" /></li>
    </g:eachError>
  </ul>
</g:hasErrors>

<g:set var="updateDiv" value="dimension-${dimension?.id}" />
<g:formRemote name="delete-dimension" url="[controller:'dimension', action:'delete']"
      update="[success:updateDiv,failure:'mtm-modal-data']"
      onSuccess="location.reload(true);"
      asynchronous="false"
      >

  <fieldset>
    <g:hiddenField name="id" value="${dimension.id}" />
    <p>
      Are you sure you want to delete the <strong>${dimension.name}</strong> dimension?
      This action cannot be reversed.
    </p>
  </fieldset>

  <div class="buttonset">
    <button class="delete-button" id="delete">Delete</button>
    <mtm:closeDialogButton>Cancel</mtm:closeDialogButton>
  </div>

</g:formRemote>
