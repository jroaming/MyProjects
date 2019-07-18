<?php
/**
 * Created by PhpStorm.
 * User: selde
 * Date: 03/06/2019
 * Time: 11:48
 */

namespace SallePW\SlimApp\controller\middleware;
use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Http\Message\ResponseInterface as Response;

class TimingMiddleware
{
    public function __invoke(Request $request, Response $response, callable $next)
    {
        $response->getBody()->write('Before calling "next"!');

        $response = $next($request, $response);

        $response->getBody()->write('After calling "next"!');

        return $response;
    }
}