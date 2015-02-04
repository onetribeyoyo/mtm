<%@ page import="com.onetribeyoyo.mtm.project.DimensionData" %>

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

<h1>${project.name}</h1>

<mtm:tabs project="${project}" selectedTab="config___" />

<div id="project-${project.id}" class="section float-left">
  <div>
    <div class="card-actions float-right non-printing">
      <g:link action="export" id="${project.id}"><img src="${fam.icon(name: 'disk')}" title="export project as json" /></g:link>
      <simplemodal:link action="edit" id="${project.id}" title="Edit ${project}" width="narrow"><img src="${fam.icon(name: 'page_edit')}" title="edit project" /></simplemodal:link>
      <simplemodal:confirm controller="project" action="delete" id="${project.id}"
            confirmLabel="Delete" confirmClass="delete"
            message="Are you sure you want to delete this project?  You will delete all of the projecy's stories and this action cannot be reversed."
            title="Delete '${project.name}'"><img src="${fam.icon(name: 'delete')}" title="delete project" /></simplemodal:confirm>
    </div>
    <h2>Project Data</h2>
  </div>
  <hr />
  <table>
    <tr> <th> Project Name </th>   <td> ${project?.name} </td> </tr>
    <tr> <th colspan="2"> ${project?.showEstimates ? "Show" : "Hide"} Estimates </th> </tr>
    <tr> <th> Estimate Units </th> <td> <mtm:format value="${project?.estimateUnits}" /> </td> </tr>
    <tr> <th colspan="2"> ${project?.showExtraDimensions ? "Show" : "Hide"} Extra Dimensions </th> </tr>
    <tr> <th> Card Colour </th>    <td> <mtm:format value="${project.colourDimension?.name?.capitalize()}" /> </td> </tr>
    <tr> <th> Highlight </th>      <td> <mtm:format value="${project.highlightDimension?.name?.capitalize()}" /> </td> </tr>
    <tr> <th> Primary X Axis </th> <td> <mtm:format value="${project.primaryXAxis?.name?.capitalize()}" /> </td> </tr>
    <tr> <th> Primary Y Axis </th> <td> <mtm:format value="${project.primaryYAxis?.name?.capitalize()}" /> </td> </tr>
  </table>
  <hr class="non-printing" />
  <p class="narrow hint non-printing">
    If you're one of those people who just has to edit story lists with a spreadsheet you can
    <g:link controller="export" action="stories" id="${project.id}" params="[format: 'csv', extension: 'csv']">Export</g:link>
    and
    <simplemodal:link controller="import" action="storyFile" id="${project.id}" title="Import Stories">Import</simplemodal:link>
    story lists in CSV format.
  </p>
</div>

<mtm:mapList project="${project}" xAxis="${xAxis}" yAxis="${yAxis}" />

<hr class="clear" />

<h1>Dimensions</h1>

<g:each var="dimension" in="${project.dimensions}">
  <div id="dimension-${dimension.id}" class="float-left">
    <g:render contextPath="/dimension" template="show" model="[dimension:dimension]" />
  </div>
</g:each>

<hr class="clear non-printing" />

<div class="section float-left non-printing">
  <h3>Additional Dimensions...</h3>
  <hr />
  <p>
    <simplemodal:link controller="dimension" action="create" id="${project.id}"
          title="New Dimension" width="narrow"><button>Add A Custom Dimension</button></simplemodal:link>
  </p>
  <g:if test="${!project.dimensionFor('assigned to') || !project.dimensionFor('bugs') || !project.dimensionFor('feature') || !project.dimensionFor('release') || !project.dimensionFor('status') || !project.dimensionFor('strategy')}">
    <hr />
    <p class="hint">
      Or start with predifined dimensions and elements.
    </p>
    <g:if test="${!project.dimensionFor('assigned to')}">
      <p>
        <g:link action="addAssignedToDimension" id="${project.id}"><button>Add <em>assigned to</em> Dimension</button></g:link>
        <span class="narrow hint">for mapping ${DimensionData.ASSIGNED_TO.elements.join(", ")},&nbsp...</span>
      </p>
    </g:if>
    <g:if test="${!project.dimensionFor('bugs')}">
      <p>
        <g:link action="addBugDimension" id="${project.id}"><button>Add <em>bug</em> Dimension</button></g:link>
        <span class="narrow hint">for mapping ${DimensionData.BUG.elements.join(", ")},&nbsp...</span>
      </p>
    </g:if>
    <g:if test="${!project.dimensionFor('feature')}">
      <p>
        <g:link action="addFeatureDimension" id="${project.id}"><button>Add <em>feature</em> Dimension</button></g:link>
        <span class="narrow hint">for mapping ${DimensionData.FEATURE.elements.join(", ")},&nbsp...</span>
      </p>
    </g:if>
    <g:if test="${!project.dimensionFor('release')}">
      <p>
        <g:link action="addReleaseDimension" id="${project.id}"><button>Add <em>release</em> Dimension</button></g:link>
        <span class="narrow hint">for mapping ${DimensionData.RELEASE.elements.join(", ")},&nbsp...</span>
      </p>
    </g:if>
    <g:if test="${!project.dimensionFor('status')}">
      <p>
        <g:link action="addStatusDimension" id="${project.id}"><button>Add <em>status</em> Dimension</button></g:link>
        <span class="narrow hint">for mapping ${DimensionData.STATUS.elements.collect { key, value -> key }.join(", ")},&nbsp...</span>
      </p>
    </g:if>
    <g:if test="${!project.dimensionFor('strategy')}">
      <p>
        <g:link action="addStrategyDimension" id="${project.id}"><button>Add <em>strategy</em> Dimension</button></g:link>
        <span class="narrow hint">for mapping ${DimensionData.STRATEGY.elements.join(", ")},&nbsp...</span>
      </p>
    </g:if>
  </g:if>
</div>

</body>

</html>
