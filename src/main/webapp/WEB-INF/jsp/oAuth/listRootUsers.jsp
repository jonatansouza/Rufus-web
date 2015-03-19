<h3>Admin users:</h3>
<table class="table table-hover">
    <thead>
    <tr>
    <td>Email</td><td>Action</td>
</tr>
</thead>
<tbody>
    <c:forEach items="${admins}" var="admin">
        <tr>
            <td>${admin}</td><td><a href="javascript:deleteRootUser('${admin}')"><i class="fa fa-trash-o fa-2x"></i></a></td>
        </tr>
    </c:forEach>
</tbody>
</table>
<script>
    function deleteRootUser(name){
        bootbox.confirm("Are you sure you want delete \"" + name + "\" ?", function (result) {
            if (result) {
                {
                    window.location = "${pageContext.request.contextPath}/oauth/deleteRootUser/" + name;
                }
            }
        });
    }
</script>