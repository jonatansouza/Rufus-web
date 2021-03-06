<jsp:include page="../layout/menu.jsp"></jsp:include>
<div id="page-wrapper">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-9">
                <div id="error" style="display: none">

                    <div class="row">
                        <div class="col-sm-4">
                            <p class="alert-danger"><b>
                                    The name typed is not valid, make sure:
                                    <ul>
                                        <li>There is not other container with this name</li>
                                        <li>The name cannot be empty</li>
                                        <li>The name cannot contains space</li>
                                    </ul>

                                </b></p><br/>
                        </div>
                    </div>
                </div>
                <div id="cloneSuccess" style="display: none">
                    <h3 class="text-info text-center">Container Created!</h3>
                </div>

                <div class="row" id="rowForm">
                    <div class="col-sm-4">

                        <div class="form-group">
                            <form id="containerCreateForm">
                                <label for="containername">Container: </label>
                                <input class="form-control" type="text" name="name" id="containername" placeholder="Type Container name here" autofocus>
                                <br>
                                <br>


                                <button id="buttonClone" onclick="creating()" class="btn btn-default">Create</button>
                            </form>
                        </div>

                    </div>


                </div>
                <div id="waiting" class="hidden">
                    <div class="text-center col-md-offset-2">
                        <h1>Creating Container<br><small>Please wait</small></h1>
                        <i class="fa fa-spinner fa-spin fa-5x"></i>
                    </div></div>


            </div>
        </div>
    </div>

</div>

<script src="/rufus/assets/js/jquery-2.1.1.min.js"></script>
<script src="/rufus/assets/js/bootstrap.min.js"></script>
<script src="/rufus/assets/js/bootbox.min.js"></script>
<script src="/rufus/assets/js/side-bar-admin.js"></script>

<script>
                                $('form').on('submit', function (e) {
                                    return false;
                                });

                                function creating() {
                                    $("#cloneSuccess").hide();
                                    $("#error").hide();
                                    $("#rowForm").hide();

                                    $('#waiting').removeClass('hidden');

                                    $.ajax({
                                        method: "POST",
                                        url: '/rufus/rufus/save',
                                        data: {
                                            name: $("#containername").val()
                                        }
                                    }).done(function (data) {
                                        $("#cloneSuccess").show();
                                        $('#containername').val('');
                                        console.log("done");
                                    }).fail(function () {
                                        $("#error").show();
                                        console.log("fail");
                                    }).always(function (data) {
                                        $('#waiting').show();
                                        $("#rowForm").show();
                                        $('#waiting').addClass('hidden');
                                        console.log("always");
                                    });
                                }
</script>

</body>
</html>
97,4          Bot
