<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Http\Requests;
use App\Http\Controllers\Controller;

use App\Endereco;
use App\Pedido;
use App\PedidoItem;
use App\Receita;
use App\User;

use DB;
use Input;
use Response;

class APIController extends Controller
{
    public function __construct() {
        $this->middleware('auth');
    }

    public function list_recipes(Request $request) {
        $receitas = Receita::orderBy('created_at', 'asc')->get();

        $responseJson = ['recipes' => $receitas];
        return Response::json($responseJson, 200);
    }

    public function get_recipe(Request $request, $id) {
        $receita = Receita::findOrFail($id);
        $etapas = $receita->etapas()->get();

        $responseJson = [
            "name" => $receita->nome,
            "dificulty" => $receita->dificuldade,
            "quantity_of_people" => $receita->quantidade,
            "value" => $receita->valor,
            "image" => $receita->imagem,
            "stages" => [],
        ];

        foreach($etapas as $i => $etapa)
        {
            $responseJson{"stages"}[$i]{"name"} = $etapa->nome;
            $responseJson{"stages"}[$i]{"ingredients"} = [];
            $responseJson{"stages"}[$i]{"prepare"} = [];

            $ingredientes = $etapa->ingredientes()->get();
            foreach($ingredientes as $j => $ingrediente)
            {
                $responseJson{"stages"}[$i]{"ingredients"}[] = $ingrediente->descricao;
            }

            $passos = $etapa->passos()->get();
            foreach($passos as $j => $passo)
            {
                $responseJson{"stages"}[$i]{"prepare"}[] = $passo->instrucao;
            }
        }

        return Response::json($responseJson, 200);
    }

    public function include_ingredient(Request $request) {
        $usuario = $request->user();
        $usuario = User::findOrFail($usuario->id);

        switch(Input::get('ingredient_name'))
        {
            case 'carne':
                $usuario->aceita_carne = true;
                break;
            case 'gluten':
                $usuario->aceita_gluten = true;
                break;
            case 'leite':
                $usuario->aceita_leite = true;
                break;
            case 'ovo':
                $usuario->aceita_ovo = true;
                break;
            default:
                return Response::json('Ingrediente inválido', 400);
        }

        $usuario->save();
        return Response::json('OK', 200);
    }

    public function exclude_ingredient(Request $request) {
        $usuario = $request->user();
        $usuario = User::findOrFail($usuario->id);

        switch(Input::get('ingredient_name'))
        {
            case 'carne':
                $usuario->aceita_carne = false;
                break;
            case 'gluten':
                $usuario->aceita_gluten = false;
                break;
            case 'leite':
                $usuario->aceita_leite = false;
                break;
            case 'ovo':
                $usuario->aceita_ovo = false;
                break;
            default:
                return Response::json('Ingrediente inválido', 400);
        }

        $usuario->save();
        return Response::json('OK', 200);
    }

    public function ingredient_options(Request $request) {
        $usuario = $request->user();

        $responseJson = [
            'carne' => $usuario->aceita_carne,
            'gluten' => $usuario->aceita_gluten,
            'leite' => $usuario->aceita_leite,
            'ovo' => $usuario->aceita_ovo,
        ] ;

        return Response::json($responseJson, 200);
    }

    public function order(Request $request)
    {
        $usuario = $request->user();
        $usuario = User::find($usuario->id);
        $order_data = $request->only('address', 'number', 'complement', 'quantity_of_people', 'recipe_id', 'payment_method');

        $receita = Receita::findOrFail($order_data['recipe_id']);
        if(!$receita)
        {
            return Response::json('Receita inválida', 400);
        }

        $enderecos = $usuario->enderecos()->get();
        $endereco = null;
        if($enderecos)
        {
            foreach($enderecos as $endereco_atual)
            {
                if(
                    $endereco_atual->logradouro == $order_data['address']
                &&  $endereco_atual->numero == $order_data['number']
                &&  $endereco_atual->complemento == $order_data['complement']
                )
                {
                    $endereco = $endereco_atual;
                }
            }
        }

        DB::beginTransaction();
        if(!$endereco)
        {
            $endereco = new Endereco;
            $endereco->usuario = $usuario->id;
            $endereco->logradouro = $order_data['address'];
            $endereco->numero = $order_data['number'];
            $endereco->complemento = $order_data['complement'];
            if(!$endereco->save())
            {
                DB::rollback();
                return Response::json("Falha na criação do endereço", 500);
            }
        }

        $pedido = new Pedido;
        $pedido->endereco = $endereco->id;
        $pedido->receita = $receita->id;
        $pedido->quantidade = $order_data['quantity_of_people'];
        $pedido->valor = $receita->valor * ceil($order_data['quantity_of_people'] / $receita->quantidade);
        $pedido->metodo_de_pagamento = $order_data['payment_method'];
        $resultado = $pedido->save();

        if(!$resultado)
        {
            DB::rollback();
            return Response::json("Falha na criação do pedido", 500);
        }

        $etapas = $receita->etapas()->get();
        if($etapas)
        {
            foreach($etapas as $etapa)
            {
                $ingredientes = $etapa->ingredientes()->get();

                if($ingredientes)
                {
                    foreach($ingredientes as $pivot)
                    {
                        $ingrediente = $pivot->ingrediente()->get()[0];

                        $item = new PedidoItem;
                        $item->pedido = $pedido->id;
                        $item->ingrediente = $ingrediente->id;
                        $item->descricao = $pivot->descricao;
                        if(!$item->save())
                        {
                            DB::rollback();
                            return Response::json("Falha na criação do ítem do pedido", 500);
                        }
                    }
                }
            }
        }

        DB::commit();
        return Response::json("OK", 200);
    }
}
