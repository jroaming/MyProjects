var renderer;
var scene;
var model;
var camera;

var cameraControl;


function createRenderer() {
    renderer = new THREE.WebGLRenderer();
    renderer.setClearColor(0x000000,1.0);
    renderer.setSize(window.innerWidth, window.innerHeight);
    renderer.shadowMap.enabled = true;
}

function createCamera(){
    camera = new THREE.PerspectiveCamera(
        45,
        window.innerWidth / window.innerHeight,
        0.1, 1000);
        
    camera.position.x = 90;
    camera.position.y = 32;
    camera.position.z = 32;
    camera.lookAt(scene.position);
    
    cameraControl = new THREE.OrbitControls(camera);
}

function init() {
    scene = new THREE.Scene();
    
    createCamera();
    createRenderer();

   // createBox();
    //createPlane();
   // createEarth(createEarthMaterial(),1,'earth');
    //createEarth(createFairClouds(),1.01,'clouds');
    createLight();
    createEnviroment();
    
    createHead();   
    document.body.appendChild(renderer.domElement);
    
    render();
}

function render() {
    renderer.render(scene,camera);

    cameraControl.update();
    //scene.getObjectByName('earth').rotation.y += 0.005;
    //scene.getObjectByName('clouds').rotation.y += 0.01;
    
    requestAnimationFrame(render);
}

//GEOMETRY!

function createBox() {
    var boxGeometry = new THREE.BoxGeometry(6,4,6);
    var boxMaterial = new THREE.MeshLambertMaterial({
        color: "red"
    });

    var box = new THREE.Mesh(boxGeometry, boxMaterial);
    box.castShadow = true;
    scene.add(box);
}


function createPlane() {
    var planeGeometry = new THREE.PlaneGeometry(20,20);
    var planeMaterial = new THREE.MeshLambertMaterial({
        color: 0xcccccc
    });

    var plane = new THREE.Mesh(planeGeometry, planeMaterial);
    plane.receiveShadow = true;
    plane.rotation.x = -0.5 * Math.PI;
    plane.position.y = -2;
    scene.add(plane);
}

function createLight() {
    var spotLight = new THREE.SpotLight(0xffffff);
    spotLight.position.set(100,10,-50);
    spotLight.shadow.camera.near = 20;
        spotLight.shadow.camera.far = 50;
    spotLight.castShadow = true;
    scene.add(spotLight);

    var directionalLight = new THREE.DirectionalLight(0xffffff,1);

    directionalLight.position.set(100,10, -50);
    directionalLight.name = 'directional';
    scene.add(directionalLight);
    
    var ambientLight = new THREE.AmbientLight(0x111111);
    scene.add(ambientLight);
}

function createFairClouds() {
    var cloudsTexture = new THREE.Texture();
    var loader = new THREE.ImageLoader();
    loader.load('assets/fair_clouds_1k.png', function (image){
        cloudsTexture.image = image;
        cloudsTexture.needsUpdate = true;
    });
    var fairCloudsMaterial = new THREE.MeshBasicMaterial();
    fairCloudsMaterial.transparent = true;
    fairCloudsMaterial.map = cloudsTexture;
    return fairCloudsMaterial;
}

function createHead(){
    var material = new THREE.MeshPhongMaterial();

    var loader = new THREE.OBJLoader();

    loader.load('assets/lee.obj', function (object) {
        
        object.traverse(function (child) {
            if (child instanceof THREE.Mesh) {
                child.material = material;
                child.receiveShadow = true;
                child.castShadow = true; 
                child.name = "model";
            }
        });
        scene.add(object);
    });
}

function createEarthMaterial() {
    var earthTexture = new THREE.Texture();
    var loader = new THREE.ImageLoader();
    loader.load('assets/earthmap2k.jpg', function (image) {
        earthTexture.image = image;
        earthTexture.needsUpdate = true;
    });



    var earthMaterial = new THREE.MeshPhongMaterial();
    earthMaterial.map = earthTexture;

    var normalTexture = new THREE.Texture();

    loader.load('assets/earth_normalmap_flat2k.jpg', function (image) {
        normalTexture.image = image;
        normalTexture.needsUpdate = true;
    });
    earthMaterial.normalMap = normalTexture;
    earthMaterial.normalScale = new THREE.Vector2(1.0,1.0);


    var specTexture = new THREE.Texture();
    loader.load('assets/lee_spec.jpg', function (image) {
        earthMaterial.specularMap = image;
        normalTexture.needsUpdate = true;
    });
    earthMaterial.specularMap = specTexture;
    earthMaterial.specular = new THREE.Color(0x262626);

    return earthMaterial;
}

function createEarth(material,scale,name) {
    var sphereGeometry = new THREE.SphereGeometry(15* scale,30 * scale,30 * scale);
    var sphereMaterial = material;
    var earthMesh = new THREE.Mesh(sphereGeometry, sphereMaterial);
    earthMesh.name = name;
    scene.add(earthMesh);
}

function createEnviroment() {
    var envGeometry = new THREE.SphereGeometry(90,32,32);

    var envMaterial = new THREE.MeshBasicMaterial();
    envMaterial.map = THREE.ImageUtils.loadTexture('assets/galaxy_starfield.png');
    envMaterial.side = THREE.BackSide;

    var mesh = new THREE.Mesh(envGeometry, envMaterial);
    scene.add(mesh);
}

init();


//IT MUST BE USED ONLY ONE TIME!!! THAT'S WHY IT APPEARS HERE, just one time!!
window.addEventListener("keydown", function(e) {
    console.log(model);
    model = scene.getObjectByName("model");
    if ( model != null) {
        switch (e.key) {
            case 'a':
                model.position.x += 1;
                break;
            case 'd':
                model.position.x -= 1;
                break;
            //default:
            //  break;
        }    
    }
});