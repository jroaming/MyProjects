
//Conexiones con el servidor y cargar el .json, etc.
//Funciones de no-game


var request;  // variable que usaremos para ajax
var token = "e18585f4-62f5-4e6d-aed5-77a14cb71bf9"; // token del server
var listaSlots;	// variable que usaremos para obtener la lista de partidas
var aux;	// variable auxiliar donde almacenaremos temporalmente informacion del servidor

var requestLista;
var obj;

function disableGestionar(value) {
	document.getElementById('cnueva').disabled = value;
	document.getElementById('c1').disabled = value;
	document.getElementById('c2').disabled = value;
	document.getElementById('g1').disabled = value;
	document.getElementById('g2').disabled = value;
	document.getElementById('b1').disabled = value;
	document.getElementById('b2').disabled = value;
	document.getElementById('parriba').disabled = value;
	document.getElementById('izquierda').disabled = value;
	document.getElementById('derecha').disabled = value;
	document.getElementById('pabajo').disabled = value;

	if (value) {
		document.getElementById('cnueva').style.opacity = .5;
		document.getElementById('c1').style.opacity = .5;
		document.getElementById('c2').style.opacity = .5;
		document.getElementById('g1').style.opacity = .5;
		document.getElementById('g2').style.opacity = .5;
		document.getElementById('b1').style.opacity = .5;
		document.getElementById('b2').style.opacity = .5;
		document.getElementById('parriba').style.opacity = .5;
		document.getElementById('pabajo').style.opacity = .5;
		document.getElementById('izquierda').style.opacity = .5;
		document.getElementById('derecha').style.opacity = .5;
	} else {
		document.getElementById('cnueva').style.opacity = 1;
		document.getElementById('c1').style.opacity = 1;
		document.getElementById('c2').style.opacity = 1;
		document.getElementById('g1').style.opacity = 1;
		document.getElementById('g2').style.opacity = 1;
		document.getElementById('b1').style.opacity = 1;
		document.getElementById('b2').style.opacity = 1;
		document.getElementById('parriba').style.opacity = 1;
		document.getElementById('pabajo').style.opacity = 1;
		document.getElementById('izquierda').style.opacity = 1;
		document.getElementById('derecha').style.opacity = 1;
	}
	
}

function manageSlot(orden, slot) {
	requestLista = new XMLHttpRequest();
	// primero siempre pedimos la lista de partidas del servidor
	requestLista.open("GET", "http://puigpedros.salleurl.edu/pwi/pac4/partida.php?token="+token, true);
	requestLista.onreadystatechange = function() {
		// tratamos la respuesta del servidor
		if (requestLista.readyState == 4 && requestLista.status == 200) {	// si no ha habido ningun problema
			// ha recibido bien la lista de slots
			listaSlots = JSON.parse(requestLista.responseText);

			// Una vez reciba respuesta del server, gestionara si el jugador quiere
			// cargar, borrar o guardar partida y, de cada uno, el slot en cuestion.
			if (orden == "cargar") {	// si queremos cargar una partida del server:
				// descargamos la info del slot en cuestion del servidor
				getSlot(slot);

			} else if (orden == "guardar") {	// si queremos guardar una partida en el server
				// intentamos guardar la partida en uno de los dos slots del servidor
				if (!searchSlot(slot)) saveSlot(slot);
				else alert("¡Ese slot ya está ocupado!");

			} else if (orden == "borrar") {
				// borramos la partida de uno de los dos slots del server
				deleteSlot(slot);
			}

			getSlotList();

		} else if (requestLista.readyState == 4 && requestLista.status == 404) {	// si no ha recibido bien la lista
			alert("¡No se ha podido recibir la lista de partidas!");
		}
	};
	requestLista.send(null);
	
}


function getSlot(slot) {
	request = new XMLHttpRequest();

	// alert("We now getting this slot: "+slot+"!");
	request.open('get', "http://puigpedros.salleurl.edu/pwi/pac4/partida.php?token="+token+"&slot="+slot, true);
	request.onreadystatechange = function() {
		if (request.readyState == 4 && request.status == 200) {

			alert("Cargando partida!");
			// guardamos el json del slot del server en nuestra variable
			partida = JSON.parse(request.responseText);
			console.log(partida);

			// pedimos los datos y, si todo es correcto, empezamos la partida
			if (slot == "nueva") {
				disableGestionar(true);
				pedirDatosIniciales();
			}
			if (slot == "1" || slot == "2") empezarPartida();

		} else if (request.readyState == 4 && request.status == 404) {
			alert("No se ha podido cargar la partida "+slot+"!");
		}
	};
	request.send(null);
}

