<?php

namespace App\Http\Controllers\Auth;

use Auth;
use Input;
use Request;
use Response;
use Validator;
use App\User;
use App\Http\Controllers\Controller;
use Illuminate\Foundation\Auth\ThrottlesLogins;
use Illuminate\Foundation\Auth\AuthenticatesAndRegistersUsers;

class AuthController extends Controller
{
    /*
    |--------------------------------------------------------------------------
    | Registration & Login Controller
    |--------------------------------------------------------------------------
    |
    | This controller handles the registration of new users, as well as the
    | authentication of existing users. By default, this controller uses
    | a simple trait to add these behaviors. Why don't you explore it?
    |
    */

    use AuthenticatesAndRegistersUsers, ThrottlesLogins;

    /**
     * Create a new authentication controller instance.
     *
     * @return void
     */
    public function __construct()
    {
        $this->middleware('guest', ['except' => 'getLogout']);
    }

    /**
     * Get a validator for an incoming registration request.
     *
     * @param  array  $data
     * @return \Illuminate\Contracts\Validation\Validator
     */
    protected function validator(array $data)
    {
        return Validator::make($data, [
            'name' => 'required|max:255',
            'email' => 'required|email|max:255|unique:users',
            'password' => 'required|confirmed|min:6',
        ]);
    }

    /**
     * Create a new user instance after a valid registration.
     *
     * @param  array  $data
     * @return User
     */
    protected function create(array $data)
    {
        return User::create([
            'name' => $data['name'],
            'email' => $data['email'],
            'password' => bcrypt($data['password']),
        ]);
    }

    public function signIn()
    {
        $credentialsInputs = Request::only('email', 'password');
        // Mock para pegar access_token para o usuário do Amauri
        //$credentialsInputs = [
        //    'email' => 'example@example.com',
        //    'password' => 'teste123',
        //];
        if(Auth::once($credentialsInputs))
        {
            $user = Auth::user();
            $user->device_token = Request::input('device_token');
            $user->plataforma = Request::input('platform');
            $user->save();
            $jwt = $user->generateJWT();
            return Response::json([
                'data' => [
                    'access_token' => $jwt,
                     'user' => $user,
                ]
            ], 200);
        }
        else
        {
            return Response::make('Unauthorized', 401);
        }
    }
}
