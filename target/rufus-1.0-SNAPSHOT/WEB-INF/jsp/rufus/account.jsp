<jsp:include page="../layout/menu.jsp"></jsp:include>
    <div class="container">

        <table class="table table-hover">
            <thead>
                <tr>
                    <th>Name</th><th>Email</th><th>Id</th>
                </tr>
            </thead>
            <tbody>
                <tr class="info">
                    <td>${userSession.currentUser().name}</td><td>${userSession.currentUser().email}</td><td>${userSession.currentUser().id}</td>
            </tr>
        </tbody>
    </table>

    <br>
    <button class="btn btn-primary" onclick="javascript:signout('${userSession.currentUser().name}')">Sign out</button>


</div>
<script src="${pageContext.request.contextPath}/assets/js/jquery-2.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/bootbox.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/side-bar-admin.js"></script>
<script type="text/javascript">
        function signout(user) {
            bootbox.confirm("Are you sure?", function (result) {
                location.href = "/rufus/oauth/logout"
            });
        }
</script>