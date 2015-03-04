<jsp:include page="../layout/menu.jsp"></jsp:include>
    <div class="container">
    <c:forEach items="${workflowFiles}" var="file">
        
            <div class="col-xs-6 col-md-3">
                <a href="${linkTo[RufusController].downloadWorkflowFile}?requiredFile=${file.path.replaceAll("/var/rufus/users","")}" class="thumbnail">
                    <img src="${pageContext.request.contextPath}/assets/img/file.png" alt="Workflow" style="width: 50px">
                    <div class="caption">
                        <h4 class="text-center">${file.name}</h4>
                    </div>
                </a>
            </div>

        
    </c:forEach>
</div>