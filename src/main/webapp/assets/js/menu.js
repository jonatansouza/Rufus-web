

function signout(name) {
    bootbox.confirm(name+" Are you sure?", function (result) {
        if(result==true){
            location.href = "/rufus/oauth/logout"
        }else{
            bootbox.hideAll();
        }
    });
}