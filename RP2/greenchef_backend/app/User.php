<?php

namespace App;

use Config;
use JWT;
use Illuminate\Auth\Authenticatable;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Auth\Passwords\CanResetPassword;
use Illuminate\Foundation\Auth\Access\Authorizable;
use Illuminate\Contracts\Auth\Authenticatable as AuthenticatableContract;
use Illuminate\Contracts\Auth\Access\Authorizable as AuthorizableContract;
use Illuminate\Contracts\Auth\CanResetPassword as CanResetPasswordContract;

class User extends Model implements AuthenticatableContract,
                                    AuthorizableContract,
                                    CanResetPasswordContract
{
    use Authenticatable, Authorizable, CanResetPassword;

    /**
     * The database table used by the model.
     *
     * @var string
     */
    protected $table = 'usuarios';

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = ['name', 'sobrenome', 'email', 'password', 'data_nascimento', 'aceita_carne', 'aceita_gluten', 'aceita_leite', 'aceita_ovo'];

    /**
     * The attributes excluded from the model's JSON form.
     *
     * @var array
     */
    protected $hidden = ['password', 'remember_token'];

    public function receitas()
    {
        return $this->hasMany(Receita::class, 'criador');
    }

    /*
    public function pedidos()
    {
        return $this->hasMany(Pedido::class, );
    }
    */

    public function enderecos()
    {
        return $this->hasMany(Endereco::class, 'usuario');
    }

    public function generateJWT()
    {
        $infoArr = [
            'iss' => Config::get('app.url'), //iss: the issuer
            'aud' => $this->id, //aud: the audience
            'iat' => time(), //iat: the issued at timestamp
            'exp' => time() + Config::get('app.expirationTime'), //exp: expiration time
        ];
        $jwt = JWT::encode($infoArr, Config::get('app.secretKey'));
        return $jwt;
    }
}
