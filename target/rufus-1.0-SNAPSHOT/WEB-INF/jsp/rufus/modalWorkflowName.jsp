
<div class='form-group' id="workflowDiv">
    <input class='form-control' id='nameWork' type='text' name='first_name' placeholder="Type a name here" autofocus/>
    <br>
    <button class="btn btn-primary pull-right" id="btn-name" style="display: none" onclick="javascript:saveWorkflowName()">Save</button>
    <br>
</div>

<script type="text/javascript">
    folders = JSON.parse('${folders}');
    var testName
    $("#nameWork").keyup(function (evt) {
        testName = $("#nameWork").val();
        $("#workflowDiv").removeClass('has-error');
        if (testName.length == 0 || testName.indexOf(" ") >= 0 || $.inArray(testName, folders) >= 0) {
            $("#workflowDiv").addClass('has-error');
            $("#btn-name").hide();
        } else {
            $("#btn-name").show("slow");
        }
    });
    
    $("#btn-name").click(function (){
       saveWorkflowName(testName); 
    });
       
</script>
