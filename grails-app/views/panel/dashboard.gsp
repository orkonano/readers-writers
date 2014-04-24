<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main_logged">
    <title><g:message code="default.panel.label"  default="Dashboard" /></title>
    <g:javascript library="dashboard"/>
</head>
<body>
    <g:set var="NAME_CACHE_1_" value="DASH_1"/>
    <g:set var="NAME_CACHE_2_" value="DASH_2"/>
    <g:set var="NAME_CACHE_3_" value="DASH_3"/>
    <div class="row">
        <cache:render key="${NAME_CACHE_1_+currentUser.id}" template="common-dashboard-content"
         model="['columnNumber':6,'panelTitle':'Historias escritas','panelBodyClass':'historias-propias','panelBodyListId':'own-telling-id']"/>
        <cache:render key="${NAME_CACHE_2_+currentUser.id}" template="common-dashboard-content"
           model="['columnNumber':6,'panelTitle':'Historias seguidas','panelBodyClass':'historias-ajenas','panelBodyListId':'historias-like-id']"/>
    </div>
     <div class="row">
         <cache:render key="${NAME_CACHE_3_+currentUser.id}" template="common-dashboard-content"
                   model="['columnNumber':12,'panelTitle':'Autores seguidos','panelBodyClass':'historias-autores','panelBodyListId':'autores-seguidos-id']"/>
      </div>
</div>
<script id="template-telling-like" tytpe="text/html">
    <li class="list-group-item">
    <a href="${createLink(controller: 'telling',action: 'read')}/{{telling.id}}">{{telling.title}}</a>&nbsp;-&nbsp;
    <a href="${createLink(controller: 'user',action: 'showAuthor')}/{{telling.author.id}}">{{telling.author.name}}</a>
    </li>
</script>
<script id="template-own-telling" tytpe="text/html">
    <li class="list-group-item">
    <a href="${createLink(controller: 'telling',action: 'show')}/{{telling.id}}">{{telling.title}}</a>
    </li>
</script>
<script id="template-author-followed" tytpe="text/html">
    <li class="list-group-item">
    <a href="${createLink(controller: 'user',action: 'showAuthor')}/{{author.id}}">{{author.name}}</a>
    </li>
</script>
</body>


</html>
