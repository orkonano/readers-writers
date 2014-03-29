<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main_logged">
		<g:set var="entityName" value="${message(code: 'telling.label', default: 'Telling')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
    <g:set var="titleSubMenu" value="${message(code: 'default.create.label',args: [entityName])}" />
    <g:set var="titleSubMenuIndex" value="${message(code: 'default.list.label',args: [entityName])}" />
    <g:render template="/layouts/submenu-nav-logged"
              model="['submenu':[
                      'name' : titleSubMenu,
                      'items':[
                              ['controller':'panel',
                                'action':'dashboard',
                                'name':'Home'
                              ],
                              ['controller':'telling',
                                      'action':'index',
                                      'name':titleSubMenuIndex
                              ]
                      ]
              ]
              ]"/>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
            <g:if test="${flash.invalidToken}">
                Don't click the button twice!
            </g:if>
			<g:hasErrors bean="${tellingInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${tellingInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
        <div class="row">
            <div class="col-md-12">
                <g:form url="[resource:tellingInstance, action:'save']" useToken="true" class="form-signin">
                        <g:render template="form"/>
                        <g:submitButton name="create" class="btn btn-lg btn-primary btn-block" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                </g:form>
            </div>
        </div>
	</body>
</html>
