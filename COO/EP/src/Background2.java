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

import java.awt.Color;

class Background2 extends Background {
	/**
	 * @var	double	velocidade
	 */
	public static final double speed = 0.045;

	/**
	 * @var	int		Quantidade padrão de elementos
	 */
	public static final int quantity = 50;

	/**
	 * Construtor
	 *
	 * @param	double	x	Posição X
	 * @param	double 	y	Posição y
	 */
	public Background2(double x, double y) {
		super(x, y);
	}

	/**
	 * Factory padrão
	 */
	public static Background2 create() {
		double x = Math.random() * GameLib.WIDTH;
		double y = Math.random() * GameLib.HEIGHT;
		Background2 element = new Background2(x, y);
		return element;
	}

	/**
	 * Fundo movendo-se
	 *
	 * @return	void
	 */
	protected void move() {
		Timer timer = Game.getInstance().getTimer();
		this.y += Background2.speed * timer.getDelta();
	}

	/**
	 * Desenha objeto na tela
	 *
	 * @return	void
	 */
	public void plot() {
		GameLib.setColor(Color.DARK_GRAY);
		GameLib.fillRect(this.x, this.y % GameLib.HEIGHT, 2, 2);
	}
}