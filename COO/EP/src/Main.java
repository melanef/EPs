/******************************************************************************/
/* Escola de Artes, Ciências e Humanidades - EACH USP                         */
/* Projeto de Computação Orientada a Objetos                                  */
/* Professor Flávio Coutinho                                                  */
/*                                                                            */
/* Amauri de Melo Junior                                  8516650             */
/* Gisely Alves Brandão                                   8921454             */
/* Matheus Takao Harada Guerrero                          8084659             */
/* Regiany Nunes de Almeida                               8921496             */
/******************************************************************************/

public class Main {
	/* Método principal */	
	public static void main(String [] args){

		/* Instancia objeto gerenciador do jogo */
		Game game = StagedGame.getInstance();
		game.run();
		
		System.exit(0);
	}
}
