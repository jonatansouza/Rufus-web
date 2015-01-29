<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${linkTo[RufusController].dashboard}">LXC Containers</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="${linkTo[RufusController].dashboard}">Home</a></li>

            </ul>
        </div><!--/.nav-collapse -->
    </div>
    <div class="container-fluid">

        <div id="sidebar-wrapper">

            <ul class="sidebar-nav">
                <li class="sidebar-brand">
                    <a href="${linkTo[RufusController].dashboard}">
                        Container System
                    </a>
                </li>

                <li>
                    <a href="${linkTo[RufusController].create}">Create new Container</a>
                </li>
                <li>
                    <a href="${linkTo[RufusController].fileList}">Files</a>
                </li>
                
                <li>
                    <a href="${linkTo[RufusController].workflow}">Workflow</a>
                </li>

            </ul>
        </div>
    </div>
</nav>