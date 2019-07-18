<?php
/**
 * Created by PhpStorm.
 * User: selde
 * Date: 08/07/2019
 * Time: 13:07
 */

namespace SallePW\SlimApp\controller\middleware;

use Dflydev\FigCookies\FigRequestCookies;
use Dflydev\FigCookies\FigResponseCookies;
use Dflydev\FigCookies\SetCookie;
use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Http\Message\ResponseInterface as Response;

class SessionMiddleware
{
    private const COOKIES_ADVICE = 'cookies_session';

    public function __invoke(Request $request, Response $response, callable $next)
    {
        session_start();

        if (!isset($_SESSION['message'])) {
            $_SESSION['message'] = '';
        }

        $adviceCookie = FigRequestCookies::get($request, self::COOKIES_ADVICE);
        $cookie = $adviceCookie->getValue();

        if ($cookie != '') {
            $_SESSION['isLogged'] = true;
            $_SESSION['username'] = $cookie;
        }

        if (!isset($_SESSION['isLogged'])) {
            $_SESSION['isLogged'] = false;
        }

        return $next($request, $response);
    }
}