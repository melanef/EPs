<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateUsersTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('usuarios', function (Blueprint $table) {
            $table->increments('id');
            $table->string('nome');
            $table->string('sobrenome');
            $table->date('data_nascimento');
            $table->string('email')->unique();
            $table->string('password', 60);
            $table->boolean('aceita_carne')->default(true);
            $table->boolean('aceita_gluten')->default(true);
            $table->boolean('aceita_leite')->default(true);
            $table->boolean('aceita_ovo')->default(true);
            $table->string('genero', 45)->nullable();
			$table->string('device_token', 200)->nullable();
			$table->string('plataforma', 10)->nullable();
            $table->rememberToken();
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::drop('usuarios');
    }
}
