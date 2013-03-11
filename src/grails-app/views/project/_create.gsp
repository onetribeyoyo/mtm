<g:if test="${flash.message}"><div class="message">${flash.message}</div></g:if>
<g:if test="${flash.error}"><div class="error">${flash.error}</div></g:if>

<g:hasErrors bean="${project}">
  <ul class="errors">
    <g:eachError bean="${project}" var="error">
      <li><g:message error="${error}" /></li>
    </g:eachError>
  </ul>
</g:hasErrors>

<g:form name="create-project" action="save">
 <%-- onSuccess="closeMtmModal('mtm-modal-content')" --%>
 <%-- TODO: forcing the page to reload is too big a hammer.  onSuccess should simply refresh the card and if necessary move it to the new location. --%>

  <fieldset>
    <g:render template="properties" />
  </fieldset>

  <div class="buttonset">
    <g:submitButton name="create" class="save" value="Create" />
    <mtm:closeDialogLink>Cancel</mtm:closeDialogLink>
  </div>

</g:form>
