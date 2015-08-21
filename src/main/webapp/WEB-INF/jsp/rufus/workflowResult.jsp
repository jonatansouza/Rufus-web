<jsp:include page="../layout/menu.jsp"></jsp:include>
    <div id="page-wrapper">    
        <div class="container-fluid">
        <c:if test="${workflows.size() == 0}">
            <h3>No Workflows submitted!</h3>
        </c:if>

        <c:forEach items="${workflows}" var="workflow">

            <div class="col-xs-4 col-md-2 ">
                <div class="thumbnail">

                    <a href="${pageContext.request.contextPath}/displayResults/${workflow.name}" >
                        <img src="${pageContext.request.contextPath}/assets/img/folderWorkflow.png" alt="Workflow">
                        <div class="caption">
                            <h4 class="text-center">${workflow.name}<br><br><small>${workflow.date}</small></h4>
                        </div>
                    </a>
                    <a class="pull-right" href="javascript:removeWorkflow('${workflow.name}')" data-toggle="tooltip" data-placement="bottom" title="Delete this Workflow">
                        <i class="fa fa-trash-o fa-2x"></i>
                    </a>
                        <div class="clearfix"></div>

                </div>
            </div>


        </c:forEach>
    </div>
</div>
<script type="text/javascript">
    function removeWorkflow(name) {
        bootbox.confirm("Are you sure you want delete \"" + name + "\" ?", function (result) {
            if (result) {
                {
                    window.location = "${pageContext.request.contextPath}/deleteWorkflow/" + name;
                }
            }
        });
    }

</script>

<jsp:include page="../layout/footer.jsp"></jsp:include>