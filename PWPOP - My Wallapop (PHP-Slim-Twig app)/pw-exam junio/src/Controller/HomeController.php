<?php

namespace PwExam\Controller;

use DateInterval;
use DateTime;
use Psr\Container\ContainerInterface;
use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Http\Message\ResponseInterface as Response;
use PwExam\Model\Activity;
use PDO;
use PDOException;

final class HomeController
{
    protected $container;
    private $db;

    public function __construct(ContainerInterface $container)
    {
        $this->container = $container;
        $this->db = new PDO('mysql:host=localhost;dbname=exam', 'homestead', 'secret', []);

    }

    public function initSession(Request $request, Response $response, callable $next) {
        session_start();

        if (!isset($_SESSION['isLogged'])) $_SESSION['isLogged'] = false;
        $_SESSION['errors'] = [];

        return $next($request, $response);
    }

    public function indexAction(Request $request, Response $response)
    {
        //sacamos las actividades
        $stmt = $this->db->prepare("select * from activity;");
        $stmt->execute();
        $r = $stmt->fetchAll(PDO::FETCH_ASSOC);

        $_SESSION['activities'] = [];
        foreach ($r as $a) {
            $act = new Activity($a['id'], $a['name'], $a['description'], $a['price'], new DateTime($a['created_at']), new DateTime($a['activity_time']));
            array_push($_SESSION['activities'], $act);
        }

        return $this->container->get('view')
            ->render($response, 'home.html.twig', [
                'logged' => $_SESSION['isLogged'],
                'activities' => $_SESSION['activities'],
            ]);
    }

    public function registre(Request $request, Response $response)
    {
        return $this->container->get('view')
            ->render($response, 'register.html.twig', [
                'logged' => $_SESSION['isLogged'],
                'errors' => $_SESSION['errors'],
            ]);
    }

    public function doRegistre(Request $request, Response $response)
    {

        $_SESSION['errors'] = [];
        //var_dump($_POST);

        // comprobar que el campo nombre no está vacío
        if (strlen($_POST['username']) == 0) {
            $_SESSION['errors'][] = sprintf('El nombre no puede estar vacío');
        }
        // menos de 11 chars
        if (strlen($_POST['username']) > 10) {
            $_SESSION['errors'][] = sprintf('El nombre no puede tener mas de 10 caracteres');
        }
        // sin numeros
        if (preg_match("/[0-9]/", $_POST['username'])) {
            $_SESSION['errors'][] = sprintf('El nombre no puede tener un numero');
        }
        // comprobar que formato email correcto
        if (false === filter_var($_POST['email'], FILTER_VALIDATE_EMAIL)) {
            $_SESSION['errors'][] = sprintf('El formato del email debe ser correcto');
        }
        // contrasena entre 6 y 12 chars
        if (strlen($_POST['password']) < 6 || strlen($_POST['password']) > 12) {
            $_SESSION['errors'][] = sprintf('La contraseña debe tener entre 6 y 12 caracteres');
        }

        //var_dump($_SESSION);
        if (sizeof($_SESSION['errors']) > 0) {
            return $this->container->get('view')
                ->render($response, 'register.html.twig', [
                    'logged' => $_SESSION['isLogged'],
                    'errors' => $_SESSION['errors'],
                ]);

        } else {
            $_SESSION['isLogged'] = true;

            $stmt = $this->db->prepare("insert into user(username, email, password, created_at, updated_at) values (:username, :email, :password, now(), now());");
            $stmt->bindValue("username", $_POST['username'], PDO::PARAM_STR);
            $stmt->bindValue("email", $_POST['email'], PDO::PARAM_STR);
            $stmt->bindValue("password", $_POST['password'], PDO::PARAM_STR);
            $stmt->execute();

            // ahora necesitamos sacar la id_user
            $stmt = $this->db->prepare("select * from user where username = :username and password = :password;");
            $stmt->bindValue("username", $_POST['username'], PDO::PARAM_STR);
            $stmt->bindValue("password", $_POST['password'], PDO::PARAM_STR);

            $stmt->execute();
            $r = $stmt->fetchAll(PDO::FETCH_ASSOC);

            $_SESSION['id_user'] = $r[0]['id'];

            return $response->withStatus(200)->withHeader('Location', '/', ['errors' => $_SESSION['isLogged']]);
        }
    }

