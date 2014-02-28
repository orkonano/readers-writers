
<%@ page import="ar.com.orkodev.readerswriters.domain.Telling" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'telling.label', default: 'Telling')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-telling" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-telling" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="title" title="${message(code: 'telling.title.label', default: 'Title')}" />
					
						<g:sortableColumn property="description" title="${message(code: 'telling.description.label', default: 'Description')}" />
					
						<th><g:message code="telling.narrativeGenre.label" default="Narrative Genre" /></th>
					
						<th><g:message code="telling.tellingType.label" default="Telling Type" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${tellingInstanceList}" status="i" var="tellingInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${tellingInstance.id}">${fieldValue(bean: tellingInstance, field: "title")}</g:link></td>
					
						<td>${fieldValue(bean: tellingInstance, field: "description")}</td>
					
						<td>${fieldValue(bean: tellingInstance, field: "narrativeGenre")}</td>
					
						<td>${fieldValue(bean: tellingInstance, field: "tellingType")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${tellingInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
