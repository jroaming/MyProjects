<?php
/**
 * Created by PhpStorm.
 * User: selde
 * Date: 11/07/2019
 * Time: 12:34
 */

namespace SallePW\SlimApp\model;

class Product
{
    private $id;
    private $username;
    private $title;
    private $description;
    private $price;
    private $category;
    private $image;
    private $isActive;

    /**
     * Product constructor.
     * @param $id
     * @param $username
     * @param $title
     * @param $description
     * @param $price
     * @param $category
     * @param $image
     * @param $isActive
     */
    public function __construct($username, $title, $description, $price, $category, $image, $isActive)
    {
        $this->username = $username;
        $this->title = $title;
        $this->description = $description;
        $this->price = $price;
        $this->category = $category;
        $this->image = $image;
        $this->isActive = $isActive;
    }

    /**
     * @return mixed
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * @param mixed $id
     */
    public function setId($id): void
    {
        $this->id = $id;
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
    public function getTitle()
    {
        return $this->title;
    }

    /**
     * @return mixed
     */
    public function getDescription()
    {
        return $this->description;
    }

    /**
     * @return mixed
     */
    public function getPrice()
    {
        return $this->price;
    }

    /**
     * @return mixed
     */
    public function getCategory()
    {
        return $this->category;
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