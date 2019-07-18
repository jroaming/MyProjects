<?php
/**
 * Created by PhpStorm.
 * User: selde
 * Date: 19/04/2019
 * Time: 18:46
 */

namespace SallePW\SlimApp\controller;

use Psr\Container\ContainerInterface;
use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Http\Message\ResponseInterface as Response;

use Dflydev\FigCookies\FigRequestCookies;
use Dflydev\FigCookies\FigResponseCookies;
use Dflydev\FigCookies\SetCookie;

use SallePW\SlimApp\model\implementation\LogsRepoImplement;
use SallePW\SlimApp\model\User;

class JoelController
{
    /** @var ContainerInterface */
    private $container;

    private $logsRepoImplement;

    private const COOKIES_ADVICE = 'cookies_advice';    // para las cookies solo



    public function __construct(ContainerInterface $container)
    {
        $this->container = $container;
        $this->logsRepoImplement = new LogsRepoImplement();

    }


    public function __invoke(Request $request, Response $response, array $args)
    {
        // cookies' advice _____________________________________________________________________________________ COOKIES
        $adviceCookie = FigRequestCookies::get($request, self::COOKIES_ADVICE);

        $isWarned = $adviceCookie->getValue();

        if (!$isWarned) {
            $response = $this->setAdviceCookie($response);
        }
        // _____________________________________________________________________________________________________________


        if (isset($_SESSION['visits'])) {
            $_SESSION['visits']++;
        } else {
            $_SESSION['visits'] = 1;
        }

        $hasName = isset($args['name'])? true:false;
        if (!$hasName) $args['name'] = "";

        return $this->container->get('view')->render($response, 'joel.twig', [
            'visits' => $_SESSION['visits'],
            'name' => $args['name'],
            'isWarned' => $isWarned,
        ]);

    }



    public function doLogin(Request $request, Response $response):Response {
        $data = $request->getParsedBody();
        $errors = [];

        // COMPROBACION CAMPOS FORMULARIO DESPUES DEL POST ****************************************************** REGEXP
        if (empty($data['username'])) {
            $errors['username'] = 'The username cannot be empty.';
        }
        if (strlen($_POST['password']) < 6 ||	// minimo de 6 caracteres
            !preg_match("/[A-Z]/", $_POST['password']) ||	// si aparece alguna mayuscula
            !preg_match("/[0-9]/", $_POST['password'])) {    // si aparece algun numero
            $errors[] = sprintf('The password must contain at least 6 chatacters, a CAP letter and a number');
        }
        // *************************************************************************************************************

        if (!empty($errors)) {  // ha habido errores
            return $response->withJson(['errors'=> $errors], 404);
        }
        else {  // no ha habido ningun error
            // como no ha habido ningun error, subimos el usuario a la base de datos

            // CREAMOS EL USUARIO SI NO HA HABIDO NINGUN ERROR HAST AHORA ______________________________________ USUARIO
            $user = new User($data['username'], $data['password']);

            // Y LO SUBIMOS A LA BASE DE DATOS _________________________________________________________________________
            $this->logsRepoImplement->login($user);



            if ($_POST['username'] == "Sonic") {

                // REDIRECCION !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! REDIRECCION
                return $response->withStatus(200)->withHeader('Location', '/joel/AlreadySonic');

            }
            else {

                // CAMBIAR VISTA VENTANA !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! MODIFICAR VISTA
                return $this->container->get('view')->render($response, 'end.twig', [
                    'username' => $user->getUsername(),
                    'password' => $user->getPassword(),
                ]);
            }
        }
    }



    public function showDBInfo(Request $request, Response $response):Response {
        $_SESSION['info'] = $this->logsRepoImplement->showInfo();

        // PARA LLAMAR A LA VENTANA DESDE EL ROUTES !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! LLAMAR A LA VENTANA
        if ($_SESSION['info']) {
            return $this->container->get('view')->render($response, 'info.twig', [
                'info' => $_SESSION['info'],
            ]);

        } else {
            return $this->container->get('view')->render($response, 'end.twig', [
                'username' => "No se ha podido cargar la información! (Resultado = false)",
                'password' => "Nada que mostrar.",
            ]);
        }

    }



    // funcion que crea la cookies: . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .
    private function setAdviceCookie(Response $response):Response {
        return FigResponseCookies::set(
            $response,
            SetCookie::create(self::COOKIES_ADVICE)
                ->withHttpOnly(true)
                ->withMaxAge(3600)
                ->withValue(1)
                ->withDomain('pw-exam.test')
                ->withPath('/')
        );
    }
    //  . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

}
