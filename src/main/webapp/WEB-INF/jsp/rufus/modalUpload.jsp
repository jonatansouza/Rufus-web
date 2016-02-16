<div class='form-group' id="workflowDiv">
    <br>
    <input id="workflowToLoad" type="file" class="form-control">
    <br>
    <input class="form-control" type="text" name="wrName" id="wrName" placeholder="Workflow Desired Name" required >
    <br>
    <button type="submit" onclick="checkDesireName()" class="btn btn-primary">Load</button>

</div>
<script>
    function checkDesireName() {
        var wrName = $("#wrName").val();
        var listWrName = JSON.parse('${folders}');
        if (!wrName) {
          bootbox.alert("Please choice a name!");
        }else {
            if ($.inArray(wrName, listWrName) > -1) {
                bootbox.alert("this workflow already exist!");
            } else {
                loadWorkflowFromUpload(wrName);
            }
        }
    }
</script>
