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
use SallePW\SlimApp\model\Product;


final class ItemController
{
    /** @var ContainerInterface */
    private $container;

    /** Allowed extensions for user's profile image */
    private const ALLOWED_EXTENSIONS = ['jpg', 'png'];

    /** @var string Directory where uploads are sent */
    private const UPLOADS_DIR = __DIR__ . '/../../public/assets/uploads/productImages';

    /** @var string Possible categories */
    private const CATEGORY = ['Computers & Electronic', 'Cars', 'Sports', 'Games', 'Fashion', 'Home', 'Others'];

    /**
     * FlashController constructor.
     * @param ContainerInterface $container
     */
    public function __construct(ContainerInterface $container)
    {
        $this->container = $container;
    }

    public function __invoke(Request $request, Response $response, array $args)
    {
        $errors = [];

        // si no esta logueado no puede comprar (porque necesitamos sus datos de contacto -telefono, correo, etc.)
        if ($_SESSION['isLogged']) {

            // 1. Buscamos el producto _________________________________________________________________________________
            /** @var Product $item */
            $item = $this->container->get('db')->getItemInfo($args['id']);


            if (sizeof($item) < 1)  // si no ha encontrado el item activo en cuestion
            {
                $errors[] = sprintf("Item not found!");

            } else {

                // si encuentra mas de un resultado por algun motivo
                if (sizeof($item) > 1) {
                    $errors[] = sprintf("Something went wrong when trying to get the item out of the database!");

                } else {  // si ha ido bien y tenemos un item:

                    // comprobamos que el item no esta inactivo ya
                    if (!$item[0]['is_active']) {
                        $errors[] = sprintf("This product is already sold out or inactive");
                    }

                    // 2. Miramos si nuestro usuario es el propietario o somos un comprador ____________________________

                    $isOwner = !strcmp($_SESSION['username'], $item[0]['username']);

                    return $this->container->get("view")->render($response, 'item.twig', [
                        'errors' => $errors,
                        'item' => $item[0],
                        'isOwner' => $isOwner,
                        'isLogged' => $_SESSION['isLogged']
                    ]);

                }

            }

        }
        // Si no está logueado
        /** @var Product $item */
        $item = $this->container->get('db')->getItemInfo($args['id']);

        if (sizeof($item) < 1)  // si no ha encontrado el item activo en cuestion
        {
            $errors[] = sprintf("Item not found!");

        }
        return $this->container->get("view")->render($response, 'item.twig', [
            'errors' => $errors,
            'item' => $item[0],
            'isOwner' => false,
            'isLogged' => $_SESSION['isLogged']
        ]);
    }

