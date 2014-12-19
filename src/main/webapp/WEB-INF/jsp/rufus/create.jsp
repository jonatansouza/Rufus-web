<jsp:include page="../layout/menu.jsp"></jsp:include>
    <div style =margin-top:100px;></div>
    <div class="container">
    <c:forEach items="${errors}" var="error">
        <div class="row">
            <div class="col-sm-4">

                <p class="alert-danger"><b>
                        ${error.category} - ${error.message}</b></p><br/>

            </div>
        </div>
    </c:forEach>
    <br> 
    <div class="row">
        <form method="POST" action="${linkTo[RufusController].save}">
            <div class="form-group">
                <div class="col-sm-4">
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
                    <button type="submit" class="btn btn-default">Create</button>
                </div>
            </div>

        </form>
    </div>



</div>