instructions
...?

<hr />

<g:if test="${flash.message}"> <div class="message">${flash.message}</div> </g:if>
<g:if test="${flash.error}"> <div class="error">${flash.error}</div> </g:if>

<g:form action="importStories" enctype="multipart/form-data">
  <fieldset>
    <p> Filename: <input name="file" id="importFile" type="file" value="${filename}" /> </p>
    <input name="id" type="hidden" value="${id}" />
  </fieldset>
  <div class="buttonset">
    <button id="import">Import</button>
    <mtm:closeDialogButton>Cancel</mtm:closeDialogButton>
  </div>

</g:form>
