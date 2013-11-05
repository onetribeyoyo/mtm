<!doctype html>
<html lang="en" class="no-js">

<head>
  <meta name="layout" content="main" />
  <title>Story Mapping Process</title>
  <r:require module="tabs" />

  <style type="text/css">
     table { border: solid firebrick 1px; margin: 0pt; padding: 0pt; }
     caption { font-weight: bold; background-color: #dddddd; }
     th { text-align: right; vertical-align: bottom; font-weight: normal; min-width: 3em; }
     td { text-align: left; vertical-align: top; min-width: 2em; }

     ul { }
     ol li { padding-bottom: 1em; }
     ul li { padding-bottom: 0em; }

     dt { font-weight: bold; }

     .a, .b, .c, .d { margin: 4px; }
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

<tabs:render project="${project}" selectedTab="process" includeFaqDetails="true" />

<div class="section left">

  <h2>1. Collect the "big" features &mdash; the goals of the system</h2>

  <div class="heavy-padding">
    <table>
      <tr>
        <td class="a e"> feature x </td>
        <td class="a e"> feature y </td>
        <td class="a e"> feature z </td>
      </tr>
    </table>
    <p class="hint">goal = swim-lane = workflow = epic = user story</p>
  </div>

  <hr />

  <h2>2. Add "user" stories</h2>

  <div class="heavy-padding">
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
  </div>

  <hr />

  <h2>3. Order the cards as they will be used by end users</h2>

  <ul>
    <li>consider who uses the feature,
    <li>consider how often the feature is used,
    <li>and consider how valuable the feature is
  </ul>

  <hr />

  <h2>4. Group by business priority</h2>

  <ul>
    <li> how critical to our business is it that someone actually use each feature?
    <li> is the feature innovative?
    <li> does it match something competing product?
    <li> is it a refinement/freshening of existing features?
  </ul>

  <hr />

  <h2>5. Note logical breaks in workflow(s)</h2>

  <ul>
    <li>These may indicate possibilities for refactoring goals.
  </ul>

  <hr />

  <h2>6. Mark the first system span</h2>

  <div class="heavy-padding">
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
    <p>This is the smallest set of features necessary to be minimally useful in a business context.</p>
  </div>

  <hr />

  <h2>7. Fill in build estimates </h2>

  <ul>
    <li>Ask yourself: does first release still make sense?
  </ul>

  <hr />

  <h2>8. Slice into subsequent releases</h2>

  <div class="heavy-padding">
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
  </div>

</div>

</body>

</html>
