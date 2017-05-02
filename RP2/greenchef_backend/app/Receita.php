<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

use App\Etapa;
use App\Usuario;

class Receita extends Model
{
    protected $table = 'receitas';

    protected $fillable = ['nome', 'dificuldade', 'quantidade', 'imagem', 'valor'];

    public function etapas()
    {
        return $this->hasMany(Etapa::class, 'receita');
    }

    public function criador()
    {
        return $this->belongsTo(User::class, 'criador');
    }
}
