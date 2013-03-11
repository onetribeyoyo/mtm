<g:if test="${flash.message}"><div class="message">${flash.message}</div></g:if>
<g:if test="${flash.error}"><div class="error">${flash.error}</div></g:if>

<g:hasErrors bean="${dimension}">
  <ul class="errors">
    <g:eachError bean="${dimension}" var="error">
      <li><g:message error="${error}" /></li>
    </g:eachError>
  </ul>
</g:hasErrors>

<g:form name="create-story" action="save">

  <fieldset>
    <g:render contextPath="/dimension" template="properties" />
  </fieldset>

  <div class="buttonset">
    <g:submitButton name="create" class="save" value="Create" />
    <mtm:closeDialogLink>Cancel</mtm:closeDialogLink>
  </div>

</g:form>
