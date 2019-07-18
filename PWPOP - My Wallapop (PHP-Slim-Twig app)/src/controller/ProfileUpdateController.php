<?php
/**
 * Created by PhpStorm.
 * User: selde
 * Date: 12/07/2019
 * Time: 14:13
 */

namespace SallePW\SlimApp\controller;

use Psr\Container\ContainerInterface;
use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Message\UploadedFileInterface;
use SallePW\SlimApp\model\User;

class ProfileUpdateController
{
    /** @var ContainerInterface */
    private $container;
    /** @var User */
    private $user;

    /** Allowed extensions for user's profile image */
    private const ALLOWED_EXTENSIONS = ['jpg', 'png'];

    /** @var string Directory where uploads are sent */
    private const UPLOADS_DIR = __DIR__ . '/../../public/assets/uploads/userImages';


    /**
     * ProfileController constructor.
     * @param ContainerInterface $container
     */
    public function __construct(ContainerInterface $container)
    {
        $this->container = $container;
    }

    // funcion que actualiza el nombre de usuario
    public function updateName(Request $request, Response $response) {
        $errors = [];

        // comprobamos si esta vacio
        if ($_POST['name'] == '')
            $errors[] = sprintf("Empty field!");

        else {  // si no lo esta, comprobamos el formato

            if (!ctype_alnum($_POST["name"])) {

                $errors[] = sprintf("'Name' must contain only alphanumeric characters!");

            } else {    // si el campo es valido:

                $this->getUserInfo(); // PILLAMOS TODA LA INFO DE LA DB DE ESE USUARIO _________________________________

                $this->container->get('db')->updateUserName($this->user->getUsername(), $_POST['name']); // ACTUALIZAMOS ___

                $isUserUpdated = $this->getUserInfo(); // VOLVEMOS A PILLAR EL NUEVO USUARIO PARA ACTUALIZAR EL FORMULARIO

                if (!$isUserUpdated) {  // si no se ha podido actualizar el usuario
                    $errors[] = sprintf("Error when trying to get the new info!");

                } else {    // si funsa, agregamos el mensaje y redirigimos al profile de nuevo

                    $_SESSION['message'] = "'Name' succesfully updated!";
                    return $response->withStatus(200)->withHeader('Location', '/profile');

                }

            }

        }


        $this->getUserInfo(); // pillamos la info del user (sin modificarla, obviamente)

        return $this->container->get("view")->render($response, 'profile.twig', [
            'errors' => $errors,
            'user' => $this->user
        ]);
    }

    // funcion que actualiza el email del usuario
    public function updateEmail(Request $request, Response $response) {
        $errors = [];

        // comprobamos si esta vacio
        if ($_POST['email'] == '')
            $errors[] = sprintf("Empty field!");

        else {  // si no lo esta, comprobamos el formato

            // comprobamos que el formato del email es valido
            if (false === filter_var($_POST['email'], FILTER_VALIDATE_EMAIL)) {

                $errors[] = sprintf("'Email' format must be written correctly!");

            } else {    // si el campo es valido:

                $this->getUserInfo(); // PILLAMOS TODA LA INFO DE LA DB DE ESE USUARIO _________________________________

                $this->container->get('db')->updateUserEmail($this->user->getUsername(), $_POST['email']); // ACTUALIZAMOS

                $isUserUpdated = $this->getUserInfo(); // VOLVEMOS A PILLAR EL NUEVO USUARIO PARA ACTUALIZAR EL FORMULARIO

                if (!$isUserUpdated) {  // si no se ha podido actualizar el usuario
                    $errors[] = sprintf("Error when trying to get the new info!");

                } else {

                    $_SESSION['message'] = "'Email' succesfully updated!";
                    return $response->withStatus(200)->withHeader('Location', '/profile');

                }

            }

        }

        $this->getUserInfo(); // pillamos la info del user (sin modificarla, obviamente)

        return $this->container->get("view")->render($response, 'profile.twig', [
            'errors' => $errors,
            'user' => $this->user
        ]);
    }

