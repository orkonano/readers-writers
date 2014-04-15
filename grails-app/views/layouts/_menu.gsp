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
            <a class="navbar-brand"
               href="${createLink(controller: 'telling', action: 'list', params: '[init:true]')}">Narrativos</a>
            <sec:ifAllGranted roles="ROLE_ADM">
                <a class="navbar-brand"
                   href="${createLink(controller: 'hibernate')}">Hibernate Stats</a>
            </sec:ifAllGranted>
            <a class="navbar-brand" href="#" data-action="logout" data-url-logout="${createLink(controller: 'logout')}"
               data-url-redirect="${createLinkTo(dir: '/')}">Salir</a>
        </sec:ifLoggedIn>
    </nav>
</div>