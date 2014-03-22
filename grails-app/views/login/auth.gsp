<html>
<head>
	<meta name='layout' content='main'/>
	<title><g:message code="springSecurity.login.title"/></title>
    <g:javascript library="signin"/>
</head>

<body>
    <div class="row">
        <div class="col-md-6">
            <form role="form" action='${postUrl}' method='POST' id="loginForm" autocomplete='off' class="form-signin">
                <g:if test='${flash.message}'>
                    <div class="alert alert-danger">${flash.message}</div>
                </g:if>
                <h2 class="form-signin-heading"><g:message code="springSecurity.login.header"/></h2>
                <input type="email" class="form-control" placeholder="Enter user" name='j_username' required="" autofocus="">
                <input type="password" class="form-control" placeholder="Password" name="j_password" required="">
                <label class="checkbox">
                    <input type="checkbox" name='${rememberMeParameter}' id='remember_me' <g:if test='${hasCookie}'>checked='checked'</g:if>> <g:message code="springSecurity.login.remember.me.label"/>
                </label>
                <button class="btn btn-lg btn-primary btn-block" type="submit">${message(code: "springSecurity.login.button")}</button>
            </form>
        </div>
        <div class="col-md-6">
            <facebookAuth:connect/>
        </div>

    </div>



</body>
</html>