// Globals
let app;
let window_width  = 800;
let window_height = 600;

let playerSprite;
let pVelX = 0, pVelY = 0;

// Keybinds
let kLeft,
	kRight,
	kUp,
	kDown;

// Copied from https://github.com/kittykatattack/learningPixi#keyboard-movement
function keyboard(value) {
	let key = {};
	key.value = value;
	key.isDown = false;
	key.isUp = true;
	key.press = undefined;
	key.release = undefined;
	//The `downHandler`
	key.downHandler = event => {
		if (event.key === key.value) {
			if (key.isUp && key.press) key.press();
			key.isDown = true;
			key.isUp = false;
			event.preventDefault();
		}
	};

	//The `upHandler`
	key.upHandler = event => {
		if (event.key === key.value) {
			if (key.isDown && key.release) key.release();
			key.isDown = false;
			key.isUp = true;
			event.preventDefault();
		}
	};

	//Attach event listeners
	const downListener = key.downHandler.bind(key);
	const upListener = key.upHandler.bind(key);

	window.addEventListener(
		"keydown", downListener, false
	);
	window.addEventListener(
		"keyup", upListener, false
	);
	  
	// Detach event listeners
	key.unsubscribe = () => {
		window.removeEventListener("keydown", downListener);
		window.removeEventListener("keyup", upListener);
	};
	
	return key;
}

function resize(width, height) {
	window_width = width;
	window_height = height;
	app.renderer.resize(width, height);
}

function getBounds(sprite) {
	return {
		left: 	sprite.x - sprite.width / 2,
		top: 	sprite.y - sprite.height / 2,
		right:	sprite.x + sprite.width / 2,
		bottom: sprite.y + sprite.height / 2
	};
}

function checkBounds() {
	let bound = getBounds(playerSprite);
	let x = playerSprite.x, y = playerSprite.y;
	
	if (bound.left < 0) {
		x = playerSprite.width / 2;
	}
	if (bound.right > window_width) {
		x = window_width - playerSprite.width / 2;
	}
	if (bound.top < 0) {
		y = playerSprite.height / 2;
	}
	if (bound.bottom > window_height) {
		y = window_height - playerSprite.height / 2;
	}

	setPlayerPosition(x, y);	
}

function setPlayerPosition(x, y) {
	playerSprite.position.set(
		x, y
	);
}

function movePlayer(delta) {
	playerSprite.x += pVelX;
	playerSprite.y += pVelY;
}

function setup() {
	playerSprite = new PIXI.Sprite(
		app.loader.resources["assets/shrek.png"].texture
	);

	// Set origin to center of sprite
	playerSprite.anchor.set(0.5);

	setPlayerPosition(
		window_width / 2,
		window_height / 2
	);

	app.stage.addChild(playerSprite);

	// Key listeners
	kLeft = keyboard("ArrowLeft");
	kLeft.press = () => {
		pVelX = -1;
	}
	kLeft.release = () => {
		pVelX = 0;
	}

	kRight = keyboard("ArrowRight");
	kRight.press = () => {
		pVelX = 1;
	}
	kRight.release = () => {
		pVelX = 0;
	}

	kUp = keyboard("ArrowUp");
	kUp.press = () => {
		pVelY = -1;
	}
	kUp.release = () => {
		pVelY = 0;
	}

	kDown = keyboard("ArrowDown");
	kDown.press = () => {
		pVelY = 1;
	}
	kDown.release = () => {
		pVelY = 0;
	}

	app.ticker.add(delta => loop(delta));
}

window.onload = function init() {
	let type = "WebGL";
	if (!PIXI.utils.isWebGLSupported()) {
		type = "canvas";
	}

	PIXI.utils.sayHello(type);

	app = new PIXI.Application({
		width: window_width,
		height: window_height,
		antialias: true,
		transparent: false,
		resolution: 1
	});

	document.body.appendChild(app.view);

	app.loader
		.add("assets/shrek.png")
		.load(setup);
}

function loop(delta) {
	
	movePlayer(delta);
	checkBounds();
}

function quit() {
	app.stage.removeChild(playerSprite);
}
