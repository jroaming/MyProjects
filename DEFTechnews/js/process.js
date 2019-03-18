var cb;	// "codebird"
var api_key = "WqtRUB9oQJorHxXuuHIvZu3yp";
var api_secret_key = "kDSmELwBgQy7NOVw9wSrEq2u1Um71Z8bt75AgEl7RiWNeIUgtE";
var access_token = "1074809163472084993-Aooge4PfT5DNTR1mfVDZpVCzSqYHNk";
var access_secret_token = "OVG83utC1N1V2PGdASIbVGHhunSAW4ksrmq7JCmwmoohP";

function includeCodebird() {
	cb = new Codebird;
}

function setAPIKeys() {
	cb.setConsumerKey(api_key, api_secret_key);
}

function setAccessTokens() {
	cb.setToken(access_token, access_secret_token);
}

function createAndUploadTweet(message) {
	var params = {
	  status: "TECH NEWS - " + message
	};
	cb.__call("statuses_update", params, function(reply, rate, err) {
		// esto es la respuesta de twitter del tweet que acabamos de subir.
		console.log(reply);
	});
}