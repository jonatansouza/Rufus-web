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
        $("#tableFiles").css("display", "block");
        $("#buttonSend").css("display", "block");
        $("#text-drop").html("File Dropped");
        if (entryCheck.isFile) {
            entry = e.originalEvent.dataTransfer.files[i];
                itemsToUpload[itemsToUpload.length] = entry;
                
                $("#listFiles").append("<tr id='file" +i+ "'><td>" + entry.name + "</td><td> " + 
                    "<a class='pull-right' href=\"javascript:deleteNode('file" + i + "')\"> " +
                    "<i class='glyphicon glyphicon-remove'></td></tr>");
            
        } else if (entryCheck.isDirectory) {
            
                bootbox.alert("Directories should be ziped!", function (){
                    
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
        url: uploadURL,
        type: "POST",
        contentType: false,
        processData: false,
        cache: false,
        data: fd,
        success: function (data) {
            bootbox.alert("Files Upload Successful!", function (){
            });
            
        }
    });

}

$("#buttonSend").click(function () {
    prepareFilesToSend();
});
