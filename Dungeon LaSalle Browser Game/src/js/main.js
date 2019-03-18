var partida;  // json con toda la info de la partida (obtenida del server)
var playing;  // booleano que indica que la partida ya ha empezado

var fighting = false; // booleano que indica que el jugador esta peleando contra un enemigo
// variables temporales durante el combate (con los stats actualizados, para ahorrar operaciones)
var finTurno = false; // cada vez que el jugador ataca y el enemigo (si sobrevive) lo ataca a él es un turno

var item; // int que vale 1 2 o 3 segun el item a equipar

// musica
var music = document.getElementById('music');
var playing_music = false;

/*********************************************************************************** FUNCIONES UTILES */
// atoi
function atoi(str) {
  if (str == "1") {
    return 1;
  } else if (str == "2") {
    return 2;
  } else if (str == "3") {
    return 3;
  } else {
    return -1;
  }
}

// funcion que recibe un nombre del objeto y devuelve el indice de lalista
function stringToIndex(aux) {
  for (var i=0; i<14; i++) {
    if (partida.objetos[i].nombre == aux) {
      return i;
    }
  }
  return -1;
}

// funcion de gestion de contenido (elimina el div indicado por la id del parametro)
function clearBox(elementID){
  if(elementID == "visor" || elementID == "datos"){
      document.getElementById(elementID).innerHTML = "";
  }else{
      document.getElementById(elementID).visible = false;
  }
}

// funcion que devuelve un random entre 0 y el numero pasado por parámetro
function getRandom(max) {
  return Math.floor((Math.random() * 2));
}

// funcion que bloquea los botones de gestion de partida
function disableButtons(value) {
  document.getElementById('c1').disabled = value;
  document.getElementById('c2').disabled = value;
  document.getElementById('g1').disabled = value;
  document.getElementById('g2').disabled = value;
  document.getElementById('b1').disabled = value;
  document.getElementById('b2').disabled = value;
  document.getElementById('izquierda').disabled = value;
  document.getElementById('derecha').disabled = value;
  document.getElementById('pabajo').disabled = value;

}

/************************************************************************************** FUNCIONES DEL JUEGO */
window.onload = function () {
  // al cargarse la ventana, iniciamos el juego:
  creaMapa();
};

function iniciarJuego() {
  creaMapa();
}

function creaMapa(){
  var minimapa = document.createElement('div');
  minimapa.classList.add("mapa-container");
  for(var i=0; i<10;i++){
    var fila = document.createElement('div');
    minimapa.appendChild(fila);
    for(var j=0; j<10;j++){
        var casella = document.createElement('div');
        casella.id="f"+j+"c"+i;
        casella.classList.add("mapa-item");
        fila.appendChild(casella);
    }
  }

  var grid = document.getElementById('minimapa');
  grid.appendChild(minimapa);
}

