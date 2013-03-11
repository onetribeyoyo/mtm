/*~ buttons ------------------------------------------------------------------------------------- */

.buttonset {
}

.buttonset a {
   -webkit-border-radius: 2px;
   -webkit-box-shadow: 0px 1px 3px rgba(0, 0, 0, 0.1);
   -webkit-user-select: none;
   background: -webkit-linear-gradient(#fafafa, #f4f4f4 40%, #e5e5e5);
   border: 1px solid #aaa;
   color: #444;
   cursor: pointer;
   font-size: inherit;
   min-width: 4em;
   padding: 0.25em 1em;
   font-family: sans-serif;
}
.buttonset a:hover {
   -webkit-box-shadow: 0px 1px 3px rgba(0, 0, 0, 0.2);
   background: #ebebeb -webkit-linear-gradient(#fefefe, #f8f8f8 40%, #e9e9e9);
   border-color: #999;
   color: #222;
}
.buttonset a:active {
   -webkit-box-shadow: inset 0px 1px 3px rgba(0, 0, 0, 0.2);
   background: #ebebeb -webkit-linear-gradient(#f4f4f4, #efefef 40%, #dcdcdc);
   color: #333;
}


button {
   background: #8b7b8b;
   border: 1px solid #bbb;
   border-radius: 1em;
   margin: 0.25em;
   padding: 0.35em 1em;
}
.add, .cancel, .delete, .edit {
   padding-left: 16px;
   background-position: left center;
   background-repeat: no-repeat;
}
.add {
   background-image: url("${fam.icon(name: 'add')}");
}
.cancel {
   background-image: url("${fam.icon(name: 'cancel')}");
}
.delete {
   background-image: url("${fam.icon(name: 'delete')}");
}
.edit {
   background-image: url("${fam.icon(name: 'page_edit')}");
}
