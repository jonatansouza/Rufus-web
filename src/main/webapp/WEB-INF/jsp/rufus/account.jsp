<jsp:include page="../layout/menu.jsp"></jsp:include>
    <div id="page-wrapper">    
        <div class="container">
        <c:if test="${not empty message}">
            <script>
                function hideMe(){
                    setTimeout(function(){
                        $("#message").fadeOut("slow");
                    }, 3000);
                }
                hideMe();
            </script>
            <span id="message">
                <h3 class="text-info">${message}</h3>
            </span>    
        </c:if>
            <div class="row" id="adminsArea">

            </div>

            <br>
            <div class="row">
                <p>
                <c:if test="${userSession.currentUser().admin}">
                <div class="col-lg-1">
                    <a href="javascript:unblockForm()" id="clickMe"><button class="btn btn-primary">Add Admin</button></a>
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
    $(document).ready(function () {

        $.get("/rufus/oauth/getRootUsers", function (data) {
            $("#adminsArea").html(data);
        });

    });

    function unblockForm() {
        $("#formRoot").css("display", "block");
    }

    function hideMe() {
        $("#formRoot").css("display", "none");
    }


</script>