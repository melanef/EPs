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

public class PlayerProjectile extends Projectile {
	/**
	 * Construtor
	 *
	 * @param	Player 	shooter	Objeto do jogador que fez o disparo
	 */
	public PlayerProjectile(Player shooter) {
		super(shooter, 0.0, -1.0);
		this.y = shooter.getY() - 2 * shooter.radius;
	}

	public void turn() {
		super.turn();
	}

	public void plot() {
		if (this.state == ACTIVE) {
			GameLib.setColor(Color.GREEN);
			GameLib.drawLine(this.x, this.y - 5, this.x, this.y + 5);
			GameLib.drawLine(this.x - 1, this.y - 3, this.x - 1, this.y + 3);
			GameLib.drawLine(this.x + 1, this.y - 3, this.x + 1, this.y + 3);
		}
	}

	public void checkHit(Character target) {
		if (target.getState() == ACTIVE) {
			double dx = this.x - target.getX();
			double dy = this.y - target.getY();
			double dist = Math.sqrt((dx * dx) + (dy * dy));
			
			if (dist < target.getRadius()) {
				target.hit();
				this.deactivate();
			}
		}
	}
}