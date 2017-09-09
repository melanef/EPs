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

public abstract class Background extends GameObject {
	/**
	 * Construtor
	 *
	 * @param	double	x	Posição X
	 * @param	double 	y	Posição y
	 */
	public Background(double x, double y) {
		super();
		this.state = ACTIVE;
		this.x = x;
		this.y = y;
	}

	public void turn() {
		this.move();
	}

	abstract void move();
}