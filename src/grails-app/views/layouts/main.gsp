<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->

<g:render contextPath="/layouts" template="buildInfo" />

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

  <title><g:layoutTitle default="${grailsApplication.metadata['app.name']}" /></title>

  <link rel="shortcut icon"    href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
  <link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
  <link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}" sizes="114x114" >

  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <meta name="description"    content="tbd" />
  <meta name="keywords"       content="tbd" />
  <meta http-equiv="keywords" content="tbd" />
  <meta name="author"         content="amiller" />
  <meta name="author"         content="onetribeyoyo" />

  <meta http-equiv="Expires" content="__now__" />
  <meta http-equiv="Pragma" content="no-cache" />
  <meta http-equiv="Cache-Control" content="no-cache" />

  <r:script disposition="head">
    var URL_ROOT = '${request.contextPath}'
  </r:script>

  <r:require module="mtm" />

  <g:layoutHead/>
  <r:layoutResources />
</head>

<body>
  <g:render contextPath="/layouts" template="banner" />
  <g:render contextPath="/layouts" template="messages" />
  <g:layoutBody />
  <simplemodal:div />
  <r:layoutResources />
</body>

</html>
