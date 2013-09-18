<g:if test="${flash.message}"><div class="message">${flash.message}</div></g:if>
<g:if test="${flash.error}"><div class="error">${flash.error}</div></g:if>

<g:hasErrors bean="${story}">
  <div class="error">
    <ul class="errors">
      <g:eachError bean="${story}" var="error">
        <li><g:message error="${error}" /></li>
      </g:eachError>
    </ul>
  </div>
</g:hasErrors>

<g:set var="updateDiv" value="story-${story.id}" />
<g:formRemote name="edit-story" url="[action:'update']"
      update="[success:updateDiv,failure:'mtm-modal-data']"
      onSuccess="location.reload(true);"
      asynchronous="false"
      >
 <%-- onSuccess="closeMtmModal('mtm-modal-content')" --%>
 <%-- TODO: forcing the page to reload is too big a hammer.  onSuccess should simply refresh the card and if necessary move it to the new location. --%>

  <fieldset>
    <g:hiddenField name="id" value="${story?.id}" />
    <g:render template="properties" />
  </fieldset>

  <div>
    <button type="submit" id="edit" value="Update">Update</button>
    <simplemodal:closeButton>Cancel</simplemodal:closeButton>
  </div>

</g:formRemote>
