<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Pedido extends Model
{
    protected $table = 'pedidos';

    protected $fillable = ['quantidade', 'status', 'valor', 'metodo_de_pagamento'];

    public function endereco()
    {
        return $this->hasOne(Endereco::class);
    }

    public function receita()
    {
        return $this->hasOne(Receita::class);
    }

    public function ingredientes()
    {
        return $this->hasManyThrough(Ingrediente::class, PedidoItem::class);
    }
}
