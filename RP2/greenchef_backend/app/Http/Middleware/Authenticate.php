<?php

namespace App\Http\Middleware;

use Closure;
use Exception;
use App\User;
use Illuminate\Contracts\Auth\Guard;

class Authenticate
{
    /**
     * The Guard implementation.
     *
     * @var Guard
     */
    protected $auth;

    /**
     * Create a new filter instance.
     *
     * @param  Guard  $auth
     * @return void
     */
    public function __construct(Guard $auth)
    {
        $this->auth = $auth;
    }

    /**
     * Handle an incoming request.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \Closure  $next
     * @return mixed
     */
    public function handle($request, Closure $next)
    {
        $headers = $request->header();

        // Mock usando usuário do Amauri
        //$headers['authorization'] = ['Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wvZ3JlZW5jaGVmLW1lbGFuZWYuYzkuaW9cL2dyZWVuY2hlZlwvcHVibGljXC8iLCJhdWQiOjEsImlhdCI6MTQ0NzcxMTIzNiwiZXhwIjoxNDQ3NzExMjM2fQ.E1qIU-9X4n_tDgkKzytAHxwaNp1Jo-XcjCUD0JD4kRw'];
        $decoded = null;
        if(!empty($headers['authorization']))
        {
            $jwt = explode(' ', $headers['authorization'][0])[1];
            try
            {
                $this->decoded = \JWT::decode($jwt, \Config::get('app.secretKey'), array('HS256'));

                //Coloca o usuario do jwt no request
                $customUserResolver = function() {
                    $user = \DB::table('usuarios')->find($this->decoded->aud);
                    return $user;
                };
                $request->setUserResolver($customUserResolver);
            }
            catch (Exception $e)
            {
                //return response($e->getMessage(), 401);
                // * @throws DomainException              Algorithm was not provided
                // * @throws UnexpectedValueException     Provided JWT was invalid
                // * @throws SignatureInvalidException    Provided JWT was invalid because the signature verification failed
                // * @throws BeforeValidException         Provided JWT is trying to be used before it's eligible as defined by 'nbf'
                // * @throws BeforeValidException         Provided JWT is trying to be used before it's been created as defined by 'iat'
                // * @throws ExpiredException             Provided JWT has since expired, as defined by the 'exp' claim
                if ($e instanceof \SignatureInvalidException)
                {
                    return response('Unauthorized - Signature verification failed', 401);
                }
                else if ( $e instanceof \DomainException )
                {
                    return response('Unauthorized - Signature verification failed', 401);
                }
                else if ( $e instanceof \UnexpectedValueException )
                {
                    return response('Unauthorized - Signature verification failed', 401);
                }
                else if ( $e instanceof \BeforeValidException )
                {
                    return response('Unauthorized - Signature verification failed', 401);
                }
                else if ( $e instanceof \ExpiredException )
                {
                    return response('Unauthorized - Signature verification failed', 401);
                }
            }
        }
        else
        {
            return \Response::make('Unauthorized - token not found', 401);
        }
        return $next($request);
    }
}
