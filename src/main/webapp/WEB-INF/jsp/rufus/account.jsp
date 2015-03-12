<jsp:include page="../layout/menu.jsp"></jsp:include>
    <div id="page-wrapper">    
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
        <p>
        <div class="col-md-1">
            <button class="btn btn-primary" onclick="javascript:signout('${userSession.currentUser().name}')">Sign out</button>
        </div>
        <div class="col-md-1">

            <a href="http://auth.comcidis.lncc.br:3000/users/me"><button class="btn btn-success">Edit your profile</button></a>
        </div>

        </p>

    </div>
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