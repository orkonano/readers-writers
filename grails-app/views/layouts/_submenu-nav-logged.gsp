<div class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">
                ${submenu.name}
            </a>
        </div>
    </div>
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav">
            <g:if test="${submenu.items}">
                <g:each in="${submenu.items}" var="itemMenu">
                <li>
                    <a href="${createLink(controller: itemMenu.controller, action: itemMenu.action)}">
                        ${itemMenu.name}
                    </a>
                </li>
                </g:each>
            </g:if>
        </ul>
    </div><!-- /.navbar-collapse -->
</div>