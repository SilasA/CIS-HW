var gl;
var points;

var x = 0.0;
var y = 0.0;
var xLoc, yLoc;

var dirs = [null, null]; // Horizontal, Vertical

const LEFT_KEYCODE = 65;
const RIGHT_KEYCODE = 68;
const UP_KEYCODE = 87;
const DOWN_KEYCODE = 83;
const SPACE_KEYCODE = 32;

window.onload = function init() { 
    window.addEventListener(
	"keydown",
	function (e) {
		console.log("Keycode: " + e.keyCode);
		if (e.keyCode == LEFT_KEYCODE) {
			dirs[0] = false;
		} else if (e.keyCode == RIGHT_KEYCODE) {
			dirs[0] = true;
		} else if (e.keyCode == UP_KEYCODE) {
			dirs[1] = true;
		} else if (e.keyCode == DOWN_KEYCODE) {
			dirs[1] = false;
		} else if (e.keyCode == SPACE_KEYCODE) {
			dirs[0] = null;
			dirs[1] = null;
		}
	},
	false
    );

    // Setup canvas and WebGL
    var canvas = document.getElementById('gl-canvas');
    gl = WebGLUtils.setupWebGL(canvas);
    if (!gl) { alert('WebGL unavailable'); }

    // Triangle verticies
    var vertices = [
        vec2(-0.25, -0.25),
        vec2(0, 0.25),
        vec2(0.25, -0.25)
    ];

    // config WebGL
    gl.viewport(0, 0, canvas.width, canvas.height);
    gl.clearColor(1.0, 1.0, 1.0, 1.0);

    // Load and init shaders
    var program = initShaders(gl, 'vertex-shader', 'fragment-shader');
    gl.useProgram(program);

    xLoc = gl.getUniformLocation(program, "x");
    yLoc = gl.getUniformLocation(program, "y");

    // Load data into GPU
    var bufferID = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, bufferID);
    gl.bufferData(gl.ARRAY_BUFFER, flatten(vertices), gl.STATIC_DRAW);

    // Set position and render
    var vPosition = gl.getAttribLocation(program, 'vPosition');
    gl.vertexAttribPointer(vPosition, 2, gl.FLOAT, false, 0, 0);
    gl.enableVertexAttribArray(vPosition);
    render();
};

function render() {
    //x += 0.1;
    //y += 0.1;

    if (dirs[0] === true)
	x += 0.01;
    else if (dirs[0] === false) // move left
	x -= 0.01;
    if (dirs[1] === true) // move up
	y += 0.01;
    else if (dirs[1] === false) // move down
	y -= 0.01;

    gl.uniform1f(xLoc, x);
    gl.uniform1f(yLoc, y);
    
    gl.clear(gl.COLOR_BUFFER_BIT);
    gl.drawArrays(gl.TRIANGLES, 0, 3);

    requestAnimFrame(render);
}

