<!doctype html>
<html lang="en" class="no-js">

<head>
  <meta name="layout" content="main" />
  <title>Project List</title>
  <r:require module="tabs" />
</head>

<body>

<h1> Project List </h1>

<mtm:tabs project="${project}" selectedTab="projects___" />

<div class="section">

  <g:if test="${projectList}">

    <table class="list">
      <thead>
        <tr>
          <g:sortableColumn property="name" title="Project Name" />
          <th>&nbsp;</th>
          <th>&nbsp;</th>
        </tr>
      </thead>
      <tbody>
        <g:each var="project" in="${projectList}" status="i">
          <g:set var="release" value="${project.dimensionFor('release')}" />
          <g:set var="offsetIndex" value="${i + (params.offset ?: 0)}" />
          <tr class="${(((Math.ceil(((offsetIndex as int)+1)/3) as int) % 2) == 0) ? 'even' : 'odd'}">

            <th class="nowrap"> ${fieldValue(bean: project, field: "name")} </th>

            <g:set var="storyCount" value="${project.stories?.size() ?: 0}" />
            <td class="nowrap">${storyCount} ${(storyCount == 1) ? 'story' : 'stories'}</td>

            <td>
              <span class="actions">
                <g:link action="map" id="${project.id}"><img src="${fam.icon(name: 'map')}" title="map" /></g:link>
                <g:link action="show" id="${project.id}"><img src="${fam.icon(name: 'cog')}" title="configure" /></g:link>
              </span>
            </td>

          </tr>
        </g:each>
      </tbody>
    </table>
    <div class="pagination">
      <g:paginate total="${projectTotal}" prev="&laquo;" next="&raquo;" />
    </div>

  </g:if><g:else>
    <p class="hint">&laquo; no projects &raquo;</p>
  </g:else>

</div>

<div>
  <simplemodal:link action="create" title="New Project" width="narrow"><button>New Project</button></simplemodal:link>
  <simplemodal:link action="projectFile" title="Import Project" width="narrow"><button>Import Project</button></simplemodal:link>
</div>

</body>

</html>
