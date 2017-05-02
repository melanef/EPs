<?php

use Illuminate\Database\Seeder;

class IngredientsSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('usuarios')->insert([
            'nome' => 'Amauri',
            'sobrenome' => 'Melo Junior',
            'email' => 'amaurimelojunior@gmail.com',
            'password' => bcrypt('amjsenha')
        ]);

        DB::table('usuarios')->insert([
            'nome' => 'Example User',
            'sobrenome' => '',
            'email' => 'example@example.com',
            'password' => bcrypt('teste123')
        ]);

        DB::table('receitas')->insert([
            'nome' => 'Escondidinho',
            'quantidade' => 4,
            'dificuldade' => 3,
            'criador' => 1,
        ]);

        DB::table('etapas')->insert([
            'receita' => 1,
        ]);

        DB::table('ingredientes')->insert([
            'nome' => 'mandioca',
        ]);

         DB::table('etapa_ingredientes')->insert([
            'etapa' => 1,
            'ingrediente' => 1,
            'descricao' => '700 g de aipim (mandioca) cozido em água e sal',
        ]);

        DB::table('ingredientes')->insert([
            'nome' => 'batata inglesa',
        ]);

        DB::table('etapa_ingredientes')->insert([
            'etapa' => 1,
            'ingrediente' => 2,
            'descricao' => '300 g de batata inglesa cozida em água e sal',
        ]);

        DB::table('ingredientes')->insert([
            'nome' => 'manteiga',
            'derivado_leite' => true,
        ]);

        DB::table('etapa_ingredientes')->insert([
            'etapa' => 1,
            'ingrediente' => 3,
            'descricao' => '1 colher de sopa de manteiga',
        ]);

        DB::table('ingredientes')->insert([
            'nome' => 'creme de leite',
            'derivado_leite' => true,
        ]);

        DB::table('etapa_ingredientes')->insert([
            'etapa' => 1,
            'ingrediente' => 4,
            'descricao' => '1 caixinha de creme de leite',
        ]);

        DB::table('ingredientes')->insert([
            'nome' => 'cebola',
        ]);

        DB::table('etapa_ingredientes')->insert([
            'etapa' => 1,
            'ingrediente' => 5,
            'descricao' => '1 cebola pequena picada ou ralada (depende do gosto da pessoa)',
        ]);

        DB::table('ingredientes')->insert([
            'nome' => 'caldo de vegetais',
        ]);

        DB::table('etapa_ingredientes')->insert([
            'etapa' => 1,
            'ingrediente' => 6,
            'descricao' => '1 tablete de caldo de vegetais',
        ]);

        DB::table('ingredientes')->insert([
            'nome' => 'leite',
            'derivado_leite' => true,
        ]);

        DB::table('etapa_ingredientes')->insert([
            'etapa' => 1,
            'ingrediente' => 7,
            'descricao' => 'Leite aos poucos',
        ]);

        DB::table('ingredientes')->insert([
            'nome' => 'sal',
        ]);

        DB::table('etapa_ingredientes')->insert([
            'etapa' => 1,
            'ingrediente' => 8,
            'descricao' => 'Sal a gosto',
        ]);

        DB::table('ingredientes')->insert([
            'nome' => 'brocolis',
        ]);

        DB::table('etapa_ingredientes')->insert([
            'etapa' => 1,
            'ingrediente' => 9,
            'descricao' => '1/2 kg de brocolis cozidos e picados',
        ]);

        DB::table('etapa_ingredientes')->insert([
            'etapa' => 1,
            'ingrediente' => 3,
            'descricao' => '2 colheres de manteiga ou de azeite',
        ]);

        DB::table('ingredientes')->insert([
            'nome' => 'alho',
        ]);

        DB::table('etapa_ingredientes')->insert([
            'etapa' => 1,
            'ingrediente' => 10,
            'descricao' => '2 dentes de alho amassado',
        ]);

        DB::table('etapa_ingredientes')->insert([
            'etapa' => 1,
            'ingrediente' => 5,
            'descricao' => '1 cebola média em rodelas',
        ]);

        DB::table('ingredientes')->insert([
            'nome' => 'salsa',
        ]);

        DB::table('etapa_ingredientes')->insert([
            'etapa' => 1,
            'ingrediente' => 11,
            'descricao' => '1 colher de sopa de salsa picada',
        ]);

        DB::table('ingredientes')->insert([
            'nome' => 'cebolinha',
        ]);

        DB::table('etapa_ingredientes')->insert([
            'etapa' => 1,
            'ingrediente' => 12,
            'descricao' => '1 colher de sopa de cebolinha picada',
        ]);

        DB::table('ingredientes')->insert([
            'nome' => 'pimenta calabresa',
        ]);

        DB::table('etapa_ingredientes')->insert([
            'etapa' => 1,
            'ingrediente' => 13,
            'descricao' => 'Pimenta calabresa a gosta ou pimenta-do-reino (usei a calabresa)',
        ]);

        DB::table('ingredientes')->insert([
            'nome' => 'bacon',
            'derivado_carne' => true,
        ]);

        DB::table('etapa_ingredientes')->insert([
            'etapa' => 1,
            'ingrediente' => 14,
            'descricao' => 'Bacon a gosto',
        ]);

        DB::table('ingredientes')->insert([
            'nome' => 'catupiry',
            'derivado_leite' => true,
        ]);

        DB::table('etapa_ingredientes')->insert([
            'etapa' => 1,
            'ingrediente' => 15,
            'descricao' => 'Catupiry a gosto',
        ]);

        DB::table('ingredientes')->insert([
            'nome' => 'queijo mussarela',
            'derivado_leite' => true,
        ]);

        DB::table('etapa_ingredientes')->insert([
            'etapa' => 1,
            'ingrediente' => 16,
            'descricao' => 'Mussarela a gosto',
        ]);

        DB::table('etapa_passos')->insert([
            'etapa' => 1,
            'instrucao' => 'Passar aipim, batata e manteiga pelo espremedor de batatas (tem que ficar bem macio), formando um purê'
        ]);

        DB::table('etapa_passos')->insert([
            'etapa' => 1,
            'instrucao' => 'Misturar ao purê a cebola, o caldo de carne ou galinha, o creme de leite e se necessário colocar leite até ficar cremoso (grosso).'
        ]);

        DB::table('etapa_passos')->insert([
            'etapa' => 1,
            'instrucao' => 'Verificar o sal do purê (adicionar se achar necessário) e reservar.'
        ]);

        DB::table('etapa_passos')->insert([
            'etapa' => 1,
            'instrucao' => 'Fritar o alho, a cebola, depois colocar o brócolis cozido e picado e fritar mais um pouco, colocar a salsa e cebolinha. Reservar.'
        ]);

        DB::table('etapa_passos')->insert([
            'etapa' => 1,
            'instrucao' => 'Verificar o sal (adicionar se achar necessário) e reservar.'
        ]);

        DB::table('etapa_passos')->insert([
            'etapa' => 1,
            'instrucao' => 'Colocar metade do purê em uma forma refratária (untada com manteiga no fundo e nas laterais também), cobrindo seu fundo'
        ]);

        DB::table('etapa_passos')->insert([
            'etapa' => 1,
            'instrucao' => 'Colocar o recheio de brócolis.'
        ]);

        DB::table('etapa_passos')->insert([
            'etapa' => 1,
            'instrucao' => 'Cortar uma pontinha do saco de catupiry e espalhar por cima do recheio.'
        ]);

        DB::table('etapa_passos')->insert([
            'etapa' => 1,
            'instrucao' => 'Pode adicionar fatias de mussarela por cima do catupiry.'
        ]);

        DB::table('etapa_passos')->insert([
            'etapa' => 1,
            'instrucao' => 'Cobrir tudo com a outra metade restante do do purê'
        ]);

        DB::table('etapa_passos')->insert([
            'etapa' => 1,
            'instrucao' => 'Pode ser colocado queijo parmesão por cima também'
        ]);

        DB::table('etapa_passos')->insert([
            'etapa' => 1,
            'instrucao' => 'Levar ao forno por mais ou menos 20 minutos ou até dourar.'
        ]);

        DB::table('receitas')->insert([
            'nome' => 'Lasanha de beringela',
            'quantidade' => 4,
            'dificuldade' => 3,
            'criador' => 1,
        ]);

         DB::table('etapas')->insert([
            'receita' => 2,
        ]);

        DB::table('ingredientes')->insert([
            'nome' => 'berinjela',
        ]);

        DB::table('etapa_ingredientes')->insert([
            'etapa' => 2,
            'ingrediente' => 17,
            'descricao' => '2 a 3 berinjelas cortadas em fatias',
        ]);

        DB::table('ingredientes')->insert([
            'nome' => 'óleo',
        ]);

        DB::table('etapa_ingredientes')->insert([
            'etapa' => 2,
            'ingrediente' => 18,
            'descricao' => 'Fio de óleo',
        ]);

        DB::table('etapa_ingredientes')->insert([
            'etapa' => 2,
            'ingrediente' => 5,
            'descricao' => '1 cebola picada',
        ]);

        DB::table('etapa_ingredientes')->insert([
            'etapa' => 2,
            'ingrediente' => 10,
            'descricao' => '4 dentes de alho triturados',
        ]);

        DB::table('ingredientes')->insert([
            'nome' => 'pimentão vermelho',
        ]);

        DB::table('etapa_ingredientes')->insert([
            'etapa' => 2,
            'ingrediente' => 19,
            'descricao' => '1 colher (sopa) de pimentão vermelho picado',
        ]);

        DB::table('ingredientes')->insert([
            'nome' => 'orégano',
        ]);

        DB::table('etapa_ingredientes')->insert([
            'etapa' => 2,
            'ingrediente' => 20,
            'descricao' => '1 colher (chá) de orégano',
        ]);

        DB::table('ingredientes')->insert([
            'nome' => 'manjericão',
        ]);

        DB::table('etapa_ingredientes')->insert([
            'etapa' => 2,
            'ingrediente' => 21,
            'descricao' => '2 colheres (sopa) de manjericão picado',
        ]);

        DB::table('ingredientes')->insert([
            'nome' => 'pimenta-do-reino',
        ]);

        DB::table('etapa_ingredientes')->insert([
            'etapa' => 2,
            'ingrediente' => 22,
            'descricao' => 'Pimenta-do-reino a gosto',
        ]);

        DB::table('etapa_ingredientes')->insert([
            'etapa' => 2,
            'ingrediente' => 8,
            'descricao' => 'Sal a gosto',
        ]);

        DB::table('ingredientes')->insert([
            'nome' => 'açúcar',
        ]);

        DB::table('etapa_ingredientes')->insert([
            'etapa' => 2,
            'ingrediente' => 23,
            'descricao' => 'Pitada de açúcar',
        ]);

        DB::table('ingredientes')->insert([
            'nome' => 'tomate',
        ]);

        DB::table('etapa_ingredientes')->insert([
            'etapa' => 2,
            'ingrediente' => 24,
            'descricao' => '2 latas de tomate pelado picado ou se preferir molho de tomate',
        ]);

        DB::table('etapa_ingredientes')->insert([
            'etapa' => 2,
            'ingrediente' => 16,
            'descricao' => '100g de queijo mussarela fatiado',
        ]);

        DB::table('ingredientes')->insert([
            'nome' => 'queijo branco',
            'derivado_leite' => true,
        ]);

        DB::table('etapa_ingredientes')->insert([
            'etapa' => 2,
            'ingrediente' => 25,
            'descricao' => '200g de queijo branco',
        ]);

        DB::table('ingredientes')->insert([
            'nome' => 'queijo parmesão',
            'derivado_leite' => true,
        ]);

        DB::table('etapa_ingredientes')->insert([
            'etapa' => 2,
            'ingrediente' => 26,
            'descricao' => 'Queijo parmesão para salpicar',
        ]);

        DB::table('etapa_ingredientes')->insert([
            'etapa' => 2,
            'ingrediente' => 21,
            'descricao' => 'Manjericão para salpicar',
        ]);

        DB::table('etapa_passos')->insert([
            'etapa' => 2,
            'instrucao' => 'Corte as berinjelas no sentido longitudinal, e cozinhe-as em água fervente com sal por aproximadamente 4 minutos, escorra e coloque em uma tigela com gelo para cessar o cozimento, escorra novamente e seque cada fatia.'
        ]);

        DB::table('etapa_passos')->insert([
            'etapa' => 2,
            'instrucao' => 'Em uma panela, coloque o óleo e refogue a cebola, o alho, o pimentão picado, o orégano, o sal, a pimenta e manjericão. Adicione o tomate pelado e uma pitada de açúcar. Deixe apurar um pouco e reserve.'
        ]);

        DB::table('etapa_passos')->insert([
            'etapa' => 2,
            'instrucao' => 'Em uma travessa untada, disponha uma camada de molho, uma camada de berinjela, uma cama de queijo (branco e mussarela), outra de berinjela, outra camada de molho, e siga alternando as camadas e finalizando com os queijos.'
        ]);

        DB::table('etapa_passos')->insert([
            'etapa' => 2,
            'instrucao' => 'Salpique queijo parmesão ralado.'
        ]);

        DB::table('etapa_passos')->insert([
            'etapa' => 2,
            'instrucao' => 'Leve ao forno pré-aquecido 180° por 20 minutos, ou até dourar.'
        ]);
    }
}
