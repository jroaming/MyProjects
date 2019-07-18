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

class ProfileDeleteController
{
    /** @var ContainerInterface */
    private $container;

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

        if ($_SESSION['isLogged']) {

            // 1. SACAMOS LA INFO DE LA BASE DE DATOS __________________________________________________________________

            $r = $this->container->get('db')->getUserInfo($_SESSION['username']);

            if (sizeof($r) != 1) {
                $errors[] = sprintf("Error while trying to get user's information from the database!");

                $response = $this->container->get("view")->render($response, 'profile.twig', [
                    'errors' => $errors
                ]);

            } else {

                // 2. SI NO HA HABIDO NINGUN PROBLEMA, BORRAMOS EL USUARIO _____________________________________________

                $r = $this->container->get('db')->deleteAccount($_SESSION['username']);

                // 3. Y TODOS SUS ITEMS ________________________________________________________________________________

                $r = $this->container->get('db')->deleteAccountItems($_SESSION['username']);

                return $response->withStatus(200)->withHeader('Location', '/logout');
            }

        } else {
            // si el usuario no esta logueado, hay que mandar esto: codigo 403, peticion rechazada.
            return $response->withStatus(403);

        }
        return $response;
    }
}