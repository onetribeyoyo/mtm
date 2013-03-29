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
  <div id="dimension-${dimension.id}" class="float-left">
    <g:render contextPath="/dimension" template="sort" model="[dimension:dimension]" />
  </div>
</g:each>

<hr class="clear" />

<div class="section float-left">
  <h3>Additional Dimensions...</h3>
  <div>
    <g:if test="${!project.dimensionFor('assigned to')}">
      <p class="narrow hint">For mapping ${ProjectService.ASSIGNED_TO_DIMENSION_DATA.elements.join(", ")},&nbsp...</p>
      <p><g:link action="addAssignedToDimension" id="${project.id}"><button>Add <em>assigned to</em> Dimension</button></g:link></p>
      <hr />
    </g:if>
  </div>
  <div>
    <g:if test="${!project.dimensionFor('bugs')}">
      <p class="narrow hint">For mapping ${ProjectService.BUG_DIMENSION_DATA.elements.join(", ")},&nbsp...</p>
      <p><g:link action="addBugDimension" id="${project.id}"><button>Add <em>bug</em> Dimension</button></g:link></p>
      <hr />
    </g:if>
  </div>
  <div>
    <g:if test="${!project.dimensionFor('feature')}">
      <p class="narrow hint">For mapping ${ProjectService.FEATURE_DIMENSION_DATA.elements.join(", ")},&nbsp...</p>
      <p><g:link action="addFeatureDimension" id="${project.id}"><button>Add <em>feature</em> Dimension</button></g:link></p>
      <hr />
    </g:if>
  </div>
  <div>
    <g:if test="${!project.dimensionFor('release')}">
      <p class="narrow hint">For mapping ${ProjectService.RELEASE_DIMENSION_DATA.elements.join(", ")},&nbsp...</p>
      <p><g:link action="addReleaseDimension" id="${project.id}"><button>Add <em>release</em> Dimension</button></g:link></p>
      <hr />
    </g:if>
  </div>
  <div>
    <g:if test="${!project.dimensionFor('status')}">
      <p class="narrow hint">For mapping ${ProjectService.STATUS_DIMENSION_DATA.elements.collect { key, value -> key }.join(", ")},&nbsp...</p>
      <p><g:link action="addStatusDimension" id="${project.id}"><button>Add <em>status</em> Dimension</button></g:link></p>
      <p class="narrow hint">...</p>
      <hr />
    </g:if>
  </div>
  <div>
    <g:if test="${!project.dimensionFor('strategy')}">
      <p class="narrow hint">For mapping ${ProjectService.STRATEGY_DIMENSION_DATA.elements.join(", ")},&nbsp...</p>
      <p><g:link action="addStrategyDimension" id="${project.id}"><button>Add <em>strategy</em> Dimension</button></g:link></p>
      <hr />
    </g:if>
  </div>
  <div>
    <p class="narrow hint">Add and configure dimensions of your choice...</p>
    <p><mtm:dialogLink controller="dimension" action="create" id="${project.id}" title="New Dimension"><button>Add A Custom Dimension</button></mtm:dialogLink></p>
  </div>
</div>

</body>

</html>
