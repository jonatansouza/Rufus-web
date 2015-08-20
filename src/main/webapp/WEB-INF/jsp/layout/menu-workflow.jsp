<!doctype html>
<html>
    <head>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/sb-admin.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/font-awesome/css/font-awesome.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/joint.css">
        <meta name="viewport" content="width=device-width, initial-scale=1">
    </head>
    <body>
        <div class="wrapper">
            <nav class="navbar navbar-inverse navbar-static-top" role="navigation">
                <div class="navbar-header">
                    <a class="navbar-brand" href="${linkTo[RufusController].index}"><span class="active"><strong>Rufus</strong></span> lxc-containers</a>
                </div>
                <div class="col-lg-4 col-lg-offset-2">
                    <ul class="nav navbar-top-links text-center">
                        <c:if test = "${userSession.currentUser().name != null}">
                            <li><a href="${linkTo[RufusController].account}"><i class="fa fa-user fa-fw"></i> Welcome ${userSession.currentUser().name}</a></li>
                            </c:if>
                    </ul>
                </div>

                <ul class="nav navbar-top-links navbar-right">
                    <li><a href="${linkTo[RufusController].fileList}" target="_blank"><i class="fa fa-fw fa-file"></i> Files</a></li>
                    <li><a href="#" onclick="saveWorkflow()" id="linksave"> <i class="fa fa-fw fa-save"></i>Save</a></li>
                    <li><a href="#" onclick="sendXML()"><i class="fa fa-fw fa-play"></i>Run</a></li>



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



                <div class="navbar-default sidebar" role="navigation">
                    <div class="sidebar-nav navbar-collapse">
                        <ul class="nav" id="side-menu">
                            <li>
                                <a class="active">Resources<span class="pull-right"><i class="fa fa-arrow-down"></i></span></a>
                                <ul class="nav nav-second-level">
                                    <li>
                                        <a href="#" onclick="input()"><i class="fa fa-fw fa-toggle-down"></i>Input</a>
                                    </li>        
                                    <li>
                                        <a href="#" onclick="file()"><i class="fa fa-fw fa-file-o"></i>File</a>
                                    </li>
                                </ul>
                            </li>
                            <li>
                                <a class="active">Action<span class="pull-right"><i class="fa fa-arrow-down"></i></span></a>
                                <ul class="nav nav-second-level">
                                    <li>
                                        <a href="#" onclick="blast()"<i class="fa fa-random"></i> Blast</a>
                                    </li>
                                    <li>
                                        <a href="#" onclick="blastNode()"<i class="fa fa-sitemap"></i> Blast-Node</a>
                                    </li>
                                    
                                    <li>
                                        <a href="#" onclick="skymapp()"<i class="fa fa-globe"></i> Skymapp</a>
                                    </li>
                                    
                                    <li>
                                        <a href="#" onclick="skyadd()"<i class="fa fa-globe"></i> Skyadd</a>
                                    </li>
                                    
                                    <li>
                                        <a href="#" onclick="addition()"<i class="fa fa-plus-circle"></i> Addition</a>
                                    </li>

                                </ul>
                            </li>
                            <li>
                                <a class="active">Workflow<span class="pull-right"><i class="fa fa-arrow-down"></i></span></a>
                                <ul class="nav nav-second-level">
                                    <li>
                                        <a href="${linkTo[RufusController].workflowResult}"><i class="fa fa-line-chart"></i> Results</a>
                                    </li>
                                    <li>
                                        <a href="javascript:listFilesToLoad()"><i class="fa fa-line-chart"></i> load</a>
                                    </li>
                                </ul>
                            </li>



                        </ul>
                    </div>
                    <!-- /.sidebar-collapse -->
                </div>

                <!-- /.navbar-static-side -->
            </nav>
        </div>