function pintaMapa(){
  // el indice del mapa[] a cargar siempre es 3 veces superior al nivel:
  // nivel -3 = mapa[0]
  // nivel -2 = mapa[1]
  // nivel -1 = mapa[2]
  // nivel 0 = partida terminada
  var nivelPlayer = partida.player.estadoPartida.nivel + 3;
  var mapa = partida.mapa[nivelPlayer];
  var player_x = partida.player.estadoPartida.x;
  var player_y = partida.player.estadoPartida.y;
  var player_dir = partida.player.estadoPartida.direccion;
  var minimapa = document.getElementsByClassName('mapa-container');


  //Segons el que trobem en la casella, ho pintem d'un color o altre
  var celda;
  var i = 0, j = 0;
  for (i=0; i<10; i++) {
    for (j=0; j<10; j++) {
      celda = document.getElementById('f'+i+'c'+j); // pillamos la casilla del minimapa
      // y cambiamos el color
      if (mapa[i][j] == 0) {
        celda.style.backgroundColor = "#ffd59e";
      } else if (mapa[i][j] == 1) {
        celda.style.backgroundColor = "#5e5e5e";
      } else if (mapa[i][j] == 2) {
        celda.style.backgroundColor = "#ea0000";
      } else if (mapa[i][j] == 3) {
        celda.style.backgroundColor = "#cbce10";
      } else if (mapa[i][j] == 4) {
        celda.style.backgroundColor = "#ffd59e";
      } else if (mapa[i][j] == 5) {
        celda.style.backgroundColor = "#35fff8";
      }
    }
  }
  // por ultimo, pintamos al jugador:
  document.getElementById('f'+player_y+'c'+player_x).style.backgroundColor = "white";

  // ahora ocultamos todo lo que no este a una casilla del jugador
  for (i=0; i<10; i++) {
    for (j=0; j<10; j++) {
      celda = document.getElementById('f'+i+'c'+j); // pillamos la casilla del minimapa
      // le metemos minima opacidad
      celda.style.opacity = 0;
      //celda.style.opacity = 0.5;  // DESCOMENTAMOS ESTO SI QUEREMOS DEBUGGAR
    }
  }

  // comprobamos donde esta para subirle la opacidad a las casillas de alrededor
  document.getElementById('f'+player_y+'c'+player_x).style.opacity = 1.0;
  document.getElementById('f'+(player_y-1)+'c'+(player_x)).style.opacity = 0.75;
  document.getElementById('f'+(player_y+1)+'c'+(player_x)).style.opacity = 0.75;
  document.getElementById('f'+(player_y)+'c'+(player_x-1)).style.opacity = 0.75;
  document.getElementById('f'+(player_y)+'c'+(player_x+1)).style.opacity = 0.75;
  document.getElementById('f'+(player_y-1)+'c'+(player_x-1)).style.opacity = 0.75;
  document.getElementById('f'+(player_y-1)+'c'+(player_x+1)).style.opacity = 0.75;
  document.getElementById('f'+(player_y+1)+'c'+(player_x-1)).style.opacity = 0.75;
  document.getElementById('f'+(player_y+1)+'c'+(player_x+1)).style.opacity = 0.75;

  // ahora iluminamos mas la casilla hacia la que está mirando el jugador
  var aux;
  switch (player_dir) {
      case 0:
        document.getElementById('f'+(player_y-1)+'c'+(player_x)).style.opacity = 1;
        aux = document.getElementById('f'+(player_y-2)+'c'+(player_x));
        break;

      case 1:
        document.getElementById('f'+(player_y+1)+'c'+(player_x)).style.opacity = 1;
        aux = document.getElementById('f'+(player_y+2)+'c'+(player_x));
        break;

      case 2:
        document.getElementById('f'+(player_y)+'c'+(player_x-1)).style.opacity = 1;
        aux = document.getElementById('f'+(player_y)+'c'+(player_x-2));
        break;

      case 3:
        document.getElementById('f'+(player_y)+'c'+(player_x+1)).style.opacity = 1;
        aux = document.getElementById('f'+(player_y)+'c'+(player_x+2));
        break;
  }

  //if (partida.player.estadoPartida.direccion == 0)

}

// funcion que actualiza los stats de los jugadores en funcion del item que lleven equipado
function loadItemStats() {
	if (partida.player.manoderecha != "") {
    partida.player.ataque += partida.objetos[stringToIndex(partida.player.manoderecha)].ataque;
    partida.player.defensa += partida.objetos[stringToIndex(partida.player.manoderecha)].defensa;
  }
  if (partida.player.manoizquierda != "") {
    partida.player.ataque += partida.objetos[stringToIndex(partida.player.manoizquierda)].ataque;
    partida.player.defensa += partida.objetos[stringToIndex(partida.player.manoizquierda)].defensa;
  }
}

// funcion que deja el atauqe y la defensa del jugador a su nivel original:
function unloadItemStats() {
  partida.player.defensa = partida.player.nivel - 1;
  partida.player.ataque = Math.floor(partida.player.nivel/2);
}

// funcion que inicia la partida
function empezarPartida() {
  /*
  if (!playing_music) {
    music.play();
  }
  */
  // incrementamos los stats del jugador por los items que tiene equipados
  loadItemStats();
  
  // desbloqueamos los botones de guardar:
  document.getElementById('g1').disabled = false;
  document.getElementById('g1').style.opacity = 1;
  document.getElementById('g2').disabled = false;
  document.getElementById('g2').style.opacity = 1;



  // PROBANDO ASPECTO DE ITEMS
  inicializarItems();


  // limpiamos el anterior div donde habíamos pedido el nombre
  clearBox("datos");
  // cargamos los datos del jugador del json de la variable partida
  cargaHUD();
  // pintamos el minimapa
  pintaMapa();
  // dibujamos la casilla hacia donde estamos mirando
  pintaCasilla(partida.player.estadoPartida.x, partida.player.estadoPartida.y, partida.player.estadoPartida.direccion);
  gestionaArrows();

  playing = true;
}

