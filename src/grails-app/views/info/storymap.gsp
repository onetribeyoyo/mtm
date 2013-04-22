<!doctype html>
<html lang="en" class="no-js">

<head>
  <meta name="layout" content="main" />
  <title>Story Mapping Process</title>
  <r:require module="tabs" />
</head>

<body>

<h1>Story Maps</h1>
<g:render template="tabs" model="[selectedTab: 'storymaps']" />

<div class="section float-left">

  <h2> Why Story Mapping? </h2>

  <div class="light-padding float-right">
    <p class="narrow hint light-padding float-right">Using story maps doesn't get us all the way there either.  To
    truely capture the context it ussually takes some sort of drawing of the system.  Maybe a sketch of the domain
    model; a diagram or two showing how the application will be required to interact with other parts of the ecosystem;
    and so on.</p>
  </div>

  <p class="article">What I need is to see at a glance the context of the entire project and long lists of stories and
  backlog items don't cut it.  Story maps (and the story mapping process) are one solution to this problem.</p>

  <p class="article">In addition to showing the context, story maps provide a glimpse into the "completeness" of the
  project.  They let us see and share with sponsors what's done, where the problems are, and what's in need of further
  definition.</p>

  <p class="article">And, perhaps most importantly, story maps help us look ahead.  When we're doing iteration planning
  the maps help us see what's coming later so we can make judgement calls on what's best to tackle first &mdash; and we
  can easily do this both from a user's perspective and from a technical complexity/risk perspective.</p>

  <p class="article">In other words, story maps simplify sticking to the S.M.A.R.T. principle...</p>

  <ul>
    <li> <strong>S</strong>pecific,
    <li> <strong>M</strong>eaningful,
    <li> <strong>A</strong>greed to,
    <li> <strong>R</strong>ealistic, and
    <li> <strong>T</strong>ime phased.
  </ul>

  <h2> Multi-dimensional Mapping </h2>

  <p class="article">Many development teams use Kanban boards to track work-in-progress, work-on-deck, etc.  This is a
  great approach and many tools are available that automate the low tech "post-its on the wall" technique.  With any
  project the complexity soon passes the point of being able to see it all at a glance.  AND kanban boards are most
  often used to

  </p>

  <p class="article">
  ...
  </p>

  <p class="article">
  ...
  </p>

  <h2> Accuracy and Precision </h2>

  <p class="article">The goal is for absolute accuracy with a moderate level of precision.  At some point the maps will
  represent a summary of the features (both user and technical) of the system.  To this end, the maps need to be
  validated with the team -- at least the most relevant rows.  The rest remains fluid until the project is close to
  completion.</p>

  <p class="article">So by <em>absolute accuracy</em> I mean accurate at a point in time.  There will always be questions
  but when the entire team agrees to the map then the team is also aware of the questions.

  <p class="article">The status reflects the state of the project.  Some stories are in progress, some are done, some
  haven't been started.  Over time the maps show where construction is complete and where portions of the system have
  been tested/accepted.</p>

  <p class="article">Back to accuracy: The story maps will be accurate, but the precision (the detail) will be in the
  stories and tasks in jira.  So as we are planning iterations we'll be guided by the high-er level priorities shown in
  the story map and make sure we drive out the details in jira to represent the work we are doing to get everything
  done.</p>

</div>

</body>

</html>
