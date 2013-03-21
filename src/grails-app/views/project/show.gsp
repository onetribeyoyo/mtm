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

<h1> ${project.name} </h1>
<project:tabs project="${project}" selectedTab="config___" />

<g:each var="dimension" in="${project.dimensions}">
  <div id="dimension-${dimension.id}">
    <g:render contextPath="/dimension" template="sort" model="[dimension:dimension]" />
  </div>
</g:each>

<g:if test="${!project.dimensionFor('assigned to')}">
  <g:render template="addDimension" model="[dimensionName:'assigned to', dimensionData: ProjectService.ASSIGNED_TO_DIMENSION_DATA, action:'addAssignedToDimension']" />
</g:if>

<g:if test="${!project.dimensionFor('feature')}">
  <g:render template="addDimension" model="[dimensionName:'feature', dimensionData: ProjectService.FEATURE_DIMENSION_DATA, action:'addFeatureDimension']" />
</g:if>

<g:if test="${!project.dimensionFor('release')}">
  <g:render template="addDimension" model="[dimensionName:'release', dimensionData: ProjectService.RELEASE_DIMENSION_DATA, action:'addReleaseDimension']" />
</g:if>

<g:if test="${!project.dimensionFor('status')}">
  <g:render template="addDimension" model="[dimensionName:'status', dimensionData: ProjectService.STATUS_DIMENSION_DATA, action:'addStatusDimension']" />
</g:if>

<g:if test="${!project.dimensionFor('strategy')}">
  <g:render template="addDimension" model="[dimensionName:'strategy', dimensionData: ProjectService.STRATEGY_DIMENSION_DATA, action:'addStrategyDimension']" />
</g:if>


<div class="section float-left">
  <div>
    <mtm:dialogLink controller="dimension" action="create" id="${project.id}" title="New Dimension"><button>Custom Dimension</button></mtm:dialogLink>
  </div>
  <p class="narrow hint">
    Add and configure a dimension of your choice.
  </p>
</div>

</body>

</html>
