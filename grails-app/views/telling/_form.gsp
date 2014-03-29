<%@ page import="ar.com.orkodev.readerswriters.domain.Telling" %>

<div class="fieldcontain ${hasErrors(bean: tellingInstance, field: 'title', 'error')} required">
	<g:textField class="form-control" name="title" required=""
                 placeholder="${message(code: 'telling.title.label',default: 'Título')}"
                 value="${tellingInstance?.title}"/>
</div>
<br/>
<div class="fieldcontain ${hasErrors(bean: tellingInstance, field: 'description', 'error')} required">
	<g:textArea class="form-control" placeholder="${message(code: 'telling.description.label',default: 'Descripción')}"
                name="description" required="" value="${tellingInstance?.description}"/>
</div>
<br/>
<div class="fieldcontain ${hasErrors(bean: tellingInstance, field: 'narrativeGenre', 'error')} required">
    <label for="narrativeGenre">
        <g:message code="telling.narrativeGenre.label" default="Genero Narrativo" />
    </label>
    <g:select class="form-control" id="narrativeGenre" name="narrativeGenre.id" from="${narrativesGenre}" optionKey="id" required="" value="${tellingInstance?.narrativeGenre?.id}" />

</div>
<br/>
<div class="fieldcontain ${hasErrors(bean: tellingInstance, field: 'tellingType', 'error')} required">
    <label for="tellingType">
        <g:message code="telling.tellingType.label" default="Tipo de cuento" />
    </label>
    <g:select id="tellingType" name="tellingType.id" from="${tellingsType}" optionKey="id" required="" value="${tellingInstance?.tellingType?.id}" class="form-control"/>

</div>
<br/>
<div class="fieldcontain ${hasErrors(bean: tellingInstance, field: 'text', 'error')} required">
	<g:textArea class="form-control" name="text" required="" value="${tellingInstance?.text}"
                placeholder="${message(code: 'telling.text.label',default: 'Texto')}"/>
</div>
<br/>