    public function login(Request $request, Response $response)
    {
        return $this->container->get('view')
            ->render($response, 'login.html.twig', [
                'logged' => $_SESSION['isLogged'],
                'errors' => $_SESSION['errors'],
            ]);
    }

    public function doLogin(Request $request, Response $response)
    {
        $_SESSION['errors'] = [];
        //var_dump($_POST);
        $ismail = false;

        // comprobamos si es un nombre o un email
        if (preg_match("/@/m", $_POST['username'])) $ismail = true;

        // comprobar que el campo nombre no está vacío
        if (strlen($_POST['username']) == 0) {
            $_SESSION['errors'][] = sprintf('El nombre no puede estar vacío');
        }
        // menos de 11 chars
        if (!$ismail && strlen($_POST['username']) > 10) {
            $_SESSION['errors'][] = sprintf('El nombre no puede tener mas de 10 caracteres');
        }
        // sin numeros
        if (!$ismail && preg_match("/[0-9]/m", $_POST['username'])) {
            $_SESSION['errors'][] = sprintf('El nombre no puede tener un numero');
        }
        // comprobar que formato email correcto
        if ($ismail && false === filter_var($_POST['username'], FILTER_VALIDATE_EMAIL)) {
            $_SESSION['errors'][] = sprintf('El formato del email debe ser correcto');
        }
        // contrasena entre 6 y 12 chars
        if (strlen($_POST['password']) < 6 || strlen($_POST['password']) > 12) {
            $_SESSION['errors'][] = sprintf('La contraseña debe tener entre 6 y 12 caracteres');
        }

        //var_dump($_SESSION);
        if (sizeof($_SESSION['errors']) > 0) {
            return $this->container->get('view')
                ->render($response, 'login.html.twig', [
                    'logged' => $_SESSION['isLogged'],
                    'errors' => $_SESSION['errors'],
                ]);
        } else {
            if ($ismail) {
                $stmt = $this->db->prepare("select * from user where email = :email and password = :password;");
                $stmt->bindValue("email", $_POST['username'], PDO::PARAM_STR);
                $stmt->bindValue("password", $_POST['password'], PDO::PARAM_STR);
            }
            else {
                $stmt = $this->db->prepare("select * from user where username = :username and password = :password;");
                $stmt->bindValue("username", $_POST['username'], PDO::PARAM_STR);
                $stmt->bindValue("password", $_POST['password'], PDO::PARAM_STR);
            }

            $stmt->execute();
            $r = $stmt->fetchAll(PDO::FETCH_ASSOC);

            //bucscamos al tio y si lo encontramos
            if (sizeof($r) > 0) {
                $_SESSION['isLogged'] = true;
                $_SESSION['id_user'] = $r[0]['id'];

                return $response->withStatus(200)->withHeader('Location', '/');
            } else {
                $_SESSION['errors'][] = sprintf('User not found in database!');
                $_SESSION['isLogged'] = false;

                return $this->container->get('view')
                    ->render($response, 'login.html.twig', [
                        'logged' => $_SESSION['isLogged'],
                        'activities' => $_SESSION['activities'],
                        'errors' => $_SESSION['errors'],
                    ]);
            }
        }
    }

    public function activity(Request $request, Response $response)
    {
        if ($_SESSION['isLogged']) {
            return $this->container->get('view')
                ->render($response, 'activity.html.twig', [
                    'logged' => $_SESSION['isLogged'],
                    'errors' => $_SESSION['errors'],
                ]);
        }
        else {
            return $response->withStatus(200)->withHeader('Location', '/login');

        }
    }

