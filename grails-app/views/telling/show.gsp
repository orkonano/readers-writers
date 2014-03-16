
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
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-telling" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list telling">
			
				<g:if test="${tellingInstance?.title}">
				<li class="fieldcontain">
					<span id="title-label" class="property-label"><g:message code="telling.title.label" default="Title" /></span>
					
						<span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${tellingInstance}" field="title"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${tellingInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="telling.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${tellingInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${tellingInstance?.text}">
				<li class="fieldcontain">
					<span id="text-label" class="property-label"><g:message code="telling.text.label" default="Text" /></span>
					
						<span class="property-value" aria-labelledby="text-label"><g:fieldValue bean="${tellingInstance}" field="text"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${tellingInstance?.narrativeGenre}">
				<li class="fieldcontain">
					<span id="narrativeGenre-label" class="property-label"><g:message code="telling.narrativeGenre.label" default="Narrative Genre" /></span>
					
						<span class="property-value" aria-labelledby="narrativeGenre-label"><g:link controller="narrativeGenre" action="show" id="${tellingInstance?.narrativeGenre?.id}">${tellingInstance?.narrativeGenre?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${tellingInstance?.tellingType}">
				<li class="fieldcontain">
					<span id="tellingType-label" class="property-label"><g:message code="telling.tellingType.label" default="Telling Type" /></span>
					
						<span class="property-value" aria-labelledby="tellingType-label"><g:link controller="tellingType" action="show" id="${tellingInstance?.tellingType?.id}">${tellingInstance?.tellingType?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${tellingInstance?.state}">
				<li class="fieldcontain">
					<span id="state-label" class="property-label"><g:message code="telling.state.label" default="State" /></span>
					
						<span class="property-value" aria-labelledby="state-label">${tellingInstance.getStringState()}</span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:tellingInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
                    <g:if test="${tellingInstance.isEditable()}">
					<g:link class="edit" action="edit" resource="${tellingInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    </g:if>
                    <g:if test="${tellingInstance.isEliminable()}">
					    <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                    </g:if>
                    <g:if test="${tellingInstance.isPublicable()}">
                        <g:link class="publish" action="publish" resource="${tellingInstance}"><g:message code="default.button.publish.label" default="Publish" /></g:link>
                    </g:if>

				</fieldset>
			</g:form>
		</div>
	</body>
</html>
