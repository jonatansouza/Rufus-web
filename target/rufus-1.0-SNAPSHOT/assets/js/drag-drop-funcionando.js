

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
                   if(!files.isFile){
                       console.log("*******FILE");
                       $("#tableFiles").css("display", "block");
                        for (var i = 0; i < files.length; i++) {
                            filesToSend.push(files[i]);
                            rows++;
                            $("#statusbar").append("<tr id=\"status" + rows + "\"><td> " + files[i].name + " </td><td><a href=\"javascript:deleteNode(" + rows + ");\" class=\"pull-right\"><i class=\"glyphicon glyphicon-remove\"></i></a></td></tr>");
                            $("#buttonSendFiles").css('display', 'block');

                        }
                   }else{
                       console.log("*******Diretory");
                   }
                }

                function deleteNode(rowId) {
                    $("#status" + rowId).remove();
                    console.log(filesToSend.length + " removing index " + filesToSend[rowId - 1].name);
                    filesToSend.splice((rowId - 1), 1);
                }


                function sendFiles() {
                    fd = new FormData();
                    
                    for (i = 0; i < filesToSend.length; i++) {
                        fd.append("file[" + i + "]", filesToSend[i]);
                    }
                    sendFileToServer(fd);
                }

                //Send FormData() to Server using jQuery AJAX API
                function sendFileToServer(formdata) {
                    var uploadURL = "/rufus/rufus/saveFile"; //Upload URL
                    $.ajax({
                            url: uploadURL,
                            type: "POST",
                            contentType: false,
                            processData: false,
                            cache: false,
                            data: formdata,
                            success: function (data) {
                                //window.location.href = "${linkTo[RufusController].index}";
                            }
                    });
                }