function cargaHUD(){

  /*Colocamos los datos del jugador uno por uno*/
  var datos = document.getElementById("datos");

  var jugador_nombre = document.createElement("p");
  jugador_nombre.innerHTML = "Nombre: " + partida.player.nombre;
  datos.appendChild(jugador_nombre);

  var jugador_nivel_xp = document.createElement("p");
  jugador_nivel_xp.innerHTML = "Nivel: " + partida.player.nivel + "\t | xp: [" + partida.player.xp + "/" + ((partida.player.nivel+1)*10)+"]";
  datos.appendChild(jugador_nivel_xp);

  var jugador_vida = document.createElement("p");
  jugador_vida.innerHTML = "Vida: " + partida.player.vida + "/" + (partida.player.nivel*10);
  datos.appendChild(jugador_vida);

  var jugador_ataque = document.createElement("p");
  jugador_ataque.innerHTML = "Ataque: " + partida.player.ataque;
  datos.appendChild(jugador_ataque);

  var jugador_defensa = document.createElement("p");
  jugador_defensa.innerHTML = "Defensa: " + partida.player.defensa;
  datos.appendChild(jugador_defensa);

  /*mostramos minimapa antes oculto*/
  var mapa = document.getElementById('minimapa');
  mapa.style.visibility = 'visible';

  /*mostramos controles antes ocultos*/
  var controles = document.getElementById('controles');
  controles.style.visibility = 'visible';

  jugador_nombre.id = "nombre";
  jugador_nivel_xp.id = "nivel";
  jugador_ataque.id = "ataque";
  jugador_defensa.id = "defensa";
  jugador_vida.id = "vida";

  pintaMapa();
}

// funcion que actualiza el display de los stats del jugador despues de cada ronda de
function actualitzaDisplayStats() {
  var datos = document.getElementById("datos");
  //nombre
  document.getElementById("nivel").innerHTML = "Nivel: "+partida.player.nivel+"\t | xp: ["+partida.player.xp+"/"+((partida.player.nivel+1)*10)+"]";
  document.getElementById("vida").innerHTML = "Vida: "+partida.player.vida+"/"+(partida.player.nivel*10);
  document.getElementById("ataque").innerHTML = "Ataque: "+partida.player.ataque;
  document.getElementById("defensa").innerHTML = "Defensa: "+partida.player.defensa;
}

/*  Pinta imagen en el visor */
function pintaNav(src, x, y) {
  // Consigue el canvas
  var canvas = document.getElementById('visor');
  var context = canvas.getContext('2d');
  var base_image = new Image();
  base_image.src = "./media/images/"+src;
  base_image.onload = function () {
    canvas.width = 400;  // borramos el canvas antes de pintar la nueva imagen
    // Pinta imagen en el canvas
    context.drawImage(base_image, 0, 0, 400, 400);
  };
}

/* Pinta al visor lo que hay en el mapa */
function pintaCasilla(x, y, dir) {
  switch (dir) {
      case 0:
          pintaNav(mapaToImg(x, y-1), 0, 0);
      break;
      case 1:
          pintaNav(mapaToImg(x, y+1), 0, 0);
      break;
      case 2:
          pintaNav(mapaToImg(x-1, y), 0, 0);
      break;
      case 3:
          pintaNav(mapaToImg(x+1, y), 0, 0);
      break;
  }
}

// funcion que devuelve el nombre del archivo que dibujar en el visor segun el valor del mapa
function mapaToImg(x, y){
  if (partida.mapa[partida.player.estadoPartida.nivel + 3][y][x] == 0){
    return "dungeon_step.png";
  }else if (partida.mapa[partida.player.estadoPartida.nivel + 3][y][x] == 1){
    return "dungeon_wall.png";
  }else if (partida.mapa[partida.player.estadoPartida.nivel + 3][y][x] == 2){
    return "dungeon_enemy.png";
  }else if (partida.mapa[partida.player.estadoPartida.nivel + 3][y][x] == 3){
    return "dungeon_door.png";
  }else if (partida.mapa[partida.player.estadoPartida.nivel + 3][y][x] == 4){
    return "dungeon_step.png";
  }else if (partida.mapa[partida.player.estadoPartida.nivel + 3][y][x] == 5){
    return "dungeon_exit.png";
  }
}

