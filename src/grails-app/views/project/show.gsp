<!doctype html>
<html lang="en" class="no-js">

<head>
  <meta name="layout" content="main" />
  <title>Project Details</title>
  <r:require module="project-config" />
  <r:require module="card-map" />
  <r:require module="tabs" />
</head>

<body>

<h1> ${project.name} </h1>
<project:tabs project="${project}" selectedTab="config___" />
<div>
  <mtm:dialogLink controller="dimension" action="create" id="${project.id}" title="New Dimension"><button>New Dimension</button></mtm:dialogLink>
</div>

<g:each var="dimension" in="${project.dimensions}">
  <div id="dimension-${dimension.id}">
    <g:render contextPath="/dimension" template="sort" model="[dimension:dimension]" />
  </div>
</g:each>

</body>

</html>
