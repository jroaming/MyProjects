<?php

use PwExam\Controller\HomeController;

$app->add(HomeController::class . ':initSession');

$app->get('/', HomeController::class . ':indexAction');

$app->get('/register', HomeController::class . ':registre');
$app->post('/register', HomeController::class . ':doRegistre');

$app->get('/login', HomeController::class . ':login');
$app->post('/login', HomeController::class . ':doLogin');

$app->get('/activity', HomeController::class . ':activity');
$app->post('/activity', HomeController::class . ':doActivity');

$app->get('/activity/{id}', HomeController::class . ':idActivity');
$app->get('/activity/{id}/delete', HomeController::class . ':idActivityDelete');
