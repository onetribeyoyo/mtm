<!doctype html>
<html lang="en" class="no-js">

<head>
  <meta name="layout" content="main" />
  <title>Project Map</title>
  <r:require module="card-map" />
  <r:require module="tabs" />
</head>

<body>

<h1>${project.name}</h1>

<project:tabs project="${project}" selectedTab="${selectedTab ?: xAxis?.name}" />

<div class="non-printing">
  <g:render template="storyActions" />
</div>

<project:cardGrid project="${project}" xAxis="${xAxis}" yAxis="${yAxis}" />

<hr class="clear non-printing" />

<project:switchDimensions project="${project}" xAxis="${xAxis}" yAxis="${yAxis}" />

</body>

</html>
