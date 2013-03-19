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

<%-- TODO: should be formRemote --%>
<g:form name="create-project" action="save">
 <%-- onSuccess="closeMtmModal('mtm-modal-content')" --%>
 <%-- TODO: forcing the page to reload is too big a hammer.  onSuccess should simply refresh the card and if necessary move it to the new location. --%>

  <fieldset>
    <g:render template="properties" />
  </fieldset>

  <div class="buttonset">
    <button id="create">Create</button>
    <mtm:closeDialogButton>Cancel</mtm:closeDialogButton>
  </div>

</g:form>
