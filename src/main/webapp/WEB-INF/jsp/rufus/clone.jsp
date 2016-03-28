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
                        <h3 class="text-info text-center">Container Cloned!</h3>
                    </div>


                    <div class="row" id="rowForm">
                        <div class="col-sm-4">
                            <h3>Clone from ${baseContainer}</h3>
                        <div class="form-group">
                            <form method="POST" action="${linkTo[RufusController].save}" id="containerForm">

                                <label for="containername">Container: </label>
                                <input class="form-control" type="text" name="name" value="${baseContainer}" id="containername" placeholder="Type Container name here">
                                <br>
                            </form>
                            <button id="buttonClone" onclick="clone('${baseContainer}')" class="btn btn-default">Create</button>
                        </div>

                    </div>


                </div>
                <div id="waiting"></div>


            </div>
        </div>
    </div>
</div>
<script>
    function clone(baseContainer) {
        $("#buttonClone").hide();
        $.get("/rufus/templates/save-waiting/Cloning", function (data) {
            $("#waiting").html(data);
        });

        $.ajax({
            method: "POST",
            url: '/rufus/rufus/saveClone',
            data: {
                baseContainer: baseContainer,
                newContainer: $("#containername").val()
            }

        }).done(function (data) {
             $("#cloneSuccess").show();
             $("#buttonClone").show();
             $("#waiting").hide();
             setTimeout(function (){
             $("#cloneSuccess").fadeOut();
             }, 5000);
            console.log("done");
        }).fail(function () {
             $("#buttonClone").show();
             $("#waiting").fadeOut();
             $("#error").show();
             setTimeout(function (){
             $("#error").fadeOut();
             }, 10000);
        });

    }

</script>
<jsp:include page="../layout/footer.jsp"></jsp:include>