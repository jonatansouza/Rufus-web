
<jsp:include page="../layout/menu.jsp"></jsp:include>
    <div class="container-fluid col-lg-offset-2">
        <div class="page-header"><h3 class="col-lg-offset-1">Upload File</h3></div>
        <div class="col-md-12">
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
    <div class="col-md-12">

        <a class="btn btn-primary btn-lg" href="${linkTo[RufusController].upload}">
            Upload Files
        </a>
    </div>
</div>

<script type="text/javascript">
    function removeFile(name) {
        bootbox.confirm("Are you sure you want delete \"" + name + "\" ?", function (result) {
            if (result) {
                {
                    window.location = "${pageContext.request.contextPath}/rufus/" + name + "/deleteFile";
                }
            }
        });
    }

</script>

<jsp:include page="../layout/footer.jsp"></jsp:include>