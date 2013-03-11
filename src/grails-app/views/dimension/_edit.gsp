<g:if test="${flash.message}"><div class="message">${flash.message}</div></g:if>
<g:if test="${flash.error}"><div class="error">${flash.error}</div></g:if>

<g:hasErrors bean="${dimension}">
  <ul class="errors">
    <g:eachError bean="${dimension}" var="error">
      <li><g:message error="${error}" /></li>
    </g:eachError>
  </ul>
</g:hasErrors>

<g:set var="updateDiv" value="dimension-${dimension.id}" />
<g:formRemote name="edit-dimension" url="[action:'update']"
      update="[success:updateDiv,failure:'mtm-modal-data']"
      onSuccess="location.reload(true);"
      asynchronous="false"
      >

  <fieldset>
    <g:hiddenField name="id" value="${dimension?.id}" />
    <g:render contextPath="/dimension" template="properties" />
  </fieldset>

  <div class="buttonset">
    <g:submitButton name="edit" class="update" value="Update" />
    <mtm:closeDialogLink>Cancel</mtm:closeDialogLink>
  </div>

</g:formRemote>
