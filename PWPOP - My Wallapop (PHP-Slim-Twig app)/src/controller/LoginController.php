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
use Dflydev\FigCookies\FigRequestCookies;
use Dflydev\FigCookies\FigResponseCookies;
use Dflydev\FigCookies\SetCookie;

final class LoginController
{
    // cookie's identifier
    private const COOKIES_ADVICE = 'cookies_session';

    /** @var ContainerInterface */
    private $container;

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
        $msg = $_SESSION['message'];
        $_SESSION['message'] = '';

        $response = $this->container->get("view")->render($response, 'login.twig', [
            'msg' => $msg
        ]);

        return $response;
    }

    public function doLogin(Request $request, Response $response)
    {
        $errors = [];

        // 1. COMPROBAMOS LOS CAMPOS DEL FORMULARIO ____________________________________________________________________

        // comprobar que formato email correcto
        if (false === filter_var($_POST['email'], FILTER_VALIDATE_EMAIL)) {
            $errors[] = sprintf("'Email' format must be written correctly!");
        }
        // comprobar strlen del password
        if (strlen($_POST["password"]) < 6)
            $errors[] = sprintf("'Password' must be more than 6 characters long!");


        // Ya hay errores, no hace falta uqe busquemose n la base de datos
        if (sizeof($errors) > 0) {
            $action = $this->container->get("view")->render($response, 'login.twig', [
                'isLogged' => $_SESSION['isLogged'],
                'errors' => $errors,
            ]);
        }
        else {

            // 2. BUSCAMOS AL USUARIO EN LA BASE DE DATOS ______________________________________________________________

            $r = $this->container->get('db')->searchUser($_POST['email'], md5($_POST['password'])); // password encriptada!
            if (sizeof($r) < 1) {
                $errors[] = sprintf("User not found in database! Make sure about your info.");
            }

            if (sizeof($errors) > 0) {
                $action = $this->container->get("view")->render($response, 'login.twig', [
                    'isLogged' => $_SESSION['isLogged'],
                    'errors' => $errors,
                ]);
            }
            else {

                // 3. SI NO HA HABIDO NINGUN ERROR, INICIAMOS SESION ___________________________________________________

                $_SESSION['isLogged'] = true;
                $_SESSION['username'] = $r[0]['username'];

                // SI ESTA ACTIVADO EL RECUERDAME, CREAMOS LA COOKIE

                if (isset($_POST['remember']))
                    $response = $this->setAdviceCookie($response);

                $_SESSION['message'] = 'User logged successfully! Enjoy your shopping :)';

                $action = $response->withStatus(200)->withHeader('Location', '/');
            }
        }

        return $action;
    }

    // funcion de log out de la sesion
    public function doLogout(Request $request, Response $response) {
        if ($_SESSION['isLogged']) {
            unset($_SESSION['username']);
            $_SESSION['message'] = '';
            $_SESSION['isLogged'] = false;

            // miramos si existe la cookie de remember me y, si esta, la borramos cambiandola por otra ya caducada
            if ($_COOKIE[self::COOKIES_ADVICE] != '') {

                $response = $this->setFinishedCookie($response);

            }
        }

        session_destroy();
        session_unset();

        return $response->withStatus(200)->withHeader('Location', '/');
    }


    // metodo de creacion de cookies
    private function setAdviceCookie(Response $response): Response
    {
        return FigResponseCookies::set(
            $response,
            SetCookie::create(self::COOKIES_ADVICE)
                ->withHttpOnly(true)
                ->withMaxAge(3600)
                //->withExpires('2012-01-01')
                ->withValue($_SESSION['username'])
                ->withDomain('pwpop.test')
                ->withPath('/')
        );
    }

    // metodo de eliminacion de cookies (crear otra con fecha de expiracion pasada)
    private function setFinishedCookie(Response $response): Response
    {
        return FigResponseCookies::set(
            $response,
            SetCookie::create(self::COOKIES_ADVICE)
                ->withHttpOnly(true)
                ->withMaxAge(0)
                ->withExpires(time() - 3600)
                ->withValue('')
                ->withDomain('pwpop.test')
                ->withPath('/')
        );
    }
}
