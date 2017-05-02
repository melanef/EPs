<?php

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It's a breeze. Simply tell Laravel the URIs it should respond to
| and give it the controller to call when that URI is requested.
|
*/

use App\Receita;
use Illuminate\Http\Request;

Route::group(['prefix' => 'api/v1'], function() {
    Route::get('/recipes', 'APIController@list_recipes');
    Route::get('/recipe/{id}', 'APIController@get_recipe');

    Route::get('/options/ingredients', 'APIController@ingredient_options');
    Route::post('/options/ingredient/include', 'APIController@include_ingredient');
    Route::post('/options/ingredient/exclude', 'APIController@exclude_ingredient');

    Route::post('/auth/signin', 'Auth\AuthController@signIn');
    // Mock para pegar access_token para o usuário do Amauri
    //Route::get('/auth/signin', 'Auth\AuthController@signIn');

    Route::post('/order', 'APIController@order');
});