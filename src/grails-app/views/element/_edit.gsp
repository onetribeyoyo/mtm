<g:set var="successDivId" value="element-${element.id}" />
<g:formRemote name="editElement" url="${[controller: 'element', action: 'update']}"
      update="[success:successDivId,failure:'simplemodal-data']"
      onSuccess="closeSimpleModal()"
      onFailure="refreshSimpleModal()"
      asynchronous="false"
      >
  <div class="simplemodal-content">
    <g:render contextPath="/layouts" template="messages" model="[instance: element]" />
    <fieldset>
      <g:render template="properties" />
    </fieldset>
  </div>
  <div class="buttonset">
    <button id="update">Update</button>
    <simplemodal:closeButton>Cancel</simplemodal:closeButton>
  </div>
</g:formRemote>
