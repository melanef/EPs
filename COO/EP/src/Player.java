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

public class Player extends Character {
	/**
	 * @var	long	timestamp do próximo disparo
	 */
	protected long nextShot;

	/**
	 * @var	double	velocidade de deslocamento em função de X e Y
	 */
	protected double vx;
	protected double vy;

	/**
	 * @var	double	raio
	 */
	public static final double radius = 12.0;

	/**
	 * @var	long	duração da explosão
	 */
	protected static final long explosionTime = 2000;

	/**
	 * Construtor
	 *
	 * @param	Timer 	timer 	Objeto do temporizador
	 */
	public Player(Timer timer) {
		super();
		this.nextShot = timer.getTime();
		this.x = GameLib.WIDTH / 2;
		this.y = GameLib.HEIGHT * 0.90;
		this.vx = 0.25;
		this.vy = 0.25;
		this.state = ACTIVE;
	}

	/**
	 * Construtor
	 *
	 * @param	Timer 	timer 		Objeto do temporizador
	 * @param	int 	hitPoints	Quantidade de pontos de vida do jogador
	 */
	public Player(Timer timer, int hitPoints) {
		super();
		this.nextShot = timer.getTime();
		this.x = GameLib.WIDTH / 2;
		this.y = GameLib.HEIGHT * 0.90;
		this.vx = 0.25;
		this.vy = 0.25;
		this.state = ACTIVE;
		this.hitPoints = hitPoints;
		this.initialHitPoints = hitPoints;
	}

	/**
	 * Getter para o raio
	 *
	 * @return	double
	 */
	public double getRadius() {
		return Player.radius;
	}

	/**
	 * Getter para a duração da explosão
	 *
	 * @return	long
	 */
	public long getExplosionTime() {
		return Player.explosionTime;
	}

	/**
	 * Ações do turno do jogador
	 *
	 * @return	void
	 */
	public void turn() {
		this.explode();
		if (this.state == ACTIVE && !this.outOfBounds()) {
			this.action();
		}
	}

	/**
	 * Desenha o player
	 *
	 * @return	void
	 */
	protected void drawBody() {
		GameLib.setColor(Color.BLUE);
		GameLib.drawPlayer(this.x, this.y, this.radius);
	}

	/**
	 * Ação de explodir (consiste apenas na mudança de estado com o passar do tempo)
	 *
	 * @return	void
	 */
	protected void explode() {
		if (this.state == EXPLODING) {
			Timer timer = Game.getInstance().getTimer();
			if (timer.getTime() > this.explosionEnd && this.explosionEnd != 0.0) {
				this.revive();
			}
		}
	}

	/**
	 * Obtem e executa ação do jogador
	 *
	 * @return	void
	 */
	protected void action() {
		Timer timer = Game.getInstance().getTimer();
		if (GameLib.iskeyPressed(GameLib.KEY_UP)) {
			this.y -= timer.getDelta() * this.vy;
		}

		if (GameLib.iskeyPressed(GameLib.KEY_DOWN)) {
			this.y += timer.getDelta() * this.vy;
		}

		if (GameLib.iskeyPressed(GameLib.KEY_LEFT)) {
			this.x -= timer.getDelta() * this.vx;
		}

		if (GameLib.iskeyPressed(GameLib.KEY_RIGHT)) {
			this.x += timer.getDelta() * this.vy;
		}

		if (GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
			this.shoot();
		}

		if (GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) {
			Game.getInstance().exit();
		}
	}

	/**
	 * Ação de disparar contra o player
	 *
	 * @return	void
	 */
	protected void shoot() {
		Timer timer = Game.getInstance().getTimer();
		if (timer.getTime() > this.nextShot) {
			ArrayList<PlayerProjectile> playerProjectiles = Game.getInstance().getPlayerProjectiles();
			PlayerProjectile projectile = new PlayerProjectile(this);
			playerProjectiles.add(projectile);
			this.nextShot = timer.getTime() + 100;
		}
	}

	/**
	 * Verificação de posição do objeto e, se for o caso, inativação
	 *
	 * @return	boolean
	 */
	protected boolean outOfBounds() {
		if (this.x < 0.0) {
			this.x = 0.0;
		}
		
		if (this.x >= GameLib.WIDTH) {
			this.x = GameLib.WIDTH - 1;
		}
		
		if (this.y < 25.0) {
			this.y = 25.0;
		}

		if (this.y >= GameLib.HEIGHT) {
			this.y = GameLib.HEIGHT - 1;
		}

		return false;
	}

	/**
	 * Restaura status (e pontos de vida)
	 *
	 * @return	void
	 */
	protected void revive() {
		this.state = ACTIVE;
		this.hitPoints = this.initialHitPoints;
		Game.getInstance().restart();
	}
}