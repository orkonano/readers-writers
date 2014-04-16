<%@ page import="ar.com.orkodev.readerswriters.domain.User" %>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'username', 'error')} required">
    <g:if test="${userInstance?.id}">
        <h3><span class="label label-default">
        <g:if test="${!userInstance.facebook}">
             ${userInstance.username}
         </g:if>
         <g:else>
             Cuenta asociada a facebook
         </g:else>
        </span></h3>
    </g:if>
    <g:else>
        <g:field class="form-control" placeholder="Enter username" type="email" name="username" required="" value="${userInstance?.username}"/>
    </g:else>
</div>
<g:if test="${!userInstance || !userInstance.id}">
<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', 'error')} required">
	<g:passwordField class="form-control" placeholder="Password" name="password" required="" value="${userInstance?.password}"/>
</div>
</g:if>
<g:if test="${userInstance?.id}">
    <div class="fieldcontain">
        <g:textField class="form-control" placeholder="${message(code: 'user.firstname', default: 'Nombre')}" name="firstname" value="${userInstance?.firstname}"/>
    </div>
    <div class="fieldcontain">
        <g:textField class="form-control" placeholder="${message(code: 'user.lastname', default: 'Apellido')}" name="lastname"  value="${userInstance?.lastname}"/>
    </div>
</g:if>