function gestionaArrows(){
  document.addEventListener('keydown', (event) => {
    var key = event.key;
    if(key=='ArrowLeft'){
      gestionaControls("izquierda");
    }else{
      if(key=='ArrowUp'){
        gestionaControls("parriba");
      }else{
        if(key=='ArrowRight'){
          gestionaControls("derecha");
        }else{
          if(key=='ArrowDown'){
            gestionaControls("pabajo");
          }
        }
      }
    }
  });
}

//funcio que gestiona les fletxes dels controls
function gestionaControls(id) {
  var jugador = partida.player.estadoPartida;

  /*  Direcciones:
  0 - Norte
  1 - Sur
  2 - Este
  3 - Oeste
   */
  if (id == "parriba") {
    if (fighting) { // si estamos peleando, el boton de arriba es para atacar
      jugarTurno(); // cada vez que el usuario pulse, jugaremos turno
      actualitzaDisplayStats();
    }
    else {
      switch (jugador.direccion) {
        // si es un muro (1), no puede pasar, así que no lo controlamos y ya esta.
        // si es un enemigo, llama a la funcion de comenzar el combate
        // si es camino libre (0), pasa
        // si es un objeto puede recogerlo (FALTA CONTROLARLO)
          case 0:
            if (partida.mapa[jugador.nivel+3][jugador.y-1][jugador.x] == 0 ||
              partida.mapa[jugador.nivel+3][jugador.y-1][jugador.x] == 4) {
                // si la casilla es 0 o 4 (posicion inicial) avanza sin problemas
              jugador.y = jugador.y-1;
            } else if (partida.mapa[jugador.nivel+3][jugador.y-1][jugador.x] == 2) {
              if (!fighting) empezarCombate();
              else {
                jugarTurno();
                actualitzaDisplayStats();
              }
            } else if (partida.mapa[jugador.nivel+3][jugador.y-1][jugador.x] == 3) {
              subirPlantaMazmorra();
            } else if (partida.mapa[jugador.nivel+3][jugador.y-1][jugador.x] == 5) {
              terminarJuego(true);  // termina el juego con victoria = true
            }
            break;

          case 1:
            if (partida.mapa[jugador.nivel+3][jugador.y+1][jugador.x] == 0 ||
              partida.mapa[jugador.nivel+3][jugador.y+1][jugador.x] == 4) {
              jugador.y = jugador.y+1;
            } else if (partida.mapa[jugador.nivel+3][jugador.y+1][jugador.x] == 2) {
              if (!fighting) empezarCombate();
            } else if (partida.mapa[jugador.nivel+3][jugador.y+1][jugador.x] == 3) {
              subirPlantaMazmorra();
            } else if (partida.mapa[jugador.nivel+3][jugador.y+1][jugador.x] == 5) {
              terminarJuego();
            }
            break;

          case 2:
            if (partida.mapa[jugador.nivel+3][jugador.y][jugador.x-1] == 0 ||
              partida.mapa[jugador.nivel+3][jugador.y][jugador.x-1] == 4) {
              jugador.x = jugador.x-1;
            } else if (partida.mapa[jugador.nivel+3][jugador.y][jugador.x-1] == 2) {
              if (!fighting) empezarCombate();
            } else if (partida.mapa[jugador.nivel+3][jugador.y][jugador.x-1] == 3) {
              subirPlantaMazmorra();
            } else if (partida.mapa[jugador.nivel+3][jugador.y][jugador.x-1] == 5) {
              terminarJuego();
            }
            break;

          case 3:
            if (partida.mapa[jugador.nivel+3][jugador.y][jugador.x+1] == 0 ||
              partida.mapa[jugador.nivel+3][jugador.y][jugador.x+1] == 4) {
              jugador.x = jugador.x+1;
            } else if (partida.mapa[jugador.nivel+3][jugador.y][jugador.x+1] == 2) {
              if (!fighting) empezarCombate();
            } else if (partida.mapa[jugador.nivel+3][jugador.y][jugador.x+1] == 3) {
              subirPlantaMazmorra();
            } else if (partida.mapa[jugador.nivel+3][jugador.y][jugador.x+1] == 5) {
              terminarJuego();
            }
            break;
      }
    }

  }else{
    if(id == "pabajo"){
      switch (partida.player.estadoPartida.direccion) {
          case 0:
            partida.player.estadoPartida.direccion = 1;
            break;
          case 1:
            partida.player.estadoPartida.direccion = 0;
            break;
          case 2:
            partida.player.estadoPartida.direccion = 3;
            break;
          case 3:
            partida.player.estadoPartida.direccion = 2;
            break;
      }
    }else{
      if(id == "derecha"){
        switch (partida.player.estadoPartida.direccion) {
            case 0:
              partida.player.estadoPartida.direccion = 3;
              break;
            case 1:
              partida.player.estadoPartida.direccion = 2;
              break;
            case 2:
              partida.player.estadoPartida.direccion = 0;
              break;
            case 3:
              partida.player.estadoPartida.direccion = 1;
              break;
        }

      } else {
        if(id == "izquierda"){
          switch (partida.player.estadoPartida.direccion) {
              case 0:
                partida.player.estadoPartida.direccion = 2;
                break;
              case 1:
                partida.player.estadoPartida.direccion = 3;
                break;
              case 2:
                partida.player.estadoPartida.direccion = 1;
                break;
              case 3:
                partida.player.estadoPartida.direccion = 0;
                break;
          }
        }
      }
    }
  }

  // actualizamos el minimapa
  pintaMapa();
  // actualizamos el canvas
  pintaCasilla(partida.player.estadoPartida.x, partida.player.estadoPartida.y, partida.player.estadoPartida.direccion);
  /*
  console.log(partida.player.estadoPartida.x);
  console.log(partida.player.estadoPartida.y);
  console.log(partida.player.estadoPartida.direccion);
  */
}

