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
            <div class="row">

            <c:forEach items="${containers}" var="lxc">
                <div class="col-xs-4 col-md-2">

                    <div class="thumbnail">
                        <a href="${pageContext.request.contextPath}/rufus/containerEdit/${lxc.name}">
                            <object data="${pageContext.request.contextPath}/icons/${lxc.name}.png" width="100px" class="img-responsive center-block">
                                <img src="${pageContext.request.contextPath}/icons/container.png" width="100px" class="img-responsive center-block" alt="container">
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
