<%@ page import="ar.com.orkodev.readerswriters.domain.User" %>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'username', 'error')} required">
	<label for="username">
		<g:message code="user.username.label" default="Username" />
        <g:if test="${!userInstance?.id}">
		    <span class="required-indicator">*</span>
         </g:if>
	</label>
    <g:if test="${userInstance?.id}">
        <g:if test="${!userInstance.facebookUser}">
            ${userInstance.username}
         </g:if>
         <g:else>
             Cuenta asociada a facebook
         </g:else>
    </g:if>
    <g:else>
        <g:field type="email" name="username" required="" value="${userInstance?.username}"/>
    </g:else>

</div>
<g:if test="${!userInstance || !userInstance.id}">
<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', 'error')} required">
	<label for="password">
		<g:message code="user.password.label" default="Password" />
		<span class="required-indicator">*</span>
	</label>
	<g:passwordField name="password" required="" value="${userInstance?.password}"/>

</div>
</g:if>
<g:if test="${userInstance?.id}">
    <div class="fieldcontain">
        <label for="firstname">
            <g:message code="user.firstname" default="Nombre" />
        </label>
        <g:textField name="firstname" value="${userInstance?.firstname}"/>
    </div>
    <div class="fieldcontain">
        <label for="lastname">
            <g:message code="user.lastname" default="Apellido" />
        </label>
        <g:textField name="lastname"  value="${userInstance?.lastname}"/>
    </div>
</g:if>