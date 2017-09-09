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

public abstract class Character extends GameObject {
	/**
	 * @var	double	raio
	 */
	public static double radius;

	/**
	 * @var	double	timers da explosão
	 */
	protected long explosionStart;
	protected long explosionEnd;

	/**
	 * @var	int 	quantidade de pontos de vida
	 */
	protected int hitPoints = 1;

	/**
	 * @var int 	quantidade original de pontos de vida
	 */
	protected int initialHitPoints = 1;

	/**
	 * Construtor
	 */
	public Character() {
		super();
	}

	/**
	 * Marca o timestamp no qual a explosão começou e deve terminar
	 *
	 * @return	void
	 */
	protected void setExplosion() {
		Timer timer = Game.getInstance().getTimer();
		this.state = EXPLODING;
		this.explosionStart = timer.getTime();
		this.explosionEnd = timer.getTime() + this.getExplosionTime();
	}

	/**
	 * Define os pontos de vida do personagem
	 *
	 * @param	int 	hitPoints 	quantidade de pontos de vida
	 *
	 * @return	void
	 */
	public void setHitPoints(int hitPoints) {
		this.hitPoints = hitPoints;
		this.initialHitPoints = hitPoints;
	}

	/**
	 * Ação de ser atingido (por um disparo)
	 *
	 * @return	void
	 */
	public void hit() {
		if (--this.hitPoints == 0) {
			this.setExplosion();
		}
	}

	/**
	 * Desenha o personagem
	 *
	 * @return	void
	 */
	public void plot() {
		if (this.state == EXPLODING) {
			Timer timer = Game.getInstance().getTimer();
			double alpha = (double) (timer.getTime() - this.explosionStart) / (this.explosionEnd - this.explosionStart);
			GameLib.drawExplosion(this.x, this.y, alpha);
		} else if(this.state == ACTIVE) {
			this.drawBody();
			this.drawHitPointsBar();
		}
	}


	/**
	 * Desenha barra de vida abaixo do personagem
	 *
	 * @return	void
	 */
	protected void drawHitPointsBar() {
		if (this.initialHitPoints > 1) {
			double hitPointsBarWidth = 80;
			double currenthitPointsBarWidth = hitPointsBarWidth * ((double) this.hitPoints / this.initialHitPoints);
			double hitPointsBarX = this.x;
			double hitPointsBarY = this.y + this.getRadius() + 15;
			/**
			 * Desenha o "contorno" (uma caixa com 1px a mais de cada lado) branco
			 */
			GameLib.setColor(Color.WHITE);
			GameLib.drawRect(hitPointsBarX, hitPointsBarY, hitPointsBarWidth, 10);

			/**
			 * Desenha o preenchimento (uma caixa dentro da outra, compensando a posição)
			 */
			GameLib.setColor(Color.GREEN);
			GameLib.fillRect(hitPointsBarX - (hitPointsBarWidth - currenthitPointsBarWidth) / 2, hitPointsBarY, currenthitPointsBarWidth, 10);
		}
	}

	abstract public double getRadius();
	abstract public long getExplosionTime();
	abstract protected void drawBody();
}