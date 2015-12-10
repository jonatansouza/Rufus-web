
<jsp:include page="../layout/menu.jsp"></jsp:include>
    <div id="page-wrapper">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-9">
                    <div class="page-header"><h3 class="col-lg-offset-1">Upload File</h3></div>
                    <div class="col-md-12">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>File Name</th><th>Date</th><th>Download</th><th>Remove</th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${fileList}" var="file">
                                <tr><td>${file.name}</td><td>${file.date}</td>
                                    <td><a href="javascript:downloadFile('${file.name}')">
                                            <i class="glyphicon glyphicon-download"></i></a></td>
                                    <td><a href="javascript:removeFile('${file.name}')"><i class="glyphicon glyphicon-remove"></i></a></td></tr>
                                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="col-md-12">

                    <a class="btn btn-primary btn-lg" href="${linkTo[RufusController].upload}">
                        Upload Files
                    </a>
                    <br><br><br>
                </div>
            </div>
        </div>
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

    function downloadFile(name) {

        window.location = "${pageContext.request.contextPath}/rufus/" + name + "/download";

    }

</script>

<jsp:include page="../layout/footer.jsp"></jsp:include>
