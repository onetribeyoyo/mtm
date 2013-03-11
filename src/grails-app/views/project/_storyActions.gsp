<div class="buttonset">

  <mtm:dialogLink controller="story" action="create" id="${project.id}" title="New Story">New Story</mtm:dialogLink>

  <mtm:dialogLink controller="import" id="${project.id}" title="Import Stories">Import Stories</mtm:dialogLink>

  <g:link controller="export" action="exportStories" id="${project.id}" params="[format: 'csv', extension: 'csv']">Export Stories</g:link>

</div>
