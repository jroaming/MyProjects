<?php
/**
 * Created by PhpStorm.
 * User: selde
 * Date: 09/07/2019
 * Time: 17:18
 */

namespace SallePW\SlimApp\model;

use PDO;
use PDOException;

class DatabaseCon
{
    private $db;
    private $dbname;
    private const DateFormat = 'Y-m-d H:i:s';

    public function __construct()
    {
        try {
            $this->dbname = 'pwpop';
            $this->db = new PDO('mysql:host=localhost;dbname=' . $this->dbname, 'homestead', 'secret', []);
        }
        catch (PDOException $e) {
            // error when dealing with the database.
        }

    }

    // funcon para buscar un usuario (mediante el username, el correo y la contraseña)
    public function searchFullUser($username) {
        $stmt = $this->db->prepare("select * from user where username = :username and is_active = 1;");
        $stmt->bindValue("username", $username, PDO::PARAM_STR);
        $stmt->execute();

        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    // funcon para buscar un usuario (mediante únicamente el correo y la contraseña)
    public function searchUser($email, $password) {
        $stmt = $this->db->prepare("select * from user where email = :email and password = :password and is_active = 1;");
        $stmt->bindValue("email", $email, PDO::PARAM_STR);
        $stmt->bindValue("password", $password, PDO::PARAM_STR);
        $stmt->execute();

        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    // funcion para crear un usuario
    public function createUser(User $user) {
        $stmt = $this->db->prepare("insert into user values (:uname, :uusername, :uemail, :ubirthdate, :uphone_number, :upassword, :uimage, 1);");
        $stmt->bindValue("uname", $user->getName(), PDO::PARAM_STR);
        $stmt->bindValue("uusername", $user->getUsername(), PDO::PARAM_STR);
        $stmt->bindValue("uemail", $user->getEmail(), PDO::PARAM_STR);
        $stmt->bindValue("ubirthdate", $user->getBirthdate(), PDO::PARAM_STR);
        $stmt->bindValue("uphone_number", $user->getPhone(), PDO::PARAM_STR);
        $stmt->bindValue("upassword", $user->getPassword(), PDO::PARAM_STR);
        $stmt->bindValue("uimage", $user->getImage(), PDO::PARAM_STR);

        $stmt->execute();
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    // funcion para obtener informacion del usuario con el correo proporcionado
    public function getUserInfo($username) {
        $stmt = $this->db->prepare("select * from user where username = :username and is_active = 1;");
        $stmt->bindValue("username", $username, PDO::PARAM_STR);
        $stmt->execute();

        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    // funcion para poner en inactivo el usuario con el email proporcionado
    public function deleteAccount($username) {
        $stmt = $this->db->prepare("update user set is_active = 0 where username = :username and is_active = 1;");
        $stmt->bindValue("username", $username, PDO::PARAM_STR);
        $stmt->execute();

        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    // function para actualiza el nombre de usuario
    public function updateUserName($username, $name) {
        $stmt = $this->db->prepare("update user set name = :uname where username = :username and is_active = 1;");
        $stmt->bindValue("uname", $name, PDO::PARAM_STR);
        $stmt->bindValue("username", $username, PDO::PARAM_STR);
        $stmt->execute();
    }

    // function para actualiza el email del usuario
    public function updateUserEmail($username, $email) {
        $stmt = $this->db->prepare("update user set email = :email where username = :username and is_active = 1;");
        $stmt->bindValue("email", $email, PDO::PARAM_STR);
        $stmt->bindValue("username", $username, PDO::PARAM_STR);
        $stmt->execute();
    }

    // function para actualiza el telefono del usuario
    public function updateUserPhone($username, $phone) {
        $stmt = $this->db->prepare("update user set phone_number = :phone where username = :username and is_active = 1;");
        $stmt->bindValue("phone", $phone, PDO::PARAM_STR);
        $stmt->bindValue("username", $username, PDO::PARAM_STR);
        $stmt->execute();
    }

    // function para actualiza el password de usuario
    public function updateUserPassword($username, $password) {
        $stmt = $this->db->prepare("update user set password = :password where username = :username and is_active = 1;");
        $stmt->bindValue("password", $password, PDO::PARAM_STR);
        $stmt->bindValue("username", $username, PDO::PARAM_STR);
        $stmt->execute();
    }

    // function para actualiza el la fecha de nacimiento del usuario
    public function updateUserBirthdate($username, $birthdate) {
        $stmt = $this->db->prepare("update user set birthdate = :birthdate where username = :username and is_active = 1;");
        $stmt->bindValue("birthdate", $birthdate, PDO::PARAM_STR);
        $stmt->bindValue("username", $username, PDO::PARAM_STR);
        $stmt->execute();
    }

    // function para actualiza la imagen de usuario
    public function updateUserImage($username, $image) {
        $stmt = $this->db->prepare("update user set image = :image where username = :username and is_active = 1;");
        $stmt->bindValue("image", $image, PDO::PARAM_STR);
        $stmt->bindValue("username", $username, PDO::PARAM_STR);
        $stmt->execute();
    }

    // function que agrega el producto a la tabla
    public function createProduct(Product $product) {
        $stmt = $this->db->prepare("insert into product values (0, :username, :title, :description, :price, :category, :image, 1);");
        $stmt->bindValue("username", $product->getUsername(), PDO::PARAM_STR);
        $stmt->bindValue("title", $product->getTitle(), PDO::PARAM_STR);
        $stmt->bindValue("description", $product->getDescription(), PDO::PARAM_STR);
        $stmt->bindValue("price", $product->getPrice(), PDO::PARAM_STR);
        $stmt->bindValue("category", $product->getCategory(), PDO::PARAM_STR);
        $stmt->bindValue("image", $product->getImage(), PDO::PARAM_STR);
        $stmt->execute();
    }

    // funcion que busca que no haya ningun producto con el nombre ofrecido (por si hay repetidos, avisar al addItem)
    public function searchRepeatedProduct($name) {
        $stmt = $this->db->prepare("select * from product where title = :title;");
        $stmt->bindValue("title", $name, PDO::PARAM_STR);
        $stmt->execute();

        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    // function para pillar todos los items a la venta del usuario indicado
    public function getItemsList($username) {
        $stmt = $this->db->prepare("select * from product where username = :username and is_active = 1;");
        $stmt->bindValue("username", $username, PDO::PARAM_STR);
        $stmt->execute();

        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    // function para pillar toda la info de un item dado su ID
    public function getItemInfo($id) {
        $stmt = $this->db->prepare("select * from product where id = :id;");
        $stmt->bindValue("id", $id, PDO::PARAM_INT);
        $stmt->execute();

        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    // function para actualiza el titulo del producto cuya ID hemos recibido
    public function updateProductTitle($id, $title) {
        $stmt = $this->db->prepare("update product set title = :title where id = :id;");
        $stmt->bindValue("title", $title, PDO::PARAM_STR);
        $stmt->bindValue("id", $id, PDO::PARAM_INT);
        $stmt->execute();
    }

    // function para actualiza la descripcion del producto cuya ID hemos recibido
    public function updateProductDescription($id, $title) {
        $stmt = $this->db->prepare("update product set description = :title where id = :id;");
        $stmt->bindValue("title", $title, PDO::PARAM_STR);
        $stmt->bindValue("id", $id, PDO::PARAM_INT);
        $stmt->execute();
    }

    // function para actualiza el precio del producto cuya ID hemos recibido
    public function updateProductPrice($id, $title) {
        $stmt = $this->db->prepare("update product set price = :title where id = :id;");
        $stmt->bindValue("title", $title, PDO::PARAM_STR);
        $stmt->bindValue("id", $id, PDO::PARAM_INT);
        $stmt->execute();
    }

    // function para actualiza la categoria del producto cuya ID hemos recibido
    public function updateProductCategory($id, $title) {
        $stmt = $this->db->prepare("update product set category = :title where id = :id;");
        $stmt->bindValue("title", $title, PDO::PARAM_STR);
        $stmt->bindValue("id", $id, PDO::PARAM_INT);
        $stmt->execute();
    }

    // function para actualiza la categoria del producto cuya ID hemos recibido
    public function updateProductImage($id, $title) {
        $stmt = $this->db->prepare("update product set image = :title where id = :id;");
        $stmt->bindValue("title", $title, PDO::PARAM_STR);
        $stmt->bindValue("id", $id, PDO::PARAM_INT);
        $stmt->execute();
    }

    // funcion que pone a inactivo el producto indicado
    public function setProductActive($id, $value) {
        $stmt = $this->db->prepare("update product set is_active = :is_active where id = :id;");
        $stmt->bindValue("is_active", $value, PDO::PARAM_BOOL);
        $stmt->bindValue("id", $id, PDO::PARAM_INT);
        $stmt->execute();
    }

    // funcion que devuelve el email del usuario que recibimos
    public function getUserEmail($username) {
        $stmt = $this->db->prepare("select email from user where username = :username and is_active = 1;");
        $stmt->bindValue("username", $username, PDO::PARAM_STR);
        $stmt->execute();

        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    // funcion que devuelve todos los productos activos de la base de datos
    public function getActiveProducts() {
        $stmt = $this->db->prepare("select * from product where is_active = 1 order by id desc;");
        $stmt->execute();

        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    // funcion que devuelve todos los productos activos de la base de datos
    public function getActiveProductsNotOwned($username) {
        $stmt = $this->db->prepare("select * from product where username <> :username and is_active = 1 order by id desc;");
        $stmt->bindValue("username", $username, PDO::PARAM_STR);
        $stmt->execute();

        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    // funcion para obtener los items de resultado de la busqueda
    public function getItems($title) {
        $stmt = $this->db->prepare("select * from product where title like concat('%', :title, '%') and is_active = 1;");
        $stmt->bindValue("title", $title, PDO::PARAM_STR);
        $stmt->execute();

        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    // funcion que borra los items de un usuario (los pone a inactivo)
    public function deleteAccountItems($username) {
        $stmt = $this->db->prepare("update product set is_active = false where username = :username and is_active = 1;");
        $stmt->bindValue("username", $username, PDO::PARAM_STR);
        $stmt->execute();

        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }
}
