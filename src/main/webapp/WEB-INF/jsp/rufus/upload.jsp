<jsp:include page="../layout/menu.jsp"></jsp:include>
    <div class="container">   
        <style>

            #dragandrophandler{   

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
                <div id="dragandrophandler">
                    <div id="default-message">
                        <h3 class="h3"><i class="fa fa-cloud-upload fa-5x"></i><br>Drop your files here</h3>
                    </div>
                </div>
                <div style =margin-top:450px;></div>
                <div class="row">
                    <div class="col-md-4" id="tableFiles" style="display: none">
                        <table  class="table table-striped" >
                            <thead>
                                <tr>
                                    <th>File Name</th><th style="text-align: right">Remove</th>
                                </tr>  
                            </thead>
                            <tbody id="statusbar" >

                            </tbody>
                        </table>
                    </div>
                </div>
                <br><br>
                <div class="row">
                    <div><input id="buttonSendFiles" type="button" onclick="javascript:sendFiles()" class="btn btn-default" value="Send Files" style="margin-left: 160px; display: none;"></div>
                </div>
            </div>
            <script type="text/javascript">


                //Handle drag and drop events with jQuery
                var obj = $("#dragandrophandler");
                var fd = [];
                var filesToSend = [];
                obj.on('dragenter', function (e)
                {
                    e.stopPropagation();
                    e.preventDefault();
                    $(this).css('border', '2px solid #000000');
                });

                obj.on('dragover', function (e)
                {
                    e.stopPropagation();
                    e.preventDefault();
                });
                obj.on('drop', function (e)
                {

                    $(this).css('border', '2px dotted #000000');
                    e.preventDefault();
                    var files = e.originalEvent.dataTransfer.files;
                    //We need to send dropped files to Server
                    handleFileUpload(files, obj);
                });



//If the files are dropped outside the div, 
                //file is opened in the browser window. 
                //To avoid that we can prevent ?drop? event on document.


                $(document).on('dragenter', function (e) {
                    e.stopPropagation();
                    e.preventDefault();
                });
                $(document).on('dragover', function (e) {
                    e.stopPropagation();
                    e.preventDefault();
                    obj.css('border', '2px dotted #000000');
                });
                $(document).on('drop', function (e) {
                    e.stopPropagation();
                    e.preventDefault();
                });


//Read the file contents using HTML5 FormData() when the files are dropped.

                var rows = 0;
                function handleFileUpload(files, obj) {

                    $("#tableFiles").css("display", "block");
                    for (var i = 0; i < files.length; i++) {
                        //fd[fd.length] = new FormData();
                        //fd[fd.length - 1].append('file', files[i]);
                        filesToSend.push(files[i]);
                        rows++;
                        //$("#statusbar").append("<li id=\"status" + rows +"-"+ files[i].name +"\"> " + files[i].name + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span  name=\""+ files[i].name +"\" id=\"status" + rows + "\" class=\"glyphicon glyphicon-remove\"></span></li>");
                        $("#statusbar").append("<tr id=\"status" + rows + "\"><td> " + files[i].name + " </td><td><a href=\"javascript:deleteNode(" + rows + ");\" class=\"pull-right\"><i class=\"glyphicon glyphicon-remove\"></i></a></td></tr>");
                        $("#buttonSendFiles").css('display', 'block');

                    }
                    //var status = new createStatusbar(obj); //Using this we can set progress.
                    //status.setFileNameSize(files[i].name, files[i].size);
                    //sendFileToServer(fd[fd.length], status);

                }

                function deleteNode(rowId) {
                    $("#status" + rowId).remove();
                    console.log(filesToSend.length + " removing index " + filesToSend[rowId - 1].name);
                    filesToSend.splice((rowId - 1), 1);
                }


                function sendFiles() {
                    fd = new FormData();
                    ;
                    for (i = 0; i < filesToSend.length; i++) {
                        fd.append("file[" + i + "]", filesToSend[i]);
                    }
                    sendFileToServer(fd);
                }

                //Send FormData() to Server using jQuery AJAX API
                function sendFileToServer(formdata) {
                    var uploadURL = "${linkTo[RufusController].saveFile}"; //Upload URL
                    var extraData = {}; //Extra Data.
                    var jqXHR = $.ajax({
                    xhr: function () {
                        var xhrobj = $.ajaxSettings.xhr();
                        if (xhrobj.upload) {
                            xhrobj.upload.addEventListener('progress', function (event) {
                                var percent = 0;
                                var position = event.loaded || event.position;
                                var total = event.total;
                                if (event.lengthComputable) {
                                    percent = Math.ceil(position / total * 100);
                                }
                                //Set progress
                                //status.setProgress(percent);
                            }, false);
                        }
                        return xhrobj;
                    },
                            url: uploadURL,
                            type: "POST",
                            contentType: false,
                            processData: false,
                            cache: false,
                            data: formdata,
                            success: function (data) {
                                window.location.href = "${linkTo[RufusController].index}";
                            }
                    });
                }


        </script>