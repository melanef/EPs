<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateRecipesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('receitas', function (Blueprint $table) {
            $table->increments('id');
            $table->string('nome');
            $table->string('imagem')->nullable();
            $table->integer('criador')->unsigned();
            $table->tinyInteger('dificuldade');
            $table->tinyInteger('quantidade');
            $table->decimal('valor', 5, 2);
            $table->timestamps();

            $table->foreign('criador')->references('id')->on('usuarios');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::drop('receitas');
    }
}
