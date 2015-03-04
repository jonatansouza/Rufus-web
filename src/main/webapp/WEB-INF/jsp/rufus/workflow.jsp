
<jsp:include page="../layout/menu-workflow.jsp"></jsp:include>

<div id="page-wrapper">
        <div class="container-fluid">
            <!-- Page Heading -->
            <div class="col-md-12">
                
                <h3 class="page-header">
                    <span id="workflowHeaderName"></span>
                    <br><small>Workflow Drawing board<small> 
                </h3>
            </div>
            <div class="row">
                <div id="DrawingBoard" class="col-md-12">
                    <div id="paper" class="col-md-12"></div>
                </div>
            </div>
            <!-- /.row -->
        </div>
        <!-- /.container-fluid -->
        <div>
            <form hidden method="POST" action="${linkTo[RufusController].runWorkflow}" id="postForm">
            <textarea name="xmlTextArea" id="xmlTextArea"></textarea>
        </form>
    </div>
    <!-- /#post -->
</div>
<!-- /#page-wrapper -->
</div>

<script src="${pageContext.request.contextPath}/assets/js/workflow/jquery.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/bootbox.min.js"></script>


<script src="${pageContext.request.contextPath}/assets/js/workflow/joint.js"></script>

<script src="${pageContext.request.contextPath}/assets/js/workflow/lodash.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/workflow/backbone.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/workflow/core.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/workflow/vectorizer.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/workflow/geometry.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/workflow/joint.dia.graph.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/workflow/joint.dia.cell.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/workflow/joint.dia.element.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/workflow/joint.dia.link.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/workflow/joint.dia.paper.js"></script> 
<script src="${pageContext.request.contextPath}/assets/js/workflow/joint.shapes.basic.js"></script> 
<script src="${pageContext.request.contextPath}/assets/js/workflow/joint.connectors.normal.js"></script>

<script src="${pageContext.request.contextPath}/assets/js/workflow/FileSaver.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/workflow/amphitrite.js"></script>


<script src="${pageContext.request.contextPath}/assets/js/workflow/customNodes.js"></script>

</body>
</html>





