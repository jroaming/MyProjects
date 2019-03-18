/*

var json_to_send = {
	player:	{
		nombre:"",
		nivel:0,
		vida:10,
		xp:0,
		ataque:0,
		defensa:0,
		manoderecha:"Doran's Blade",
		manoizquierda:"",
		mochila:[],
		estadoPartida: {
			x:1,
			y:8,
			nivel:-3,
			direccion:0
		}
	},

	enemigo: {
		vida:0,
		ataque:0,
		defensa:0,
		xp:0,
		objetos:["Doran's Blade"]
	},

	objetos: [
		{
			nombre: "Doran's Blade",
			ataque: 1,
			defensa: 0,
			vida: 0,
			imagen: "https://i.pinimg.com/originals/9c/5e/aa/9c5eaad2413dc02f8b637b2f198284f0.png",
			equipable: true
		},
		{
			nombre: "Diamond Sword",
			ataque: 5,
			defensa: 0,
			vida: 3,
			imagen: "http://pixelartmaker.com/art/b493cbb8333d5cc.png",
			equipable: true
		},
		{
			nombre: "Classic Sword",
			ataque: 2,
			defensa: 0,
			vida: 0,
			imagen: "https://ya-webdesign.com/images1280_/minecraft-swords-png-8.png",
			equipable: true
		},
		{
			nombre: "Devil's Sword",
			ataque: 4,
			defensa: -1,
			vida: 0,
			imagen: "https://ya-webdesign.com/images1280_/minecraft-tools-png-1.png",
			equipable: true

		},
		{
			nombre: "Stick",
			ataque: 1,
			defensa: 2,
			vida: 0,
			imagen: "https://ya-webdesign.com/images1280_/minecraft-stick-png-1.png",
			equipable: true
		},
		{
			nombre: "Knive",
			ataque: 2,
			defensa: 1,
			vida: 1,
			imagen: "https://ya-webdesign.com/images1280_/minecraft-iron-sword-png-6.png",
			equipable: true
		},
		{
			nombre: "Common Shield",
			ataque: 0,
			defensa: 1,
			vida: 0,
			imagen: "https://i.imgur.com/KxjKC4h.png",
			equipable: true
		},
		{
			nombre: "Gold Shield",
			ataque: 2,
			defensa: 3,
			vida: 0,
			imagen: "https://lh3.googleusercontent.com/OLp5sfAJt92yxZJ-03tyXI4CdQZQ3rC1Vqf9nvTmysAbyssIeJMzwmjxOsEyKPhzRDqLE482J81kDkh5YfeJ-g",
			equipable: true
		},
		{
			nombre: "Diamond Shield",
			ataque: 3,
			defensa: 5,
			vida: -1,
			imagen: "https://lh3.googleusercontent.com/-N9i6RjJ6dCIR3m1l0RlFigguKbmez8Ya-MZt82JHDGTcZ5r6Eb2Aw0q0aMjVObkcD_sl_YdCoez3_hKbnd5dHM",
			equipable: true
		},
		{
			nombre: "Jewel",
			ataque: 2,
			defensa: 1,
			vida: 0,
			imagen: "https://vignette.wikia.nocookie.net/hexxit/images/d/db/Silky_Jewel_ig.png",
			equipable: false
		},
		{
			nombre: "Copper",
			ataque: 0,
			defensa: 0,
			vida: 1,
			imagen: "https://vignette.wikia.nocookie.net/hexxit/images/0/0b/Copper_Ingot_ig.png",
			equipable: false
		},
		{
			nombre: "Lava Jewel",
			ataque: 1,
			defensa: 1,
			vida: 0,
			imagen: "https://vignette.wikia.nocookie.net/hexxit/images/0/0d/Lava_Crystal_ig.png",
			equipable: false
		},
		{
			nombre: "Emerald",
			ataque: 0,
			defensa: 1,
			vida: 5,
			imagen: "https://d1u5p3l4wpay3k.cloudfront.net/minecraft_gamepedia/6/6a/Emerald.png",
			equipable: false
		},
		{
			nombre: "Mario",
			ataque: 0,
			defensa: 0,
			vida: 10,
			imagen: "https://ih0.redbubble.net/image.39047923.0157/pp,550x550.jpg",
			equipable: false
		}
	],

	mapa: [
				[
					[1, 1, 1, 1, 1, 1, 1, 1, 1, 1],
					[1, 3, 2, 0, 0, 1, 2, 0, 2, 1],
					[1, 1, 1, 0, 1, 2, 1, 0, 1, 1],
					[1, 0, 1, 0, 1, 0, 1, 0, 0, 1],
					[1, 2, 0, 0, 0, 0, 0, 1, 2, 1],
					[1, 1, 1, 0, 1, 1, 0, 2, 0, 1],
					[1, 0, 2, 0, 0, 1, 1, 0, 1, 1],
					[1, 0, 1, 1, 0, 2, 0, 1, 2, 1],
					[1, 0, 0, 2, 1, 1, 0, 0, 0, 1],
					[1, 1, 1, 1, 1, 1, 1, 1, 1, 1]
				],
				[
					[1, 1, 1, 1, 1, 1, 1, 1, 1, 1],
					[1, 4, 1, 2, 2, 1, 2, 0, 3, 1],
					[1, 0, 0, 0, 1, 2, 0, 1, 0, 1],
					[1, 0, 1, 0, 0, 0, 1, 1, 0, 1],
					[1, 1, 0, 0, 2, 0, 0, 0, 0, 1],
					[1, 0, 1, 0, 1, 0, 0, 1, 1, 1],
					[1, 0, 0, 0, 0, 1, 0, 0, 0, 1],
					[1, 1, 0, 1, 2, 0, 1, 0, 1, 1],
					[1, 2, 0, 2, 1, 1, 0, 0, 2, 1],
					[1, 1, 1, 1, 1, 1, 1, 1, 1, 1]
				],
				[
					[1, 1, 1, 1, 1, 1, 1, 1, 1, 1],
					[1, 0, 0, 0, 0, 2, 0, 0, 4, 1],
					[1, 2, 1, 0, 0, 1, 0, 1, 0, 1],
					[1, 1, 0, 0, 0, 0, 0, 0, 0, 1],
					[1, 0, 0, 2, 1, 2, 0, 1, 2, 1],
					[1, 0, 1, 1, 5, 1, 0, 0, 1, 1],
					[1, 2, 0, 2, 0, 2, 0, 0, 0, 1],
					[1, 0, 0, 0, 1, 0, 0, 1, 2, 1],
					[1, 1, 2, 0, 0, 0, 0, 0, 2, 1],
					[1, 1, 1, 1, 1, 1, 1, 1, 1, 1]
				]
			],

	readme:	{
		watisdis: "aqui esta el resumen y un par de cosas del juego",
		0:"nada",
		1:"muro",
		2:"enemigo",
		3:"puerta",
		4:"inicio",
		5:"final",
		notas: "X e Y van de 0 a 9, NO de 1 a 10."
	}
};


function createSlot(slot) {
	var request = new XMLHttpRequest();

	var defJSON = JSON.stringify(json_to_send);	// iguala la variable a un string con los datos del .json (comillas, etc.)
	request.open('post', "http://puigpedros.salleurl.edu/pwi/pac4/partida.php?token="+token+"&slot="+slot, true);
	request.onreadystatechange = getSlot('nueva'); // cuando recibas un cambio en el server, pide los datos de la nueva partida (funcion de abajo)
	request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	request.send("json="+defJSON);  //aqui enviamos el archivo .json
}

// funcion que recibe el json del slot que pasamos por parametro
function getSlot(slot) {
	request = new XMLHttpRequest();

	alert("We now getting this slot: "+slot+"!");
	request.open('get', "http://puigpedros.salleurl.edu/pwi/pac4/partida.php?token="+token+"&slot="+slot, true);
	request.onreadystatechange = function() {
		if (request.readyState == 4 && request.status == 200) {
			alert("Cambio detectado!");
			var obj = JSON.parse(request.responseText);
			console.log(obj);
		}
	};
	request.send(null);
}

// funcion que recibe un array con todos los slots con información
function requestAllSlots() {
	var request = new XMLHttpRequest();

	alert("We now gettin' all game slots!");
	request.open("GET", "http://puigpedros.salleurl.edu/pwi/pac4/partida.php?token="+token, true);
	request.onreadystatechange = function() {
		if (request.readyState == 4 && request.status == 200) {
			alert("Cambio detectado!");
			obj = JSON.parse(request.responseText);
			console.log(obj);
		}
	};
	request.send(null);
}

// funcion que borra el slot que le pasamos por parametro ('nueva', 1 o 2)
function deleteSlot(slot) {
	deleting = new XMLHttpRequest();

	alert("We now deletin' all game slots!");
	deleting.open('delete', "http://puigpedros.salleurl.edu/pwi/pac4/partida.php?token="+token+"&slot="+slot, true);
	deleting.onreadystatechange = requestAllSlots;
		// ^ cuando borre una partida, devuelve una lista con todos los slots que hay guardados en el servidor
	deleting.send(null);
}

// funcion que devuelve el daño efectuado por el ataque durante el combate
// IMPORTANTE: si devuelve un 2, hay que quitarle 2 de vida, si devuelve un 0 o un numero negativo (o un cero negativo), nada
function requestAttack(att, def) {
	var request = new XMLHttpRequest();
	alert("We attacking!");
	request.open("GET", "http://puigpedros.salleurl.edu/pwi/pac4/ataque.php?token="+token+"&ataque="+att+"&defensa="+def, true);
	request.onreadystatechange = function() {
		if (request.readyState == 4 && request.status == 200)
			console.log(request.responseText);
	};
	request.send(null);
}

*/