    // funcion que actualiza el telefono del usuario
    public function updatePhone(Request $request, Response $response) {
        $errors = [];

        // comprobamos si esta vacio
        if ($_POST['phone'] == '')
            $errors[] = sprintf("Empty field!");

        else {  // si no lo esta, comprobamos el formato

            // comprobamos que el formato del email es valido
            // comprobamos el telefono caracter a caracter, porque molamos
            $count = 0;
            $spacecount = 0;
            $valid = true;
            for ($i = 0; $i < strlen($_POST['phone']); $i++) {
                $c = $_POST['phone'][$i];
                if ($valid) {
                    if (preg_match("/[0-9 ]/", $c)) { // si equivale a un numero, perf
                        if ($c != ' ') {
                            $count++;
                        } // si no es un espacio, es digito mas
                        else {
                            $spacecount++;
                        }
                    } else {
                        $valid = false;
                    }
                }
            }
            // tras analizar la estructura del telefono ofrecido, miramos si esta es valida
            if ($count != 9 || $spacecount != 2 || !$valid) {
                // si no lo es
                $_SESSION['errors'][] = sprintf("'Phone' can only contain 8 numbers and 2 '_'s!");

            } else {    // si el campo es valido:

                $this->getUserInfo(); // PILLAMOS TODA LA INFO DE LA DB DE ESE USUARIO _________________________________

                $this->container->get('db')->updateUserPhone($this->user->getUsername(), $_POST['phone']); // ACTUALIZAMOS

                $isUserUpdated = $this->getUserInfo(); // VOLVEMOS A PILLAR EL NUEVO USUARIO PARA ACTUALIZAR EL FORMULARIO

                if (!$isUserUpdated) {  // si no se ha podido actualizar el usuario
                    $errors[] = sprintf("Error when trying to get the new info!");

                } else {

                    $_SESSION['message'] = "'Phone' succesfully updated!";
                    return $response->withStatus(200)->withHeader('Location', '/profile');

                }

            }

        }

        $this->getUserInfo(); // pillamos la info del user (sin modificarla, obviamente)

        return $this->container->get("view")->render($response, 'profile.twig', [
            'errors' => $errors,
            'user' => $this->user
        ]);
    }

    // funcion que actualiza el password de usuario
    public function updatePassword(Request $request, Response $response) {
        $errors = [];

        // comprobamos si esta vacio
        if ($_POST['password'] == '')
            $errors[] = sprintf("Empty field!");

        else {  // si no lo esta, comprobamos el formato

            if (strlen($_POST["password"]) < 6) {
                $errors[] = sprintf("'Password' must be more than 6 characters long!");

            } else {    // si el campo es valido:

                $this->getUserInfo(); // PILLAMOS TODA LA INFO DE LA DB DE ESE USUARIO _________________________________

                $this->container->get('db')->updateUserPassword($this->user->getUsername(), md5($_POST['password'])); // ACTUALIZAMOS

                $isUserUpdated = $this->getUserInfo(); // VOLVEMOS A PILLAR EL NUEVO USUARIO PARA ACTUALIZAR EL FORMULARIO

                if (!$isUserUpdated) {  // si no se ha podido actualizar el usuario
                    $errors[] = sprintf("Error when trying to get the new info!");

                } else {

                    $_SESSION['message'] = "'Password' succesfully updated! Don't forget it!";
                    return $response->withStatus(200)->withHeader('Location', '/profile');

                }

            }

        }

        $this->getUserInfo(); // pillamos la info del user (sin modificarla, obviamente)

        return $this->container->get("view")->render($response, 'profile.twig', [
            'errors' => $errors,
            'user' => $this->user
        ]);
    }

