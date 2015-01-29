//     Amphitrite.js 4.4
//     (c) 2014 Christian Mury
//     Amphitrite may be freely distributed under the GNU GPL.

// Creating drawing paper
var graph = new joint.dia.Graph;
var height = window.outerHeight;
var width = window.outerWidth;
var nodeSize = height / 30 + width / 30;

var paper = new joint.dia.Paper({
    el: $('#paper'),
    width: $("#DrawingBoard").width() - 30,
    height: $("#DrawingBoard").height(),
    gridSize: 10,
    model: graph,

    defaultLink: new joint.dia.Link({
        attrs: {
            '.marker-target': {
                d: 'M 10 0 L 0 5 L 10 10 z'
            }
        }
    }),
    validateConnection: function (cellViewS, magnetS, cellViewT, magnetT, end, linkView) {
        // Prevent loop linking
        return (magnetS !== magnetT);
    },
    // Enable link snapping within 75px lookup radius
    snapLinks: {
        radius: 75
    }
});

var selected;
paper.on('cell:pointerdown', function (cellView) {
    selected = cellView.model;
});

// Right button menu
// https://groups.google.com/forum/#!topic/jointjs/e3sq1fZkq0w
paper.$el.on('contextmenu', function (evt) {
    evt.stopPropagation(); // Stop bubbling so that the paper does not handle mousedown.
    evt.preventDefault(); // Prevent displaying default browser context menu.
    var cellView = paper.findView(evt.target);
    if (cellView) {
        // The context menu was brought up when clicking a cell view in the paper.
        // console.log(cellView.model.id); // Node id
        // console.log(cellView.model.attributes.name); // Node name
        if (cellView.model.attributes.name == "file") {
          bootbox.dialog({
                message: "Select an option",
                title: "Options",
                buttons: {
                    main: {
                        label: "Select File",
                        className: "btn-info",
                        callback: function () {
                            $.get("/templates/modal/modalFile.php", function(data,status){
   		                bootbox.dialog({
                                    message: data,
                                    title: "Select File <a type=\"button\" class=\"btn btn-default\" href=\"/file.php\" ><i class=\"fa fa-fw fa-eye\"></i> All files</a></a>",
                                });
                            });
                        }
                    },
                    danger: {
                        label: "Delete node",
                        className: "btn-danger",
                        callback: function () {
			    if (selected) selected.remove();
                        }
                    }
                }
            }); 
        } else {
          bootbox.dialog({
                message: "Select an option",
                title: "Options",
                buttons: {
                    main: {
                        label: "Custom code",
                        className: "btn-info",
                        callback: function () {
                            bootbox.prompt("Input custom code", function (customCode) {
                                if (customCode === null) {} else {
                                    if (selected) selected.set('activity', customCode);
                                }
                            });
                        }
                    },
                    danger: {
                        label: "Delete node",
                        className: "btn-danger",
                        callback: function () {
                             if (selected) selected.remove();
                        }
                    }
                }
            });
        }// End Else
    }
})

// Test node connections
function testConnection() {
    alert("Click on the node you want to test connection");
    // Trigger signaling on element click.
    paper.on('cell:pointerdown', function (cellView) {
        cellView.model.trigger('signal', cellView.model);
    });

    // Signaling.
    // ----------
    graph.on('signal', function (cell, data) {
        if (cell instanceof joint.dia.Link) {
            var targetCell = graph.getCell(cell.get('target').id);
            sendToken(cell, 1, function () {
                targetCell.trigger('signal', targetCell);
            });

        } else {
            flash(cell);
            var outboundLinks = graph.getConnectedLinks(cell, {
                outbound: true
            });
            _.each(outboundLinks, function (link) {
                link.trigger('signal', link);
            });
        }
    });

    function flash(cell) {
        var cellView = paper.findViewByModel(cell);
        cellView.highlight();
        _.delay(function () {
            cellView.unhighlight();
        }, 200);
    }

    function sendToken(link, sec, callback) {
        var token = V('circle', {
            r: 10,
            fill: 'green'
        });
        $(paper.viewport).append(token.node);
        token.animateAlongPath({
            dur: sec + 's',
            repeatCount: 1
        }, paper.findViewByModel(link).$('.connection')[0]);
        _.delay(function () {
            token.remove();
            callback();
        }, sec * 1000);
    }
}

// Show Download Modal
function modalDownload(){
    $.get("/templates/modal/modalDownload.php", function(data,status){
        bootbox.dialog({
            message: data,
            title: "Select File",
        });
    });
}

// Show Upload Modal
function modalUpload(){
    $.get("/templates/modal/modalUpload.php", function(data,status){
        bootbox.dialog({
            message: data,
            title: "Select File",
        });
    });
}

// Insert File
function insertFile(customCode){
    if (selected) selected.set('activity', "/home/ubuntu/upload/" + customCode);
}

// Upload Diagram
function uploadFile(){
        var fileInput = document.getElementById('fileUpload').files[0];
        //http://stackoverflow.com/questions/254184/filter-extensions-in-html-form-upload
        str = document.getElementById('fileUpload').value.toUpperCase();
        suffix = ".FLOW";
        if (str.indexOf(suffix, str.length - suffix.length) == -1) {
            alert('File type not allowed,\nAllowed file: *.flow');
        } else {
            var reader = new FileReader();
            reader.onload = function (e) {
                var jsonUpload = reader.result;
                var regex = new RegExp("nodeSizeHolder", 'g');
                jsonUpload = jsonUpload.replace(regex, nodeSize);
                graph.fromJSON(JSON.parse(jsonUpload));
            }
            reader.readAsText(fileInput, "UTF-8");
        }
}