function saveSlot(slot) {
	// cuando vamos a guardar el json tenemos que resetear los stats adicionales por los objetos al original del player
	unloadItemStats();

	request = new XMLHttpRequest();

	// stringifeamos la variable .json del juego actual (partida)
	var defJSON = JSON.stringify(partida);

	request.open('post', "http://puigpedros.salleurl.edu/pwi/pac4/partida.php?token="+token+"&slot="+slot, true);
	request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	request.onreadystatechange = function() {
		if (request.readyState == 4 && request.status == 404)
			alert("¡Error al guardar! (Borra el slot #"+slot+" antes de guardar)");
		else if (request.readyState == 4 && request.status == 200)
			alert("¡Partida guardada correctamente en el slot #"+slot+"!");
	};
	request.send("json="+defJSON);  //aqui enviamos el archivo .json
}

function deleteSlot(slot) {

	if (searchSlot(slot)) {	// si encuentra la partida en la lista

		request = new XMLHttpRequest();

		request.onreadystatechange = function() {
			if (request.readyState == 4 && request.status == 404) {
				alert("¡No se ha podido eliminar la partida #"+slot+"! (el slot ya está vacío)");
			} else if (request.readyState == 4 && request.status == 200) {
				alert("¡Partida #"+slot+" eliminada correctamente!");
			}
		};
		request.open('DELETE', "http://puigpedros.salleurl.edu/pwi/pac4/partida.php?token="+token+"&slot="+slot, true);
		request.send();  //aqui enviamos el archivo .json

	} else {
		alert("¡No hay ninguna partida guardada en el slot #"+slot+"!");
	}
}

// funcion que busca la partida de la orden en la lista local
function searchSlot(slot) {
	for (var i = 0; i < listaSlots.length; i++) {
		if (listaSlots[i] == slot) {
			return true;
		}
	}
	return false;
}

function getSlotList() {
	requestLista = new XMLHttpRequest();
	// primero siempre pedimos la lista de partidas del servidor
	requestLista.open("GET", "http://puigpedros.salleurl.edu/pwi/pac4/partida.php?token="+token, true);
	requestLista.onreadystatechange = function() {
		// tratamos la respuesta del servidor
		if (requestLista.readyState == 4 && requestLista.status == 200) {	// si no ha habido ningun problema
			// ha recibido bien la lista de slots
			listaSlots = JSON.parse(requestLista.responseText);

		} else if (requestLista.readyState == 4 && requestLista.status == 404) {	// si no ha recibido bien la lista
			alert("¡Error al actualizar la lista de partidas!");
		}
	};
	requestLista.send(null);
}

function limpiarDivStats(estado) {
	document.getElementById("nombre").style.display = estado;
	document.getElementById("nivel").style.display = estado;
	document.getElementById("ataque").style.display = estado;
	document.getElementById("defensa").style.display = estado;
	document.getElementById("vida").style.display = estado;
}

function pedirDatosIniciales() {
	// vaciar si los stats estaban creados (nombre nivel y tal del jugador)
	if (playing) {
		limpiarDivStats("none");
	}

	/*creamos input para introducir nombre*/
	var inputName = document.createElement("INPUT");
	inputName.setAttribute("type","text");
	var buttonName = document.createElement("INPUT");
	buttonName.setAttribute("type","button");
	buttonName.setAttribute("value","Aceptar");

	var datos = document.getElementById("datos");

	var pregunta = document.createElement('p');
	pregunta.innerHTML="¿Cuál es tu nombre?";
	pregunta.id="pregunta";
	datos.appendChild(pregunta);

	var divInput = document.createElement('div');
	divInput.appendChild(inputName);
	divInput.appendChild(buttonName);

	datos.appendChild(divInput);
  //datos.innerHTML = "<p> ¿Cual es tu nombre? </p>";
  //datos.appendChild(inputName);
  //datos.appendChild(buttonName);

	buttonName.onclick = function() {
		if (inputName != "" && inputName != " ") {
			disableGestionar(false);

			// Si hemos introducido un nombre valido, EMPEZAMOS LA PARTIDA
			partida.player.nombre = inputName.value;

			empezarPartida();

		} else {
			alert("Nombre no válido!");
		}
	}
}
