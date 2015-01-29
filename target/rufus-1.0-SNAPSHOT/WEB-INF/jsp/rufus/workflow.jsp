<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/joint.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/sb-admin.css"> 
<!-- Wrapper -->
    <div id="wrapper">
        <!-- Navigation -->
	       <!-- Navigation -->
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${linkTo[RufusController].dashboard}">Dashboard</a>
            </div>
            <!-- Top Menu Items -->
            <ul class="nav navbar-right top-nav">
	        <li><a href="file.php"><i class="fa fa-fw fa-file"></i> Files</a></li>
                <li><a href="#" onclick="sendXML()"><i class="fa fa-fw fa-play"></i> Run</a></li>
                <!-- Options -->
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-cog fa-spin"></i> Options <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="#" onclick="modalDownload()" ><i class="fa fa-fw fa-download"></i> Download File</a>
                        </li>
                        <li>
                            <a href="#" onclick="modalUpload()"><i class="fa fa-fw fa-upload"></i> Upload File</a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#" onclick="testConnection()"><i class="fa fa-fw fa-arrows-h"></i> Test Nodes</a>
                        </li>
                        <li>
                            <a href=""><i class="fa fa-fw fa-square-o"></i> Clear Nodes</a>
                        </li>
                    </ul>
                </li>
               
                <!-- End Login -->
            </ul>
            <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
            <div class="collapse navbar-collapse navbar-ex1-collapse">
                <ul class="nav navbar-nav side-nav">
                    <li class="active">
                        <a class="active"><i class="fa fa-fw fa-wrench"></i> Workbench</a>
                    </li>
                    <li>
                        <a href="#" onclick="input()"><i class="fa fa-fw fa-toggle-down"></i>Input</a>
                        <a href="#" onclick="file()"><i class="fa fa-fw fa-file-o"></i>File</a>

                        <a href="#" onclick="addition()">+ Addition</a>
                        <a href="#" onclick="subtraction()">- Subtraction</a>
                        <a href="#" onclick="multiplication()">* Multiplication</a>
                        <a href="#" onclick="division()">/ Division</a>
                        <a href="#" onclick="result()">= Result</a>
			<a href="#" onclick="matrix()"># Matrix(2)</a>
			
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </nav>
   

<div id="page-wrapper">
            <div class="container-fluid">
                <!-- Page Heading -->
                <div class="col-md-12">
                    <h1 class="page-header">
                         Drawing Board <br><small>Workflow Diagram
                         
                         </small>
                     </h1>
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
                <form hidden method="POST" action="${linkTo[RufusController].sendXML}" id="postForm">
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








