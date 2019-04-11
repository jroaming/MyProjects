// game sets
var board;  // div board node
var cell = [];

// players
var p1;
var p2;

// interacted
var shipsInteracted;

// array of pos. interacted (so 1 player cannot fire the same pos twice)
var interacted = function() {
  this.arr = [];
  // methods
  this.posRepeated = function(pos) {
    for (var i = 0; i < this.arr.length; i++) {
      if (this.arr[i] == pos) return true;
    }
  };
  this.addPos = function(pos) {
    this.arr.push(pos);
  };
};

// funcs/players attributes
var ship = function(x,y) {
  this.x = x;
  this.y = y;
};

var player = function(name) {
  this.name = name;
  this.ships = [];
  this.shipsAlive = 0;

  this.hasShipHere = function(pos) {
    for (var i = 0; i < this.shipsAlive; i++) {
      if (this.ships[i].x == getCol(pos) && this.ships[i].y == getRow(pos)) return true;
    }
    return false;
  };

  this.removeShip = function(pos) {
    for (var i = 0; i < this.shipsAlive; i++) {
      if (this.ships[i].x == getCol(pos) && this.ships[i].y == getRow(pos)) {
        this.ships.splice(i, 1);
        this.shipsAlive--;
      }
    }
  };
};

//functions that returns the col and row value of a pos
function getCol(pos) {
  return pos[3] + pos[4];
}
function getRow(pos) {
  return pos[1];
}

// game booleans
var playing;  // boolean if music is playing
var gameover = false;
var settingP1 = false;  // when players are setting their ships on the board
var settingP2 = false;  // when players are setting their ships on the board
var gameReady = false;  // when players have set their ships
var playingP1 = false;  // if it's P1's torn
var playingP2 = false;  // if it's P2's torn
var winner;             // winner of the game

// funcs to load game
function editCells() {
  cell[0][0].innerHTML = "1";
  cell[0][0].classList.add('cellScope');
  cell[0][1].innerHTML = "2";
  cell[0][1].classList.add('cellScope');
  cell[0][2].innerHTML = "3";
  cell[0][2].classList.add('cellScope');
  cell[0][3].innerHTML = "4";
  cell[0][3].classList.add('cellScope');
  cell[1][4].innerHTML = "a";
  cell[1][4].classList.add('cellScope');
  cell[2][4].innerHTML = "b";
  cell[2][4].classList.add('cellScope');
  cell[3][4].innerHTML = "c";
  cell[3][4].classList.add('cellScope');
  cell[4][4].innerHTML = "d";
  cell[4][4].classList.add('cellScope');
  cell[0][7].innerHTML = "1";
  cell[0][7].classList.add('cellScope');
  cell[0][8].innerHTML = "2";
  cell[0][8].classList.add('cellScope');
  cell[0][9].innerHTML = "3";
  cell[0][9].classList.add('cellScope');
  cell[0][10].innerHTML = "4";
  cell[0][10].classList.add('cellScope');
  cell[1][6].innerHTML = "a";
  cell[1][6].classList.add('cellScope');
  cell[2][6].innerHTML = "b";
  cell[2][6].classList.add('cellScope');
  cell[3][6].innerHTML = "c";
  cell[3][6].classList.add('cellScope');
  cell[4][6].innerHTML = "d";
  cell[4][6].classList.add('cellScope');
  cell[0][4].classList.add('cellEmpty');
  cell[0][6].classList.add('cellEmpty');
  cell[0][5].classList.add('cellBlack');
  cell[1][5].classList.add('cellBlack');
  cell[2][5].classList.add('cellBlack');
  cell[3][5].classList.add('cellBlack');
  cell[4][5].classList.add('cellBlack');
}

function createBoard() {
  board = document.getElementById("board");

  for (var row = 0; row < 5; row++) {
    cell[row] = [];
    for (var col = 0; col < 11; col++) {
      cell[row][col] = document.createElement('div');
      cell[row][col].classList.add('cell');
      // le anadimos un 0 delante si es inferior a 10, para que sea mas facil de leer
      if (col < 9) cell[row][col].id = "F"+row+"C0"+(col+1);
      else cell[row][col].id = "F"+row+"C"+(col+1);
      cell[row][col].addEventListener("click", interact, false);

      board.appendChild(cell[row][col]);
    }
  }

  editCells();
}

function initPlayers() {
  p1 = new player("Player A");
  p2 = new player("Player B");

  // player1 starts setting his ships
  playingP1 = true;
  settingP1 = true;
}

var interact = function(ev) {
  var pos = ev.target.id;

  if (!playing) { // plays music if it's still off
    document.getElementById('audio').play();
    document.getElementById('audio').volume = 0.2;
    playing = true;
  }

  if (!gameover) {        // if player is setting his ships
    if (gameReady) {
      // Controlar jugadores y disparos
      fireShips(pos);
    } else {
      // Los jugadores están 'seteando' sus barcos (posiciones)
      setShips(pos);
    }
  }
};

function validPosition(player, pos) {
  if (getRow(pos) < 1 || getRow(pos) > 4) {
    alert("¡Posición no válida!");
    return false;
  }
  else {
    if (player == p1) {
      if (getCol(pos) < 1 || getCol(pos) > 4) {
        alert("¡Posición no válida!");
        return false;
      }
    }
    if (player == p2) {
      if (getCol(pos) < 8 || getCol(pos) > 11) {
        alert("¡Posición no válida!");
        return false;
      }
    }
  }
  return true;
}

