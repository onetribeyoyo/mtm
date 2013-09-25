<!doctype html>
<html lang="en" class="no-js">

<head>
  <meta name="layout" content="main" />
  <title>Project Map</title>
  <r:require module="card-map" />
  <r:require module="tabs" />
  <r:require module="dropmenu" />
</head>

<body>

<h1>${project.name}</h1>

<tabs:render project="${project}" selectedTab="map___" />

<div class="non-printing">
  <g:render template="storyActions" />
</div>

<storymap:grid project="${project}" xAxis="${xAxis}" yAxis="${yAxis}" />

</body>

</html>
