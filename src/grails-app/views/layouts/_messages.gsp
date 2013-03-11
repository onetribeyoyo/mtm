<div id="messages">
  <g:if test="${flash.message}">
    <div class="message">
      <a class="close" onClick="$('.message').close();">×</a>
      ${flash.message}
    </div>
  </g:if>
  <g:if test="${flash.error}">
    <div class="error">
      <a class="close" onClick="$('.error').close();">×</a>
      ${flash.error}
    </div>
  </g:if>
</div>