// funcion que sube al jugador de planta y lo situa en la nueva posicion y actualiza el hud
function subirPlantaMazmorra() {
  var celda;

  partida.player.estadoPartida.nivel = partida.player.estadoPartida.nivel + 1;
  // actualizamos la posicion inicial del jugador en la planta
  for (var j=0; j<10; j++) for (var i=0; i<10; i++) {
    celda = document.getElementById('f'+i+'c'+j);
    if (partida.mapa[partida.player.estadoPartida.nivel+3][j][i] == 4) {
      partida.player.estadoPartida.x = i;
      partida.player.estadoPartida.y = j;
    }
  }
  partida.player.estadoPartida.direccion = 1; // que mire p'abajo, donde no hay muro

  // actualizamos el minimapa y el visor:
  pintaMapa();
  pintaCasilla(partida.player.estadoPartida.x, partida.player.estadoPartida.y, partida.player.estadoPartida.direccion);
}


/************************************************************************************* COMBATE */

// funcion que devuelve un item de la lista de objetos del .json
function getItem(index) {
  return partida.objetos[index];
}

// funcion que añade objetos a los enemigos (que inicialmente solo tienen una espada de doran) segun su nivel
function agregarObjetosEnemigo(nivel) {
  var count_items = 0;
  var lista = [];
  lista.push("Doran's Blade");
  var item;
  while (count_items < nivel) {
    item = getItem(getRandom(partida.objetos.length)).nombre;
    // agrega otro item (si el nivel del enemigo es 2, agregara uno mas a la lista, si es 3, agregara 2, etc.)
    lista.push(item);
    count_items++;
  }

  return lista;
}

// funcion que carga los stats del enemigo contra quien luchara el jugador
function cargarStats() {
  // IMPROTANTE: OBJETOS Y MOCHILA ES UNA LISTA DE STRINGS, NO CONTIENE LOS STATS DE LOS ITEMS, SOLO EL NOMBRE

  // los enemigos tienen el mismo nivel que el jugador, pero sus stats escalan de forma distinta:
  // lo unico que escala segun el nivel en los enemigos es la vida (lo tenemos que recalcular)  
  partida.enemigo.nivel = partida.player.nivel;
  partida.enemigo.vida = 8*partida.enemigo.nivel;
  partida.enemigo.xp = partida.enemigo.vida;
  partida.enemigo.defensa = partida.enemigo.nivel;
  partida.enemigo.ataque = Math.floor(partida.enemigo.nivel/2);
  partida.enemigo.objeto = partida.objetos[getRandom(partida.objetos.length)].nombre; // el enemigo tinee un item random

}

