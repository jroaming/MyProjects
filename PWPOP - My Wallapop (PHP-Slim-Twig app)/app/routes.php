<?php

use SallePW\SlimApp\controller\middleware\SessionMiddleware;
use SallePW\SlimApp\controller\ItemController;
use SallePW\SlimApp\controller\HomeController;
use SallePW\SlimApp\controller\LoginController;
use SallePW\SlimApp\controller\ProfileController;
use SallePW\SlimApp\controller\RegisterController;
use SallePW\SlimApp\controller\ProfileDeleteController;
use SallePW\SlimApp\controller\ProfileUpdateController;
use SallePW\SlimApp\controller\ItemUploadController;
use SallePW\SlimApp\controller\ItemListController;
use SallePW\SlimApp\controller\ItemBuyController;
use SallePW\SlimApp\controller\ItemSearchController;


$app->add(SessionMiddleware::class);

$app->get('/', HomeController::class);
$app->get('/home', HomeController::class);

$app->get('/item', ItemController::class);

$app->get('/login', LoginController::class);
$app->post('/login', LoginController::class . ':doLogin');
$app->get('/logout', LoginController::class . ':doLogout');


$app->get('/register', RegisterController::class);
$app->post('/register', RegisterController::class . ':doRegister');

$app->get('/profile', ProfileController::class);

// profile updates:
$app->post('/update/name', ProfileUpdateController::class . ':updateName');
$app->post('/update/password', ProfileUpdateController::class . ':updatePassword');
$app->post('/update/email', ProfileUpdateController::class . ':updateEmail');
$app->post('/update/birthdate', ProfileUpdateController::class . ':updateBirthdate');
$app->post('/update/phone', ProfileUpdateController::class . ':updatePhone');
$app->post('/update/image', ProfileUpdateController::class . ':updateImage');

// profile delete:
$app->post('/deleteAccount', ProfileDeleteController::class);

$app->get('/addItem', ItemUploadController::class);
$app->post('/addItem', ItemUploadController::class . ':doAddItem');

$app->get('/items', ItemListController::class);

$app->get('/item/{id}', ItemController::class);
$app->post('/item/{id}', ItemController::class . ':doItemUpload');
$app->post('/deleteItem/{id}', ItemController::class . ':deleteItem');

$app->post('/buyItem/{id}', ItemBuyController::class . ':buyItem');

$app->post('/search', ItemSearchController::class . ':doSearchItem');