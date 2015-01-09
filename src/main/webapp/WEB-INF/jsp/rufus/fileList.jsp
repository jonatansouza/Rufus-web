<jsp:include page="../layout/menu.jsp"></jsp:include>
    <div style =margin-top:100px;></div>
    <div class="container">
        <div class="col-md-6" style="border: solid 1px;">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>File Name</th><th>Date</th><th>Remove</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${fileList}" var="file">
                    <tr><td>${file.name}</td><td>${file.date}</td><td style="text-align: center"><a href="javascript:removeFile('${file.name}')"><i class="glyphicon glyphicon-remove"></i></a></td></tr>
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
            
            <script type="text/javascript">
                function removeFile(name){
                    bootbox.confirm("Are you sure you want delete \"" + name + "\" ?", function (result) {
            if (result) {
                {
                        window.location = "${pageContext.request.contextPath}/rufus/" + name + "/deleteFile";
                }
            }
        });
                }
                
            </script>