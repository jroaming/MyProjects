var FPS = 60;
var gameStarted = false;
var main_loop;
var sec_loop;

var level = 15;

var x_size = 700;
var y_size = 300;

var canvas;
var ctx;

var music;
var playing = false;

var char;
var pedobear;
var ground;
var gameover = false;

var points = 0;

var end_img;

document.addEventListener("keydown", keyEvent);

function start() {
  // initialize our main objects
  init(canvas);

  // create the instances
  char = new char();
  pedobear = new pedobear();
  ground = new ground();

  // load the game over image but don't show it
  end_img = new Image();
  end_img.src = 'img/gameover.png';

  // prepare the music
  music = document.getElementById("music");
  if (!playing) {
    playing = true;
    music.play();
  }

  // we can start the main loop!
  gameStarted = true;
  main_loop = setInterval(game, 1000/FPS);
  sec_loop = setInterval(loadPoints, FPS*5);
}

// Funcion a repetir cada loop del juego
function game() {
  //console.log("loop");
  clearCanvas();


  ground.update();
  ground.draw();
  pedobear.tick();

  char.update();
  char.draw();

  ctx.font = "bold 25px Courier New";
  ctx.fillStyle = "white";
  ctx.fillText("Pts: "+ points,530,125);

  if (collide(char, pedobear)) endGame();
  if (gameover) {
    clearInterval(main_loop);
    clearInterval(sec_loop);
  }

}

// Funcion que inicaliza las variables mas importantes
function init(canvas) {
  // Inicializamos el canvas
  this.canvas = document.getElementById("game_canvas");

  // Contexto del canvas
  ctx = this.canvas.getContext('2d');

}

// Funcion que limpia el canvas donde dibujamos (basta con actualizar un valor de tamano)
function clearCanvas() {
  canvas.width = x_size;
}

function loadPoints() {
  points += 1;
  if (points/10 > level && level < 35) {
    level+=1;
  }
}

// Funcion que recibe las comandas del teclado
function keyEvent(key) {
  if (gameStarted && (key.keyCode == 32 ||key.keyCode == 38)) {
    if (!char.onAir) char.jump();
  }
  if (!gameStarted && key.keyCode == 13) start();
}

function endGame() {
  gameover = true;

  ctx.drawImage(end_img, 0, 0); // we show the gameover image

  ctx.font = "bold 25px Courier New";
  ctx.fillStyle = "yellow";
  ctx.fillText("Pts: "+ points,530,125);  // we show the points again

  document.getElementById('game_title').innerHTML = "GOTCHA!";
  document.getElementById('start').innerHTML = "¡Press F5 to reload!";
  music.pause();

  music.src = 'audio/doh.mp3';
  music.play();
}
