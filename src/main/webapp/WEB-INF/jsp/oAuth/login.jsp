<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/sb-admin.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/font-awesome/css/font-awesome.min.css">


<div id="loginModal" class="modal show" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="row">
                    <div class="col-md-3">
                        <img class="col-md-12" src="${pageContext.request.contextPath}/assets/img/rufus.png">
                    </div>
                    <div class="col-md-6 text-center">
                        <h2>Rufus</h2>
                        <span class="text-muted"><b>lxc-containers management</b></span>
                    </div>
                    <div class="col-md-3"></div>
                </div>
            </div>
            <div class="modal-body">
                <div class="alert">
                    <h2>You are not logged!</h2>
                    <span class="text-muted"><b>Click bellow to login on VirtualIS</b></span>
                    <br><br>
                    <a href="${pageContext.request.contextPath}/oauth/login" style="text-decoration: none"><button class="btn btn-primary col-md-12">Sign in</button></a>
                </div>
                <div class="clearfix"></div>
            </div>
            <div class="modal-footer">
                <div class="col-md-3 text-left text-muted">
                    Copyright &copy; 2015
                </div>
                <div class="col-md-6 text-center">

                </div>
                <div class="col-md-3 text-muted">
                    <i class="fa fa-linux"></i>
                    <i class="fa fa-css3"></i>
                    <i class="fa fa-html5"></i>
                    <i class="fa fa-git"></i>
                </div>    
            </div>
        </div>
    </div>
</div>
