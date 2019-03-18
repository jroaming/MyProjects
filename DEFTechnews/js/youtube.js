// El objetivo de este script es llamar a los relacionados, vídeos de YouTube (contactar con la api y mostrar un vídeo)

// API vars and methods:

var request;
var obj;
var yt_api_key = "AIzaSyCDZ_Be9g8rXCiun_QmOgHiJ0TirUdf1pQ";


var player_ready = false;
var tag;
var player;


function getSearchResults(search) {
	request = new XMLHttpRequest();
	
	request.open('get', 'https://www.googleapis.com/youtube/v3/search?q='+searchToUrl(search)+'&part=snippet&type=video&key='+yt_api_key, true);
	request.send(null);
	request.onload = manageResults;
	console.log(request.responseText);
  
	var aviso = document.createElement('p');
	aviso.innerHTML = "Searching related videos...";
	aviso.style = "color: white; font-size: 40px;";
	document.getElementById('player').appendChild(aviso);

}

function searchToUrl(text) {
  return text.replace(" ","%20");
}

function manageResults() {
	if (request.status == 200 && request.readyState == 4) {
		obj = JSON.parse(request.responseText);
		
		// Ponemos el titulo del video en el figcaption de la imagen:
		document.getElementById('caption').innerHTML = "Related video: "+obj.items[0].snippet.title+" ->";
				
		if (obj.items.length != 0) {	// si hemos recibido algun resultado:
			console.log(obj);
			player = new YT.Player('player', {
				height: '564',
				width: '960',
				videoId: obj.items[0].id.videoId,
				events: {
				  'onReady': onPlayerReady,
				  'onStateChange': onPlayerStateChange
			    }
			});
		}
	}
	
}

// PLAYER vars and methods

//2. This code loads the IFrame Player API code asynchronously.
function setScript() {
	tag = document.createElement('script');
	
	tag.src = "https://www.youtube.com/iframe_api";
	var firstScriptTag = document.getElementsByTagName('script')[0];
	firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

}

// 3. This function creates an <iframe> (and YouTube player)
//    after the API code downloads.

function onYouTubeIframeAPIReady() {
  player_ready = true;
  
}


// 4. The API will call this function when the video player is ready.
function onPlayerReady(event) {	// esto es para el autoplay (ahora desactivado)
  //event.target.playVideo();
}

// 5. The API calls this function when the player's state changes.
//    The function indicates that when playing a video (state=1),
//    the player should play for six seconds and then stop (solo durante el testeo).
var done = false;
function onPlayerStateChange(event) {
	/*
	if (event.data == YT.PlayerState.PLAYING && !done) {
    	setTimeout(function() {player.pauseVideo();}, 6000);
    	done = true;
  	}
	 */
}
