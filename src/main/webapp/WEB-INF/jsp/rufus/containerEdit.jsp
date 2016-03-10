<jsp:include page="../layout/menu.jsp"></jsp:include>
    <style>
        .btn-file {
            position: relative;
            overflow: hidden;
        }
        .btn-file input[type=file] {
            position: absolute;
            top: 0;
            right: 0;
            min-width: 100%;
            min-height: 100%;
            font-size: 100px;
            text-align: right;
            filter: alpha(opacity=0);
            opacity: 0;
            outline: none;
            background: white;
            cursor: inherit;
            display: block;
        }

    </style>
    <script src="${pageContext.request.contextPath}/assets/js/jquery-2.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/bootbox.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/side-bar-admin.js"></script>
<div id="page-wrapper">
    <div class="container-fluid">
        <div class="row">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <td><b>Name</b></td>
                        <td><b>Status</b></td>
                        <td><b>Ip</b></td>
                        <td><b>Tools</b></td>
                    </tr>
                </thead>
                <tr>
                    <td>${lxc.name}</td>
                    <td>${lxc.state}</td>
                    <td id="updateIp"><c:forEach items="${lxc.ips}" var="ips">
                            ${ips}
                        </c:forEach>
                    </td>
                    <td><div  class="tools-show">
                            <c:if test = "${lxc.state=='RUNNING'}">

                                <a id="buttonOff" class="btn btn-default" href="${pageContext.request.contextPath}/rufus/${lxc.name}/update/${lxc.state}"  data-toggle="tooltip" data-placement="left" title="Shutdown" >
                                    <span class="glyphicon glyphicon-off"></span>
                                </a>   

                                <a class="btn-default" href="${pageContext.request.contextPath}/rufus/remoteAccess/${lxc.ips[0]}" title="Remote access" data-toggle="tooltip" data-placement="left" target="_blank">
                                    <span class="fa-stack fa-lg">
                                        <span id="iconTerminal"></span>
                                    </span>
                                </a>

                                <script>
                                    function ipAvailable(name, ip) {
                                        var validIp = null;
                                        setTimeout(function (data) {

                                            $.get("/rufus/containers/json/" + name, function (data)
                                            {
                                                if (data.ips.length) {
                                                    var ipFromServer = data.ips[0];
                                                    $('#iconTerminal').html("<i class='fa fa-square fa-stack-2x'></i><i class='fa fa-terminal fa-stack-1x fa-inverse'></i>");
                                                    $('#updateIp').html(ipFromServer);
                                                    $('#buttonOff').show();
                                                    
                                                } else {

                                                    ipAvailable(name, ip);
                                                }
                                            });
                                        }, 1000);
                                        $('#iconTerminal').html("<button class='btn btn-default'><i class='fa fa-cog fa-spin'></i></button>");
                                        $('#buttonOff').hide();
                                    }

                                    ipAvailable('${lxc.name}', '${lxc.ips[0]}');
                                </script>
                            </c:if>
                            <c:if test = "${lxc.state=='STOPPED'}">
                                <a class="btn btn-primary" href="${pageContext.request.contextPath}/rufus/${lxc.name}/update/${lxc.state}"  data-toggle="tooltip" data-placement="right" title="Turn on"  >
                                    <span class="glyphicon glyphicon-off"></span>
                                </a>
                                <a class="btn btn-danger" onclick="tools('${lxc.name}')" data-toggle="tooltip" data-placement="left" title="Delete this Container">
                                    <span class="glyphicon glyphicon-remove"></span>
                                </a>


                            </c:if>
                        </div></td>


                </tr>
            </table>

            <form id="formIcon" name="iconForm" method="POST" onchange="return validateForm()" action="${pageContext.request.contextPath}/rufus/uploadIcon"  enctype="multipart/form-data">
                <div class="form-group">
                    <span class="btn btn-primary btn-file">
                        Click to upload Icon<input type="file" id="exampleInputFile" name="uploadedFile">
                    </span>
                    <input type="hidden" value="${lxc.name}" name="name">

                </div>

            </form>

            <h3>Container Icon</h3>        
            <div class="pull-left"> 
                <object data="${pageContext.request.contextPath}/icons/${lxc.name}.png" width="100px" class="img-thumbnail center-block">
                    <img src="${pageContext.request.contextPath}/icons/container.png" width="100px" class="img-thumbnail center-block" alt="container">
                </object>
            </div>

        </div>

    </div>

</div>
<script>
    function validateForm() {
        var x = document.forms["iconForm"]["uploadedFile"].value;
        if (x == null || x == "") {
            return false;
        } else {
            $("#formIcon").submit();
        }
    }

    function tools(name) {
        bootbox.confirm("Are you sure you want delete \"" + name + "\" ?", function (result) {
            if (result) {
                {
                    window.location = "${pageContext.request.contextPath}/rufus/" + name + "/delete";
                }
            }
        });
    }

</script>
</body>
</html>