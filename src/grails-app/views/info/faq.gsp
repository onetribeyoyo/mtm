<!doctype html>
<html lang="en" class="no-js">

<head>
  <meta name="layout" content="main" />
  <title>Story Mapping Process</title>
  <r:require module="tabs" />
</head>

<body>

<h1>MTM FAQ</h1>

<mtm:tabs project="${project}" selectedTab="faq" includeFaqDetails="true" />

<div class="section float-left">

  <h2> What is the MTM? </h2>

  <p class="article">The MTM is a multi-dimensional story mapping tool.</p>

  <h2> Why Story Mapping? </h2>

  <div class="light-padding float-right">
    <p class="narrow hint float-right">To be fair, using story maps doesn't get us all the way there either. To truly
    capture the context it usually takes some sort of drawing of the system. Maybe a sketch of the domain model; a
    diagram or two showing how the application will be required to interact with other parts of the ecosystem; and so
    on.</p>
  </div>

  <p class="article">When prioritizing features and planning releases it is really useful to be able to see at a glance
  the entire context.  Long lists of stories and backlog items just don't cut it, but story maps (and the <g:link
  action="process">story mapping process</g:link>) are one solution to this problem.</p>

  <p class="article">In addition to showing the context, story maps provide a glimpse into the "completeness" of the
  project.  They let us see and share with sponsors what's done, where the problems are, and what's in need of further
  definition.  And, perhaps most importantly, story maps help us look ahead.  When we're doing release planning the maps
  help us see what's coming later so we can make judgement calls on what's best to tackle first &mdash; and we can
  easily do this both from a user's perspective and from a technical complexity/risk perspective.</p>

  <h2> Multi-dimensional Mapping </h2>

  <p class="article">Many development teams use Kanban boards to track work-in-progress, work-on-deck, etc.  This is a
  great approach and many tools are available that automate the low tech "post-its on the wall" technique.  With any
  project the complexity soon passes the point of being able to see it all at a glance.  While kanban boards are most
  often used to track what's going on NOW, story mappings are intended to help track what's happening (or going to
  happen) over a longer period of time.</p>

  <p class="article">Let's consider this scenario...</p>
  <ol>
    <li>You've started with a healthy list of possibilities and worked through your initial release plan.</li>
    <li>You've got a good start at what you think are some useful second and third releases.</li>
    <li>Suddenly your competition announces a new product.</li>
    <li>You'd love to drop everything for the second release and nail them to the wall with an awesome come back.</li>
    <li>But...</li>
    <li>It doesn't make business (or technical sense) to leave what you've been working on half done.</li>
  </ol>

  <p class="article">What should you do?</p>

  <p class="article">You could craw under a rock and fail.  You could run screaming into the
  land-of-complete-chaotic-releases.  Or you could step back, calmly and clearly consider your options and plan your
  next release or two based on your business' strategy.  I bet that's what we all really want to do every time something
  like this happens but it's not always so easy to do.</p>

  <p class="article">That's where the <em>multi-dimensional</em> part of MTM gets good.  You're not limited to release
  &amp; status.  You can add a dimension at any time and compare your stories by any way you need to.  Maybe it makes
  sense to consider product features by</p>
  <ol>
    <li>market focus (competitive, innovative, refreshing, ...)</li>
    <li>SWOT analysis (Strengths, Weaknesses, Opportunities, and Threats)</li>
    <li>user persona (beginners, experts, executives, ...)</li>
    <li>...</li>
  </ol>

  <p class="article">
  ...
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


  <h2> Why is it called &ldquo;MTM&rdquo;? </h2>

  <p class="article">MTM &equiv; Magic Task Machine.  It's called the magic task machine 'cause I like the way it
  sounds.</p>


  <h2> How did this all start? </h2>

  <p class="article">Sometime back in the 1900s I saw Edward Tufte speak.  Part of his presentation/show was the
  <a href="http://www.musanim.com/">music animation machine</a> and that got me thinking about better ways to show scope
  and progress.</p>

  <p class="article">The first version of MTM was based on strategic planning work facilitated by Arleta Little for the
  now defunct Dwelling In The Woods retreat center.  We were both on the board at the time and Arleta's process lead
  directly to the multi-dimensional nature of my story maps.</p>

  <p class="article">First use of the MTM was to plan a fireplace renovation project.  The project turned out to be
  bigger and more difficult than I envisioned so I decided to spend my time futzing with MTM instead.  Since then I've
  used MTM personally and professionally on a large number of projects (fireplace was finally completed in the summer of
  2014.)</p>


  <h2> Copyright and Licensing </h2>

  <p class="article"> Copyright 2003-2016 Andrew R. Miller. </p>

  <p class="article">MTM was open sourced in 2013.</p>

  <p class="article"> The MTM is licensed under the GNU GENERAL PUBLIC LICENSE, Version 3:
  <a href="http://www.gnu.org/licenses/gpl.html">http://www.gnu.org/licenses/gpl.html</a>.</p>


<!--
  <p class="article">In other words, story maps simplify sticking to the S.M.A.R.T. principle...</p>

  <ul>
    <li> <strong>S</strong>pecific,
    <li> <strong>M</strong>eaningful,
    <li> <strong>A</strong>greed to,
    <li> <strong>R</strong>ealistic, and
    <li> <strong>T</strong>ime phased.
  </ul>
-->

</div>

</body>

</html>
