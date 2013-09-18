instructions
...?

<hr />

<g:form action="importProject" enctype="multipart/form-data" >
  <fieldset>
    <dl>
      <dt> <label for="projectName" class="required">Project Name</label> </dt>
      <dd> <input name="projectName" id="projectName" type="text" value="${projectName}" /> </dd>

      <dt> <label for="projectFile" class="required">Import File</label> </dt>
      <dd> <input id="projectFile" name="projectFile" type="file" value="${filename}" /> </dd>
    </dl>
  </fieldset>
  <div class="buttonset">
    <button id="import">Import Project</button>
    <simplemodal:closeButton>Cancel</simplemodal:closeButton>
  </div>
</g:form>
