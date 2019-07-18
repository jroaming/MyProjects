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


final class HomeController
{
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
        $errors = [];
        $msg = '';
        if ($_SESSION['message'] != '') {
            $msg = $_SESSION['message'];
            $_SESSION['message'] = '';
        }
        $r = [];

        //var_dump($_SESSION);
        //var_dump($_COOKIE);

        // si el usuario no esta logueado, cargar la info de todos los productos activos de la base de datos

        if (!$_SESSION['isLogged'])
        {
            $r = $this->container->get('db')->getActiveProducts();
        }
        else // si el usuario esta logueado, cargar los productos QUE NO SON SUYOS
        {
            $r = $this->container->get('db')->getActiveProductsNotOwned($_SESSION['username']);
        }


        $response = $this->container->get("view")->render($response, 'home.twig', [
            'errors' => $errors,
            'items' => $r,
            'msg' => $msg,
            'isLogged' => $_SESSION['isLogged']
        ]);
        return $response;
    }
}
