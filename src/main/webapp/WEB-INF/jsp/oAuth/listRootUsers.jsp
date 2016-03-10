<h3>Admin users:</h3>
<table class="table table-hover">
    <thead>
    <tr>
        <td>Email</td><c:if test="${userSession.currentUser().admin}"><td>Action</td></c:if>
</tr>
</thead>
<tbody>
    <c:forEach items="${admins}" var="admin">
        <tr>
            <td>${admin}</td><c:if test="${userSession.currentUser().admin}"><td><a href="javascript:deleteRootUser('${admin}')"><i class="fa fa-trash-o fa-2x"></i></a></td>
        </c:if></tr>
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