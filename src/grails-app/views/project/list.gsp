<!doctype html>
<html lang="en" class="no-js">

<head>
  <meta name="layout" content="main" />
  <title>Project List</title>
  <r:require module="tabs" />
</head>

<body>

<h1> Project List </h1>
<ul class="tabrow"> <%-- begin tabs --%>
  <li class="selected"> <strong>Project List</strong> </li>
  <li> <g:link controller="info">FAQ</g:link> </li>
</ul> <%-- end tabs --%>


<div class="section">
  <table>
    <thead>
      <tr>
        <g:sortableColumn property="name" title="Project Name" />
        <th>Stories</th>
        <th width="100%">Story Maps</th>
      </tr>
    </thead>
    <tbody>
      <g:each var="project" in="${projectList}" status="i">
        <g:set var="release" value="${project.dimensionFor('release')}" />
        <g:set var="offsetIndex" value="${i + (params.offset ?: 0)}" />
        <tr class="${(((Math.ceil(((offsetIndex as int)+1)/3) as int) % 2) == 0) ? 'even' : 'odd'}">

          <th class="nowrap"> <g:link action="show" id="${project.id}">${fieldValue(bean: project, field: "name")}</g:link> </th>

          <g:set var="storyCount" value="${project.stories?.size() ?: 0}" />
          <td class="nowrap">${storyCount} ${(storyCount == 1) ? 'story' : 'stories'}</td>

          <td>
            <g:each var="dimension" in="${project.dimensions}">
              <g:if test="${dimension != release}">
                <g:link action="map" id="${project.id}" params="[x: dimension.name, y:'release']"><span class="nowrap">${dimension}&bull;release</span></g:link>
                &emsp;
              </g:if>
            </g:each>
          </td>
        </tr>
      </g:each>
    </tbody>
  </table>
  <div class="pagination">
    <g:paginate total="${projectTotal}" prev="&laquo;" next="&raquo;" />
  </div>
</div>

<div>
  <mtm:dialogLink action="create" title="New Project"><button>New Project</button></mtm:dialogLink>
  <mtm:dialogLink action="projectFile" title="Import Project"><button>Import Project</button></mtm:dialogLink>
</div>

</body>

</html>
