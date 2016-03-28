    <!DOCTYPE html>
<html lang="en">
    <head>

        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!--<script src="${pageContext.request.contextPath}/assets/js/jquery-2.1.1.min.js"></script>-->
        <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>

        <style>
            .carousel-inner > .item > img,
            .carousel-inner > .item > a > img {
                width: 70%;
                margin: auto;
            }

            .carousel-control.left{
                background:white;

            }

            .carousel-control.right{
                background:white;
            }       

            .thumbnail{
                border: none;
            }

            .carousel-control{

            }




        </style>
    </head>
    <body>

        <script>
            $(".carousel").carousel({
                interval: false
            });
        </script>
        <div class="container">
            <h3>Options available:</h3>
            <br>
            <div id="cluster" class="carousel slide col-md-3" data-ride="carousel">
                <!-- Indicators -->
                <ol class="carousel-indicators">
                    <li data-target="#cluster" data-slide-to="0" class="active"></li>
                    <li data-target="#cluster" data-slide-to="1"></li>
                    <li data-target="#cluster" data-slide-to="2"></li>
                    <li data-target="#cluster" data-slide-to="3"></li>
                </ol>

                <!-- Wrapper for slides -->
                <div class="carousel-inner" role="listbox">
                    <div class="item active">
                        <div class="row">
                            <div class="thumbnail">
                                <a href="#" onclick="setNumberNodesCluster(2)">
                                    <img src="${pageContext.request.contextPath}/assets/img/cluster.png" width="100px">
                                    <p class="caption text-center"><strong>2 Nodes</strong></p>
                                </a>
                            </div>
                        </div>

                    </div>
                    <c:forEach items="${nodes}" var="node">
                        <c:if test="${node>2}">
                            <div class="item">
                                <div class="row">
                                    <div class="thumbnail">
                                        <a href="#" onclick="setNumberNodesCluster('${node}')">
                                            <img src="${pageContext.request.contextPath}/assets/img/cluster.png" width="100px">
                                            <p class="caption text-center"><strong>${node} Nodes</strong></p>
                                        </a>
                                    </div>
                                </div>

                            </div>
                        </c:if>
                    </c:forEach>
                </div>



                <!-- Left and right controls -->
                <a class="left carousel-control" href="#cluster" role="button" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="right carousel-control" href="#cluster" role="button" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>
        </div>


    </body>


</html>