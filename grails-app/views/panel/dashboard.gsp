<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title><g:message code="default.panel.label"  default="Dashboard" /></title>
    <g:javascript library="dashboard"/>
</head>
<body>
    <div>
        <h1>Dashboard</h1>
    </div>

    <div>
        <div class="dashboard-title">
            <h2>Historias escritas</h2>
        </div>
        <div class="dashboard-body historias-propias">
            <ul id="historias-propias-id">

            </ul>
        </div>
    </div>

    <div>
        <div class="dashboard-title">
            <h2>Historias seguidas</h2>
        </div>
        <div class="dashboard-body historias-ajenas">
            <ul id="historias-like-id">

            </ul>
        </div>
    </div>

    <div>
        <div class="dashboard-title">
            <h2>Autores seguidas</h2>
        </div>
        <div class="dashboard-body historias-autores">
            <ul id="autores-seguidos-id">

            </ul>
        </div>
    </div>
</div>
<script id="template-telling-like" tytpe="text/html">
    <li>
    <a href="${createLink(controller: 'telling',action: 'read')}/{{telling.id}}">{{telling.title}}</a>&nbsp;-&nbsp;
    <a href="${createLink(controller: 'user',action: 'showAuthor')}/{{telling.author.id}}">{{telling.author.name}}</a>
    </li>
</script>
<script id="template-author-followed" tytpe="text/html">
    <li>
    <a href="${createLink(controller: 'user',action: 'showAuthor')}/{{author.id}}">{{author.name}}</a>
    </li>
</script>
</body>


</html>
