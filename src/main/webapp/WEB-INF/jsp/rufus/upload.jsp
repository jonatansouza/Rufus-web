<jsp:include page="../layout/menu.jsp"></jsp:include>

    <style>

        #mydropzone{   

            width: 500px;
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



    <body>

        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div id="mydropzone">
                        <div id="default-message">
                            <h3 class="h3"><i class="fa fa-cloud-upload fa-5x"></i><br><spam  id="text-drop">Drop your files here<br>or Click to upload!</spam></h3>
                        </div>
                    </div>
                </div>
                <div class="row col-md-5 col-md-offset-4">
                    <div class="" id="tableFiles" style="display: none">
                        <table  class="table table-striped" >
                            <thead>
                                <tr>
                                    <th>File Name</th><th>size</th><th style="text-align: right">Remove</th>
                                </tr>  
                            </thead>
                            <tbody id="listFiles" >

                            </tbody>
                        </table>
                    </div>
                    <button id="buttonSend" class="btn btn-default center-block" style=" display: none;">Send Files</button>

                </div>

                <div class="row">

                    <br><br>

                    <div id="myclickzone"></div>

                    <h3 id="countfiles" style="display: none">Uploading <spam id="count-files"></spam></h3>

                    <div id="myMessage" style="display: none"><h3><spam id="text-information"></spam><h3></div>

                                <div id="uploadDialog" style="display: none">

                                    <div class="progress">
                                        <div id="myBar" class="progress-bar progress-bar-info progress-bar-striped" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100" style="width: 0%">
                                            <span class="sr-only"></span>
                                        </div>
                                    </div>
                                    <h3 id="text-progressbar"></h3>

                                </div>
                                </div>
                                </div>
                                </div>

                                <script src="${pageContext.request.contextPath}/assets/js/jquery-2.1.1.min.js"></script>
                            <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
                            <script src="${pageContext.request.contextPath}/assets/js/bootbox.min.js"></script>
                            <script src="${pageContext.request.contextPath}/assets/js/drag-drop.js"></script>
                            <script src="${pageContext.request.contextPath}/assets/js/side-bar-admin.js"></script>

                            </body>
                            </html>