<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateStageInstructionsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('etapa_passos', function (Blueprint $table) {
            $table->increments('id');
            $table->integer('etapa')->unsigned();
            $table->text('instrucao');
            $table->timestamps();

            $table->foreign('etapa')->references('id')->on('etapas');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::drop('etapa_passos');
    }
}
