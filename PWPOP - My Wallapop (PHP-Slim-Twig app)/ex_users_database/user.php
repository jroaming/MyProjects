<?php
	session_start();
	
	// si la sessionID no existe, no hemos iniciado sesion:
	if (empty($_SESSION['sessionID'])) {
		echo "The session is not initialized! We are heading to <b>login.php</b>...";
		header('Location: login.php');
	} else {
		echo "Session detected! ID: " . $_SESSION['sessionID'] . ". Loading database info...<br>";
		
		getDBInfo();
		
	}
	
	function getDBInfo() {
		$con = new PDO('mysql:host=localhost;dbname=acs', 'homestead', 'secret', []);

		$statement = $con->query('select * from user where id = ' . $_SESSION['sessionID'] . ";");
		$results = $statement->fetchAll();	// guardamos tambien el resultado en formato array, para que la info sea mas comoda de tratar
		
		// statement deberia haber recibido los datos correctamente si el resultado llegados a este punto ha sido de una sola fila
		// (significa que la validacion del correo y la password ha sido correcta, por ejemplo, y que hemos podido conectar con
		// la base de datos anteriormente)
		
		if (!empty($statement) && $statement->rowCount() == 1) {
			echo "Name: <b>" . explode('@', $results[0]['email'])[0] . "</b><br>";
			echo "Email: <b>" . $results[0]['email'] . "</b><br><br>";
			
		} else {
			echo "Something went wrong, try it again later. Apologies.";
		}
	}
	
?>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>URSELF.</title>
	</head>
	
	<body>
		<a href="login.php"> Go to login.php!<br></a>
		<a href="register.php"> Go to register.php!<br></a>
		<a href="user.php"> Refresh user.php!<br></a>
	</body>
</html>
