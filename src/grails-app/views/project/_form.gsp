<%@ page import="com.onetribeyoyo.mtm.domain.Project" %>



<div class="fieldcontain ${hasErrors(bean: project, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="project.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="name" cols="40" rows="5" maxlength="255" required="" value="${project?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: project, field: 'defaultFeature', 'error')} ">
	<label for="defaultFeature">
		<g:message code="project.defaultFeature.label" default="Default Feature" />

	</label>
	<g:select id="defaultFeature" name="defaultFeature.id" from="${com.onetribeyoyo.mtm.domain.Feature.list()}" optionKey="id" value="${project?.defaultFeature?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: project, field: 'defaultRelease', 'error')} ">
	<label for="defaultRelease">
		<g:message code="project.defaultRelease.label" default="Default Release" />

	</label>
	<g:select id="defaultRelease" name="defaultRelease.id" from="${com.onetribeyoyo.mtm.domain.Release.list()}" optionKey="id" value="${project?.defaultRelease?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: project, field: 'defaultStatus', 'error')} ">
	<label for="defaultStatus">
		<g:message code="project.defaultStatus.label" default="Default Status" />

	</label>
	<g:select id="defaultStatus" name="defaultStatus.id" from="${com.onetribeyoyo.mtm.domain.Status.list()}" optionKey="id" value="${project?.defaultStatus?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: project, field: 'defaultStrategy', 'error')} ">
	<label for="defaultStrategy">
		<g:message code="project.defaultStrategy.label" default="Default Strategy" />

	</label>
	<g:select id="defaultStrategy" name="defaultStrategy.id" from="${com.onetribeyoyo.mtm.domain.Status.list()}" optionKey="id" value="${project?.defaultStrategy?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: project, field: 'doneStatus', 'error')} ">
	<label for="doneStatus">
		<g:message code="project.doneStatus.label" default="Done Status" />

	</label>
	<g:select id="doneStatus" name="doneStatus.id" from="${com.onetribeyoyo.mtm.domain.Status.list()}" optionKey="id" value="${project?.doneStatus?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: project, field: 'features', 'error')} ">
	<label for="features">
		<g:message code="project.features.label" default="Features" />

	</label>

<ul class="one-to-many">
<g:each in="${project?.features?}" var="f">
    <li><g:link controller="feature" action="show" id="${f.id}">${f?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="feature" action="create" params="['project.id': project?.id]">${message(code: 'default.add.label', args: [message(code: 'feature.label', default: 'Feature')])}</g:link>
</li>
</ul>

</div>

<div class="fieldcontain ${hasErrors(bean: project, field: 'releases', 'error')} ">
	<label for="releases">
		<g:message code="project.releases.label" default="Releases" />

	</label>

<ul class="one-to-many">
<g:each in="${project?.releases?}" var="r">
    <li><g:link controller="release" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="release" action="create" params="['project.id': project?.id]">${message(code: 'default.add.label', args: [message(code: 'release.label', default: 'Release')])}</g:link>
</li>
</ul>

</div>

<div class="fieldcontain ${hasErrors(bean: project, field: 'statuses', 'error')} ">
	<label for="statuses">
		<g:message code="project.statuses.label" default="Statuses" />

	</label>

<ul class="one-to-many">
<g:each in="${project?.statuses?}" var="s">
    <li><g:link controller="status" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="status" action="create" params="['project.id': project?.id]">${message(code: 'default.add.label', args: [message(code: 'status.label', default: 'Status')])}</g:link>
</li>
</ul>

</div>

<div class="fieldcontain ${hasErrors(bean: project, field: 'stories', 'error')} ">
	<label for="stories">
		<g:message code="project.stories.label" default="Stories" />

	</label>

<ul class="one-to-many">
<g:each in="${project?.stories?}" var="s">
    <li><g:link controller="story" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="story" action="create" params="['project.id': project?.id]">${message(code: 'default.add.label', args: [message(code: 'story.label', default: 'Story')])}</g:link>
</li>
</ul>

</div>

<div class="fieldcontain ${hasErrors(bean: project, field: 'strategies', 'error')} ">
	<label for="strategies">
		<g:message code="project.strategies.label" default="Strategies" />

	</label>

<ul class="one-to-many">
<g:each in="${project?.strategies?}" var="s">
    <li><g:link controller="strategy" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="strategy" action="create" params="['project.id': project?.id]">${message(code: 'default.add.label', args: [message(code: 'strategy.label', default: 'Strategy')])}</g:link>
</li>
</ul>

</div>