// funcion que sube la experiencia
function subirExperiencia() {
  partida.player.xp += partida.enemigo.xp;
  if (partida.player.xp >= 10*(partida.player.nivel+1)) subirNivel();
  
  console.log(partida.player);
}

// funcion que sube de nivel a nuestro jugador
function subirNivel() {
  partida.player.defensa++;
  if (partida.player.nivel > 1 && partida.player.nivel%2 != 0) partida.player.ataque++;
  partida.player.vida = partida.player.vida + partida.player.nivel*10;
  partida.player.nivel++;
}

// funcion que controla todo el procedimiento de combate, desde que empieza hasta que termina
function empezarCombate() {
  fighting = true;

  // inicia el primer turno
  jugarTurno();
  actualitzaDisplayStats();

  // mientras estamos en combate, anulamos todos los controles (para que el jugador no haga trampa) de los botones
  disableButtons(true);
}

// funcion que hace el procedimiento de atacar
function attackRequest(att, def) {
  var request = new XMLHttpRequest();
  request.open('get', 'http://puigpedros.salleurl.edu/pwi/pac4/ataque.php?token='+token+'&ataque='+att+'&defensa='+def, false); // que sea síncrono, será mas sencillo
  request.send();

  if (request.status == 200 && request.readyState == 4) {
    // ha atacado correctamente y hemos recibido un valor
    var num = request.responseText;
    if (num < 0) num = 0;
    
    return num;
  }
  else if (request.status == 404 && request.readyState == 4) {
    alert("¡Error al atacar! Vuelve a intentarlo.");
  }

  return -1;  // si llega aqui, ha habido algun error
}

// funcion que juega un turno del combate:
function jugarTurno() {
  
  alert("¡Empieza un nuevo turno!");
  var estado = 0; // si es 0 significa que durante el turno todo ha ido bien (ajax, etc.)
  
  // El jugador ataca primero:
  estado = attackRequest(partida.player.ataque, partida.enemigo.defensa);
  alert("El jugador ataca: "+estado+" de daño");
  if (estado >= 0) {
    // le quitamos la vida al enemigo:
    partida.enemigo.vida -= estado;
    if (partida.enemigo.vida < 0) partida.enemigo.vida = 0;
    alert("Vida del enemigo: "+partida.enemigo.vida+"puntos.");

    if (partida.enemigo.vida > 0) { // si el enemigo aun está vivo, nos ataca
      estado = attackRequest(partida.player.ataque, partida.enemigo.defensa);
      
      if (estado >= 0) {
        // de nuevo, no ha habido ningun error
        partida.player.vida -= estado;
        alert("El enemigo ataca: "+estado+" de daño");

        if (partida.player.vida < 0) partida.player.vida = 0;
        alert("Vida de "+partida.player.nombre+": "+partida.player.vida+"puntos.");

        // comprobamos que no nos haya matao
        if (partida.player.vida <= 0) { //  si nos ha matao
          terminarJuego(false);
          fighting = false;
        }
      }

    } else {  // si hemos eliminado al enemigo
      actualizarMapa(); // tenemos que quitar al enemigo del mapeado
      var loRecoge = prompt("¡Enemigo eliminado! ¿Quieres recoger: "+partida.enemigo.objeto+"? [Y/N]");
      if (loRecoge == "Y") {
        var slot_error = true;
        while (slot_error) {
          var item_slot = prompt("¿En qué slot quieres almacenar el item? [1/2/3]");
          if (item_slot == 1 || item_slot == 2 || item_slot == 3) {
            slot_error = false;
          }
          agregaItem(partida.enemigo.objeto, atoi(item_slot));
          if (slot_error) alert("Error, elige entre 1, 2 o 3.");
        }
      }
      subirExperiencia();

      fighting = false;
      cargarStats();  // reseteamos los datos del enemigo

    }
  }

  if (estado < 0) {
    alert("Un error ha ocurrido a lo largo del combate: ¡vuelve a atacar!");
    // Ha habido un error, de modo que el jugador debe volver a comenzar el turno
  }

  if (!fighting) {  // si la pelea ha acabado:
      // al terminar el combate los dejamos como estaban
      disableButtons(false);
  }

}

