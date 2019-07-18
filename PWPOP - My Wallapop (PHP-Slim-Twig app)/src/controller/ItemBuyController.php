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


final class ItemBuyController
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

    // funcion para comprar el item actual
    public function buyItem(Request $request, Response $response, array $args)
    {

        $errors = [];

        // buscamos el producto en la base de datos
        $r = $this->container->get('db')->getItemInfo($args['id']);


        // miramos si ha habido algun error
        if (sizeof($r) != 1)
            $errors[] = sprintf("Error when trying to get product's information!");


        if (!$_SESSION['isLogged']) {   // si no esta logueado, damos error y cargamos el login
            $errors[] = sprintf("You gotta be logged if you want to buy a product!");

            return $response->withStatus(403);
        }



        if (sizeof($errors) == 0) {
            $r = $r[0];

            // ahora tenemos que comprar el producto

            $productName = $r['title'];

            $ownerUsername = $r['username'];

            $ownerEmail = $this->container->get('db')->searchFullUser($r['username'])[0]['email'];

            $buyerUsername = $_SESSION['username'];

            $buyerEmail = $this->container->get('db')->searchFullUser($buyerUsername)[0]['email'];

            $buyerPhone = $this->container->get('db')->searchFullUser($buyerUsername)[0]['phone_number'];

            $this->sendEmailToOwner($productName, $ownerUsername, $ownerEmail, $buyerUsername, $buyerPhone, $buyerEmail);

            $this->container->get('db')->setProductActive($args['id'], false);  // lo hemos comprado, ya no esta activo

            /*
            return $this->container->get("view")->render($response, 'item.twig', [
                'errors' => [$ownerEmail, $buyerUsername, $buyerPhone],
                'isOwner' => !strcmp($_SESSION['username'], $r['username'])
            ]);
            */

            return $response->withStatus(200)->withHeader('Location', '/');
        }


        return $this->container->get("view")->render($response, 'item.twig', [
            'errors' => $errors,
            'isOwner' => !strcmp($_SESSION['username'], $r['username'])
        ]);
    }

    // funcion que manda el email al propietario del producto
    private function sendEmailToOwner($productName, $ownerUsername, $ownerEmail, $buyerName, $buyerPhone, $buyerEmail)
    {
        $mail = new PHPMailer(TRUE);

        try {

            /* SMTP parameters. */

            /* Tells PHPMailer to use SMTP. */
            $mail->isSMTP();

            /* SMTP server address. */
            $mail->Host = 'smtp.live.com';

            /* Set the SMTP port. */
            $mail->Port = 25;

            /* Use SMTP authentication. */
            $mail->SMTPAuth = TRUE;

            /* SMTP authentication username. */
            $mail->Username = 'INSERT-YOUR-EMAIL(MUST-BE-HOTMAIL)';

            /* SMTP authentication password. */
            $mail->Password = "INSERT-YOUR-EMAIL'S-PASSWORD-HERE";

            /* Set the encryption system. */
            $mail->SMTPSecure = '';

            $mail->setFrom($buyerEmail, 'PWPOP items\' service');
            //$mail->setFrom('seldengamer@hotmail.com', 'PWPOP items\' service');
            $mail->addAddress($ownerEmail, $ownerUsername);

            $mail->Subject = $productName . ' just got bought!';
            $mail->Body = $productName . ' has been sold to ' . $buyerName . '.
                For more information, here\'s his phone number: ' . $buyerPhone . '.';

            /* Finally send the mail. */
            $mail->send();
        }
        catch (Exception $e)
        {
            echo $e->errorMessage();
        }
        catch (\Exception $e)
        {
            echo $e->getMessage();
        }
    }

}
