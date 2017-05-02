<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

use App\Etapa;

class EtapaPasso extends Model
{
    protected $table = 'etapa_passos';

    protected $fillable = ['instrucao'];

    public function etapa()
    {
        return $this->belongsTo(Etapa::class);
    }
}
