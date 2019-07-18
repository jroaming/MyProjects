<?php
/**
 * Created by PhpStorm.
 * User: selde
 * Date: 11/07/2019
 * Time: 12:34
 */

namespace SallePW\SlimApp\model;

class User
{
    private $name;
    private $username;
    private $email;
    private $password;
    private $birthdate;
    private $phone;
    private $image;
    private $isActive;

    /**
     * User constructor.
     * @param $name
     * @param $username
     * @param $email
     * @param $password
     * @param $birthdate
     * @param $phone
     * @param $image
     * @param $isActive
     */
    public function __construct($name, $username, $email, $password, $birthdate, $phone, $image, $isActive)
    {
        $this->name = $name;
        $this->username = $username;
        $this->email = $email;
        $this->password = $password;
        $this->birthdate = $birthdate;
        $this->phone = $phone;
        $this->image = $image;
        $this->isActive = $isActive;
    }

    /**
     * @return mixed
     */
    public function getName()
    {
        return $this->name;
    }

    /**
     * @return mixed
     */
    public function getUsername()
    {
        return $this->username;
    }

    /**
     * @return mixed
     */
    public function getEmail()
    {
        return $this->email;
    }

    /**
     * @return mixed
     */
    public function getPassword()
    {
        return $this->password;
    }

    /**
     * @return mixed
     */
    public function getBirthdate()
    {
        return $this->birthdate;
    }

    /**
     * @return mixed
     */
    public function getPhone()
    {
        return $this->phone;
    }

    /**
     * @return mixed
     */
    public function getImage()
    {
        return $this->image;
    }

    /**
     * @return mixed
     */
    public function getisActive()
    {
        return $this->isActive;
    }
}
