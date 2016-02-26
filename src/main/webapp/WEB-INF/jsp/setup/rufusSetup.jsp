<!doctype html>
<html>
    <head>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/docs.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/sb-admin.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/font-awesome/css/font-awesome.min.css">
        <meta name="viewport" content="width=device-width, initial-scale=1">        <title>Setup</title>
    </head>
    <body class="container">
        <div class="col-md-6 col-md-offset-3">
            <h1 class="text-center">Location Setup</h1>
            <form onSubmit="return alert('Setup Configured')" id="formSetup" class="form-horizontal" method="POST" action="${linkTo[SetupController].saveAddress}">
                <div class="form-group">
                    <label for="rufusWeb" class="col-sm-4 control-label">Rufus-Web</label>
                    <div class="col-sm-8">
                        <input value="${hosts.hosts[2].url}" required type="text" name="web" class="form-control" id="rufusWeb" placeholder="192.168.1.101">
                    </div>
                </div>
                <div class="form-group">
                    <label for="rufusCore" class="col-sm-4 control-label">Rufus-Core</label>
                    <div class="col-sm-8">
                        <input value="${hosts.hosts[1].url}" required type="text" name="core" class="form-control" id="rufusCore" placeholder="192.168.1.102">
                    </div>
                </div>
                <div class="form-group">
                    <label for="auth" class="col-sm-4 control-label">Auth</label>
                    <div class="col-sm-8">
                        <input value="${hosts.hosts[0].url}" required type="text" name="auth" class="form-control" id="auth" placeholder="192.168.1.102">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-4 col-sm-10">
                        <button type="submit" id="buttonSetup"  class="btn btn-default"><i class="fa fa-wrench"></i></button>
                    </div>
                </div>

            </form>


        </div>

        <script src="${pageContext.request.contextPath}/assets/js/jquery-2.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/bootbox.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/side-bar-admin.js"></script>
        <script>
                function setupAlert() {
                    bootbox.alert('Setup done!');
                }
        </script>
    </body>
</html>



