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
        <th>&nbsp;</th>
        <th width="100%">&nbsp;</th>
      </tr>
    </thead>
    <tbody>
      <g:each var="project" in="${projectInstanceList}" status="i">
        <g:set var="offsetIndex" value="${i + (params.offset ?: 0)}" />
        <tr class="${(((Math.ceil(((offsetIndex as int)+1)/3) as int) % 2) == 0) ? 'even' : 'odd'}">
          <th class="nowrap">${fieldValue(bean: project, field: "name")}</th>

          <g:set var="storyCount" value="${project.stories?.size() ?: 0}" />
          <td class="nowrap"><g:link action="show" id="${project.id}">${storyCount} ${(storyCount == 1) ? 'story' : 'stories'}</g:link>,</td>

          <td class="nowrap">
            ${project.dimensions.size()} dimensions:
            <g:each var="dimension" in="${project.dimensions}">${dimension}, </g:each>
          </td>
        </tr>
      </g:each>
    </tbody>
  </table>
  <div class="pagination">
    <g:paginate total="${projectInstanceTotal}" prev="&laquo;" next="&raquo;" />
  </div>
</div>

<div class="buttonset">
  <mtm:dialogLink controller="project" action="create" title="New Project">New Project</mtm:dialogLink>
</div>

</body>

</html>
