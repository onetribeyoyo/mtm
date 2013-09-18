<div class="card-actions nowrap non-printing">
  <simplemodal:link controller="story" action="edit" id="${story.id}" title="Edit Story ${story.id}"><img src="${fam.icon(name: 'page_edit')}" title="edit story" /></simplemodal:link>
  <simplemodal:link controller="story" action="confirmDelete" id="${story.id}" title="Delete Story ${story.id}"><img src="${fam.icon(name: 'delete')}" title="delete story" /></simplemodal:link>
</div>
