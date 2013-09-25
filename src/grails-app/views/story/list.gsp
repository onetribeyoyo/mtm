<!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML//EN">
<html>
<head>
<title></title>
</head>

<body>

<h1>${storyTotal} Stories</h1>

<ul>
  <g:each var="story" in="${stories}">
    <li>${story}</li>
  </g:each>
</ul>

</body>
</html>