    public function doItemUpload(Request $request, Response $response, array $args)
    {
        $errors = [];

        // tenemos que pillar la info de la base de datos, por si algo va mal y no podemos actualizar nada
        $item = $this->container->get('db')->getItemInfo($args['id']);

        if (sizeof($item) < 1)  // si no ha encontrado el item activo en cuestion
        {
            $errors[] = sprintf("Item not found!");

        } else {

            // si encuentra mas de un resultado por algun motivo
            if (sizeof($item) != 1) {
                $errors[] = sprintf("Something went wrong when trying to get the item out of the database!");
            }
        }


        // checkeamos los errores de update en aquellos campos que no estén vacíos
        // el campo no puede estar vacío
        if ($_POST['title'] != '' && $_POST['title'] != $item[0]['title']) {

            // tambien tenemos que comprobar que no haya ningun producto con el nuevo nombre en la base de datos

            if (sizeof($this->container->get('db')->searchRepeatedProduct($_POST['title'])) > 0) {

                $errors[] = sprintf("There's already a product with that name in the database!");

            } else    // si no hay ninguno con ese nombre, entonces podemos actualizar el producto
            {

                // actualizamos el nombre del producto en la base de datos
                $this->container->get('db')->updateProductTitle($args['id'], $_POST['title']);

            }

        }


        if (strlen($_POST['description']) > 20)
            $this->container->get('db')->updateProductDescription($args['id'], $_POST['description']);

        $stringPrice = $_POST['price'] . '';
        $numDecimals = strlen(substr(strrchr($stringPrice, "."), 1));

        if ($numDecimals <= 2)  // si tiene el numero correcto de decimales
            $this->container->get('db')->updateProductPrice($args['id'], $_POST['price']);

        // actualizar categoria (por cojones va a estar bien
        $this->container->get('db')->updateProductCategory($args['id'], self::CATEGORY[$_POST['category'] - 1]);

        if ($_FILES['image']['name'] != '') { // si hay imagen, la actualizamos
            $errors = $this->checkImage($request->getUploadedFiles(), $errors);

            if (sizeof($errors) == 0) { // si la imagen no tiene errores
                // actualizamos la imagen de la base de datos
                $this->container->get('db')->updateProductImage($args['id'], $_SESSION['item_upload_img']);

            }

        }

        // volver a crear el producto con los nuevos datos de la base de datos
        $r = $this->container->get('db')->getItemInfo($args['id']);

        if (sizeof($r) != 1) {

            $errors[] = sprintf("Error when trying to get item's new information!");

        } else {

            // miramos si hay algun error y si no, cargamos la pagina correctamente
            if (sizeof($errors) <= 0) {

                // pillamos la informacion de la base de datos (esta vez ya estara actualizado lo que toque)
                $item = $this->container->get('db')->getItemInfo($args['id']);

                if (sizeof($item) < 1)  // si no ha encontrado el item activo en cuestion
                {
                    $errors[] = sprintf("Item not found!");

                } else {

                    // si encuentra mas de un resultado por algun motivo
                    if (sizeof($item) != 1) {
                        $errors[] = sprintf("Something went wrong when trying to get the item out of the database!");

                    } else {

                        return $this->container->get("view")->render($response, 'item.twig', [
                            'isLogged' => $_SESSION['isLogged'],
                            'errors' => $errors,
                            'item' => $item[0],
                            'isOwner' => true
                        ]);
                    }
                }


            }
        }

        return $this->container->get("view")->render($response, 'item.twig', [
            'isLogged' => $_SESSION['isLogged'],
            'errors' => $errors,
            'item' => $item[0],
            'isOwner' => true
        ]);
    }


    // funcion para borrar el item actual
    public function deleteItem(Request $request, Response $response, array $args)
    {

        $errors = [];

        // buscamos el producto en la base de datos
        $r = $this->container->get('db')->getItemInfo($args['id']);


        // miramos si ha habido algun error
        if (sizeof($r) != 1) {
            $errors[] = sprintf("Error when trying to get product's information!");

        } else {
            $r = $r[0];
            // solo el owner puede borrar el producto
            $isOwner = $_SESSION['username'] == $r['username'];

            // si no es el owner, no tiene derecho a borrar el producto
            if (!$isOwner) {

                $errors[] = sprintf("You need to be its owner in order to delete a product!");

                //return $response->withStatus(403);

            } else {
                // si sí que es el owner:

                // si no, ponemos su is_active a 0
                $this->container->get('db')->setProductActive($args['id'], false);

                return $response->withStatus(200)->withHeader('Location', '/items');
            }
        }


        return $this->container->get("view")->render($response, 'item.twig', [
            'errors' => $errors,
            'isOwner' => false
        ]);
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

            if ($_FILES['image']['size'] > 1000000) {
                $errors[] = sprintf("Image's size must be lower than 1Mb!");
            }

            // si no ha habido ningun error movemos la imagen
            if (sizeof($errors) == 0) {
                $name = $_POST['title'] . '.' . $format; // le cambiamos el nombre por el del usuario

                // guardamos el nombre en la sesion un momento, ya que lo necesitamos para crear el usar mas adelante
                $_SESSION['item_upload_img'] = $name;

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
