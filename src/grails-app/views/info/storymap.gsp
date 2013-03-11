<!doctype html>
<html lang="en" class="no-js">

<head>
  <meta name="layout" content="main" />
  <title>Story Mapping Process</title>
  <r:require module="tabs" />

  <style type="text/css">
     table { border: solid red 1px; margin: 0pt; padding: 0pt; }
     caption { font-weight: bold; background-color: #dddddd; }
     th { text-align: right; vertical-align: bottom; font-weight: normal; min-width: 3em; }
     td { text-align: left; vertical-align: top; min-width: 2em; }

     ul { }
     ol li { padding-bottom: 1em; }
     ul li { padding-bottom: 0em; }

     dt { font-weight: bold; }

     .a { color: #555555; background-color: lightgreen; padding: 0 2em 0 0.5em; }
     .b { color: #555555; background-color: lightblue; padding: 0 0.5em; }
     .c { color: black; background-color: lightgray; }
     .d { color: black; }

     .e { border-left: solid gray 4px; }

     .left { float: left; }
     .right { float: right; }
     .clear { clear: both; }
  </style>
</head>

<body>

<h1>The Story Mapping Process</h1>
<g:render template="tabs" model="[selectedTab: 'storymap']" />

<div class="left">
  <ol>
    <li> collect "big" features &mdash; the goals of the system
      <table>
        <tr>
          <td class="a e"> feature x </td>
          <td class="a e"> feature y </td>
          <td class="a e"> feature z </td>
        </tr>
      </table>

      goal = swim-lane = workflow = epic = user story

    <li> add detail stories
      <table>
        <tr>
          <th></th>
          <td class="a e" colspan="3"> feature x </td>
          <td class="a e" colspan="5"> feature y </td>
          <td class="a e" colspan="4"> feature z </td>
        </tr>
        <tr>
          <th> stories: </th>
          <td class="e b"> x1... </td>
          <td class="b"> x2... </td>
          <td class="b"> x3... </td>
          <td class="e b"> y1... </td>
          <td class="b"> y2... </td>
          <td class="b"> y3... </td>
          <td class="b"> y4... </td>
          <td class="b"> y5... </td>
          <td class="e b"> z1... </td>
          <td class="b"> z2... </td>
          <td class="b"> z3... </td>
          <td class="b"> z4... </td>
        </tr>
      </table>

      story = implementation story (of 1..* tasks)

  <li> order the cards as they will be used by end users<br />
        consider
      <ul>
        <li>who uses the feature,
        <li>how often the feature is used,
        <li>and how valuable the feature is
      </ul>

    <li> group by business priority &mdash; how critical to our business is it that someone actually
        use each feature?  is the feature innovative?  does it match something competing product?
        is it a refinement/freshening of existing features?

    <li> note logical breaks in workflow(s) &mdash; these may indicate possibilities for refactoring
        goals

    <li> mark the first system span (the smallest set of features necessary to be minimally useful
        in a business context)

      <table>
        <tr>
          <th></th>
          <td class="a e" colspan="3"> feature x </td>
          <td class="a e" colspan="5"> feature y </td>
          <td class="a e" colspan="4"> feature z </td>
        </tr>
        <tr>
          <th> r0.1&rarr; </th>
          <td class="e"> &nbsp; </td>
          <td class=""> &nbsp; </td>
          <td class=""> &nbsp; </td>

          <td class="e b"> y1... </td>
          <td class="b"> y2... </td>
          <td class=""> &nbsp; </td>
          <td class="b"> y4... </td>
          <td class=""> &nbsp; </td>

          <td class="e b"> z1... </td>
          <td class=""> &nbsp; </td>
          <td class=""> &nbsp; </td>
          <td class=""> &nbsp; </td>
        </tr>
        <tr>
          <th> unscheduled&rarr;</th>
          <td class="e b"> x1... </td>
          <td class="b"> x2... </td>
          <td class="b"> x3... </td>

          <td class="e"> &nbsp; </td>
          <td class=""> &nbsp; </td>
          <td class="b"> y3... </td>
          <td class=""> &nbsp; </td>
          <td class="b"> y5... </td>

          <td class=" e"> &nbsp; </td>
          <td class="b"> z2... </td>
          <td class="b"> z3... </td>
          <td class="b"> z4... </td>
        </tr>
      </table>

    <li> fill in build estimates <br />
        does first release still make sense?

    <li> slice into subsequent releases

      <table>
        <tr>
          <th></th>
          <td class="a e" colspan="3"> feature x </td>
          <td class="a e" colspan="5"> feature y </td>
          <td class="a e" colspan="4"> feature z </td>
        </tr>
        <tr>
          <th> r0.1&rarr; </th>
          <td class="e"> &nbsp; </td>
          <td class=""> &nbsp; </td>
          <td class=""> &nbsp; </td>
          <td class="e b"> y1... </td>
          <td class="b"> y2... </td>
          <td class=""> &nbsp; </td>
          <td class="b"> y4... </td>
          <td class=""> &nbsp; </td>
          <td class="e b"> z1... </td>
          <td class=""> &nbsp; </td>
          <td class=""> &nbsp; </td>
          <td class=""> &nbsp; </td>
        </tr>
        <tr>
          <th> r0.2&rarr; </th>
          <td class="e b"> x1... </td>
          <td class=""> &nbsp; </td>
          <td class=""> &nbsp; </td>
          <td class="e"> &nbsp; </td>
          <td class=""> &nbsp; </td>
          <td class="b"> y3... </td>
          <td class=""> &nbsp; </td>
          <td class="b"> y5... </td>
          <td class=" e"> &nbsp; </td>
          <td class="b"> z2... </td>
          <td class=""> &nbsp; </td>
          <td class=""> &nbsp; </td>
        </tr>
        <tr>
          <td> unscheduled&rarr; </td>
          <td class="e"> &nbsp; </td>
          <td class="b"> x2... </td>
          <td class="b"> x3... </td>
          <td class=" e"> &nbsp; </td>
          <td class=""> &nbsp; </td>
          <td class=""> &nbsp; </td>
          <td class=""> &nbsp; </td>
          <td class=""> &nbsp; </td>
          <td class=" e"> &nbsp; </td>
          <td class="">  </td>
          <td class="b"> z3... </td>
          <td class="b"> z4... </td>
        </tr>
      </table>

  </ol>
</div>

</body>

</html>
