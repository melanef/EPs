<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class PedidoItem extends Model
{
    protected $table = 'pedido_itens';

    protected $fillable = ['descricao'];

    public function pedido()
    {
        return $this->belongsTo(Pedido::class, 'pedido');
    }

    public function ingrediente()
    {
        return $this->belongsTo(Ingrediente::class, 'ingrediente');
    }
}
