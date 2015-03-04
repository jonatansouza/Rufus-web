<jsp:include page="../layout/menu.jsp"></jsp:include>
    <div class="container">
    <c:forEach items="${workflows}" var="workflow">
        
            <div class="col-xs-4 col-md-2">
                <a href="${pageContext.request.contextPath}/displayResults/${workflow.name}" class="thumbnail">
                    <img src="${pageContext.request.contextPath}/assets/img/folderWorkflow.png" alt="Workflow">
                    <div class="caption">
                        <h4 class="text-center">${workflow.name}</h4>
                    </div>
                </a>
            </div>

        
    </c:forEach>
</div>