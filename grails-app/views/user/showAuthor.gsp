
<%@ page import="ar.com.orkodev.readerswriters.domain.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
    <g:javascript library="follow"/>
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
    <fieldset class="buttons" id="id-buttons-region">
        <g:if test="${isFollowed}">
            <g:link  data-object-id="${userInstance.id}" data-url="${createLink(controller: 'follower',action: 'leaveFollow')}" elementId="id-leavefollow-link" data-template-id-next-action="template-link-follow">Dejar de seguir</g:link>
        </g:if>
        <g:else>
            <g:link data-object-id="${userInstance.id}" data-url="${createLink(controller: 'follower',action: 'follow')}" elementId="id-follow-link" data-template-id-next-action="template-link-leave-follow">Seguir</g:link>
        </g:else>
    </fieldset>
</div>
<script id="template-link-leave-follow" type="text/html">
    <g:link data-object-id="${userInstance.id}" data-url="${createLink(controller: 'follower',action: 'leaveFollow')}" elementId="id-leavefollow-link" data-template-id-next-action="template-link-follow">Dejar de seguir</g:link>
</script>
<script id="template-link-follow" type="text/html">
    <g:link data-object-id="${userInstance.id}" data-url="${createLink(controller: 'follower',action: 'follow')}" elementId="id-follow-link" data-template-id-next-action="template-link-leave-follow">Seguir</g:link>
</script>
</body>
</html>
