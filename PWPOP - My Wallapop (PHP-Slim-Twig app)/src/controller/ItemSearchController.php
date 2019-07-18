<?php
/**
 * Created by PhpStorm.
 * User: selde
 * Date: 18/04/2019
 * Time: 13:20
 */

namespace SallePW\SlimApp\controller;

use PHPMailer\PHPMailer\Exception;
use PHPMailer\PHPMailer\PHPMailer;
use Psr\Container\ContainerInterface;
use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Http\Message\ResponseInterface as Response;


final class ItemSearchController
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

    // funcion para obtener los resultados de busqueda del titulo introducido
    public function doSearchItem(Request $request, Response $response)
    {

        // buscamos el producto en la base de datos
        $r = $this->container->get('db')->getItems($_POST['title']);


        return $this->container->get("view")->render($response, 'searchResults.twig', [
            'search' => $_POST['title'],
            'items' => $r,
            'isLogged' => $_SESSION['isLogged']
        ]);
    }

}
