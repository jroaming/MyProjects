<?php
/**
 * Created by PhpStorm.
 * User: selde
 * Date: 03/06/2019
 * Time: 11:43
 */

namespace SallePW\SlimApp\controller\middleware;
use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Http\Message\ResponseInterface as Response;

class SessionMiddleware
{
    public function __invoke(Request $request, Response $response, callable $next)
    {
        session_start();
        return $next($request, $response);
    }
}