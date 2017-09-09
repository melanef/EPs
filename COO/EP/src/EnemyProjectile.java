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

public class EnemyProjectile extends Projectile {
	/**
	 * @var	double	raio
	 */
	private static final double radius = 2.0;

	/**
	 * Construtor
	 *
	 * @param	Enemy 	shooter	Objeto do inimigo que fez o disparo
	 * @param	double	vx		Velocidade do projétil em função de X
	 * @param	double	vy		Velocidade do projétil em função de Y
	 */
	public EnemyProjectile(Enemy shooter, double vx, double vy) {
		super(shooter, vx, vy);
	}

	public void turn() {
		super.turn();
	}

	public void checkHit(Character target) {
		if (target.getState() == ACTIVE) {
			double dx = this.x - target.getX();
			double dy = this.y - target.getY();
			double dist = Math.sqrt((dx * dx) + (dy * dy));
			
			if (dist < (target.getRadius() + EnemyProjectile.radius) * 0.8) {
				target.hit();
				this.deactivate();
			}
		}
	}

	public void plot() {
		if (this.state == ACTIVE) {
			GameLib.setColor(Color.RED);
			GameLib.drawCircle(this.x, this.y, this.radius);
		}
	}
}