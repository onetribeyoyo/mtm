<ul class="tabrow"> <%-- begin tabs --%>
  <li class="<g:if test="${selectedTab == 'faq'}">selected</g:if>"> <g:link action="faq">FAQ</g:link> </li>
  <li class="<g:if test="${selectedTab == 'storymaps'}">selected</g:if>"> <g:link action="storymap">Story Maps</g:link> </li>
  <li class="<g:if test="${selectedTab == 'process'}">selected</g:if>"> <g:link action="process">Mapping Process</g:link> </li>
  <li class="<g:if test="${selectedTab == 'estimation'}">selected</g:if>"> <g:link action="estimation">Estimation</g:link> </li>

  <li> <g:link controller="project" action="list">Project List</g:link> </li>
</ul> <%-- end tabs --%>
