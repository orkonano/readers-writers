
<%@ page import="ar.com.orkodev.readerswriters.domain.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="${content_layout}">
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
    <title>${userInstance.nombreAMostrar} en  ${grailsApplication.config.seo.title.appDisplay}
        <g:message code="default.show.label" args="[entityName]" /></title>
    <g:javascript library="follow"/>
</head>
<body>
<div class="row">
    <div class="col-lg-3"></div>
    <div class="col-lg-6">
        <h1><g:fieldValue bean="${userInstance}" field="nombreAMostrar"/>
            <span id="id-buttons-region">
                <g:if test="${isFollowed}">
                    <g:link class="btn btn-warning btn-xs" resource="author/follower" action="delete" authorId="${userInstance.id}" id="${follower.id}"
                            elementId="id-leavefollow-link" data-template-id-next-action="template-link-follow" data-method="DELETE">
                        Dejar de seguir
                    </g:link>
                </g:if>
                <g:else>
                    <g:link class="btn btn-warning btn-xs" resource="author/follower" action="save" authorId="${userInstance.id}" elementId="id-follow-link"
                            data-template-id-next-action="template-link-leave-follow">
                        Seguir
                    </g:link>
                </g:else>
            </span>
        </h1>
    </div>
    <div class="col-lg-3"></div>
</div>
<g:if test="${!isLogged}">
    <a href="#" >Leer más</a>
</g:if>
<br>
<g:render template="/shared/list-telling"/>


</div>
<script id="template-link-leave-follow" type="text/html">
    <a href="{{urlUnfollow}}" class="btn btn-warning btn-xs" id="id-leavefollow-link" data-template-id-next-action="template-link-follow" data-method="DELETE">
         Dejar de seguir
    </a>
</script>
<script id="template-link-follow" type="text/html">
    <a href="{{urlFollow}}" class="btn btn-warning btn-xs" id="id-follow-link" data-template-id-next-action="template-link-leave-follow">
         Seguir
    </a>
</script>
</body>
</html>
