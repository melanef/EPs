<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

use App\Ingrediente;
use App\EtapaIngrediente;
use App\EtapaPasso;

class Etapa extends Model
{
    protected $table = 'etapas';

    protected $fillable = ['nome'];

    public function receita()
    {
        return $this->belongsTo(Receita::class, 'receita');
    }

    public function ingredientes()
    {
        return $this->hasMany(EtapaIngrediente::class, 'etapa');
    }

    public function passos()
    {
        return $this->hasMany(EtapaPasso::class, 'etapa');
    }
}
