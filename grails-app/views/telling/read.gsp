
<%@ page import="ar.com.orkodev.readerswriters.domain.Telling" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'telling.label', default: 'Telling')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<a href="#show-telling" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<div id="show-telling" class="content scaffold-show" role="main">
    <h1><g:message code="default.read.label" default="Leer {0}" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list telling">
        <li class="fieldcontain">
            <span id="title-label" class="property-label"><g:message code="telling.title.label" default="Title" /></span>
            <span class="property-value" aria-labelledby="title-label">${tellingInstance.title}</span>
        </li>
        <li class="fieldcontain">
            <span id="author-label" class="property-label"><g:message code="telling.author.label" default="Author" /></span>
            <span class="property-value" aria-labelledby="author-label">
                <g:link controller="user" action="showAuthor" id="${tellingInstance.author.id}">
                    ${tellingInstance.author.username}
                </g:link>
                </span>
        </li>
        <li class="fieldcontain">
            <span id="description-label" class="property-label"><g:message code="telling.description.label" default="Description" /></span>
            <span class="property-value" aria-labelledby="description-label">${tellingInstance.description}</span>
        </li>
        <li class="fieldcontain">
            <span id="narrativeGenre-label" class="property-label"><g:message code="telling.narrativeGenre.label" default="Narrative Genre" /></span>
            <span class="property-value" aria-labelledby="narrativeGenre-label">${tellingInstance.narrativeGenre.name}</span>
        </li>
        <li class="fieldcontain">
            <span id="tellingType-label" class="property-label"><g:message code="telling.tellingType.label" default="Telling Type" /></span>
            <span class="property-value" aria-labelledby="tellingType-label">${tellingInstance.tellingType.name}</span>
        </li>
        <li class="fieldcontain">
            <span id="text-label" class="property-label"><g:message code="telling.text.label" default="Text" /></span>

            <span class="property-value" aria-labelledby="text-label">${tellingInstance.text}</span>
        </li>
    </ol>
</div>
</body>
</html>
