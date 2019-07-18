<?php
	// 1. comprobamos parametros
	if ($argc != 2) {
		echo "ERROR. Usage >> php addTask.php [string]\n\n";
		exit(1);
	}
	
	// 2. intentamos obtener el archivo
	// file() para leer y crear un array con cada salto de linea en una casilla
	// file_get_contents() para almacenar el contenido del .txt en un string
	$file = file_get_contents(__DIR__ . '/tasks.txt');
	
	// 3. comprobamos que hemos podido abrir el archivo
	if (is_null($file)) {
		echo "File not found.\n\n";
		exit(1);
	}

	// 4. mostramos el estado de la info y el proceso
	echo('Adding task: ' . ($argv[1] .= "\n") . '...' . "\n");
	
	// 5. actualizamos el .txt
	file_put_contents(__DIR__ . '/tasks.txt', ($file . $argv[1]));
	echo "File 'tasks.txt' updated correctly!\n\n";
	
?>