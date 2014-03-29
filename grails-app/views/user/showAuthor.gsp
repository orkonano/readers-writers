
<%@ page import="ar.com.orkodev.readerswriters.domain.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main_logged">
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
    <g:javascript library="follow"/>
</head>
<body>
    <div class="row">
        <div class="col-lg-3"></div>
        <div class="col-lg-6">
            <h1><g:fieldValue bean="${userInstance}" field="username"/>
                <span id="id-buttons-region">
                    <g:if test="${isFollowed}">
                        <g:link class="btn btn-warning btn-xs" data-object-id="${userInstance.id}" data-url="${createLink(controller: 'follower',action: 'leaveFollow')}" elementId="id-leavefollow-link" data-template-id-next-action="template-link-follow">Dejar de seguir</g:link>
                    </g:if>
                    <g:else>
                        <g:link class="btn btn-warning btn-xs" data-object-id="${userInstance.id}" data-url="${createLink(controller: 'follower',action: 'follow')}" elementId="id-follow-link" data-template-id-next-action="template-link-leave-follow">Seguir</g:link>
                    </g:else>
                </span>
            </h1>
        </div>
        <div class="col-lg-3"></div>
    </div>
    <br>
    <g:render template="/shared/list-telling"/>
</div>
<script id="template-link-leave-follow" type="text/html">
    <g:link class="btn btn-warning btn-xs" data-object-id="${userInstance.id}" data-url="${createLink(controller: 'follower',action: 'leaveFollow')}" elementId="id-leavefollow-link" data-template-id-next-action="template-link-follow">Dejar de seguir</g:link>
</script>
<script id="template-link-follow" type="text/html">
    <g:link class="btn btn-warning btn-xs" data-object-id="${userInstance.id}" data-url="${createLink(controller: 'follower',action: 'follow')}" elementId="id-follow-link" data-template-id-next-action="template-link-leave-follow">Seguir</g:link>
</script>
</body>
</html>
