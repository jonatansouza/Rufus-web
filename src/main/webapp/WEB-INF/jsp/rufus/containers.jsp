<jsp:include page="../layout/menu.jsp"></jsp:include>
    <style>
        .thumbnail{
            height: 200px;
            width: 200px;
            overflow: auto;
        }
    </style>
    <div id="page-wrapper">
        <div class="container-fluid">
            <h1 class="page-header">Container <small>- list</small>
                <span class="pull-right">
                    <a href="${linkTo[RufusController].create}" class="btn btn-info" data-toggle="tooltip" data-placement="left" title="Create a new container"><i class="fa fa-plus-square fa-2x"></i></a>
                </span>
            </h1>
            <div class="row">

            <c:forEach items="${containers}" var="lxc">
                <div class="col-xs-4 col-md-2">

                    <div class="thumbnail img-responsive">
                        <a href="${pageContext.request.contextPath}/rufus/containerEdit/${lxc.name}">
                            <object data="${pageContext.request.contextPath}/icons/${lxc.name}.png" width="100px" class="img-responsive center-block" alt="${lxc.name}">
                                <img src="${pageContext.request.contextPath}/icons/container.png"  class="img-responsive center-block" alt="container">
                            </object>
                            <div class="caption text-capitalize text-center">
                                <h3>${lxc.name}</h3>
                            </div>

                        </a>
                    </div>
                </div>

            </c:forEach>
            
        </div>

    </div>

</div>
<jsp:include page="../layout/footer.jsp"></jsp:include>
