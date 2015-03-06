<style>
    .table-overflow{
    max-height:400px;
    overflow-y:auto;
}
</style>
<div class="table-overflow">
<table class="table table-responsive">
    <thead>
        <tr>
            <th>Workflow</th><th>Date</th>
        </tr>
    </thead>
    <tbody>
       <c:forEach items="${workflows}" var="file">
           <tr><td><a href="javascript:loadWorkflow('${file.name}')" class="btn btn-outline"><span class="text-info">${file.name}</span></a></td></a><td>${file.date}</td></tr>
        </c:forEach> 
</tbody>
</table>
</div>