
<%@ page import="ar.com.orkodev.readerswriters.domain.Telling" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main_logged">
		<g:set var="entityName" value="${message(code: 'telling.label', default: 'Telling')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
        <g:javascript library="showTelling"/>
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
            <h3><span id="id-title-telling">${tellingInstance.title}</span>
                    <g:if test="${tellingInstance.isEditable()}">
                        <a href="${createLink(action: 'edit',params: ['id': tellingInstance.id])}"
                           class="btn btn-primary btn-sm" id="id-editar-boton">Editar</a>
                    </g:if>
                    <g:if test="${tellingInstance.isPublicable()}">
                        <g:link class="btn btn-success btn-sm" elementId="id-publish-boton" resource="telling/publication" action="save" tellingId="${tellingInstance.id}">
                            Publicar
                        </g:link>
                    </g:if>
                    <g:if test="${tellingInstance.isEliminable()}">
                        <g:link class="btn btn-danger btn-sm" elementId="confirm-delete" data-confirm-action="delete" resource="telling" action="delete" id="${tellingInstance.id}">
                            Eliminar
                        </g:link>
                    </g:if>
            </h3>
            <br/>
            <span class="label label-info">${tellingInstance?.tellingType?.encodeAsHTML()}</span>&nbsp;
            <span class="label label-primary">${tellingInstance?.narrativeGenre?.encodeAsHTML()}</span>
            <br/><br/>
            <div class="well well-lg">${tellingInstance.description}</div>
            <p class="lead">

                ${tellingInstance.text}
            </p>
        </div>
    </div>

    <div id="id-modal-confirm-delete">

    </div>

    <g:render template="/shared/confirmModal"/>
	</body>
</html>
