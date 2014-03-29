
<%@ page import="ar.com.orkodev.readerswriters.domain.Telling" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main_logged">
    <g:set var="entityName" value="${message(code: 'telling.label', default: 'Tellings')}" />
    <title>${entityName}</title>
</head>
<body>
    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4">
            <form class="form-inline" role="form" action="${createLink(action: 'list')}" method="post">
                <div class="form-group">
                    <g:select id="narrativeGenre" name="narrativeGenre.id" from="${narrativesGenre}" optionKey="id" class="form-control"/>
                </div>
                <div class="form-group">
                    <g:select id="tellingType" name="tellingType.id" from="${tellingsType}" optionKey="id" class="form-control"/>
                </div>
                <g:submitButton name="list" class="btn btn-primary" value="${message(code: 'default.button.list.label', default: 'Listar')}" />
            </form>
        </div>
        <div class="col-md-4"></div>
    </div>
    <br />
    <g:render template="/shared/list-telling"/>

</body>
</html>
