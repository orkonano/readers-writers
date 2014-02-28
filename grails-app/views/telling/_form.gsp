<%@ page import="ar.com.orkodev.readerswriters.domain.Telling" %>



<div class="fieldcontain ${hasErrors(bean: tellingInstance, field: 'title', 'error')} required">
	<label for="title">
		<g:message code="telling.title.label" default="Title" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="title" required="" value="${tellingInstance?.title}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: tellingInstance, field: 'description', 'error')} required">
	<label for="description">
		<g:message code="telling.description.label" default="Description" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="description" required="" value="${tellingInstance?.description}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: tellingInstance, field: 'text', 'error')} required">
	<label for="text">
		<g:message code="telling.text.label" default="Text" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="text" required="" value="${tellingInstance?.text}"/>

</div>


<div class="fieldcontain ${hasErrors(bean: tellingInstance, field: 'narrativeGenre', 'error')} required">
	<label for="narrativeGenre">
		<g:message code="telling.narrativeGenre.label" default="Narrative Genre" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="narrativeGenre" name="narrativeGenre.id" from="${narrativesGenre}" optionKey="id" required="" value="${tellingInstance?.narrativeGenre?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: tellingInstance, field: 'tellingType', 'error')} required">
	<label for="tellingType">
		<g:message code="telling.tellingType.label" default="Telling Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="tellingType" name="tellingType.id" from="${tellingsType}" optionKey="id" required="" value="${tellingInstance?.tellingType?.id}" class="many-to-one"/>

</div>

