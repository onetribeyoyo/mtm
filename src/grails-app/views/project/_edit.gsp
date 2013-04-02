<g:if test="${flash.message}"><div class="message">${flash.message}</div></g:if>
<g:if test="${flash.error}"><div class="error">${flash.error}</div></g:if>

<g:hasErrors bean="${project}">
  <div class="error">
    <ul class="errors">
      <g:eachError bean="${project}" var="error">
        <li><g:message error="${error}" /></li>
      </g:eachError>
    </ul>
  </div>
</g:hasErrors>

<g:set var="updateDiv" value="project-${project.id}" />
<g:formRemote name="edit-project" url="[action:'update']"
      update="[success:updateDiv,failure:'mtm-modal-data']"
      onSuccess="location.reload(true);"
      onFailure="refreshMtmModal()"
      asynchronous="false"
      >

  <fieldset>
    <g:hiddenField name="id" value="${project?.id}" />
    <g:render template="properties" />
  </fieldset>

  <div class="buttonset">
    <button id="edit">Update</button>
    <mtm:closeDialogButton>Cancel</mtm:closeDialogButton>
  </div>

</g:formRemote>
