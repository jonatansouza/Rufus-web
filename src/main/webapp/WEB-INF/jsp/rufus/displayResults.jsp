<jsp:include page="../layout/menu.jsp"></jsp:include>
    <div id="page-wrapper">    
        <div class="container-fluid">
        <c:forEach items="${workflowFiles}" var="file">

            <div class="col-xs-4 col-md-2">
                <a href="${linkTo[RufusController].downloadWorkflowFile}?requiredFile=${file.path.replaceAll("/var/rufus-dev/users","")}" class="thumbnail">
                    <c:if test="${fn:containsIgnoreCase(file.name, '.xml')}">
                        <img src="${pageContext.request.contextPath}/assets/img/xml.png" alt="Workflow" style="width: 90px">
                    </c:if>
                    <c:if test="${not fn:containsIgnoreCase(file.name, '.xml')}">
                        <img src="${pageContext.request.contextPath}/assets/img/file.png" alt="Workflowxml" style="width: 50px">
                    </c:if>
                    <div class="caption">
                        <h4 class="text-center">${file.name}</h4>
                    </div>
                </a>
            </div>


        </c:forEach>
    </div>
</div>
    
    <jsp:include page="../layout/footer.jsp"></jsp:include>