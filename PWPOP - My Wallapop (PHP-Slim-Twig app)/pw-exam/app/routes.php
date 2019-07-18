<?php

use PwExam\Controller\HomeController;

$app->add(HomeController::class);

$app->get('/', HomeController::class . ':indexAction');

$app->get('/register', HomeController::class . ':registre');
$app->post('/register', HomeController::class . ':doRegistre');

$app->get('/login', HomeController::class . ':login');
$app->post('/login', HomeController::class . ':doLogin');

$app->get('/contacts', HomeController::class . ':contacts');
$app->get('/contacts/add', HomeController::class . ':addContact');
$app->post('/contacts/add', HomeController::class . ':doAddContacts');

$app->get('/contacts/delete/{id}', HomeController::class . ':deleteContact');