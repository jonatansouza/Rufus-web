<!doctype html>
<html>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/docs.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/sb-admin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/font-awesome/css/font-awesome.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <div class="wrapper">
        <nav class="navbar navbar-inverse navbar-static-top" role="navigation">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed"
                            data-toggle="collapse"
                            data-target="#bs-example-navbar-collapse-1">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#"><span class="active"><strong>Rufus</strong></span> lxc-containers</a>

                </div>
                <ul class="nav navbar-top-links navbar-right">
                    <c:if test = "${userSession.currentUser().name != null}">
                        <li><a href="${linkTo[RufusController].account}">
                                <i class="fa fa-user fa-fw"></i> Welcome ${userSession.currentUser().name}</a></li>

                    </ul>
                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                        <div class="navbar-default sidebar" role="navigation">
                            <div class="sidebar-nav">
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
                                        <a href="${linkTo[RufusController].workflow}"><i class="fa fa-wrench fa-fw"></i> Workflow</a>
                                    </li>
                                    <li>
                                        <a href="${linkTo[RufusController].account}"><i class="fa fa-user fa-fw"></i> Account</a>
                                    </li>


                                </ul>
                            </div>
                            <!-- /.sidebar-collapse -->
                        </div>
                    </div>
                </c:if>
           
        </nav>
    </div>
    <div id="page-wrapper">

        <div class="container">
            <div class="row">	
                <div class="col-md-9">

                    <div class="jumbotron" style="background:#4F4F4F !important">

                        <h1><span style="color : #E8E8E8">Rufus</span><small> lxc-containers</small></h1>
                        <div class="pull-right">
                            <img src="${pageContext.request.contextPath}/assets/img/rufus.png" width="150" height="150">
                        </div>
                        <h3><small><span style="color: #E8E8E8">lxc-containers management</span></small><br><small><span style="color: #E8E8E8">scientific workflow</span></small></h3>
                        <br>    
                        <c:if test = "${userSession.currentUser().name == null}">
                            <p><a class="btn btn-success btn-lg" href="${linkTo[RufusController].login}" role="button">Sign in</a></p>
                        </c:if>
                        <c:if test = "${userSession.currentUser().name != null}">
                            <h3><span style="color: #E8E8E8">Welcome ${userSession.currentUser().name}!</span></h3>
                        </c:if>

                    </div>

                    <div>
                        <h1 class="page-header">Rufus</h1>
                        <h3 class="page-header">The Project</h3>
                        <p>Development on COMCIDIS group at LNCC, The project Rufus is an entire environment which the 
                            user may process complex 
                            scientific workflow with a friendly interface, he may also upload files on server and
                            create,manage and change as need.</p>

                        <h3 class="page-header">Account</h3>
                        <p>To access Rufus, you need to a <a class="active" href="#">VirtualLS</a> account, once logged there,
                            you may access the Rufus.</p>
                        <h3 class="page-header">LXC-containers</h3>
                        <p>LXC-containers is a userspace interface for the Linux kernel containment features. 
                            Through a powerful API and simple tools, it lets Linux users easily create 
                            and manage system or application containers. LXC containers are often considered 
                            as something in the middle between a chroot and 
                            a full fledged virtual machine. The goal of LXC is to create an environment as close as possible 
                            to a standard Linux installation but without the need for a separate kernel.
                        </p>
                        <p>On the left menu user may manage lxc - containers , and he Also may create a customize one with a specific list of templates 					available from pylxc ,
                            the once created the user may use the container on workflow , the dashboard tab, the user may check all lxc - containers 						status , its not all, the user may ssh to container to setup or change something on lxc-container. </p>

                        <h3 class="page-header">Files and Upload</h3>

                        <p>On the first access to Rufus the user will have a folder which he will upload files, this files may be used
                            on the workflow, in this directory you also have the workflows executed and the results, both of then can be downloaded</p>

                        <h3 class="page-header">Scientific Workflow</h3>
                        <p>The Workflow Drawing board is where the user may develop complex workflow to be executed on clusters by PYLXC, 
                            after running there are some ways to get result:
                        <ul>
                            <li>The file with the result can be downloaded from user's directory, the user just have to click in results at workflow 								tab on side menu, then go to workflow desired and click to open and download.
                                <strong>the workflow results</strong>, available on the side menu, the user not only can check and download the workflows results, but 								he can also delete a non desired workflow.
                            </li>
                            <li>If is required, the workflow's XML also can be downloaded, click at files on the top menu.</li>
                        </ul></p>
                        <p>


                    </div>
                </div>
            </div>
        </div><!--div class container-->

        <footer class="bs-docs-footer" role="contentinfo">
            <div class="container">

                <p>Designed and built by <a href="#" target="_blank">@jonatangd.souza</a></p>
                <p>Infrastructure PYLXC by <a href="#" target="_blank">@allan.matheus</a></p>
                <p>Maintained by the <a href="http://comcidis.lncc.br/" target="_blank">ComCiDis team</a> .</p>
                <p>Code licensed under <a href="http://www.lncc.br/frame.html" target="_blank">LNCC</a>.</p>
                <ul class="bs-docs-footer-links text-muted">
                    <li>Currently v1.0</li>
                    <li>&middot;</li>
                    <li><a href="#">LNCC</a></li>
                    <li>&middot;</li>
                    <li><a href="http://comcidis.lncc.br/">ComCiDis</a></li>
                    <li>&middot;</li>
                    <li><a href="#">v1.0 docs	</a></li>
                    <li>&middot;</li>
                    <li><a href="#">PYLXC docs</a></li>
                    <li>&middot;</li>
                    <li><a href="#">About</a></li>
                    <li>&middot;</li>
                    <li><a href="#">Issues</a></li>
                    <li>&middot;</li>
                    <li><a href="#">Releases</a></li>
                </ul>
            </div><!--div class container-->

        </footer>
    </div>
</body>
</html>
