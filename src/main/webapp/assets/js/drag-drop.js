//files outside div

$(document).on('dragenter', function (e) {
    e.stopPropagation();
    e.preventDefault();
});
$(document).on('dragover', function (e) {
    e.stopPropagation();
    e.preventDefault();
});
$(document).on('drop', function (e) {
    e.stopPropagation();
    e.preventDefault();
});
//end 



var itemsToUpload = [];
var count = 0;
var objdroped = $("#mydropzone");
objdroped.on('dragenter', function (e) {
    e.stopPropagation();
    e.preventDefault();
});
objdroped.on('dragover', function (e)
{
    e.stopPropagation();
    e.preventDefault();
});
objdroped.on('drop', function (e) {


    var length = e.originalEvent.dataTransfer.items.length;
    for (var i = 0; i < length; i++) {
        var entryCheck = e.originalEvent.dataTransfer.items[i].webkitGetAsEntry();

        if (entryCheck.isFile) {

            $("#text-drop").html("File Dropped");
            entry = e.originalEvent.dataTransfer.files[i];
            firstStepFile(entry);
        } else if (entryCheck.isDirectory) {
            $("#text-information").html("Directories should be ziped!");
            txto = $("#myMessage").html();
            bootbox.dialog({
                message: txto
            });
        }
    }
});

function firstStepFile(entry) {
    $("#tableFiles").css("display", "block");
    $("#buttonSend").css("display", "block");
    itemsToUpload[itemsToUpload.length] = entry;
    $("#listFiles").append("<tr id='" + entry.name + "'><td>" + entry.name + "</td>" +
            "<td>" + (entry.size / 1024).toFixed(2) + " kb</td>" +
            "<td><a class='pull-right' href=\"javascript:deleteNode('" + entry.name + "')\"> " +
            "<i class='glyphicon glyphicon-remove'></td></tr>");
}


function deleteNode(idToRemove) {

    $("#" + idToRemove).remove();
    var idx = itemsToUpload.indexOf(name);
    itemsToUpload.splice(idx, 1);
    if (!itemsToUpload.length) {
        $("#tableFiles").css("display", "none");
        $("#buttonSend").css("display", "none");
        $("#text-drop").html("Drop your files here");
    }
}

function prepareFilesToSend() {

    for (var i = 0; i < itemsToUpload.length; i++) {
        var entry = itemsToUpload[i];

        uploadFileToServer(entry);

    }
}

function uploadFileToServer(file) {
    fd = new FormData();
    fd.append("file", file);
    var uploadURL = "/rufus/rufus/saveFile"; //Upload URL

    $.ajax({
        xhr: function () {
            var xhr = new window.XMLHttpRequest();
            xhr.upload.addEventListener("progress", function (evt) {
                if (evt.lengthComputable) {
                    var percentComplete = evt.loaded / evt.total;
                    //Do something with upload progress
                    $("#myBar").css("width", percentComplete * 100 + "%");
                    $("#text-progressbar").html(file.name);
                    $("#count-files").html((count + 1) + "/" + itemsToUpload.length);

                }
            }, false);
            xhr.addEventListener("progress", function (evt) {
                if (evt.lengthComputable) {
                    var percentComplete = evt.loaded / evt.total;
                    //Do something with download progress
                    $("#myBar").css("width", percentComplete * 100 + "%");
                    $("#text-progressbar").html(file.name);

                }
            }, false);
            return xhr;

        },
        url: uploadURL,
        type: "POST",
        contentType: false,
        processData: false,
        cache: false,
        data: fd,
        success: function (data) {
            count++;
            if (count == itemsToUpload.length) {
                $("#text-information").html("Uploaded");
                txto = $("#myMessage").html();
                bootbox.alert(txto, function () {
                    window.location.href = "/rufus/upload";
                });
            }
        }



    });

}

$("#buttonSend").click(function () {
    prepareFilesToSend();
    countfiles = $("#countfiles").html();
    $("#countfiles").remove();
    uploadDialog = $('#uploadDialog').html();
    $('#uploadDialog').remove();
    bootbox.dialog({
        title: countfiles,
        message: uploadDialog
    });
});

$(document).ready(function () {

    $("#mydropzone").on("click", function () {
        $("#myclickzone").append("<input type='file' id='fileclickzone' style='display: none'>");
        $("#fileclickzone").click();
        $("#fileclickzone").change(function () {
            fileClick = $(this)[0].files[0];
        $("#fileclickzone").remove();
            firstStepFile(fileClick);
        });


    });

});
