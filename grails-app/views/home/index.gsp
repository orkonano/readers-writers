<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Bienvenidos a Readers - Writers</title>
	</head>
	<body>
        <!-- Jumbotron -->
        <div class="jumbotron">
            <h1>Readers & Writers Social Web!</h1>
            <p class="lead">Primera red social de latinoamérica que te permite publicar tus narrativos y que todas las personas lo lean.</p>
            <p>
                <a class="btn btn-lg btn-success" href="${createLink(controller: 'user', action: 'create')}" role="button">Únite hoy</a>
            </p>
        </div>
        <div><h3>Historias Recientes</h3></div>
        <g:render template="/shared/list-telling"/>

	</body>
</html>
