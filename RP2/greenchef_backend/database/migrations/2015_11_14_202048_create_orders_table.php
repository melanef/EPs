<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateOrdersTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('pedidos', function (Blueprint $table) {
            $table->increments('id');
            $table->integer('endereco')->unsigned();
            $table->integer('receita')->unsigned();
            $table->tinyInteger('quantidade')->unsigned();
            $table->enum('status',['pendente', 'pago', 'entregue', 'cancelado'])->default('pendente');
            $table->enum('metodo_de_pagamento', ['cash', 'credit_card']);
            $table->decimal('valor', 5, 2);
            $table->timestamps();

            $table->foreign('endereco')->references('id')->on('enderecos');
            $table->foreign('receita')->references('id')->on('receitas');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::drop('pedidos');
    }
}
