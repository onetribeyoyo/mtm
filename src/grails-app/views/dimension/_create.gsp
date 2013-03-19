<g:if test="${flash.message}"><div class="message">${flash.message}</div></g:if>
<g:if test="${flash.error}"><div class="error">${flash.error}</div></g:if>

<g:hasErrors bean="${dimension}">
  <div class="error">
    <ul class="errors">
      <g:eachError bean="${dimension}" var="error">
        <li><g:message error="${error}" /></li>
      </g:eachError>
    </ul>
  </div>
</g:hasErrors>

<%-- TODO: should be formRemote --%>
<g:form name="create-dimension" action="save">

  <fieldset>
    <g:render contextPath="/dimension" template="properties" />
  </fieldset>

  <div class="buttonset">
    <button id="create">Create</button>
    <mtm:closeDialogButton>Cancel</mtm:closeDialogButton>
  </div>

</g:form>
