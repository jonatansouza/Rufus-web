<jsp:include page="../layout/menu.jsp"></jsp:include>
    <div id="page-wrapper">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-9">
                <c:forEach items="${errors}" var="error">

                    <div class="row">
                        <div class="col-sm-4">
                            <p class="alert-danger"><b>
                                    ${error.category} ${error.message}</b></p><br/>
                        </div>
                    </div>
                </c:forEach>

                <div class="row" id="rowForm">
                    <div class="col-sm-4">

                        <div class="form-group">
                            <form method="POST" action="${linkTo[RufusController].save}" id="containerForm">

                                <label for="containername">Container: </label>
                                <input class="form-control" type="text" name="name" id="containername" placeholder="Type Container name here">
                                <br>
                                <select name="template" class="form-control">
                                    <option value="defaut">default</option>
                                    <c:forEach items="${listTemplates}" var="template">
                                        <option value="${template}">${template}</option> 
                                    </c:forEach>
                                </select>
                                <br>


                            </form>
                            <button onclick="creating()" class="btn btn-default">Create</button>
                        </div>

                    </div>


                </div>
                <div id="waiting"></div>


            </div>
        </div>
    </div>
</div>
<script>
    function creating() {
        $("#rowForm").hide();
        $.get("/rufus/templates/save-waiting", function (data) {
            $("#waiting").html(data);
            $("#containerForm").submit();
        });
     
    }
</script>
<jsp:include page="../layout/footer.jsp"></jsp:include>