    // funcion que actualiza la birthdate del usuario
    public function updateBirthdate(Request $request, Response $response) {
        $errors = [];

        $this->getUserInfo(); // pillamos la info del user (sin modificarla, obviamente)

        // comprobamos si esta vacio
        if ($_POST['birthdate'] == '')
            $errors[] = sprintf("Empty field!");

        else {  // si no lo esta, comprobamos el formato

            $this->container->get('db')->updateUserBirthdate($this->user->getUsername(), $_POST['birthdate']); // ACTUALIZAMOS

            $isUserUpdated = $this->getUserInfo(); // VOLVEMOS A PILLAR EL NUEVO USUARIO PARA ACTUALIZAR EL FORMULARIO

            if (!$isUserUpdated) {  // si no se ha podido actualizar el usuario
                $errors[] = sprintf("Error when trying to get the new info!");

            } else {

                $_SESSION['message'] = "'Birthdate' succesfully updated!";
                return $response->withStatus(200)->withHeader('Location', '/profile');

            }

        }


        return $this->container->get("view")->render($response, 'profile.twig', [
            'errors' => $errors,
            'user' => $this->user
        ]);
    }

    // funcion que actualiza el password de usuario
    public function updateImage(Request $request, Response $response)
    {
        $errors = [];

        // comprobamos si esta vacio
        if ($_FILES['inputImage']['name'] == '') {

            $errors[] = sprintf("'New image' field is empty!");

        } else {  // si no lo esta, comprobamos el formato

            $this->getUserInfo(); // pillamos la info del user (necesitaremos el username)
            $errors = $this->checkImage($request->getUploadedFiles(), $errors);

            if (sizeof($errors) == 0)  {    // si el campo es valido:

                // volvemos a pillar la info del usuario antes de recargar la pagina, para mostrar la nueva imagen
                $this->getUserInfo();

                // como solo ha cambiado la imagen, se autocompletara sola al recargar la pagina

                $_SESSION['message'] = "'Image' successfully updated!";
                return $response->withStatus(200)->withHeader('Location', '/profile');

            }

        }

        // si ha habido errores, llegará aqui y hara lo comun: mostrarlos y dejar la info como antes
        $this->getUserInfo(); // pillamos la info del user (sin modificarla, obviamente)

        return $this->container->get("view")->render($response, 'profile.twig', [
            'errors' => $errors,
            'user' => $this->user
        ]);

    }

    // funcion que actualiza el usuario de la clase, que luego pasamos al render,
    //y devuelve un booleano que indica si la operacion ha sido exitosa o no.
    private function getUserInfo()
    {
        $this->user = null;

        $r = $this->container->get('db')->getUserInfo($_SESSION['username']);

        if (sizeof($r) != 1) {
            $errors[] = sprintf("Error while trying to get user's information from the database!");
            return false;

        } else {

            // 2. SI NO HA HABIDO NINGUN PROBLEMA, CREAMOS EL USUARIO ______________________________________________

            $r = $r[0];
            $this->user = new User(
                $r['name'],
                $r['username'],
                $r['email'],
                $r['password'],
                $r['birthdate'],
                $r['phone_number'],
                $r['image'],
                $r['is_active']
            );

            return true;
        }
    }

    // para checkear la validez de la imagen posteada
    private function checkImage($img, $errors) {

        /** @var UploadedFileInterface $uploadedFile */
        foreach ($img as $uploadedFile) {
            if ($uploadedFile->getError() !== UPLOAD_ERR_OK) {
                $errors[] = sprintf("An unexpected error occurred uploading the image!");
                continue;
            }

            $name = $uploadedFile->getClientFilename();

            $fileInfo = pathinfo($name);

            $format = $fileInfo['extension'];

            if (!$this->isValidFormat($format)) {
                $errors[] = sprintf("The received file extension '%s' is not valid", $format);
            }

            if ($_FILES['inputImage']['size'] > 500000) {
                $errors[] = sprintf("Image's size must be lower than 500Kb!");
            }

            // si no ha habido ningun error movemos la imagen
            if (sizeof($errors) == 0) {
                $name = $this->user->getUsername() . '.' . $format; // le cambiamos el nombre por el del usuario

                // actualizamos la base de datos
                $this->container->get('db')->updateUserImage($this->user->getUsername(), $name);


                // movemos la imagen:
                $uploadedFile->moveTo(self::UPLOADS_DIR . DIRECTORY_SEPARATOR . $name);
            }
        }

        return $errors;
    }

    // para el formato de la imagen
    private function isValidFormat(string $extension): bool
    {
        return in_array($extension, self::ALLOWED_EXTENSIONS, true);
    }
}