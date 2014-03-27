<%@ page import="ar.com.orkodev.readerswriters.domain.User" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main_logged">
		<g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="navbar navbar-default" role="navigation">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="#"><g:message code="default.edit.label" args="[entityName]" /></a>
                </div>
             </div>
		</div>
    <div class="row">
        <div class="col-md-4">
        </div>
        <div class="col-md-4">
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${userInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${userInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form url="[resource:userInstance, action:'update']" method="PUT"  class="form-signin">
				<g:hiddenField name="version" value="${userInstance?.version}" />
					<g:render template="form"/>
					<g:actionSubmit class="btn btn-lg btn-primary btn-block"  action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
			</g:form>
		</div>
        <div class="col-md-4">
            </div>
    </div>

	</body>
</html>
