<g:if test="${flash.message}"><div class="message">${flash.message}</div></g:if>
<g:if test="${flash.error}"><div class="error">${flash.error}</div></g:if>

<g:hasErrors bean="${element}">
  <div class="error">
    <ul class="errors">
      <g:eachError bean="${element}" var="error">
        <li><g:message error="${error}" /></li>
      </g:eachError>
    </ul>
  </div>
</g:hasErrors>

<g:formRemote name="edit-element" url="[action:'save']"
      update="[success:updateDiv,failure:'mtm-modal-data']"
      onSuccess="location.reload(true);"
      onFailure="refreshMtmModal()"
      asynchronous="false"
      >

  <fieldset>
    <g:render contextPath="/element" template="properties" />
  </fieldset>

  <div class="buttonset">
    <g:submitButton name="create" class="save" value="Create" />
    <mtm:closeDialogLink>Cancel</mtm:closeDialogLink>
  </div>

</g:formRemote>
