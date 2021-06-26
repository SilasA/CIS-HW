var gl;
var points;

var x = 0.0;
var y = 0.0;
var xLoc, yLoc;
var xDir = 1.0;
var yDir = 1.0;

function getRandomArbitrary(min, max) {
    return Math.random() * (max - min) + min;
}

window.onload = function init() {
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
    x += 0.005 * xDir;
    y += 0.01 * yDir;

    if (y > 0.9) { // top hit
        y = 0.9;
        yDir *= -1.0;
    }
	if (x > 0.9) { // right hit
		x = 0.9;
		xDir *= -1.0;
	}
	if (y < -0.9) { // bottom hit
		y = -0.9;
		yDir *= -1.0;
	}
	if (x < -0.9) { // left hit
		x = -0.9;
		xDir *= -1.0;
	}

    gl.uniform1f(xLoc, x);
    gl.uniform1f(yLoc, y);

    gl.clear(gl.COLOR_BUFFER_BIT);
    gl.drawArrays(gl.TRIANGLES, 0, 3);

	setTimeout(render, 100);
    //window.requestAnimFrame(render);
}

