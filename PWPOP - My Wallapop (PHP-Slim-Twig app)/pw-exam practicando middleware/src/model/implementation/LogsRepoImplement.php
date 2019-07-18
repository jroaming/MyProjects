<?php
/**
 * Created by PhpStorm.
 * User: selde
 * Date: 03/06/2019
 * Time: 19:32
 */

namespace SallePW\SlimApp\model\implementation;
use SallePW\SlimApp\model\LogsRepository;
use SallePW\SlimApp\model\User;
use PDO;
use PDOException;

class LogsRepoImplement implements LogsRepository
{

    private $db;

    public function __construct()
    {
        // CREAMOS EL OBJECTO PDO ______________________________________________________________________________________
        $this->db = new PDO('mysql:host=localhost;dbname=exam', 'homestead', 'secret', []);
    }

    public function login(User $user) {

        try {

            // PROCEDIMIENTO BASE DE DATOS - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

            $stmt = $this->db->prepare("insert into logs(username, password) values(:username, :password);");
            $stmt->bindValue("username", $user->getUsername(), PDO::PARAM_STR);
            $stmt->bindValue("password", $user->getPassword(), PDO::PARAM_STR);

            $stmt->execute();

            // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

        } catch(PDOException $e) {
            echo "<b>Error con la DB!</b>El usuario no ha podido inserirse, intentalo luego.";
        }

        if (!empty($e)) {
            echo $e->getMessage();
        } else {
            echo "Hecho!";
        }
    }

    public function showInfo() {
        try {

            // PROCEDIMIENTO BASE DE DATOS - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

            $stmt = $this->db->prepare("select * from logs;");

            $stmt->execute();

            $result = $stmt->fetchAll(PDO::FETCH_ASSOC);

            //var_dump($result);

            // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

        } catch(PDOException $e) {
            echo "<b>Error con la DB!</b>El usuario no ha podido inserirse, intentalo luego.";
        }

        if (!empty($e)) {
            echo $e->getMessage();
            return false;

        } else {
            return $result;
        }
    }

}
