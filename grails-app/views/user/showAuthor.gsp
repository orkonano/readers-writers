
<%@ page import="ar.com.orkodev.readerswriters.domain.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<a href="#show-user" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>

<div id="show-user" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]" /></h1>
    <ol class="property-list user">
            <li class="fieldcontain">
                <span id="username-label" class="property-label"><g:message code="user.username.label" default="Username" /></span>
                <span class="property-value" aria-labelledby="username-label"><g:fieldValue bean="${userInstance}" field="username"/></span>
            </li>
            <g:if test="${userInstance?.firstname}">
                <li class="fieldcontain">
                    <span id="firstname-label" class="property-label"><g:message code="user.firstname.label" default="Firstname" /></span>
                    <span class="property-value" aria-labelledby="firstname-label"><g:fieldValue bean="${userInstance}" field="firstname"/></span>
                </li>
            </g:if>
            <g:if test="${userInstance?.lastname}">
                <li class="fieldcontain">
                    <span id="lastname-label" class="property-label"><g:message code="user.lastname.label" default="Lastname" /></span>
                    <span class="property-value" aria-labelledby="lastname-label"><g:fieldValue bean="${userInstance}" field="lastname"/></span>
                </li>
            </g:if>
    </ol>
</div>
</body>
</html>
