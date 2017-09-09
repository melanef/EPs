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

public class Enemy2 extends Enemy {
	/**
	 * @var	boolean	status para atirar ou não
	 */
	protected boolean shootNow;

	/**
	 * @var	double	raio
	 */
	public static final double radius = 12.0;

	/**
	 * @var	int		contagem de inimigos ativos
	 */
	public static int count = 0;

	/**
	 * @var	int		quantidade de inimigos por intervalo
	 */
	public static int spawnIntervalQuantity = 10;

	/**
	 * @var	double	posição X de spawn
	 */
	public static double spawnX = GameLib.WIDTH * 0.20;

	/**
	 * @var	long	timestamp do próximo spawn
	 */
	public static long nextSpawn;

	/**
	 * @var	Color	Código da cor
	 */
	public static final Color color = Color.MAGENTA;

	/**
	 * Construtor
	 */
	public Enemy2() {
		super();
		this.x = Enemy2.spawnX;
		this.v = 0.42;
		Enemy2.count++;

		Timer timer = Game.getInstance().getTimer();
		if (Enemy2.count < Enemy2.spawnIntervalQuantity) {
			Enemy2.nextSpawn = timer.getTime() + 120;
		} else {
			Enemy2.count = 0;
			Enemy2.spawnX = Math.random() > 0.5 ? GameLib.WIDTH * 0.2 : GameLib.WIDTH * 0.8;
			Enemy2.nextSpawn = (long) (timer.getTime() + 3000 + Math.random() * 3000);
		}
	}

	/**
	 * Getter para o raio
	 *
	 * @return	double
	 */
	public double getRadius() {
		return Enemy2.radius;
	}

	/**
	 * Define timer inicial do próximo spawn
	 *
	 * @param	Timer 	timer 	Objeto do temporizador
	 *
	 * @return	void
	 */
	public static void setNextSpawn(Timer timer) {
		Enemy2.nextSpawn = timer.getTime() + 7000;
	}

	/**
	 * Cria novos objetos desse tipo
	 *
	 * @return	Enemy2
	 */
	public static Enemy2 spawn() {
		Timer timer = Game.getInstance().getTimer();
		if (timer.getTime() > Enemy2.nextSpawn) {
			return new Enemy2();
		}

		return null;
	}

	protected void move() {
		double previousY = this.y;
		this.shootNow = false;

		super.move();

		double threshold = GameLib.HEIGHT * 0.30;
		if (previousY < threshold && this.y >= threshold) {
			if (this.x < GameLib.WIDTH / 2) {
				this.rv = 0.003;
			} else {
				this.rv = -0.003;
			}
		}
		
		if(this.rv > 0 && Math.abs(this.angle - 3 * Math.PI) < 0.05){
			this.rv = 0.0;
			this.angle = 3 * Math.PI;
			this.shootNow = true;
		}
		
		if(this.rv < 0 && Math.abs(this.angle) < 0.05){
			this.rv = 0.0;
			this.angle = 0.0;
			this.shootNow = true;
		}
	}

	/**
	 * Ação de disparar contra o player
	 *
	 * @return	void
	 */
	protected void shoot() {
		if (this.shootNow) {
			double [] angles = { Math.PI/2 + Math.PI/8, Math.PI/2, Math.PI/2 - Math.PI/8 };
			
			for (int i = 0; i < angles.length; i++) {
				ArrayList<EnemyProjectile> enemyProjectiles = Game.getInstance().getEnemyProjectiles();
				double a = angles[i] + Math.random() * Math.PI/6 - Math.PI/12;
				double vx = Math.cos(a) * 0.30;
				double vy = Math.sin(a) * 0.30;
				EnemyProjectile projectile = new EnemyProjectile(this, vx, vy);
				enemyProjectiles.add(projectile);
			}
		}
	}

	public void drawBody() {
		GameLib.setColor(Enemy2.color);
		GameLib.drawDiamond(this.x, this.y, Enemy2.radius);
	}

	/**
	 * Verificação de posição do objeto e, se for o caso, inativação
	 */
	protected boolean outOfBounds() {
		/* verificando se inimigo saiu da tela */
		if(	this.x < -10 || this.x > GameLib.WIDTH + 10 ) {
			this.deactivate();
			return true;
		}
		
		return false;
	}
}