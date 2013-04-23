<%@ page import="com.onetribeyoyo.mtm.services.ProjectService" %>

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

<h1>
  ${project.name}
</h1>
<project:tabs project="${project}" selectedTab="config___" />

<div id="project-${project.id}" class="section float-left">
  <div>
    <div class="card-actions float-right non-printing">
      <g:link action="export" id="${project.id}"><img src="${fam.icon(name: 'disk')}" title="export project as json" /></g:link>
      <mtm:dialogLink action="edit" id="${project.id}" title="Edit ${project}"><img src="${fam.icon(name: 'page_edit')}" title="edit project" /></mtm:dialogLink>
    </div>
    <h2>Project Data</h2>
  </div>
  <hr />
  <table>
    <tr> <th> Project Name </th>   <td> ${project?.name} </td> </tr>
    <tr> <th> Estimate Units </th> <td> ${project?.estimateUnits ?: "<span class='hint'>not specified</span>" } </td> </tr>
    <tr> <th> Show Estimates </th> <td> ${project.showEstimates ? 'Yes' : 'No'} </td> </tr>
    <tr> <th> Card Colour </th>    <td> ${project.colourDimension?.name?.capitalize() ?: "<span class='hint'>not specified</span>" } </td> </tr>
    <tr> <th> Highlight </th>      <td> ${project.highlightDimension?.name?.capitalize() ?: "<span class='hint'>not specified</span>" } </td> </tr>
    <tr> <th> Primary Axis </th>   <td> ${project.primaryAxis?.name?.capitalize() ?: "<span class='hint'>not specified</span>" } </td> </tr>
  </table>
  <hr class="non-printing" />
  <p class="narrow hint non-printing">
    If you're one of those people who just has to edit story lists with a spreadsheet you can
    <g:link controller="export" action="stories" id="${project.id}" params="[format: 'csv', extension: 'csv']">Export</g:link>
    and
    <mtm:dialogLink controller="import" action="storyFile" id="${project.id}" title="Import Stories">Import</mtm:dialogLink>
    story lists in CSV format.
  </p>
</div>

<project:maps project="${project}" />

<hr class="clear" />

<g:each var="dimension" in="${project.dimensions}">
  <div id="dimension-${dimension.id}" class="float-left">
    <g:render contextPath="/dimension" template="sort" model="[dimension:dimension]" />
  </div>
</g:each>

<hr class="clear non-printing" />

<div class="section float-left non-printing">
  <h3>Additional Dimensions...</h3>
  <hr />
  <p>
    <mtm:dialogLink controller="dimension" action="create" id="${project.id}" title="New Dimension"><button>Add A Custom Dimension</button></mtm:dialogLink>
  </p>
  <g:if test="${!project.dimensionFor('assigned to') || !project.dimensionFor('bugs') || !project.dimensionFor('feature') || !project.dimensionFor('release') || !project.dimensionFor('status') || !project.dimensionFor('strategy')}">
    <hr />
    <p class="hint">
      Or start with predifined dimensions and elements.
    </p>
    <g:if test="${!project.dimensionFor('assigned to')}">
      <p>
        <g:link action="addAssignedToDimension" id="${project.id}"><button>Add <em>assigned to</em> Dimension</button></g:link>
        <span class="narrow hint">for mapping ${ProjectService.ASSIGNED_TO_DIMENSION_DATA.elements.join(", ")},&nbsp...</span>
      </p>
    </g:if>
    <g:if test="${!project.dimensionFor('bugs')}">
      <p>
        <g:link action="addBugDimension" id="${project.id}"><button>Add <em>bug</em> Dimension</button></g:link>
        <span class="narrow hint">for mapping ${ProjectService.BUG_DIMENSION_DATA.elements.join(", ")},&nbsp...</span>
      </p>
    </g:if>
    <g:if test="${!project.dimensionFor('feature')}">
      <p>
        <g:link action="addFeatureDimension" id="${project.id}"><button>Add <em>feature</em> Dimension</button></g:link>
        <span class="narrow hint">for mapping ${ProjectService.FEATURE_DIMENSION_DATA.elements.join(", ")},&nbsp...</span>
      </p>
    </g:if>
    <g:if test="${!project.dimensionFor('release')}">
      <p>
        <g:link action="addReleaseDimension" id="${project.id}"><button>Add <em>release</em> Dimension</button></g:link>
        <span class="narrow hint">for mapping ${ProjectService.RELEASE_DIMENSION_DATA.elements.join(", ")},&nbsp...</span>
      </p>
    </g:if>
    <g:if test="${!project.dimensionFor('status')}">
      <p>
        <g:link action="addStatusDimension" id="${project.id}"><button>Add <em>status</em> Dimension</button></g:link>
        <span class="narrow hint">for mapping ${ProjectService.STATUS_DIMENSION_DATA.elements.collect { key, value -> key }.join(", ")},&nbsp...</span>
      </p>
    </g:if>
    <g:if test="${!project.dimensionFor('strategy')}">
      <p>
        <g:link action="addStrategyDimension" id="${project.id}"><button>Add <em>strategy</em> Dimension</button></g:link>
        <span class="narrow hint">for mapping ${ProjectService.STRATEGY_DIMENSION_DATA.elements.join(", ")},&nbsp...</span>
      </p>
    </g:if>
  </g:if>
</div>

</body>

</html>
