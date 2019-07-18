<?php

	$errors = [];
	
	if (!empty($_POST)) {	// si post no esta vacio, significa que el usuario ha metido los datos
		checkInputs($errors); // de modo que comprobamos los datos de los campos
		
		if (empty($errors)) {	// si los campos son correctos, pasamos a la base de datos
			checkDatabase($errors);
			// manejamos los errores dentro del procedimiento, para reutilizar la variable PDO
		}
		
	}
	
	function checkInputs(&$errors) {	// errores esta por referencia
		// encriptamos la contrasena
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
	
	function checkDatabase(&$errors) {
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
			
			// devuelve cuantos usuarios hay con ese correo. Si hay alguno, no podemos crear el nuevo usuario (ya existe).
			if (count($result)) {
				echo "<br><b>There's already a user with that email and password!</b> Change any field and try it again.<br>";
			}
			else {	// si count vale 0 significa que no hay ningun usuario con esos datos introducidos, asi que lo insertamos
				$statement = $db->prepare('insert into user(email, password, created_at, updated_at) values(:uEmail, :uPassw, now(), now());');
				// [we already filtered user's email and password]
				$statement->bindParam(':uEmail', $filteredEmail, PDO::PARAM_STR);
				$statement->bindParam(':uPassw', $filteredPassword, PDO::PARAM_STR);
				// ejecutamos el insert del usuario en la base de datos:
				$statement->execute();
				echo "<b>User added to the database!</b><br><br>";
			}
						
		} catch (PDOException $e) {
			$errors[] = sprintf('Error when dealing with the database. Try again later, please.');
		}
	}
	
?>

<!DOCTYPE html>

<html>

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>Register URSELF.</title>
	</head>

	<body>
		<ul>
			<?php foreach ($errors as $key => $error) {?>
				<li><p><?php echo $error?></p></li>
			<?php } ?>
		
		</ul>
				
		<form action="register.php" method="POST">
			Email:<input type="text" name="email">
			Password:<input type="text" name="password">
			<input type="submit" name="register" value="Register">
		</form>
		
		<a href="login.php"> Go to login.php!<br></a>
		
	</body>

</html>
