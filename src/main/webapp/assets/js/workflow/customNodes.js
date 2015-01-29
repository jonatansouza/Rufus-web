//     Amphitrite.js 4.4
//     (c) 2014 Christian Mury
//     Amphitrite may be freely distributed under the GNU GPL.

function input() {
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

function matrix() {
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
                text: 'Matrix',
                magnet: true
            },
            image: {
                'xlink:href': '/rufus/assets/images/nodes/cluster.png',
                width: 50,
                height: 50
            },
            '.': {
                magnet: false
            },
        },
        activity: '',
        name: 'multipy'
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
	name: 'addition'
    });
    graph.addCell(node);
}

function subtraction() {
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
                text: 'Subtraction',
                magnet: true
            },
            image: {
                'xlink:href': '/rufus/assets/images/nodes/subtraction.png',
                width: 50,
                height: 50
            },
            '.': {
                magnet: false
            },
        },
        activity: '',
	name: 'subtraction'
    });
    graph.addCell(node);
}

function multiplication() {
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
                text: 'Multiplication',
                magnet: true
            },
            image: {
                'xlink:href': '/rufus/assets/images/nodes/multiplication.png',
                width: 50,
                height: 50
            },
            '.': {
                magnet: false
            },
        },
        activity: '',
	name: 'multiplication'
    });
    graph.addCell(node);
}

function division() {
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
                text: 'Division',
                magnet: true
            },
            image: {
                'xlink:href': '/rufus/assets/images/nodes/division.png',
                width: 50,
                height: 50
            },
            '.': {
                magnet: false
            },
        },
        activity: '',
	name: 'division'
    });
    graph.addCell(node);
}

function result() {
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
