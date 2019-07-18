<?php

	$errors = [];
	
	if (!empty($_POST)) {	// si post no esta vacio, significa que el usuario ha metido los datos
		checkInputs($errors); // de modo que comprobamos los datos de los campos
		
		if (empty($errors)) {	// si los campos son correctos, pasamos a la base de datos
			$id = checkDatabase($errors);
			
			// si no ha habido ningun error al acceder a la base de datos, seguimos con el procedimiento habitual
			if (empty($errors)) {	
				
				if ($id < 0) {	// si el usuario no aparece en la base de datos, habra devuelto un -1	
					echo "<b>User not found in database!</b> Email entered: '" . $_POST['email'] . "'.<br>";
					echo "[Make sure your password is correct if email seems right].<br><br>";
					
				} else {	// si el usuario aparece en la base de datos, la id sera la correcta
					// Mostrar mensaje de exito
					echo "<br><b>User found!</b> Welcome, " . $_POST['email'] 	. ".<br>";
					// Guardar la id del usuario en la sesión del usuario
					echo "User found's ID: <b>" . $id . "</b>.";
					
					// empezamos la sesion y guardamos la id
					session_start();
					$_SESSION['sessionID'] = $id;
					
					//header('Location: user.php');	// era para acceder a user.php directamente
					
				}
			}	// si ha habido algun error, el mensaje ya se habra mostrado anteriormente, en el checkDatabase
		}
		
		//echo "The username is:\t" . $_POST['username'] . ".";
		// La key es el nombre del campo del formulario
		//echo "The email is:\t" . $_POST['email'] . ".";
	}
	
	function checkInputs(&$errors) {	// errores esta por referencia
		$pass = md5($_POST['password']);

		if (false === filter_var($_POST['email'], FILTER_VALIDATE_EMAIL)) {
			$errors[] = sprintf('The email %s is not valid', $_POST['email']);
		}

		if (strlen($_POST['password']) < 6 ||	// minimo de 6 caracteres
			!preg_match("/[A-Z]/", $_POST['password']) ||	// si aparece alguna mayuscula
			!preg_match("/[0-9]/", $_POST['password'])) {	// si aparece algun numero
			$errors[] = sprintf('The password %s is not valid: make sure it has at least 6 characters, a CAP letter and a number', $_POST['password']);
		}
	}
	
	function checkDatabase(&$errors):int {
		$pass = md5($_POST['password']);

		echo "Fields are valid. Entering into the database...<br>";
		
		try {
			$db = new PDO('mysql:host=localhost;dbname=acs', 'homestead', 'secret', []);
			$db->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_ASSOC);
			$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
			
			// we set the parameters:
			$filteredEmail = filter_var($_POST['email'], FILTER_SANITIZE_FULL_SPECIAL_CHARS);	// filtramos los caracteres especiales
			$filteredPassword = filter_var($pass, FILTER_SANITIZE_FULL_SPECIAL_CHARS);	// filtramos los caracteres especiales
			// and finally we do the query:
			$statement = $db->prepare("select * from user where email = '" . $filteredEmail . "' and password = '" . $filteredPassword . "';");
			$statement->execute();
			
			$result = $statement->fetchAll();	// metemos los resultados en un array
			if (count($result)) {	// si ha encontrado algun resultado
				return $result[0]['id'];	// devuelve la id del usuario encontrado
			}	// si no ha encontrado ningun usuario con esos datos:
			else {
				return -1;
			}
			
		} catch (PDOException $e) {
			$errors[] = sprintf('Error trying to get into the database. Try again later, please.');
			return -1;
		}
	}
	
?>

<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>Login URSELF.</title>
	</head>

	<body>
		<ul>
			<?php foreach ($errors as $key => $error) {?>
				<li><p><?php echo $error?></p></li>
			<?php } ?>
		
		</ul>
		
		<form action="login.php" method="POST">
			Email:<input type="text" name="email">
			Password:<input type="text" name="password">
			<input type="submit" name="login" value="Login!">
		</form>

		<a href="user.php"> Go to user.php!<br></a>
		<a href="register.php"> Go to register.php!</a>
		
	</body>

</html>