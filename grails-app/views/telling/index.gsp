<%@ page import="ar.com.orkodev.readerswriters.domain.Telling" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main_logged">
		<g:set var="entityName" value="${message(code: 'telling.label', default: 'Telling')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
        <g:set var="titleSubMenu" value="${message(code: 'default.list.label',args: [entityName])}" />
        <g:set var="titleSubMenuCreate" value="${message(code: 'default.new.label',args: [entityName])}" />
        <g:render template="/layouts/submenu-nav-logged"
                  model="['submenu':[
                                    'name' : titleSubMenu,
                                    'items':[['controller': 'telling',
                                                'action': 'create',
                                                'name': titleSubMenuCreate
                                            ]]
                                    ]
                          ]"/>
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

                    <g:each in="${tellingList}" status="i" var="tellingInstance">
                        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                            <td><g:link resource="telling" method="GET" id="${tellingInstance.id}">${fieldValue(bean: tellingInstance, field: "title")}</g:link></td>
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
        <g:render template="/layouts/paginator" model="[total: tellingInstanceCount]"/>
    </body>
</html>
