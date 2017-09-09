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
import java.util.ArrayList;

public class Boss1 extends Enemy {
	/**
	 * @var	double	raio
	 */
	public static double radius = 20.0;

	/**
	 * @var	long	duração da explosão
	 */
	protected static final long explosionTime = 1500;

	/**
	 * @var	boolean	Flags de indicação de movimento
	 */
	protected boolean leftToRight = true;
	protected boolean topToBottom = true;

	/**
	 * Construtor
	 */
	public Boss1() {
		super();
		this.x = GameLib.WIDTH / 2 ;
		this.v = 0.6;
	}

	/**
	 * Getter para o raio
	 *
	 * @return	double
	 */
	public double getRadius() {
		return Boss1.radius;
	}

	/**
	 * Ação de deslocar-se
	 *
	 * @return	void
	 */
	protected void move() {
		int leftToRightFactor = 1;
		int topToBottomFactor = 1;

		if (!this.leftToRight) {
			leftToRightFactor = -1;
		}

		if (!topToBottom) {
			topToBottomFactor = -1;
		}

		this.x += leftToRightFactor * Math.cos(Math.PI / 4) * this.v;
		this.y += topToBottomFactor * Math.sin(Math.PI / 4) * this.v;

		if (this.leftToRight && this.x >= 460) {
			this.leftToRight = false;
		} else if (!this.leftToRight && this.x <= 20) {
			this.leftToRight = true;
		}

		if (this.topToBottom && this.y >= 250) {
			this.topToBottom = false;
		} else if (!this.topToBottom && this.y <= 75) {
			this.topToBottom = true;
		}
	}

	/**
	 * Ação de disparar contra o player
	 *
	 * @return	void
	 */
	protected void shoot() {
		Timer timer = Game.getInstance().getTimer();
		if(timer.getTime() > this.nextShot){
			ArrayList<EnemyProjectile> enemyProjectiles = Game.getInstance().getEnemyProjectiles();
			double vx = Math.cos(this.getAngle()) * 0.45;
			double vy = Math.sin(this.getAngle()) * 0.45 * (-1.0);
			EnemyProjectile projectile = new EnemyProjectile(this, vx, vy);
			enemyProjectiles.add(projectile);
			this.nextShot = (long) (timer.getTime() + 250 + Math.random() * 250);
		}
	}

	/**
	 * Desenhar na tela
	 *
	 * @return 	void
	 */
	public void drawBody() {
		GameLib.setColor(Color.GRAY);
		GameLib.fillRect(this.x - 5, this.y - 15, 2, 35);
		GameLib.fillRect(this.x + 5, this.y - 15, 2, 35);
		GameLib.drawLine(this.x, this.y - 30, this.x - 15, this.y - 35);
		GameLib.drawLine(this.x, this.y - 29, this.x - 15, this.y - 34);
		GameLib.drawLine(this.x, this.y - 30, this.x + 15, this.y - 35);
		GameLib.drawLine(this.x, this.y - 29, this.x + 15, this.y - 34);
		GameLib.setColor(Color.DARK_GRAY);
		GameLib.fillRect(this.x - 15, this.y - 38, 3, 20);
		GameLib.fillRect(this.x + 15, this.y - 38, 3, 20);
		GameLib.fillCircle(this.x, this.y, Boss1.radius);
	}

	/**
	 * Verificação de posição do objeto e, se for o caso, inativação
	 */
	protected boolean outOfBounds() {
		return false;
	}

}