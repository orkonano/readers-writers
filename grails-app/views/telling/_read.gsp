<%@ page import="ar.com.orkodev.readerswriters.domain.Telling" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="${content_layout}">
    <g:set var="entityName" value="${message(code: 'telling.label', default: 'Telling')}" />
    <sec:ifNotLoggedIn>
        <meta name="layout" content="main">
    </sec:ifNotLoggedIn>
    <title>${tellingInstance.title} de ${tellingInstance.author.nombreAMostrar} en
        ${grailsApplication.config.seo.title.appDisplay}</title>
    <g:javascript library="like"/>
</head>
<body>
<div class="row">
    <div class="col-md-12">
        <h3>${tellingInstance.title}&nbsp;&nbsp;
            <span id="id-buttons-region">
                <g:if test="${isLike}">
                    <g:link class="btn btn-warning btn-xs" resource="telling/tellingLike" action="delete" tellingId="${tellingInstance.id}" id="${tellingLike.id}"
                            elementId="id-stop-like-link" data-template-id-next-action="template-link-like">
                        Ya no me gusta
                    </g:link>
                </g:if>
                <g:else>
                    <g:link class="btn btn-warning btn-xs" resource="telling/tellingLike" action="save" tellingId="${tellingInstance.id}" elementId="id-like-link" data-template-id-next-action="template-link-stop-like">
                        Me gusta
                    </g:link>
                </g:else>
            </span></h3>
        <a class="btn btn-link" href="${createLink(controller: 'user', action: 'show', params: ['id': tellingInstance.author.id])}">
            ${tellingInstance.author.username.encodeAsHTML()}
        </a>
        &nbsp;
        <span class="label label-info">${tellingInstance?.tellingType?.encodeAsHTML()}</span>&nbsp;
        <span class="label label-success">${tellingInstance?.narrativeGenre?.encodeAsHTML()}</span>

        <br/>
        <br/>
        <div class="well well-lg">${tellingInstance.description}</div>
        <p class="lead">
            ${tellingInstance.text}
        </p>
    </div>
</div>

<script id="template-link-stop-like" type="text/html">
     <a href="{{urlunlike}}" class="btn btn-warning btn-xs" id="id-stop-like-link" data-template-id-next-action="template-link-like" data-method="DELETE">
         Ya no me gusta
    </a>
</script>
<script id="template-link-like" type="text/html">
     <a href="{{urlLike}}" class="btn btn-warning btn-xs" id="id-like-link" data-template-id-next-action="template-link-stop-like">
         Me gusta
    </a>
</script>

</body>
</html>
