<g:formRemote name="editProject" url="${[controller: 'project', action: 'update']}"
      update="[success:'nextUrl',failure:'simplemodal-data']"
      onSuccess="window.location.href = \$('#nextUrl').html()"
      onFailure="refreshSimpleModal()"
      asynchronous="false"
      >
  <g:hiddenField name="id" value="${project?.id}" />
  <div class="simplemodal-content">
    <g:render contextPath="/layouts" template="messages" model="[instance: project]" />
    <fieldset>
      <g:render template="properties" />
    </fieldset>
  </div>
  <div class="buttonset">
    <button id="update">Update</button>
    <simplemodal:closeButton>Cancel</simplemodal:closeButton>
  </div>
</g:formRemote>

<div id="nextUrl" class="hidden"></div>
