<div id="mydropzone" style="height: 400px; width: 400px; background-color: graytext; clear: right">
</div>
<br>
<div>
    <table id="tableFiles" class="table table-striped" style="display: none">
        <thead>
            <tr>
                <td>Item</td>
                
                <td>Remove</td>
            </tr>
        </thead>
        <tbody id="listFiles">

        </tbody>
    </table> 
</div>
<br>

<form id="formUpload" method="post" action="${linkTo[RufusController].saveFileTest}" enctype="multipart/form-data">
    
</form>

<button id="buttonSend" class="btn btn-default"> CLICK </button>
<script src="${pageContext.request.contextPath}/assets/js/jquery-2.1.1.min.js"></script>

<script src="${pageContext.request.contextPath}/assets/js/drag-drop.js"></script>