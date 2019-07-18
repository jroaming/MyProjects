<?php

use SallePW\SlimApp\controller\JoelController;
use SallePW\SlimApp\controller\middleware\TimingMiddleware;
use SallePW\SlimApp\controller\middleware\SessionMiddleware;

// session middleware
$app->add(SessionMiddleware::class);

// main login screen and middleware
$app->get('/joel', JoelController::class)->add(TimingMiddleware::class);
$app->post('/joel', JoelController::class . ':doLogin')->setName('joelLogin');

$app->get('/joel/{name}', JoelController::class)->add(TimingMiddleware::class);
$app->post('/joel/{name}', JoelController::class . ':doLogin')->setName('joelLogin');

$app->get('/info', JoelController::class . ':showDBInfo');
