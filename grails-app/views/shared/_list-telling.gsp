<div class="row">
<g:each in="${tellingInstanceList}" status="i" var="tellingInstance">
    <g:if test="${ (i % 3) == 0 && i != 0}">
        </div>
        <div class="row">
    </g:if>
    <div class="col-sm-6 col-md-4">
        <div class="thumbnail">
            <div class="caption">
                <a class="btn btn-link" href="${createLink(controller: 'user', action: 'showAuthor', params: ['id': tellingInstance.author.id])}">
                    ${tellingInstance.author.username.encodeAsHTML()}
                </a>
                <span class="label label-info">${tellingInstance?.tellingType?.encodeAsHTML()}</span>&nbsp;
                <span class="label label-success">${tellingInstance?.narrativeGenre?.encodeAsHTML()}</span>
                <h3>${fieldValue(bean: tellingInstance, field: "title")}</h3>
                <p>${fieldValue(bean: tellingInstance, field: "description")}</p>
                <p><a href="${createLink(controller: 'telling', action: 'read', params: ['id': tellingInstance.id])}" class="btn btn-primary" role="button">Leer</a></p>
            </div>
        </div>
    </div>
</g:each>
</div>
<g:render template="/layouts/paginator" model="[total: tellingInstanceCount]"/>