    public function doActivity(Request $request, Response $response)
    {
        $_SESSION['errors'] = [];
        //var_dump($_POST);

        // comprobar que el campo nombre no está vacío
        if (strlen($_POST['name']) == 0) {
            $_SESSION['errors'][] = sprintf('El nombre no puede estar vacío');
        }
        // menos de 21 chars
        if (strlen($_POST['name']) > 20) {
            $_SESSION['errors'][] = sprintf('El nombre no puede tener mas de 20 caracteres');
        }

        if ($_POST['date'] < '2020-04-20' || $_POST['date'] > '2020-04-23') {
            $_SESSION['errors'][] = sprintf('Data no vàlida');
        }

        //var_dump($_SESSION);


        $stmt = $this->db->prepare("select * from activity;");

        $stmt->execute();
        $r = $stmt->fetchAll(PDO::FETCH_ASSOC);


        foreach ($r as $act) {
            // DATE DATETIME DATA_CREATE - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
            $date = date_create($_POST['date']);
            if (date_format($date, 'Y-m-d H:i:s') == $act['activity_time']) {
                $_SESSION['errors'][] = sprintf('Detectada una activitat a aquesta hora!');
                break;
            }
        }

        if (sizeof($_SESSION['errors']) > 0) {
            return $this->container->get('view')
                ->render($response, 'activity.html.twig', [
                    'logged' => $_SESSION['isLogged'],
                    'errors' => $_SESSION['errors'],
                ]);

        } else {

            // metemos la actividad
            $stmt = $this->db->prepare("INSERT INTO activity(id_user,name,description,price,created_at,activity_time) VALUES (:id_user, :aname, :description, :price, now(), :adate);");
            $stmt->bindValue("id_user", $_SESSION['id_user'], PDO::PARAM_INT);
            $stmt->bindValue("aname", $_POST['name'], PDO::PARAM_STR);
            $stmt->bindValue("description", $_POST['description'], PDO::PARAM_STR);
            $stmt->bindValue("price", $_POST['price'], PDO::PARAM_INT);
            $stmt->bindValue("adate", $_POST['date'], PDO::PARAM_STR);

            $stmt->execute();

            return $response->withStatus(200)->withHeader('Location', '/');
        }
    }


    public function idActivity(Request $request, Response $response, array $args)
    {

        $stmt = $this->db->prepare("select * from activity where id = :idActivity;");
        $stmt->bindValue("idActivity", $args['id'], PDO::PARAM_INT);

        $stmt->execute();
        $r = $stmt->fetchAll(PDO::FETCH_ASSOC);
        //var_dump($r[0]);

        return $this->container->get('view')
            ->render($response, 'idActivity.html.twig', [
                'logged' => $_SESSION['isLogged'],
                'errors' => $_SESSION['errors'],
                'act' => $r[0],
            ]);
    }


    public function idActivityDelete(Request $request, Response $response, array $args)
    {

        $stmt = $this->db->prepare("select * from activity where id = :idActivity;");
        $stmt->bindValue("idActivity", $args['id'], PDO::PARAM_INT);

        $stmt->execute();
        $r = $stmt->fetchAll(PDO::FETCH_ASSOC);


        if ($_SESSION['id_user'] != $r[0]['id_user']) {
            $_SESSION['errors'][] = sprintf("¡Error! No puedes borrar una actividad que no es tuya.");
            $done = false;
        }
        else {
            $stmt = $this->db->prepare("delete from activity where id = :idActivity;");
            $stmt->bindValue("idActivity", $args['id'], PDO::PARAM_INT);

            $stmt->execute();
            $r = $stmt->fetchAll(PDO::FETCH_ASSOC);

            $done = true;
        }


        if ($done) {
            return $response->withStatus(200)->withHeader('Location', '/');

        } else {
            return $this->container->get('view')
                ->render($response, 'idActivity.html.twig', [
                    'logged' => $_SESSION['isLogged'],
                    'errors' => $_SESSION['errors'],
                    'act' => $r[0],
                ]);
        }
    }

}
