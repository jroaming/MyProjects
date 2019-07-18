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
use SallePW\SlimApp\model\User;

class ProfileController
{
    /** @var ContainerInterface */
    private $container;
    /** @var User */
    private $user;

    /**
     * ProfileController constructor.
     * @param ContainerInterface $container
     */
    public function __construct(ContainerInterface $container)
    {
        $this->container = $container;
    }

    public function __invoke(Request $request, Response $response)
    {
        $errors = [];
        $msg = $_SESSION['message'];
        $_SESSION['message'] = '';

        if ($_SESSION['isLogged']) {

            // 1. SACAMOS LA INFO DE LA BASE DE DATOS __________________________________________________________________

            $r = $this->container->get('db')->getUserInfo($_SESSION['username']);

            if (sizeof($r) != 1) {
                $errors[] = sprintf("Error while trying to get user's information from the database!");

                $response = $this->container->get("view")->render($response, 'profile.twig', [
                    'isLogged' => $_SESSION['isLogged'],
                    'errors' => $errors
                ]);

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

                // 3. Y PASAMOS LA VARIABLE AL RENDER DEL TWIG PARA PONER LOS DATOS EN LA PÁGINA _______________________
                //var_dump($this->user);
                $response = $this->container->get("view")->render($response, 'profile.twig', [
                    'isLogged' => $_SESSION['isLogged'],
                    'errors' => $errors,
                    'msg' => $msg,
                    'user' => $this->user
                ]);
            }

        } else {
            // si el usuario no esta logueado, hay que mandar esto: codigo 403, peticion rechazada.
            return $response->withStatus(403);

        }
        return $response;
    }
}