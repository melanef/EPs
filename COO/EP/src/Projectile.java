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

public abstract class Projectile extends GameObject {
	/**
	 * @var	double	velocidade de deslocamento em função de X e Y
	 */
	protected double vx;
	protected double vy;

	/**
	 * Construtor
	 *
	 * @param	Timer 		timer 	Objeto do temporizador
	 * @param	Character	shooter	Objeto do personagem que realiza o disparo
	 * @param	double			vx	Velocidade em função de X
	 * @param	double			vy	Velocidade em função de Y
	 *
	 */
	public Projectile(Character shooter, double vx, double vy) {
		super();
		this.x = shooter.getX();
		this.y = shooter.getY();
		this.vx = vx;
		this.vy = vy;
		this.state = ACTIVE;
	}

	public void turn() {
		if (this.state == ACTIVE && !this.outOfBounds()) {
			this.move();
		}
	}

	protected void move() {
		Timer timer = Game.getInstance().getTimer();
		this.x += this.vx * timer.getDelta();
		this.y += this.vy * timer.getDelta();
	}

	protected boolean outOfBounds() {
		boolean result = false;
		if(this.y < 0) {
			result = true;
		}

		if (this.y > GameLib.HEIGHT) {
			result = true;
		}

		if (result) {
			this.deactivate();
		}

		return result;
	}

	abstract void checkHit(Character target);
}