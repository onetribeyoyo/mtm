<div class="card-actions nowrap non-printing">
  <simplemodal:link controller="story" action="edit" id="${story.id}"
        title="${story.summary}" width="narrow"><img src="${fam.icon(name: 'page_edit')}" title="edit" /></simplemodal:link>
  <simplemodal:confirm controller="story" action="delete" id="${story.id}"
          onSuccess="location.reload(true);"
          confirmLabel="Delete" confirmClass="delete"
          message="This can't be undone.  Are you sure you want to delete story ${story.id}?"
          title="Delete Story"><img src="${fam.icon(name: 'delete')}" title="delete" /></simplemodal:confirm>
</div>
