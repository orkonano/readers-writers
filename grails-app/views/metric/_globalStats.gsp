<div>
    <h3> ${tituloStat.capitalize()} stats: </h3>
    <table class="table table-hover">
        <thead>
        <tr>
            <th>Type</th>
            <th>Value</th>
        </tr>
        </thead>
        <tbody>
        <tbody>
        <tr>
            <td>Proccessor time</td>
            <td>${timeProcessor} ms</td>
        </tr>
        <tr>
            <td>Render Proccessor time</td>
            <td>${renderTimeProcessor} ms</td>
        </tr>
        <tr>
            <td>Total time</td>
            <td>${totalTimeProcessor} ms</td>
        </tr>
        <tr>
            <td>Total access</td>
            <td>${totalAccess}</td>
        </tr>
        <tr>
            <td>Avg</td>
            <td>${avg} ms</td>
        </tr>
        <tr>
            <td>Render Avg</td>
            <td>${renderAvg} ms</td>
        </tr>
        <tr>
            <td>Total Avg</td>
            <td>${totalAvg} ms</td>
        </tr>
        </tbody>
    </table>
</div>