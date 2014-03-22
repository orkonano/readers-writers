<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="Readers - Writers"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
        <g:javascript>
            var relativeUrl = '${createLink(url: '/')}';
        </g:javascript>
		<g:layoutHead/>
		<g:javascript library="application"/>		
		<r:layoutResources />
	</head>
	<body>

        <div class="container">
            <div class="masthead">
                <h3 class="text-muted">Readers & Writers</h3>
                <nav class="navbar navbar-default" role="navigation">
                    <sec:ifNotLoggedIn>
                        <a class="navbar-brand" href="${createLink(url: '/')}">Home</a>
                    </sec:ifNotLoggedIn>
                    <sec:ifLoggedIn>
                        <a class="navbar-brand" href="${createLink(controller: 'panel', action: 'dashboard')}">Home</a>
                    </sec:ifLoggedIn>
                    <sec:ifNotLoggedIn>
                        <a class="navbar-brand" href="${createLink(controller: 'login', action: 'auth')}">Login</a>
                        <a class="navbar-brand" href="${createLink(controller: 'user', action: 'create')}">Únete</a>
                    </sec:ifNotLoggedIn>
                    <sec:ifLoggedIn>
                        <a class="navbar-brand" href="${createLink(controller: 'user', action: 'edit')}">Editar Usuario</a>
                        <a class="navbar-brand" href="${createLink(controller: 'telling', action: 'index')}">Mis Narrativos</a>
                        <a class="navbar-brand" href="${createLink(controller: 'telling', action: 'list',params: '[init:true]')}">Narrativos</a>
                        <a class="navbar-brand" href="#" data-action="logout" data-url-logout="${createLink(controller:'logout')}" data-url-redirect="${createLinkTo(dir: '/')}">Salir</a>
                    </sec:ifLoggedIn>
               </nav>
            </div>
            <g:layoutBody/>
            <r:layoutResources />
            <!-- Site footer -->
            <div class="footer">
                <p>© Company 2014</p>
            </div>
        </div>
	</body>
</html>