// Post XML
function sendXML() {
    postForm = document.getElementById("postForm");
    xmlTextArea = document.getElementById("xmlTextArea");
    xmlTextArea.value = JSON.stringify(graph.toJSON());
    postForm.submit();
}

// Export JSON
function toJSON() {
    var workflowName = document.getElementById('workflowName').value;
    var jsonDownload = JSON.stringify(graph.toJSON());
    var regex = new RegExp(nodeSize, 'g');
    var jsonString = jsonDownload.replace(regex, 'nodeSizeHolder');
    var blob = new Blob([jsonString], {
        type: "text/plain;charset=utf-8;",
    });
    saveAs(blob, workflowName + ".flow");
}

// Creating XML
function toXML() {

    var workflowName = document.getElementById('workflowName').value,
        AccessLVL = document.getElementById('AccessLVL').value,
        workflowID = document.getElementById('workflowID').value,
        jsonString = JSON.stringify(graph.toJSON()),
        json = jQuery.parseJSON(jsonString),
        jsonSize = Object.keys(json.cells).length,
        transID = 0;

    var xml = "<WorkflowProcess Id=\"" + workflowID + "\" Name=\"" + workflowName + "\" AccessLevel=\"" + AccessLVL + "\">\n";
    xml += "   <Activities>\n";
    for (var i = 0; i < jsonSize; i++) {
        var jsonType = json.cells[i].type;
        if (jsonType == "basic.Image") {
            var name = json.cells[i].name;
            var id = json.cells[i].id;
            var activity = json.cells[i].activity;

            xml += "      	 <Activity Id=\"" + id + "\" Name=\"" + name + "\">\n";
            xml += "	         <Implementation>";
            xml += activity + "</Implementation>\n";
            xml += "         </Activity>\n";
        }
    }

    xml += "   </Activities>\n";
    xml += "   <Transitions>";

    for (var i = 0; i < jsonSize; i++) {
        var jsonType = json.cells[i].type;
        if (jsonType == "link") {
            var source = json.cells[i].source.id;
            var target = json.cells[i].target.id;
            xml += "\n      <Transition Id=\"" + transID + "\" From=\"" + source + "\" To=\"" + target + "\"/>";
            transID++;
        }
    }

    xml += "\n   </Transitions>\n"
    xml += "</WorkflowProcess>\n"

    var blob = new Blob([xml], {
        type: "xml/plain;charset=utf-8;",
    });
    saveAs(blob, workflowName + ".xml");
}

window.onload = function () {
    // Making paper scale to screen height
    $("svg").attr("class", "col-md-12");
    $("svg").height(window.innerHeight - 179);
}

function adjustVertices(graph, cell) {

    // If the cell is a view, find its model.
    cell = cell.model || cell;

    if (cell instanceof joint.dia.Element) {

        _.chain(graph.getConnectedLinks(cell)).groupBy(function(link) {
            // the key of the group is the model id of the link's source or target, but not our cell id.
            return _.omit([link.get('source').id, link.get('target').id], cell.id)[0];
        }).each(function(group, key) {
            // If the member of the group has both source and target model adjust vertices.
            if (key !== 'undefined') adjustVertices(graph, _.first(group));
        });

        return;
    }

    // The cell is a link. Let's find its source and target models.
    var srcId = cell.get('source').id || cell.previous('source').id;
    var trgId = cell.get('target').id || cell.previous('target').id;

    // If one of the ends is not a model, the link has no siblings.
    if (!srcId || !trgId) return;

    var siblings = _.filter(graph.getLinks(), function(sibling) {

        var _srcId = sibling.get('source').id;
        var _trgId = sibling.get('target').id;

        return (_srcId === srcId && _trgId === trgId) || (_srcId === trgId && _trgId === srcId);
    });

    switch (siblings.length) {

    case 0:
        // The link was removed and had no siblings.
        break;

    case 1:
        // There is only one link between the source and target. No vertices needed.
        cell.unset('vertices');
        break;

    default:

        // There is more than one siblings. We need to create vertices.

        // First of all we'll find the middle point of the link.
        var srcCenter = graph.getCell(srcId).getBBox().center();
        var trgCenter = graph.getCell(trgId).getBBox().center();
        var midPoint = g.line(srcCenter, trgCenter).midpoint();

        // Then find the angle it forms.
        var theta = srcCenter.theta(trgCenter);

        // This is the maximum distance between links
        var gap = 20;

        _.each(siblings, function(sibling, index) {

            // We want the offset values to be calculated as follows 0, 20, 20, 40, 40, 60, 60 ..
            var offset = gap * Math.ceil(index / 2);

            // Now we need the vertices to be placed at points which are 'offset' pixels distant
            // from the first link and forms a perpendicular angle to it. And as index goes up
            // alternate left and right.
            //
            //  ^  odd indexes 
            //  |
            //  |---->  index 0 line (straight line between a source center and a target center.
            //  |
            //  v  even indexes
            var sign = index % 2 ? 1 : -1;
            var angle = g.toRad(theta + sign * 90);

            // We found the vertex.
            var vertex = g.point.fromPolar(offset, angle, midPoint);

            sibling.set('vertices', [{ x: vertex.x, y: vertex.y }]);
        });
    }
};

var myAdjustVertices = _.partial(adjustVertices, graph);

// adjust vertices when a cell is removed or its source/target was changed
graph.on('add remove change:source change:target', myAdjustVertices);

// also when an user stops interacting with an element.
paper.on('cell:pointerup', myAdjustVertices);

