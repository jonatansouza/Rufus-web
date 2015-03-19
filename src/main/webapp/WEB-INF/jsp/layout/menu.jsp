<!doctype html>
<html>
    <head>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/sb-admin.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/font-awesome/css/font-awesome.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/docs.css">
        <script src="${pageContext.request.contextPath}/assets/js/menu.js"></script>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Rufus</title>
    </head>
    <body>
        <div class="wrapper">
            <nav class="navbar navbar-inverse navbar-static-top" role="navigation">
                <div class="navbar-header">
                    <a class="navbar-brand" href="${linkTo[RufusController].index}"><span class="active"><strong>Rufus</strong></span> lxc-containers</a>

                </div>

                <ul class="nav navbar-top-links navbar-right">
                    <c:if test = "${userSession.currentUser().name != null}">
                        
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                <i class="fa fa-user fa-fw"></i> Welcome ${userSession.currentUser().name}</a>
                                <ul class="dropdown-menu">
                                   
                                    <li><a href="http://auth.comcidis.lncc.br:3000/users/me" target="_blank">Edit Profile  <i class="fa fa-pencil"></i></a></li>
                                     <li><a href="javascript:signout('${userSession.currentUser().name}')">Sign out  <i class="fa fa-sign-out"></i></a></li>
                                </ul>
                        </li>
                            </c:if>
                </ul>
                <div class="navbar-default sidebar" role="navigation">

                    <div class="sidebar-nav navbar-collapse">

                        <ul class="nav" id="side-menu">
                            <c:if test="${userSession.currentUser().admin}">
                                <li>
                                    <a href="${linkTo[RufusController].dashboard}"><i class="fa fa-laptop fa-fw"></i> Containers</a>
                                </li>
                                <li>
                                    <a href="${linkTo[RufusController].create}"><i class="fa fa-plus-circle fa-fw"></i> Create new Container</a>
                                </li>
                            </c:if>
                            <li>
                                <a href="${linkTo[RufusController].fileList}"><i class="fa fa-file-text fa-fw"></i> Files</a>

                            </li>
                            <li>
                                <a href="${linkTo[RufusController].upload}"><i class="fa fa-cloud-download fa-fw"></i> Upload Files</a>

                            </li>
                            <li>
                                <a href="${linkTo[RufusController].workflow}"><i class="fa fa-wrench fa-fw"></i> Workflow Dashboard</a>
                            </li>
                            <li>
                                <a href="${linkTo[RufusController].workflowResult}"><i class="fa fa-line-chart fa-fw"></i> Workflows</a>
                            </li>
                            <li>
                                <a href="${linkTo[RufusController].account}"><i class="fa fa-user fa-fw"></i> Account</a>
                            </li>
                        </ul>
                    </div>
                    <!-- /.sidebar-collapse -->
                </div>

                <!-- /.navbar-static-side -->
            </nav>
        </div>
