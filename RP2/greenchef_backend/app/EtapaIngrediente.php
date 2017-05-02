<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

use App\Ingrediente;

class EtapaIngrediente extends Model
{
    protected $table = 'etapa_ingredientes';

    protected $fillable = ['descricao'];

    public function ingrediente()
    {
        return $this->belongsTo(Ingrediente::class, 'ingrediente');
    }

    public function etapa()
    {
        return $this->belongsTo(Etapa::class, 'etapa');
    }
}