function setShips(pos) {
  var div, img;

  if (playingP1 && p1.shipsAlive < 4) {
    // add a ship
    if (p1.hasShipHere(pos)) alert("¡Ya tienes un barco ahí! Elige otra casilla :P");
    else {
      if (validPosition(p1, pos)) {  // si llega al else, debemos crear el barco:
          p1.ships.push(new ship(getCol(pos), getRow(pos)));
          p1.shipsAlive++;
          div = document.getElementById(pos);
          img = document.createElement('img');
          img.style.pointerEvents = "none"; //para que la imagen no sea targeteable (si no, la pos da error)
          img.src = "img/ship_p1.png";
          div.appendChild(img);
          if (p1.shipsAlive >= 4) {
            settingP1 = false;
            settingP2 = true;
          }
      }
    }

    if (p1.shipsAlive >= 4) {
      // next player plays
      playingP1 = false;
      playingP2 = !playingP1;
    }
  } else {
    if (playingP2 && p2.shipsAlive < 4) {
      // add a ship
      if (p2.hasShipHere(pos)) alert("¡Ya tienes un barco ahí! Elige otra casilla :P");
      else {
        if (validPosition(p2, pos)) {  // si llega al else, debemos crear el barco:
            p2.ships.push(new ship(getCol(pos), getRow(pos)));
            p2.shipsAlive++;
            div = document.getElementById(pos);
            img = document.createElement('img');
            img.style.pointerEvents = "none"; //para que la imagen no sea targeteable (si no, la pos da error)
            img.src = "img/ship_p2.png";
            div.appendChild(img);
        }
      }

      if (p2.shipsAlive >= 4) {
        // next player plays
        playingP2 = false;
        playingP1 = !playingP2;
        settingP2 = false;
        gameReady = true;
      }
    }
  }
}

function fireShips(pos) {
  var fired = false;
  var img;
  if (playingP1) {
    if (validPosition(p2, pos)) { // porque esta disparando en una posicion de la zona de P2.
      if (shipsInteracted.posRepeated(pos)) {
        alert("¡Ya has comprobado esa posición!");
      }
      else {
        shipsInteracted.addPos(pos);
        fired = true;
        if (p2.hasShipHere(pos)) {  // si le ha dado a un barco
          p2.removeShip(pos);
          document.getElementById(pos).getElementsByTagName("img")[0].src = "img/hit.png";
          alert("¡Barco hundido! Quedan: "+p2.shipsAlive+".");

        } else {  // si no le ha dado a  ninguno
          img = document.createElement('img');
          img.src = "img/miss.png";
          if (document.getElementById(pos) != null) document.getElementById(pos).appendChild(img);
          alert("¡Has fallado!");

        }
      }
    }
  } else {
    if (playingP2) {
      if (validPosition(p1, pos)) { // porque esta disparando en una posicion de la zona de P2.
        if (shipsInteracted.posRepeated(pos)) {
          alert("¡Ya has comprobado esa posición!");
        }
        else {
          shipsInteracted.addPos(pos);
          fired = true;
          if (p1.hasShipHere(pos)) {  // si le ha dado a un barco
            p1.removeShip(pos);
            document.getElementById(pos).getElementsByTagName("img")[0].src = "img/hit.png";
            alert("¡Barco hundido! Quedan: "+p1.shipsAlive+".");

          } else {  // si no le ha dado a  ninguno
            img = document.createElement('img');
            img.src = "img/miss.png";
            if (document.getElementById(pos) != null) document.getElementById(pos).appendChild(img);
            alert("¡Has fallado!");

          }
        }
      }
    }
  }
  if (fired) {
    playingP1 = playingP2;
    playingP2 = !playingP1;
  }
}

// main function
window.onload = function() {
  //alert("Bienvenido a 'Hundir la Flota!'");
  createBoard();
  initPlayers();

  shipsInteracted = new interacted();

  var loop = setInterval(function() {
      if (playingP1) {  // si es el turno del jugador 1, el otro se transparenta
        document.getElementById("p2").style.opacity = 0.25;
        document.getElementById("p1").style.opacity = 1;
        if (gameReady) document.getElementById("instrucciones").innerHTML = "¡Turno del jugador 1 para disparar!";
      }
      if (playingP2) {  // si es el turno del jugador 2, el otro se transparenta
        document.getElementById("p1").style.opacity = 0.25;
        document.getElementById("p2").style.opacity = 1;
        if (gameReady) document.getElementById("instrucciones").innerHTML = "¡Turno del jugador 2 para disparar!";
      }
      if (settingP1) {  // si estan poniendo los barcos en el tablero
        document.getElementById("instrucciones").innerHTML = "El jugador 1 debe colocar "+(4-p1.shipsAlive)+" barcos en su zona.";
      } else if (settingP2) {
        document.getElementById("instrucciones").innerHTML = "El jugador 2 debe colocar "+(4-p2.shipsAlive)+" barcos en su zona.";
      }
      // comprueba si alguno de los dos jugadores se ha quedado sin barcos:
      if ((!settingP1 && !settingP2) && p1.shipsAlive == 0) {
        document.getElementById("instrucciones").innerHTML = "¡¡El jugador 2 ha ganado!!";
        gameover = true;
        document.getElementById("p2").style.backgroundColor = "yellow";
        document.getElementById("p2").style.border = "3px solid white";
        document.getElementById("p1").style.opacity = 0;
        document.getElementById("p2").style.opacity = 1.0;
      }
      if ((!settingP1 && !settingP2) && p2.shipsAlive == 0) {
        document.getElementById("instrucciones").innerHTML = "¡¡El jugador 1 ha ganado!!";
        gameover = true;
        document.getElementById("p1").style.backgroundColor = "yellow";
        document.getElementById("p1").style.border = "3px solid white";
        document.getElementById("p2").style.opacity = 0;
        document.getElementById("p1").style.opacity = 1.0;
      }
      if (gameover) clearInterval(loop);
  }, 100);

};
