<%--
  Created by IntelliJ IDEA.
  User: orko
  Date: 4/19/14
  Time: 4:16 PM
--%>

<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main_logged">
    <title><g:message code="default.appStats.label"  default="App Metric Stats" /></title>
    <g:javascript library="application"/>
</head>
<body>
<h1>Controller Stats</h1>
<g:set var="globalMetric" value="${controllerMetric.controllerMetric}"/>
<div>
    <h3> ${globalMetric.name} stats: </h3>
    <table>
        <thead>
        <tr>
            <th>Type</th>
            <th>Value</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>Total time</td>
            <td>${globalMetric.totalTimeProcessor} ms</td>
        </tr>
        <tr>
            <td>Total access</td>
            <td>${globalMetric.totalAccess}</td>
        </tr>
        <tr>
            <td>Avg</td>
            <td>${globalMetric.getAvg()} ms</td>
        </tr>
        </tbody>
    </table>
</div>
<div>
    <h3>Actions Stats</h3>
    <table>
        <thead>
        <tr>
            <th>Controller</th>
            <th>Total Time</th>
            <th>Total Access</th>
            <th>Avg ms</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${controllerMetric.getAllActionMetric()}" var="actionMetric">
            <tr>
                <td>${actionMetric.name}</td>
                <td>${actionMetric.totalTimeProcessor}</td>
                <td>${actionMetric.totalAccess}</td>
                <td>${actionMetric.getAvg()} ms</td>
            </tr>
        </g:each>
        </tbody>
    </table>
</div>
</body>
</html>