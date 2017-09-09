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

public class Enemy1 extends Enemy {
	/**
	 * @var	double	raio
	 */
	public static final double radius = 9.0;

	/**
	 * @var	long	timestamp do próximo spawn
	 */
	public static long nextSpawn;

	/**
	 * @var	Color	Código da cor
	 */
	public static final Color color = Color.CYAN;

	/**
	 * Construtor
	 */
	public Enemy1() {
		super();
		this.x = Math.random() * (GameLib.WIDTH - 20.0) + 10.0;
		this.v = 0.20 + Math.random() * 0.15;
		Timer timer = Game.getInstance().getTimer();
		Enemy1.nextSpawn = timer.getTime() + 500;
	}

	/**
	 * Getter para o raio
	 *
	 * @return	double
	 */
	public double getRadius() {
		return Enemy1.radius;
	}

	/**
	 * Define timer inicial do próximo spawn
	 *
	 * @param	Timer 	timer 	Objeto do temporizador
	 *
	 * @return	void
	 */
	public static void setNextSpawn(Timer timer) {
		Enemy1.nextSpawn = timer.getTime() + 2000;
	}

	/**
	 * Cria novos objetos desse tipo
	 *
	 * @return	Enemy1
	 */
	public static Enemy1 spawn() {
		Timer timer = Game.getInstance().getTimer();
		if (timer.getTime() > Enemy1.nextSpawn) {
			return new Enemy1();
		}

		return null;
	}

	/**
	 * Ação de deslocar-se
	 *
	 * @return	void
	 */
	protected void move() {
		super.move();
	}

	/**
	 * Ação de disparar contra o player
	 *
	 * @return	void
	 */
	protected void shoot() {
		Timer timer = Game.getInstance().getTimer();
		Player player = Game.getInstance().getPlayer();
		if(timer.getTime() > this.nextShot && this.y < player.getY()){
			ArrayList<EnemyProjectile> enemyProjectiles = Game.getInstance().getEnemyProjectiles();
			double vx = Math.cos(this.getAngle()) * 0.45;
			double vy = Math.sin(this.getAngle()) * 0.45 * (-1.0);
			EnemyProjectile projectile = new EnemyProjectile(this, vx, vy);
			enemyProjectiles.add(projectile);
			this.nextShot = (long) (timer.getTime() + 200 + Math.random() * 500);
		}
	}

	/**
	 * Desenhar na tela
	 *
	 * @return 	void
	 */
	public void drawBody() {
		GameLib.setColor(Enemy1.color);
		GameLib.drawCircle(this.x, this.y, Enemy1.radius);
	}

	/**
	 * Verificação de posição do objeto e, se for o caso, inativação
	 */
	protected boolean outOfBounds() {
		/* verificando se inimigo saiu da tela */
		if (this.y > GameLib.HEIGHT + 10){
			this.deactivate();
			return true;
		}

		return false;
	}
}