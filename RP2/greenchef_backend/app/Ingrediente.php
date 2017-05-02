<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Ingrediente extends Model
{
    protected $table = 'ingredientes';

    protected $fillable = ['nome', 'derivado_leite', 'derivado_ovo', 'derivado_carne', 'contem_glutem', 'valor', 'quantidade', 'medida'];

    public function etapa()
    {
        return $this->belongsToMany(EtapaIngrediente::class);
    }

    public function pedido()
    {
        return $this->belongsToMany(PedidoItem::class);
    }
}
