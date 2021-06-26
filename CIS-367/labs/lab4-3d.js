import * as THREE from '../Common/three.js-master/build/three.module.js';
import { OrbitControls } from '../Common/three.js-master/examples/jsm/controls/OrbitControls.js';

// Globals
const FOV 	 = 75;
const NEAR 	 = 0.1;
const FAR	 = 100;

let renderer,
	camera,
	scene,
	controls;

let boxMesh,
	sphereMesh,
	octahedronMesh;

function main() {
	const canvas = document.querySelector('#c');
	renderer = new THREE.WebGLRenderer({
		canvas,
		antialias: true,
		precision: "highp"
	});
	renderer.setSize(canvas.clientWidth, canvas.clientHeight);
	renderer.setClearColor(0x999999, 1);
	camera = new THREE.PerspectiveCamera(FOV, canvas.clientWidth / canvas.clientHeight, NEAR, FAR);
	camera.position.z = 2;

	controls = new OrbitControls(camera, renderer.domElement);
	controls.listenToKeyEvents(window);
	controls.screenSpacePanning = false;

	scene = new THREE.Scene();

	// Light
	const light = new THREE.DirectionalLight(0xffffff, 1);
	light.position.set(-1, 2, 4);
	scene.add(light);

	// Objects
	const blue = new THREE.MeshPhongMaterial({ color: 0x0000ff });
	const red = new THREE.MeshPhongMaterial({ color: 0xff0000 });
	const green = new THREE.MeshPhongMaterial({ color: 0x00ff00 });

	const box = new THREE.BoxGeometry(1, 1, 1);
	boxMesh = new THREE.Mesh(box, blue);
	boxMesh.position.x = -2;
	boxMesh.position.y = -0.5;
	boxMesh.position.z = -1;
	scene.add(boxMesh);

	const sphere = new THREE.SphereGeometry(.5, 64, 64);
	sphereMesh = new THREE.Mesh(sphere, red);
	sphereMesh.position.y = 0.25;
	sphereMesh.position.z = 0.25;
	scene.add(sphereMesh);

	const octahedron = new THREE.OctahedronGeometry(.5);
	octahedronMesh = new THREE.Mesh(octahedron, green);
	octahedronMesh.position.x = 2;
	scene.add(octahedronMesh);	

	renderer.render(scene, camera);

	requestAnimationFrame(render);
}

function render(dt) {
	let time = dt * 0.001; // seconds

	boxMesh.rotation.x = time;
	boxMesh.rotation.y = time / 2;
	boxMesh.rotation.z = time;
	
	sphereMesh.rotation.z = time;

	octahedronMesh.rotation.x = time;
	octahedronMesh.rotation.y = time;
	
	controls.update();

	renderer.render(scene, camera);

	requestAnimationFrame(render);
}

window.onload = main;

