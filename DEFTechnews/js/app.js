  var RETURN_BUTTON = 10009,
      SELECT_BUTTON = 13,
      LEFT_ARROW_BUTTON = 37,
      UP_ARROW_BUTTON = 38,
      RIGHT_ARROW_BUTTON = 39,
      DOWN_ARROW_BUTTON = 40,
      COLOR_RED = 403,
      MEDIA_PLAY_PAUSE = 10252,
      MEDIA_PLAY = 415,
      MEDIA_PAUSE = 19,
      MEDIA_REWIND = 412,
      MEDIA_STOP = 413,
      VOLUME_UP = 447,
      VOLUME_DOWN = 448;
  var lobby = true;
  var level = 1;
  var catalog = 1;
  var actualNew = 1;
  var news1;
  var news2;
  var news3;
  var news4;
  var pause = 1;
  var exit = false;

// other vars
var title_selected = "";

// useful func to shorten news titles:
function shortenTitle(text) {
	if (text.length > 40)
		return text.substring(0, 40) + "... [+" + (text.length - 40) + "chars]";
	else
		return text;
}

  function loadApi1() {
    	var request = new XMLHttpRequest();

    	request.open('GET', 'https://newsapi.org/v2/top-headlines?sources=engadget&apiKey=2b67cffc69604f6db02d93043c691a33', true);
    	request.onload = function () {

    	    var data = JSON.parse(this.response);
    	    news1 = data;

    	    document.getElementById("prova-title1").innerHTML = shortenTitle(data.articles[0].title);
    	    document.getElementById("prova-img1").src = data.articles[0].urlToImage;
    	    document.getElementById("prova-title2").innerHTML = shortenTitle(data.articles[1].title);
    	    document.getElementById("prova-img2").src = data.articles[1].urlToImage;
    	    document.getElementById("prova-title3").innerHTML = shortenTitle(data.articles[2].title);
    	    document.getElementById("prova-img3").src = data.articles[2].urlToImage;
    	    document.getElementById("prova-title4").innerHTML = shortenTitle(data.articles[3].title);
    	    document.getElementById("prova-img4").src = data.articles[3].urlToImage;
    	};

    	request.send();
  }

  function loadApi2() {
    	var request = new XMLHttpRequest();

    	request.open('GET', 'https://newsapi.org/v2/top-headlines?sources=mashable&apiKey=2b67cffc69604f6db02d93043c691a33', true);
    	request.onload = function () {

    	    var data = JSON.parse(this.response);
    	    news2 = data;

    	    document.getElementById("prova-title5").innerHTML = shortenTitle(data.articles[0].title);
    	    document.getElementById("prova-img5").src = data.articles[0].urlToImage;
    	    document.getElementById("prova-title6").innerHTML = shortenTitle(data.articles[1].title);
    	    document.getElementById("prova-img6").src = data.articles[1].urlToImage;
    	    document.getElementById("prova-title7").innerHTML = shortenTitle(data.articles[2].title);
    	    document.getElementById("prova-img7").src = data.articles[2].urlToImage;
    	    document.getElementById("prova-title8").innerHTML = shortenTitle(data.articles[3].title);
    	    document.getElementById("prova-img8").src = data.articles[3].urlToImage;
    	};

    	request.send();
}

  function loadApi3() {
    	var request = new XMLHttpRequest();

    	request.open('GET', 'https://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=2b67cffc69604f6db02d93043c691a33', true);
    	request.onload = function () {

    	    var data = JSON.parse(this.response);
    	    news3 = data;

    	    document.getElementById("prova-title9").innerHTML = shortenTitle(data.articles[0].title);
    	    document.getElementById("prova-img9").src = data.articles[0].urlToImage;
    	    document.getElementById("prova-title10").innerHTML = shortenTitle(data.articles[1].title);
    	    document.getElementById("prova-img10").src = data.articles[1].urlToImage;
    	    document.getElementById("prova-title11").innerHTML = shortenTitle(data.articles[2].title);
    	    document.getElementById("prova-img11").src = data.articles[2].urlToImage;
    	    document.getElementById("prova-title12").innerHTML = shortenTitle(data.articles[3].title);
    	    document.getElementById("prova-img12").src = data.articles[3].urlToImage;
    	};

    	request.send();
}

  function loadApi4() {
    	var request = new XMLHttpRequest();

    	request.open('GET', 'https://newsapi.org/v2/top-headlines?sources=the-verge&apiKey=2b67cffc69604f6db02d93043c691a33', true);
    	request.onload = function () {

    	    var data = JSON.parse(this.response);
    	    news4 = data;

    	    document.getElementById("prova-title13").innerHTML = shortenTitle(data.articles[0].title);
    	    document.getElementById("prova-img13").src = data.articles[0].urlToImage;
    	    document.getElementById("prova-title14").innerHTML = shortenTitle(data.articles[1].title);
    	    document.getElementById("prova-img14").src = data.articles[1].urlToImage;
    	    document.getElementById("prova-title15").innerHTML = shortenTitle(data.articles[2].title);
    	    document.getElementById("prova-img15").src = data.articles[2].urlToImage;
    	    document.getElementById("prova-title16").innerHTML = shortenTitle(data.articles[3].title);
    	    document.getElementById("prova-img16").src = data.articles[3].urlToImage;
    	};

    	request.send();
}

  function keyEventHandler(e) {
    if (lobby) {  //si esta en la pantalla de lobby
      if (e.keyCode === COLOR_RED || e.keyCode === MEDIA_PLAY || e.keyCode === SELECT_BUTTON) {
        document.body.removeChild(document.getElementById('lobby'));
        document.getElementById('commands').style.display = "block";
        lobby = false;
      }
    } else {
    	if (e.keyCode === MEDIA_PLAY) {
    		player.playVideo();
    	}
    	if (e.keyCode === MEDIA_PAUSE) {
    		player.pauseVideo();
    	}
    	if (e.keyCode === MEDIA_STOP || e.keyCode === MEDIA_REWIND) {
    		player.stopVideo();
    	}
    	
  		if (e.keyCode === LEFT_ARROW_BUTTON) {
  			if (level === 1 && catalog > 1) {
  				document.getElementById("news" + catalog).style.display = "none";
    			catalog--;
    			document.getElementById("news" + catalog).style.display = "flex";
    		}
  			if (level === 2) {
  				if (actualNew > ((catalog*4)-3)) {
    				document.getElementById("prova-img" + actualNew).parentNode.style = "background-color: #24344f;";
    				document.getElementById("prova-img" + actualNew).parentNode.getElementsByTagName('p')[0].style.color = "white";
    				document.getElementById("prova-img" + actualNew).parentNode.getElementsByTagName('p')[0].style.fontWeight = "regular";
      				
    				actualNew--;
      				
    				document.getElementById("prova-img" + actualNew).parentNode.style = "background-color: #d6e7ff;";
    				document.getElementById("prova-img" + actualNew).parentNode.getElementsByTagName('p')[0].style.color = "black";
    				document.getElementById("prova-img" + actualNew).parentNode.getElementsByTagName('p')[0].style.fontWeight = "bold";
    			}
  			}
      }
      if (e.keyCode === RIGHT_ARROW_BUTTON) {
    		if (level === 1 && catalog < 4) {
    			document.getElementById("news" + catalog).style.display = "none";
    			catalog++;
    			document.getElementById("news" + catalog).style.display = "flex";
    		}
    		if (level === 2) {
    			if (actualNew < (catalog*4)) {
        				document.getElementById("prova-img" + actualNew).parentNode.style = "background-color: #24344f;";
        				document.getElementById("prova-img" + actualNew).parentNode.getElementsByTagName('p')[0].style.color = "white";
        				document.getElementById("prova-img" + actualNew).parentNode.getElementsByTagName('p')[0].style.fontWeight = "regular";

        				actualNew++;
        				
        				document.getElementById("prova-img" + actualNew).parentNode.style = "background-color: #d6e7ff;";
        				document.getElementById("prova-img" + actualNew).parentNode.getElementsByTagName('p')[0].style.color = "black";
        				document.getElementById("prova-img" + actualNew).parentNode.getElementsByTagName('p')[0].style.fontWeight = "bold";
        			}
    		}
      }
      if (e.keyCode === DOWN_ARROW_BUTTON) {
    		if (level === 1) {
				document.getElementById("prova-img" + ((catalog*4)-3)).parentNode.style = "background-color: #d6e7ff;";
				document.getElementById("prova-img" + ((catalog*4)-3)).parentNode.getElementsByTagName('p')[0].style.color = "black";
				document.getElementById("prova-img" + ((catalog*4)-3)).parentNode.getElementsByTagName('p')[0].style.fontWeight = "bold";
    			level=2;
    			actualNew = ((catalog*4)-3);
    		}
    		if (level === 0) {
    			level=1;
    			document.getElementById("news" + catalog).style.display = "flex";
    			// mostramos de nuevo la guia de comandas
    			document.getElementById('commands').style.display = "block";
    			document.getElementById("title").style.display = "block";
    			document.getElementById("catalog").style.display = "block";
    			document.getElementById("trailer").style.display = "none";
    			document.getElementById("video").pause();
    			pause=1;
    		}
      }
      if (e.keyCode === UP_ARROW_BUTTON) {
    		if (level === 1) {
    			// escondemos la guia de comandas
    			document.getElementById('commands').style.display = "none";
    			level=0;
    			document.getElementById("news" + catalog).style.display = "none";
    			document.getElementById("title").style.display = "none";
    			document.getElementById("catalog").style.display = "none";
    			document.getElementById("trailer").style.display = "block";
    		}

    		if (level === 2) {
    			
    			var i = 1;
    			while(i<17){
    				document.getElementById("prova-img" + i).parentNode.opacity = 0.35;
    				document.getElementById("prova-img" + i).parentNode.style = "background-color: #24344f;";
    				document.getElementById("prova-img" + i).parentNode.getElementsByTagName('p')[0].style.color = "white";
    				document.getElementById("prova-img" + i).parentNode.getElementsByTagName('p')[0].style.fontWeight = "regular";
    				i++;
    			}
    			level=1;
    		}
      }
      if (e.keyCode === SELECT_BUTTON) {
    		if (level === 3) {
    			var link;


    			if (catalog === 1) {
    				link = news1.articles[actualNew-1].url;
    			}

    			if (catalog === 2) {
    				link = news2.articles[actualNew-5].url;
    			}

    			if (catalog === 3) {
    				link = news3.articles[actualNew-9].url;
    			}

    			if (catalog === 4) {
    				link = news4.articles[actualNew-13].url;
    			}
    			createAndUploadTweet(document.getElementById("new-title").textContent + "link: " + link);
    			document.getElementById('new-shared').style.display = "block";
    			document.getElementById("img_twitter").style.opacity = 0.3;	//efecto de que ya has clickado twitter
		    }

    		if (level === 2) {
    			
    			// escondemos el logo, para tener más espacio
    			document.getElementById("logo").style.display = "none";
    			// escondemos los comandos del catalogo
    			document.getElementById('commands').style.display = "none";
    			
    			// actualizamos los datos y los display
    			document.getElementById("news" + catalog).style.display = "none";
    			document.getElementById("title").style.display = "none";
    			document.getElementById("catalog").style.display = "none";
    			document.getElementById("new").style.display = "block";
    			document.getElementById("img_twitter").style.opacity = 1;	//efecto de que aun no has clickado twitter (reseteo)

    			if (catalog === 1) {
    				document.getElementById("new-title").innerHTML = news1.articles[actualNew-1].title;
    				document.getElementById("new-content").innerHTML = news1.articles[actualNew-1].content;
    				document.getElementById("new-image").src = news1.articles[actualNew-1].urlToImage;
    				document.getElementById("new-author").innerHTML = news1.articles[actualNew-1].author + " - " + news1.articles[actualNew-1].publishedAt;
    			}

    			if (catalog === 2) {
    				document.getElementById("new-title").innerHTML = news2.articles[actualNew-5].title;
    				document.getElementById("new-content").innerHTML = news2.articles[actualNew-5].content;
    				document.getElementById("new-image").src = news2.articles[actualNew-5].urlToImage;
    				document.getElementById("new-author").innerHTML = news2.articles[actualNew-5].author + " - " + news2.articles[actualNew-5].publishedAt;
    			}

    			if (catalog === 3) {
    				document.getElementById("new-title").innerHTML = news3.articles[actualNew-9].title;
    				document.getElementById("new-content").innerHTML = news3.articles[actualNew-9].content;
    				document.getElementById("new-image").src = news3.articles[actualNew-9].urlToImage;
    				document.getElementById("new-author").innerHTML = news3.articles[actualNew-9].author + " - " + news3.articles[actualNew-9].publishedAt;
    			}

    			if (catalog === 4) {
    				document.getElementById("new-title").innerHTML = news4.articles[actualNew-13].title;
    				document.getElementById("new-content").innerHTML = news4.articles[actualNew-13].content;
    				document.getElementById("new-image").src = news4.articles[actualNew-13].urlToImage;
    				document.getElementById("new-author").innerHTML = news4.articles[actualNew-13].author + " - " + news4.articles[actualNew-13].publishedAt;
    			}



				//loadPlayer
				title_selected = document.getElementById('new-title').textContent;
				console.log(title_selected);
				getSearchResults(title_selected);

    			level=3;
    		}
    		if (level === 0) {
    			if (pause === 1){
    				document.getElementById("video").play();
    				pause=0;
    			}else{
    				document.getElementById("video").pause();
    				pause=1;
    			}
    		}
      }
      if (e.keyCode === RETURN_BUTTON) {
      		if (level === 3) {
      			// borramos el titulo del video relacionado anterior
      			document.getElementById('caption').innerHTML = "";
      			// mostramos de nuevo los comandos del catálogo
      			document.getElementById('commands').style.display = "block";
      			// mostramos de nuevo el logo
      			document.getElementById("logo").style.display = "block";
      			// ocultamos el texto de 'compartido en twitter'
      			document.getElementById('new-shared').style.display = "none";
      			
      			// borramos el player y reseteamos el div
      			var element = document. getElementById('player');
      			element.parentNode. removeChild(element);
      			var renew_div = document.createElement('div');
      			renew_div.id = 'player';
      			document.getElementById('media-content').appendChild(renew_div); 
      			
      			
  				document.getElementById("news" + catalog).style.display = "flex";
  				document.getElementById("title").style.display = "block";
  				document.getElementById("catalog").style.display = "block";
  				document.getElementById("new").style.display = "none";
    			level=2;
    		}else{
    			tizen.application.getCurrentApplication().exit();
    		}
      }
    }
  }

  function bindDefaultEvents() {
      document.addEventListener('keydown', keyEventHandler);
  }

  function changeLobbyImg() {
	  document.getElementById('lobby_img').src = "image/lobby_loaded.png";
  } 
  
  function beautifyCSS() {
	  // aqui modificamos el style más concreto de los objetos, para mejorar el aspecto
	  
	  if (level == 1) {	// si estamos en el catalogo, todos oscuros y luego resaltamos el actual:
		  // resaltamos el titulo del catalogo
		  document.getElementById('catalog-title').style.backgroundColor = "black";
		  document.getElementById('catalog-title').parentNode.style.opacity = 1;
		  // oscurecemos el titulo de las noticias
		  document.getElementById('title').style.opacity = 0.25;
		  document.getElementById('title').style.backgroundColor= "transparent";
		  
		  var i = 1;
		  while(i<17){
			  document.getElementById("prova-img" + i).parentNode.style.opacity = 0.35;
			  i++;
		  }

		  document.getElementById('cat1').style.opacity = 0.25;
		  document.getElementById('cat1').style.height = "100px";
		  document.getElementById('cat1').style.border= "none";
		  document.getElementById('cat2').style.opacity = 0.25;
		  document.getElementById('cat2').style.height = "100px";
		  document.getElementById('cat2').style.border= "none";
		  document.getElementById('cat3').style.opacity = 0.25;
		  document.getElementById('cat3').style.height = "100px";
		  document.getElementById('cat3').style.border= "none";
		  document.getElementById('cat4').style.opacity = 0.25;
		  document.getElementById('cat4').style.height = "100px";
		  document.getElementById('cat4').style.border= "none";
		  document.getElementById('cat'+catalog).style.opacity = 1;
		  document.getElementById('cat'+catalog).style.height= "150px";
		  document.getElementById('cat'+catalog).style.border= "solid yellow 4px";
		  
	  } else {
		  if (level == 2) {
			  // oscurecemos ligeramente el catalogo actual y modificamos el titulo de la seccion:
			  document.getElementById('catalog-title').style.backgroundColor = "transparent";
			  document.getElementById('catalog-title').parentNode.style.opacity = 0.35;
			  document.getElementById('title').style.opacity= 1;
			  document.getElementById('title').style.backgroundColor= "black";

			  var i = 1;
			  while(i<17){
				  if (i != actualNew) document.getElementById("prova-img" + i).parentNode.style.opacity = 0.35;
				  i++;
			  }

		  }
	  }
  }
  
  
  function init() {
	  // siempre mantenemos oculta la scrollbar:
	  document.body.style.overflow = 'hidden';
	  
	  // descargamos el script de YT para crear un reproductor
	  setScript();
	  
	  loadApi1();
	  loadApi2();
	  loadApi3();
	  loadApi4();
	  includeCodebird();	// 1. We include codebird (la libreria)
	  setAPIKeys();		// 2. We set our API keys
	  setAccessTokens();	// 3. We set our tokens in order to access into our account
	  bindDefaultEvents();
	  
	  changeLobbyImg();
	  setInterval(function() {
		  if (exit) clearInterval(this);
		  else beautifyCSS();
	  }, 50);
  }

  window.onload = init;
