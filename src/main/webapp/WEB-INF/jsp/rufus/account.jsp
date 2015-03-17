<jsp:include page="../layout/menu.jsp"></jsp:include>
    <div id="page-wrapper">    
        <div class="container">

            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Name</th><th>Email</th><th>Admin</th><th>Id</th>
                    </tr>
                </thead>
                <tbody>
                    <tr class="info">
                        <td>${userSession.currentUser().name}</td><td>${userSession.currentUser().email}</td><td>
                        <c:if test="${userSession.currentUser().admin}">Yes</c:if>
                        <c:if test="${not userSession.currentUser().admin}">No</c:if>
                    </td><td>${userSession.currentUser().id}</td>
                </tr>
            </tbody>
        </table>

        <br>
        <div class="row">
        <p>
        <div class="col-lg-2">
            <button class="btn btn-primary" onclick="javascript:signout('${userSession.currentUser().name}')">Sign out</button>
        </div>
        <div class="col-lg-2">

            <a href="http://auth.comcidis.lncc.br:3000/users/me"><button class="btn btn-success">Edit your profile</button></a>
        </div>
        <c:if test="${userSession.currentUser().admin}">
            <div class="col-lg-2">
                <a href="javascript:unblockForm()"><button class="btn btn-default">Add Admin</button></a>
            </div>
            </p>
            </div>
            <br>

            <div id="formRoot" style="display: none">
                <div class="row col-lg-4">
                    <br>
                    <h3 class="text-info">New User Admin</h3>
                    <form action="/rufus/newUser" method="post">
                        <div class="form-group">
                            <label for="rootEmail">Email</label>
                            <input type="email" class="form-control" id="rootEmail" placeholder="type here" name="email">
                        </div>
                        <a class="btn btn-default" href="javascript:hideMe()">Cancel</a>
                        <button class="btn btn-primary" type="submit">Submit</button>
                        
                    </form>   
                </div>
            </div>
        </c:if>


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

                function unblockForm() {
                    $("#formRoot").css("display", "block");
                }
                
                function hideMe(){
                    $("#formRoot").css("display", "none");
                }
</script>