function actualizarMapa() {
  switch (partida.player.estadoPartida.direccion) {
    case 0:
      partida.mapa[partida.player.estadoPartida.nivel + 3][partida.player.estadoPartida.y-1][partida.player.estadoPartida.x] = 0;
      break;
    case 1:
      partida.mapa[partida.player.estadoPartida.nivel + 3][partida.player.estadoPartida.y+1][partida.player.estadoPartida.x] = 0;
      break;
    case 2:
      partida.mapa[partida.player.estadoPartida.nivel + 3][partida.player.estadoPartida.y][partida.player.estadoPartida.x-1] = 0;
      break;
    case 3:
      partida.mapa[partida.player.estadoPartida.nivel + 3][partida.player.estadoPartida.y][partida.player.estadoPartida.x+1] = 0;
      break;
      
  }
}

function recogerObjeto() {
  
}

function terminarJuego(victoria) {
  if (victoria)
    alert("¡Has ganao!");
  else 
    alert("¡Has perdido! JA.");
    pintaNav("death.png", 0, 0);
  // poner todo en pausa

}

function toggleItem(item){
  var minimenu = document.getElementById("panellOpcions"), maxH = "80px";
  var itemButton = document.getElementById("item"+item);
  item = this.item;
  if(minimenu.style.height == maxH){
    minimenu.style.height = "0px";
    itemButton.style.backgroundColor = 'black';
  }else{
    minimenu.style.height = maxH;
    itemButton.style.backgroundColor = '#353535';
  }
}

function equiparDreta(){
  var itemButton = document.getElementById("item"+item);
  var aux = "";
  if(itemButton.getAttribute('itemname') != ""){
    aux = itemButton.getAttribute('itemname');
    itemButton.setAttribute('itemname', partida.player.manoderecha);
    partida.player.manoderecha = aux;
    //posar imatges corresponents
  }
}

function equiparEsquerra(){
  var itemButton = document.getElementById("item"+item);
  var aux = "";
  if(itemButton.getAttribute(itemname) != ""){
    aux = itemButton.getAttribute(itemname);
    itemButton.setAttribute("itemname", partida.player.manoizquierda);
    partida.player.manoizquierda = aux;
    //posar imatges corresponents
  }
}

function borrarItem(){
  var itemButton = document.getElementById("item"+item);
  itemButton.setAttribute(itemname, "");
  //treure imatge
}

function agregaItem(nomItem, slot){
  var itemButton = document.getElementById("item"+slot);
  itemButton.setAttribute("itemname", nomItem);
  itemButton.style.backgroundImage = url(partida.objetos[stringToIndex(nomItem)].imagen);
}

function inicializarItems() {
    

  var itemButton = document.getElementById("item1");

  itemButton.setAttribute("itemname", "");
  itemButton.style.width = "80px";
  itemButton.style.height = "80px";
  itemButton.style.backgroundImage = "url('./img/classic_sword.png')";
  itemButton.style.backgroundSize = "60px 60px";
  itemButton.style.backgroundPosition = 'center center';
  itemButton.style.borderColor = 'red';
  itemButton.style.boorderRadius = '2px';
  itemButton.style.backgroundColor = 'black';
  itemButton.style.backgroundRepeat = 'no-repeat'

  itemButton = document.getElementById("item2");
  itemButton.setAttribute("itemname", "");
  itemButton.style.width = "80px";
  itemButton.style.height = "80px";
  itemButton.style.backgroundSize = "60px 60px";
  itemButton.style.backgroundPosition = 'center center';
  itemButton.style.borderColor = 'red';
  itemButton.style.boorderRadius = '2px';
  itemButton.style.backgroundColor = 'black';
  itemButton.style.backgroundRepeat = 'no-repeat'

  itemButton = document.getElementById("item3");
  itemButton.setAttribute("itemname", "");
  itemButton.style.width = "80px";
  itemButton.style.height = "80px";
  itemButton.style.backgroundSize = "60px 60px";
  itemButton.style.backgroundPosition = 'center center';
  itemButton.style.borderColor = 'red';
  itemButton.style.boorderRadius = '2px';
  itemButton.style.backgroundColor = 'black';
  itemButton.style.backgroundRepeat = 'no-repeat'

}