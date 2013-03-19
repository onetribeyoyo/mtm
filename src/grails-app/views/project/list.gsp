<!doctype html>
<html lang="en" class="no-js">

<head>
  <meta name="layout" content="main" />
  <title>Project List</title>
</head>

<body>

<h1> Project List </h1>

<div class="section">
  <table class="" width="100%">
    <thead>
      <tr>
        <g:sortableColumn property="name" title="Project Name" />
        <th>Stories</th>
        <th width="100%">Story Maps</th>
      </tr>
    </thead>
    <tbody>
      <g:each var="project" in="${projectInstanceList}" status="i">
        <g:set var="release" value="${project.dimensionFor('release')}" />
        <g:set var="offsetIndex" value="${i + (params.offset ?: 0)}" />
        <tr class="${(((Math.ceil(((offsetIndex as int)+1)/3) as int) % 2) == 0) ? 'even' : 'odd'}">

          <th class="nowrap"> <g:link action="show" id="${project.id}">${fieldValue(bean: project, field: "name")}</g:link> </th>

          <g:set var="storyCount" value="${project.stories?.size() ?: 0}" />
          <td class="nowrap">${storyCount} ${(storyCount == 1) ? 'story' : 'stories'}</td>

          <td class="nowrap">
            <g:each var="dimension" in="${project.dimensions}">
              <g:if test="${dimension != release}">
                <g:link action="map" id="${project.id}" params="[x: dimension.name, y:'release']">${dimension}&bull;release</g:link>
                &emsp;
              </g:if>
            </g:each>
          </td>
        </tr>
      </g:each>
    </tbody>
  </table>
  <div class="pagination">
    <g:paginate total="${projectInstanceTotal}" prev="&laquo;" next="&raquo;" />
  </div>
</div>

<div>
  <g:render template="/project/projectActions" />
</div>

</body>

</html>
