<div class="container">



    <!--login modal-->
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
                    <form class="form col-md-12 center-block" method="POST" action="http://auth.comcidis.lncc.br:3000/users/sign_in" role="form" enctype="multipart/form-data">
                        <div class="form-group">
                            <input type="email" class="form-control input-lg" name="user[email]" placeholder="Login" required autofocus />
                        </div>
                        <div class="form-group">
                            <input type="password" class="form-control input-lg" name="user[password]" maxlength="20" placeholder="Password" required />
                        </div>
                        <div class="form-group">
                            <button class="btn btn-primary btn-lg btn-block">Sign In</button>
                        </div>
                    </form>
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
                        
    <div class="col-md-5 col-md-offset-4" style="margin-top: 450px">
       
        <c:forEach var="error" items="${errors}">
            <h3 class="alert-danger">${error.category} - ${error.message}</h3>
            <br>
        </c:forEach>


    </div>


</div>
