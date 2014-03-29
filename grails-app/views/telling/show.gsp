
<%@ page import="ar.com.orkodev.readerswriters.domain.Telling" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main_logged">
		<g:set var="entityName" value="${message(code: 'telling.label', default: 'Telling')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>


    <g:set var="titleSubMenu" value="${message(code: 'default.show.label',args: [entityName])}" />
    <g:set var="titleSubMenuIndex" value="${message(code: 'default.list.label',args: [entityName])}" />
    <g:set var="titleSubMenuCreate" value="${message(code: 'default.new.label',args: [entityName])}" />
    <g:set var="titleSubMenuEdit" value="${message(code: 'default.edit.label',args: [entityName])}" />
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
                              ],
                              ['controller':'telling',
                                      'action':'create',
                                      'name':titleSubMenuCreate
                              ]
                      ]
              ]
              ]"/>
    <div class="row">
        <div class="col-md-12">
            <h3>${tellingInstance.title}
                <g:form url="[resource:tellingInstance, action:'delete']" method="DELETE">
                        <g:if test="${tellingInstance.isEditable()}">
                            <a href="${createLink(action: 'edit',params: ['id': tellingInstance.id])}"
                               class="btn btn-primary btn-sm">Editar</a>
                        </g:if>
                        <g:if test="${tellingInstance.isPublicable()}">
                            <a href="${createLink(action: 'publish',params: ['id': tellingInstance.id])}"
                               class="btn btn-success btn-sm">Publicar</a>
                        </g:if>
                        <g:if test="${tellingInstance.isEliminable()}">
                            <g:actionSubmit class="btn btn-danger btn-sm" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                        </g:if>
                </g:form>
            </h3>

            <span class="label label-info">${tellingInstance?.tellingType?.encodeAsHTML()}</span>&nbsp;
            <span class="label label-primary">${tellingInstance?.narrativeGenre?.encodeAsHTML()}</span>
            <br/><br/>
            <div class="well well-lg">${tellingInstance.description}</div>
            <p class="lead">

                ${tellingInstance.text}
            </p>
        </div>
    </div>


	</body>
</html>
