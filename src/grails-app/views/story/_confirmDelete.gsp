<g:if test="${flash.message}"><div class="message">${flash.message}</div></g:if>
<g:if test="${flash.error}"><div class="error">${flash.error}</div></g:if>

<g:hasErrors bean="${story}">
  <ul class="errors">
    <g:eachError bean="${story}" var="error">
      <li><g:message error="${error}" /></li>
    </g:eachError>
  </ul>
</g:hasErrors>

<g:set var="updateDiv" value="story-${story?.id}" />
<g:formRemote name="delete-story" url="[action:'delete']"
      update="[success:updateDiv,failure:'mtm-modal-data']"
      onSuccess="location.reload(true);"
      asynchronous="false"
      >
 <%-- onSuccess="closeMtmModal('mtm-modal-content')" --%>
 <%-- TODO: forcing the page to reload is too big a hammer.  onSuccess should simply refresh the card and if necessary move it to the new location. --%>

  <fieldset>
    <g:hiddenField name="id" value="${story?.id}" />
    <p>${story?.id}: ${story?.summary}</p>
  </fieldset>

  <div class="buttonset">
    <button class="delete-button" id="delete">Delete</button>
    <simplemodal:closeButton>Cancel</simplemodal:closeButton>
  </div>

</g:formRemote>
