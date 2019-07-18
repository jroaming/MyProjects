<?php
/**
 * Created by PhpStorm.
 * User: selde
 * Date: 03/06/2019
 * Time: 19:03
 */

namespace SallePW\SlimApp\model;

interface LogsRepository
{
    public function login(User $user);

}