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
  <mtm:dialogLink class="non-printing" action="edit" id="${project.id}" title="Edit ${project}"><img src="${fam.icon(name: 'page_edit')}" /></mtm:dialogLink>
</h1>
<project:tabs project="${project}" selectedTab="config___" />

<div>
  <mtm:dialogLink controller="import" action="structureFile" id="${project.id}" title="Import Project Structure"><button>Import Structure</button></mtm:dialogLink>
  <mtm:dialogLink controller="import" action="storyFile" id="${project.id}" title="Import Stories"><button>Import Stories</button></mtm:dialogLink>
  <mtm:dialogLink controller="import" action="orderFile" id="${project.id}" title="Import Ordering"><button>Import Ordering</button></mtm:dialogLink>
|
  <g:link controller="export" action="structure" id="${project.id}" params="[format: 'csv', extension: 'csv']"><button>Export Structure</button></g:link>
  <g:link controller="export" action="stories" id="${project.id}" params="[format: 'csv', extension: 'csv']"><button>Export Stories</button></g:link>
  <g:link controller="export" action="order" id="${project.id}" params="[format: 'csv', extension: 'csv']"><button>Export Ordering</button></g:link>
</div>

<g:each var="dimension" in="${project.dimensions}">
  <div id="dimension-${dimension.id}" class="float-left">
    <g:render contextPath="/dimension" template="sort" model="[dimension:dimension]" />
  </div>
</g:each>

<hr class="clear" />

<div class="section float-left">
  <h3>Additional Dimensions...</h3>
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
