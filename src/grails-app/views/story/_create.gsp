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

<g:formRemote name="create-story" url="[action:'save']"
      update="[success:updateDiv,failure:'mtm-modal-data']"
      onSuccess="location.reload(true);"
      onFailure="refreshMtmModal()"
      asynchronous="false"
      >
  <fieldset>
    <g:render template="properties" />
  </fieldset>

  <div>
    <button type="submit" id="create" value="Create">Create</button>
    <simplemodal:closeButton>Cancel</simplemodal:closeButton>
  </div>

</g:formRemote>
