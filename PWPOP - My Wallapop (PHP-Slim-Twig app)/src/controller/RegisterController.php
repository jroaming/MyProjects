<?php
/**
 * Created by PhpStorm.
 * User: selde
 * Date: 18/04/2019
 * Time: 13:20
 */

namespace SallePW\SlimApp\controller;

use Psr\Container\ContainerInterface;
use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Message\UploadedFileInterface;
use SallePW\SlimApp\model\User;


final class RegisterController
{
    /** @var ContainerInterface */
    private $container;

    /** Allowed extensions for user's profile image */
    private const ALLOWED_EXTENSIONS = ['jpg', 'png'];

    /** @var string Directory where uploads are sent */
    private const UPLOADS_DIR = __DIR__ . '/../../public/assets/uploads/userImages';

    /**
     * FlashController constructor.
     * @param ContainerInterface $container
     */
    public function __construct(ContainerInterface $container)
    {
        $this->container = $container;
    }

    public function __invoke(Request $request, Response $response)
    {
        $response = $this->container->get("view")->render($response, 'register.twig', [
            'isLogged' => $_SESSION['isLogged']
        ]);
        return $response;
    }

    public function doRegister(Request $request, Response $response)
    {
        $errors = [];

        // 1. COMPROBAMOS LOS CAMPOS MENOS LA IMAGEN ___________________________________________________________________

        if (!ctype_alnum($_POST["name"]))
            $errors[] = sprintf("'Name' must contain only alphanumeric characters!");

        if (!ctype_alnum($_POST["username"]))
            $errors[] = sprintf("'Username' must contain only alphanumeric characters!");

        if (strlen($_POST["username"]) > 20)
            $errors[] = sprintf("'Username' must be 20 characters long or less!");

        if (false === filter_var($_POST['email'], FILTER_VALIDATE_EMAIL)) {
            $_SESSION['errors'][] = sprintf("'Email' format must be written correctly!");
        }

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
        if ($count != 9 || $spacecount != 2 || !$valid)
            $_SESSION['errors'][] = sprintf("'Phone' can only contain 8 numbers and 2 '_'s!");


        if (strlen($_POST["password"]) < 6)
            $errors[] = sprintf("'Password' must be more than 6 characters long!");

        if ($_POST["password"] != $_POST["password2"])
            $errors[] = sprintf("Passwords must match!");


        if (sizeof($errors) == 0) {     // si no hay ningun error...

            // 2. COMPROBAMOS QUE NO HAYA NINGUN USUARIO CON ESOS DATOS YA REGISTRADO EN LA DATABASE ___________________

            $r = $this->container->get('db')->searchFullUser($_POST["username"]);

            // tampoco puede haber un usuario con el mismo email y password a la vez:
            $q = $this->container->get('db')->searchUser($_POST["email"], md5($_POST['password']));


            if (sizeof($q) > 0 || sizeof($r) > 0) {
                $errors[] = sprintf("There's already a user with that information in the database!");

            } else {

                // 3. COMPROBAMOS QUE LA IMAGEN ADJUNTADA ES CORRECTA ______________________________________________________
                //var_dump($_FILES);

                // por si el tio no ha subido una imagen, se le meta por defecto.
                $_SESSION['user_img'] = "default.jpg";
                if ($_FILES['inputImage']['name'] != '') $errors = $this->checkImage($request->getUploadedFiles(), $errors);

                if (sizeof($errors) == 0) { // si no hay errores...

                    // 4. AGREGAMOS EL USUARIO A LA BASE DE DATOS __________________________________________________________

                    $user = new User($_POST['name'],
                        $_POST['username'],
                        $_POST['email'],
                        md5($_POST['password']),    // encriptamos la password del usuario!
                        $_POST['birthdate'],
                        $_POST['phone'],
                        $_SESSION['user_img'],
                        true);
                    $r = $this->container->get('db')->createUser($user);
                }
            }
        }

        var_dump($errors);

        if (sizeof($errors) > 0) {    // si hay errores, cargamos de nuevo la pagina de registro y le pasamos el array

            return $this->container->get("view")->render($response, 'register.twig', [
                'isLogged' => $_SESSION['isLogged'],
                'errors' => $errors
            ]);

        } else {  // si no hay errores, vamos al login

            // agregamos a la sesion el mensaje de que acabamos de loguearnos:
            $_SESSION['message'] = 'User registered successfully! Now you can log in here.';

            return $response->withStatus(200)->withHeader('Location', '/login');
        }
    }


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
                $name = $_POST['username'] . '.' . $format; // le cambiamos el nombre por el del usuario

                // guardamos el nombre en la sesion un momento, ya que lo necesitamos para crear el usar mas adelante
                $_SESSION['user_img'] = $name;

                // movemos la imagen:
                $uploadedFile->moveTo(self::UPLOADS_DIR . DIRECTORY_SEPARATOR . $name);
            }
        }

        return $errors;
    }

    private function isValidFormat(string $extension): bool
    {
        return in_array($extension, self::ALLOWED_EXTENSIONS, true);
    }

}
