<?php

use Slim\Flash\Messages;
use Slim\Http\Environment;
use Slim\Http\Uri;
use Slim\Views\Twig;
use Slim\Views\TwigExtension;
use SallePW\SlimApp\model\DatabaseCon;

$container = $app->getContainer();

$container['view'] = function ($c) {
    $view = new Twig(__DIR__ . '/../src/view/templates', [
        'cache' => false,
    ]);

    $router = $c->get('router');

    $uri = Uri::createFromEnvironment(new Environment($_SERVER));

    $view->addExtension(new TwigExtension($router, $uri));

    return $view;
};

$container['flash'] = function () {
    return new Messages();
};


$container['db'] = function () {
    return new DatabaseCon();
};
