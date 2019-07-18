<?php declare(strict_types = 1);

namespace PwExam\Controller;

use Psr\Container\ContainerInterface;
use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Http\Message\ResponseInterface as Response;
use PwExam\Model\Contact;

use PDO;
use PDOException;


final class HomeController
{
    /** @var ContainerInterface */
    protected $container;
    private $db;

    public function __construct(ContainerInterface $container)
    {
        if (!isset($_SESSION['contacts'])) $_SESSION['contacts'] = [];

        $this->container = $container;
        $this->db = new PDO('mysql:host=localhost;dbname=exam', 'homestead', 'secret', []);

    }

    public function __invoke(Request $request, Response $response, callable $next)
    {
        if (!isset($_SESSION['isLogged'])) $_SESSION['isLogged'] = false;
        if (!isset($_SESSION['errors'])) $_SESSION['errors'] = [];

        return $next($request, $response);
    }

    public function indexAction(Request $request, Response $response): Response
    {
        $contacts = [];
        $logged = $_SESSION['isLogged'];

        $_SESSION['contacts'] = $contacts;

        //var_dump($_SESSION);

        return $this->container->get('view')->render(
            $response,
            'home.html.twig',
            [
                'logged' => $logged,
                'contacts' => $_SESSION['contacts'],
            ]
        );
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

        // comprobar que formato email correcto
        if (false === filter_var($_POST['email'], FILTER_VALIDATE_EMAIL)) {
            $_SESSION['errors'][] = sprintf('El formato del email debe ser correcto');
        }
        // contrasena entre 6 y 12 chars
        if (strlen($_POST['password']) < 6) {
            $_SESSION['errors'][] = sprintf('La contraseña debe tener mas de 6 caracteres');
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

            $stmt = $this->db->prepare("insert into user(email, password, created_at, updated_at) values (:email, :password, now(), now());");
            $stmt->bindValue("email", $_POST['email'], PDO::PARAM_STR);
            $stmt->bindValue("password", $_POST['password'], PDO::PARAM_STR);
            $stmt->execute();

            // ahora necesitamos sacar la id_user
            $stmt = $this->db->prepare("select * from user where email = :email and password = :password;");
            $stmt->bindValue("email", $_POST['email'], PDO::PARAM_STR);
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

        // comprobar que formato email correcto
        if (false === filter_var($_POST['email'], FILTER_VALIDATE_EMAIL)) {
            $_SESSION['errors'][] = sprintf('El formato del email debe ser correcto');
        }
        // contrasena entre 6 y 12 chars
        if (strlen($_POST['password']) < 6) {
            $_SESSION['errors'][] = sprintf('La contraseña debe tener mas de 6 caracteres');
        }

        //var_dump($_SESSION);
        if (sizeof($_SESSION['errors']) > 0) {
            return $this->container->get('view')
                ->render($response, 'login.html.twig', [
                    'logged' => $_SESSION['isLogged'],
                    'errors' => $_SESSION['errors'],
                ]);
        } else {
            $stmt = $this->db->prepare("select * from user where email = :email and password = :password;");
            $stmt->bindValue("email", $_POST['email'], PDO::PARAM_STR);
            $stmt->bindValue("password", $_POST['password'], PDO::PARAM_STR);

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
                        'errors' => $_SESSION['errors'],
                    ]);
            }
        }
    }

    public function contacts(Request $request, Response $response, array $args)
    {

        if ($_SESSION['isLogged']) {
            //sacamos las contactos
            $stmt = $this->db->prepare("select * from contact where id_user = :id_user;");
            $stmt->bindValue("id_user", $_SESSION['id_user'], PDO::PARAM_STR);
            $stmt->execute();
            $r = $stmt->fetchAll(PDO::FETCH_ASSOC);

            $_SESSION['contacts'] = [];
            foreach ($r as $a) {
                $act = new Contact((int)$a['id'], $a['name'], $a['cognoms'], $a['tlf']);
                array_push($_SESSION['contacts'], $act);
            }

            if (isset($args['contacte_esborrat'])) {
                return $this->container->get('view')
                    ->render($response, 'contacts.html.twig', [
                        'logged' => $_SESSION['isLogged'],
                        'errors' => $_SESSION['errors'],
                        'contacts' => $_SESSION['contacts'],
                        'contacte_esborrat' => $args['contacte_esborrat']
                    ]);
            } else {
                return $this->container->get('view')
                    ->render($response, 'contacts.html.twig', [
                        'logged' => $_SESSION['isLogged'],
                        'errors' => $_SESSION['errors'],
                        'contacts' => $_SESSION['contacts']
                    ]);
            }

        } else {
            return $response->withStatus(200)->withHeader('Location', '/login');

        }
    }

    public function addContact(Request $request, Response $response)
    {
        if ($_SESSION['isLogged']) {
            return $this->container->get('view')
                ->render($response, 'addContact.html.twig', [
                    'logged' => $_SESSION['isLogged'],
                    'errors' => $_SESSION['errors'],
                ]);
        }
        else {
            return $response->withStatus(200)->withHeader('Location', '/login');

        }
    }

    public function doAddContacts(Request $request, Response $response)
    {
        $_SESSION['errors'] = [];
        //var_dump($_POST);

        // voy caracter a caracter porque soy pirata.
        $count = 0;
        $valid = true;
        for ($i = 0; $i < strlen($_POST['telefon']); $i++) {
            $c = $_POST['telefon'][$i];
            if ($valid) {
                if (preg_match("/[0-9 ]/", $c)) { // si equivale a un numero, perf
                    if ($c != ' ') {
                        $count++;
                    } // si no es un espacio, es digito mas
                } else {
                    $valid = false;
                }
            }
        }
        if ($count != 9 || !$valid)
            $_SESSION['errors'][] = sprintf('Solo puede haber numeros o espacios, y tienen que ser 9 digitos!');

        //var_dump($_SESSION);
        if (sizeof($_SESSION['errors']) > 0) {
            return $this->container->get('view')
                ->render($response, 'addContact.html.twig', [
                    'logged' => $_SESSION['isLogged'],
                    'errors' => $_SESSION['errors'],
                ]);
        } else {
            $stmt = $this->db->prepare("insert into contact(id_user, name, cognoms, tlf, created_at, activity_time) values (:id_user, :nom, :cognoms, :tlf, now(), now());");
            $stmt->bindValue("id_user", $_SESSION['id_user'], PDO::PARAM_INT);
            $stmt->bindValue("nom", $_POST['nom'], PDO::PARAM_STR);
            $stmt->bindValue("cognoms", $_POST['cognom'], PDO::PARAM_STR);
            $stmt->bindValue("tlf", $_POST['telefon'], PDO::PARAM_STR);
            $stmt->execute();

            return $response->withStatus(200)->withHeader('Location', '/contacts');

        }
    }

    public function deleteContact(Request $request, Response $response, array $args)
    {
        if ($_SESSION['isLogged']) {
            $stmt = $this->db->prepare("delete from contact where id_user = :id_user and id = :id;");
            $stmt->bindValue("id_user", $_SESSION['id_user'], PDO::PARAM_INT);
            $stmt->bindValue("id", $args['id'], PDO::PARAM_STR);

            $stmt->execute();

            return $response->withStatus(200)->withHeader('Location', '/contacts', [
                'contacte_esborrat' => "S'ha esborrat el contacte! Adeusiau."
            ]);

        }
        else {
            return $response->withStatus(200)->withHeader('Location', '/login');

        }
    }
}

