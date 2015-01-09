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
            $("#tableFiles").css("display", "block");
            $("#buttonSend").css("display", "block");
            $("#text-drop").html("File Dropped");
            entry = e.originalEvent.dataTransfer.files[i];
            itemsToUpload[itemsToUpload.length] = entry;
            $("#listFiles").append("<tr id='file" + i + "'><td>" + entry.name + "</td>" +
                    "<td>" + (entry.size / 1024).toFixed(2) + " kb</td>" +
                    "<td><a class='pull-right' href=\"javascript:deleteNode('file" + i + "')\"> " +
                    "<i class='glyphicon glyphicon-remove'></td></tr>");
        } else if (entryCheck.isDirectory) {
            $("#text-information").html("Directories should be ziped!");
            txto = $("#myMessage").html();
            bootbox.dialog({
                message: txto
            });
        }
    }
});
function deleteNode(idToRemove) {

    $("#" + idToRemove).remove();
    var idx = itemsToUpload.indexOf(name);
    itemsToUpload.splice(idx, 1);
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
                $("#text-information").html("Files Sended");
                txto = $("#myMessage").html();
                bootbox.alert(txto, function () {
                    window.location.href = "/rufus/";
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
