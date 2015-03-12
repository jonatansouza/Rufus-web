<jsp:include page="../layout/menu.jsp"></jsp:include>
    <div id="page-wrapper">
        <div class="container-fluid">
            <div class="row">
                <table class="table table-hover" id="tablelxc">
                    <thead>
                        <tr>
                            <td><b>Name</b></td>
                            <td><b>Status</b></td>
                            <td><b>Ip</b></td>
                            <td><b>Tools</b></td>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="lxc">
                        <tr>

                            <td>${lxc.name}</td>
                            <td>${lxc.state}</td>
                            <td><c:forEach items="${lxc.ips}" var="ips">
                                    ${ips}
                                </c:forEach>
                            </td>
                            <td><div  class="tools-show">
                                    <c:if test = "${lxc.state=='RUNNING'}">
                                        <a class="btn btn-default" href="${pageContext.request.contextPath}/rufus/${lxc.name}/update/${lxc.state}"  data-toggle="tooltip" data-placement="left" title="Shutdown" >
                                            <span class="glyphicon glyphicon-off"></span>
                                        </a>   

                                        <a class="  btn-default" href="#" data-toggle="tooltip" data-placement="right" title="Remote acces">
                                            <span class="fa-stack fa-lg">
                                                <i class="fa fa-square fa-stack-2x"></i>
                                                <i class="fa fa-terminal fa-stack-1x fa-inverse"></i>
                                            </span>
                                        </a>
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

                    </c:forEach>
                </tbody> 
            </table>
        </div>
    </div>
</div>

<script>
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

<jsp:include page="../layout/footer.jsp"></jsp:include>