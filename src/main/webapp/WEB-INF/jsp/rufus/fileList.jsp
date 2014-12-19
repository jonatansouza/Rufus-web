<jsp:include page="../layout/menu.jsp"></jsp:include>
    <div style =margin-top:100px;></div>
    <div class="container">
        <div class="col-md-6" style="border: solid 1px;">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>File Name</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${fileList}" var="file">
                    <tr><td>${file.name}</td></tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="pull-right">
        <a style="text-decoration: none" href="${linkTo[RufusController].upload}">
            <button class="btn btn-primary" style="margin-right: 400px; width: 150px; height: 60px;">
                Upload Files
            </button> 
        </a>
    </div>
</div>