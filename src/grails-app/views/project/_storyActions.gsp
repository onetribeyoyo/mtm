<mtm:dialogLink controller="story" action="create" id="${project.id}" title="New Story"><button>New Story</button></mtm:dialogLink>
<mtm:dialogLink controller="import" id="${project.id}" title="Import Stories"><button>Import Stories</button></mtm:dialogLink>
<g:link controller="export" action="exportStories" id="${project.id}" params="[format: 'csv', extension: 'csv']"><button>Export Stories</button></g:link>
