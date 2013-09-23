<%@ page import="grails.util.Environment" %>

<g:if test="${Environment.current in [Environment.PRODUCTION]}">
  <div class="warning-banner non-printing">&nbsp;</div>
</g:if>
<g:elseif test="${Environment.current in [Environment.DEVELOPMENT]}">
  <div class="caution-banner non-printing">&nbsp;</div>
</g:elseif>
