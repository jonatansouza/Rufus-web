//     Amphitrite.js 4.4
//     (c) 2014 Christian Mury
//     Amphitrite may be freely distributed under the GNU GPL.

function input() {
    var node = new joint.shapes.basic.Image({
        position: {
            x: 20,
            y: 150
        },
        size: {
            width: height / 30 + width / 30,
            height: height / 30 + width / 30
        },
        attrs: {
            text: {
                text: 'Input',
                magnet: true
            },
            image: {
                'xlink:href': '/rufus/assets/images/nodes/input.png',
                width: 50,
                height: 50
            },
            '.': {
                magnet: false
            },
        },
        activity: '',
	name: 'input'
    });
    graph.addCell(node);
}

function file() {
    
    var node = new joint.shapes.basic.Image({
        position: {
            x: 20,
            y: 10
        },
        size: {
            width: height / 30 + width / 30,
            height: height / 30 + width / 30
        },
        attrs: {
            text: {
                text: 'File',
                magnet: true
            },
            image: {
                'xlink:href': '/rufus/assets/images/nodes/file.png',
                width: 50,
                height: 50
            },
            '.': {
                magnet: false
            },
        },
        activity: '',
        name: 'file'
    });
    graph.addCell(node);
}


function addition() {
    data = {
        position: {
            x: 150,
            y: 150
        },
        size: {
            width: height / 30 + width / 30,
            height: height / 30 + width / 30
        },
        attrs: {
            text: {
                text: 'Addition',
                magnet: true
            },
            image: {
                'xlink:href': '/rufus/assets/images/nodes/addition.png',
                width: 50,
                height: 50
            },
            '.': {
                magnet: false
            },
        },
        activity: '',
	name: 'addition',
        container: true 
    };
    
    var node = new joint.shapes.basic.Image(data);
    graph.addCell(node);
}




function blast() {
    data = {
        position: {
            x: 150,
            y: 10
        },
        size: {
            width: height / 30 + width / 30,
            height: height / 30 + width / 30
        },
        attrs: {
            text: {
                text: 'Blast',
                magnet: true
            },
            image: {
                'xlink:href': '/rufus/assets/images/nodes/blast.png',
                width: 50,
                height: 50
            },
            '.': {
                magnet: false
            },
        },
        activity: '',
	name: 'blast',
        container: true 
    };
    var node = new joint.shapes.basic.Image(data);
    graph.addCell(node);
}

function result() {
    var node = new joint.shapes.basic.Image({
        position: {
            x: 150,
            y: 150
        },
        size: {
            width: height / 30 + width / 30,
            height: height / 30 + width / 30
        },
        attrs: {
            text: {
                text: 'Result',
                magnet: true
            },
            image: {
                'xlink:href': '/rufus/assets/images/nodes/equal.png',
                width: 50,
                height: 50
            },
            '.': {
                magnet: false
            },
        },
        activity: '',
	name: 'result'
    });
    graph.addCell(node);
}
// End of node creation
