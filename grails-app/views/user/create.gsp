<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
		<title>Unite a nosotros</title>
        <g:javascript library="signin"/>
	</head>
	<body>
    <div class="row">
        <div class="col-md-6">
            <g:hasErrors bean="${userInstance}">
                <div class="alert alert-danger">
                <ul>
                    <g:eachError bean="${userInstance}" var="error">
                        <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                    </g:eachError>
                </ul>
                </div>
            </g:hasErrors>
            <g:form role="form" url="[resource:userInstance, action:'save']" useToken="true" class="form-signin">
                <g:if test='${flash.message}'>
                    <div class="alert alert-danger">${flash.message}</div>
                </g:if>
                <h2 class="form-signin-heading">Únete a nosotros</h2>
                <g:render template="form"/>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Únete</button>
            </g:form>
        </div>
        <div class="col-md-6">
            <facebookAuth:connect/>
        </div>

    </div>


	</body>
</html>
