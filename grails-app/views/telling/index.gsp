<%@ page import="ar.com.orkodev.readerswriters.domain.Telling" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main_logged">
		<g:set var="entityName" value="${message(code: 'telling.label', default: 'Telling')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
        <div class="navbar navbar-default" role="navigation">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="#"><g:message code="default.list.label" args="[entityName]" /></a>
                </div>
            </div>
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li><a href="${createLink(action: 'create')}"><g:message code="default.new.label" args="[entityName]" /></a></li>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div>
        <div class="row">
            <div class="col-lg-12">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <g:sortableColumn property="title" title="${message(code: 'telling.title.label', default: 'Title')}" />
                            <g:sortableColumn property="description" title="${message(code: 'telling.description.label', default: 'Description')}" />
                            <th><g:message code="telling.narrativeGenre.label" default="Narrative Genre" /></th>
                            <th><g:message code="telling.tellingType.label" default="Telling Type" /></th>
                            <th><g:message code="telling.state.label" default="State" /></th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${tellingInstanceList}" status="i" var="tellingInstance">
                        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                            <td><g:link action="show" id="${tellingInstance.id}">${fieldValue(bean: tellingInstance, field: "title")}</g:link></td>
                            <td>${fieldValue(bean: tellingInstance, field: "description")}</td>
                            <td>${fieldValue(bean: tellingInstance, field: "narrativeGenre")}</td>
                            <td>${fieldValue(bean: tellingInstance, field: "tellingType")}</td>
                            <td>${tellingInstance.getStringState()}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-3"></div>
            <div class="col-lg-6">
                    <g:paginate total="${tellingInstanceCount}" action="index" class="pagination-sm" />
            </div>
            <div class="col-lg-3"></div>
        </div>
    </body>
</html>
