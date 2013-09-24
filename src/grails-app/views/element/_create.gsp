<g:formRemote name="createElement" url="${[controller: 'element', action: 'save']}"
      update="[success:'nextUrl',failure:'simplemodal-data']"
      onSuccess="window.location.href = \$('#nextUrl').html()"
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
    <button id="save">Save</button>
    <simplemodal:closeButton>Cancel</simplemodal:closeButton>
  </div>
</g:formRemote>

<div id="nextUrl" class="hidden"></div>
