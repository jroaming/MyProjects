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
use SallePW\SlimApp\model\Product;


final class ItemListController
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
        $msg = $_SESSION['message'];
        $_SESSION['message'] = '';


        if ($_SESSION['isLogged']) {

            // 1. SACAMOS TODOS LOS PRODUCTOS DE ESE USUARIO DE LA BASE DE DATOS _______________________________________
            $items = $this->container->get('db')->getItemsList($_SESSION['username']);
            $products = [];
            foreach ($items as $i) {
                $p = new Product($i['username'], $i['title'], $i['description'], $i['price'], $i['category'], $i['image'], 1);
                $p->setId($i['id']);
                array_push($products, $p);
            }

            // 2. Y PASAMOS LA VARIABLE AL RENDER DEL TWIG PARA PONER LOS DATOS EN LA PÁGINA ___________________________
            //var_dump($this->user);
            $response = $this->container->get("view")->render($response, 'itemList.twig', [
                'isLogged' => $_SESSION['isLogged'],
                'noItems' => sizeof($products) == 0,
                'errors' => $errors,
                'msg' => $msg,
                'items' => $products,
                'username' => $_SESSION['username']
            ]);

        } else {
            // si el usuario no esta logueado, hay que mandar esto: codigo 403, peticion rechazada.
            return $response->withStatus(403);

        }
        return $response;
    }

    public function doAddItem(Request $request, Response $response)
    {
        $errors = [];

        // 1. COMPROBAMOS LOS CAMPOS MENOS LA IMAGEN ___________________________________________________________________

        if (!strlen($_POST["title"]))
            $errors[] = sprintf("'Title' can't be empty!");

        if (strlen($_POST["description"]) < 20)
            $errors[] = sprintf("'Description' must have a minimum of 20 characters.");

        if ($_POST['category'] < 1 || $_POST['category'] > 7)
            $errors[] = sprintf("Choose a valid 'Category' from the list.");
        else
            $_POST['category'] = self::CATEGORY[$_POST['category'] - 1];

        $stringPrice = $_POST['price'] . '';
        $numDecimals = strlen(substr(strrchr($stringPrice, "."), 1));
        if ($numDecimals > 2)
            $errors[] = sprintf("Price can only be 2 decimals long!");

        if ($_FILES['image']['name'] == '') // si no hay imagen, error
            $errors[] = sprintf("You have to upload an image of your product.");

        else
            // si hay imagen, la checkeamos
            $errors = $this->checkImage($request->getUploadedFiles(), $errors);

        if (sizeof($errors) == 0) {     // si no hay ningun error...

            // 2. COMPROBAMOS QUE NO HAYA NINGUN PRODUCTO  CON ESE NOMBRE EN LA BASE DE DATOS __________________________
            if (sizeof($this->container->get('db')->searchRepeatedProduct($_POST['title'])) > 0)
                $errors[] = sprintf("There's already a product with that name in the database!");

            // 3. COMPROBAMOS QUE LA IMAGEN ADJUNTADA ES CORRECTA ______________________________________________________
            //var_dump($_FILES);

            if (sizeof($errors) == 0) { // si no hay errores...

                // 4. AGREGAMOS EL PRODUCTO A LA BASE DE DATOS _________________________________________________________

                $product = new Product($_SESSION['username'],
                    $_POST['title'],
                    $_POST['description'],
                    $_POST['price'],
                    $_POST['category'],
                    $_SESSION['item_img'],
                    true);

                $r = $this->container->get('db')->createProduct($product);

            }
        }

        if (sizeof($errors) > 0) {    // si hay errores, cargamos de nuevo la pagina de registro y le pasamos el array

            return $this->container->get("view")->render($response, 'itemUpload.twig', [
                'errors' => $errors,
                'isLogged' => $_SESSION['isLogged']
            ]);

        } else {  // si no hay errores, vamos al home

            return $response->withStatus(200)->withHeader('Location', '/addItem');
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

            if ($_FILES['image']['size'] > 1000000) {
                $errors[] = sprintf("Image's size must be lower than 1Mb!");
            }

            // si no ha habido ningun error movemos la imagen
            if (sizeof($errors) == 0) {
                $name = $_POST['title'] . '.' . $format; // le cambiamos el nombre por el del usuario

                // guardamos el nombre en la sesion un momento, ya que lo necesitamos para crear el usar mas adelante
                $_SESSION['item_img'] = $name;

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
