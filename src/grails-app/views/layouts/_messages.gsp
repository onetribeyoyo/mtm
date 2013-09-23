<div class="messages">

  <g:if test="${flash.error || flash.errors || instance?.hasErrors()}">
    <div class="error">
      <a class="close" onClick="$('.error').close();">�</a>
      <a class="close">×</a>
      ${flash.error}
      <g:if test="${flash.errors}">
        <ul>
          <g:each var="error" in="${flash.errors}"><li>${error}</li></g:each>
        </ul>
      </g:if>
      <g:if test="${instance?.hasErrors()}">
        <ul>
          <g:eachError bean="${instance}" var="error"><li><g:message error="${error}"/></li></g:eachError>
        </ul>
      </g:if>
    </div>
  </g:if>

  <g:if test="${flash.message}">
    <div class="message">
      <a class="close" onClick="$('.message').close();">�</a>
      <a class="close">×</a>
      ${flash.message}
    </div>
  </g:if>

</div>
