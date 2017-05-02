<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateStageIngredientsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('etapa_ingredientes', function (Blueprint $table) {
            $table->increments('id');
            $table->integer('etapa')->unsigned();
            $table->integer('ingrediente')->unsigned();
            $table->string('descricao');
            $table->timestamps();

            $table->foreign('etapa')->references('id')->on('etapas');
            $table->foreign('ingrediente')->references('id')->on('ingredientes');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::drop('etapa_ingredientes');
    }
}
