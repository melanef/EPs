<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Endereco extends Model
{
    protected $table = 'enderecos';

    protected $fillable = ['nome', 'logradouro', 'numero', 'complemento', 'bairro', 'cidade', 'uf', 'cep', 'padrao'];

    public function usuario()
    {
        return $this->belongsTo(User::class, 'usuario');
    }

    public function pedidos()
    {
        $this->belongsToMany(Pedido::class, 'endereco');
    }
}
