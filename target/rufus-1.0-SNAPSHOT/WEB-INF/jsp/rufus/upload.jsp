<jsp:include page="../layout/menu.jsp"></jsp:include>
    <div class="container">   
        <style>

            #mydropzone{   

                width: 400px;
                height: 400px;
                border: 3px dashed #000000;
                position:fixed; 



            }

            #default-message{
                text-align: center;
                width: 300px; 
                height: 200px; 
                top: 100px;
                margin: 0 auto; 
                position: relative; 

            }


        </style>

        <div style =margin-top:100px;></div>

        <body>
            <div class="container">
            <c:forEach items="${errors}" var="error">
                <div class="row">
                    <div class="col-sm-4">

                        <p class="alert-danger"><b>
                                ${error.category} - ${error.message}</b></p><br/>

                    </div>
                </div>
            </c:forEach>
            <div id="mydropzone">
                <div id="default-message">
                    <h3 class="h3"><i class="fa fa-cloud-upload fa-5x"></i><br><spam  id="text-drop">Drop your files here</spam></h3>
                </div>
            </div>
            <div style =margin-top:450px;></div>
            <div class="row">
                <div class="col-md-6" id="tableFiles" style="display: none">
                    <table  class="table table-striped" >
                        <thead>
                            <tr>
                                <th>File Name</th><th style="text-align: right">Remove</th>
                            </tr>  
                        </thead>
                        <tbody id="listFiles" >

                        </tbody>
                    </table>
                </div>
            </div>
            <br><br>
            <div class="row">
                <div><button id="buttonSend" class="btn btn-default" style=" display: none;">Send Files</button></div>
            </div>
        </div>
        <script src="${pageContext.request.contextPath}/assets/js/drag-drop.js"></script>