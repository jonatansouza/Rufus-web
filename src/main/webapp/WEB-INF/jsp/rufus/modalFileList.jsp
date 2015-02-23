
<table class="table table-striped">
    <thead>
        <tr>
            <th>File Name</th><th>Date</th><th>Select</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${fileList}" var="file">
            <tr><td>${file.name}</td><td>${file.date}</td><td><button class="btn btn-default" onclick="">Select</button></td></tr>
                    </c:forEach>
    </tbody>
</table>