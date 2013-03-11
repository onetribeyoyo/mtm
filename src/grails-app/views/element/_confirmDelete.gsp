<g:if test="${flash.message}"><div class="message">${flash.message}</div></g:if>
<g:if test="${flash.error}"><div class="error">${flash.error}</div></g:if>

<g:hasErrors bean="${element}">
  <ul class="errors">
    <g:eachError bean="${element}" var="error">
      <li><g:message error="${error}" /></li>
    </g:eachError>
  </ul>
</g:hasErrors>

<g:set var="updateDiv" value="element-${element?.id}" />
<g:formRemote name="delete-element" url="[controller:'element', action:'delete']"
      update="[success:updateDiv,failure:'mtm-modal-data']"
      onSuccess="location.reload(true);"
      asynchronous="false"
      >
 <%-- onSuccess="\$('#element-${element.id}').remove()" --%>

  <fieldset>
    <g:hiddenField name="id" value="${element.id}" />
    <p>Are you sure you want to delete the <strong><em>${element.dimension}</em></strong> element
    <strong><em>${element}</em></strong>?</p>
    <p>This action cannot be reversed.</p>
  </fieldset>

  <div class="buttonset">
    <g:submitButton name="delete" class="delete" value="Delete" />
    <mtm:closeDialogLink>Cancel</mtm:closeDialogLink>
  </div>

</g:formRemote>
