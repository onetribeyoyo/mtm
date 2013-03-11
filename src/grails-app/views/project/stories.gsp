<%@ page import="com.onetribeyoyo.mtm.Project" %>
<html>

<head>
  <meta name="layout" content="main" />
  <title>Story List</title>
  <r:require module="tabs" />
  <r:require module="export"/>
</head>

<body>

<h1> ${project.name} </h1>
<project:tabs project="${project}" selectedTab="stories" />
<g:render template="storyActions" />

<div class="storylist">
  <table>
    <tr>
      <g:sortableColumn property="release" title="Release" />
      <g:sortableColumn property="feature" title="Feature" />
      <g:sortableColumn property="id" title="ID" />
      <td></td>
      <g:sortableColumn property="name" title="Name" />
      <g:sortableColumn property="status" title="Status" />
      <!-- <th> details </th> -->
    </tr>

    <g:each var="story" in="${project.stories}">
      <tr>
        <td> ${story.release?.name} </td>
        <td> ${story.feature?.name} </td>
        <td> ${story.id} </td>
        <td> <g:remoteLink controller="story" action="edit" id="${story.id}" update="story-edit-dialog" after="\$('#story-edit-dialog').dialog('open')"><img src="${fam.icon(name: 'page_edit')}" /></g:remoteLink> </td>
        <td> ${story.summary} </td>
        <td> ${story.status} </td>
        <!-- <td> ${story.description} </td> -->
      </tr>
     </g:each>
  </table>
</div>

<export:formats controller="export" action="exportStories" params="[id: project.id]" />

</body>

</html>
