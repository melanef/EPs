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

public class Boss2 extends Enemy {
	/**
	 * @var	double	raio
	 */
	public static double radius = 20.0;

	/**
	 * @var	long	duração da explosão
	 */
	protected static final long explosionTime = 1500;

	/**
	 * Construtor
	 */
	public Boss2() {
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
		return Boss2.radius;
	}

	/**
	 * Ação de deslocar-se
	 *
	 * @return	void
	 */
	protected void move() {
		Timer timer = Game.getInstance().getTimer();
		Player player = Game.getInstance().getPlayer();

		int inboundProjectiles[] = {0, 0};
		boolean dodgeProjectiles = false;
		ArrayList<PlayerProjectile> playerProjectiles = Game.getInstance().getPlayerProjectiles();
		for (PlayerProjectile currentProjectile : playerProjectiles) {
			if (currentProjectile.getX() < this.x) {
				inboundProjectiles[0]++;
			} else if (currentProjectile.getX() > this.x) {
				inboundProjectiles[1]++;
			}

			double dx = this.x - currentProjectile.getX();
			double dy = this.y - currentProjectile.getY();
			if (Math.sqrt(dx * dx) < 1.5 * this.getRadius()) {
				if (currentProjectile.getY() < (this.y + (10 * Boss2.radius))) {
					dodgeProjectiles = true;
				}
			}
		}

		int xFactor = 0;
		if (dodgeProjectiles) {
			if (inboundProjectiles[0] > inboundProjectiles[1]) {
				xFactor = 1;
			} else if (inboundProjectiles[0] < inboundProjectiles[1]) {
				xFactor = -1;
			} else /*if (this.x + this.getRadius() > GameLib.WIDTH / 2)*/ {
				xFactor = -1;
			} /*else {
				xFactor = 1;
			}*/
		} else {
			if (player.getX() > this.x) {
				xFactor = 1;
			} else if (player.getX() < this.x) {
				xFactor = -1;
			}
		}

		this.x += xFactor * this.v * timer.getDelta();
	}

	/**
	 * Ação de disparar contra o player
	 *
	 * @return	void
	 */
	protected void shoot() {
		Timer timer = Game.getInstance().getTimer();
		if(timer.getTime() > this.nextShot){
			double vx;
			double vy;
			EnemyProjectile projectile;
			ArrayList<EnemyProjectile> enemyProjectiles = Game.getInstance().getEnemyProjectiles();
			
			vx = Math.cos(this.getAngle() - Math.PI / 4) * 0.45;
			vy = Math.sin(this.getAngle() - Math.PI / 4) * 0.45 * (-1.0);
			projectile = new EnemyProjectile(this, vx, vy);
			enemyProjectiles.add(projectile);

			vx = Math.cos(this.getAngle()) * 0.45;
			vy = Math.sin(this.getAngle()) * 0.45 * (-1.0);
			projectile = new EnemyProjectile(this, vx, vy);
			enemyProjectiles.add(projectile);

			vx = Math.cos(this.getAngle() + Math.PI / 4) * 0.45;
			vy = Math.sin(this.getAngle() + Math.PI / 4) * 0.45 * (-1.0);
			projectile = new EnemyProjectile(this, vx, vy);
			enemyProjectiles.add(projectile);
			
			this.nextShot = (long) (timer.getTime() + 250 + Math.random() * 250);
		}
	}

	/**
	 * Desenhar na tela
	 *
	 * @return 	void
	 */
	protected void drawBody() {
		double radius = this.getRadius();
		GameLib.setColor(Color.DARK_GRAY);	
		
		GameLib.drawLine(this.x - 4, this.y, this.x - 4, this.y + 29);
		GameLib.drawLine(this.x - 5, this.y, this.x - 5, this.y + 28);
		GameLib.drawLine(this.x - 6, this.y, this.x - 6, this.y + 27);
		GameLib.drawLine(this.x - 7, this.y, this.x - 7, this.y + 26);
		GameLib.drawLine(this.x - 8, this.y, this.x - 8, this.y + 25);
		GameLib.drawLine(this.x - 9, this.y, this.x - 9, this.y + 24);
		GameLib.drawLine(this.x - 10, this.y, this.x - 10, this.y + 23);
		GameLib.drawLine(this.x - 11, this.y, this.x - 11, this.y + 22);
		GameLib.drawLine(this.x - 12, this.y, this.x - 12, this.y + 21);
		GameLib.drawLine(this.x - 13, this.y, this.x - 13, this.y + 20);
		GameLib.drawLine(this.x - 14, this.y, this.x - 14, this.y + 19);
		GameLib.drawLine(this.x - 15, this.y, this.x - 15, this.y + 18);
		GameLib.drawLine(this.x - 16, this.y, this.x - 16, this.y + 17);
		GameLib.drawLine(this.x - 17, this.y, this.x - 17, this.y + 16);
		GameLib.drawLine(this.x - 18, this.y, this.x - 18, this.y + 15);
		GameLib.drawLine(this.x - 19, this.y, this.x - 19, this.y + 14);

		GameLib.drawLine(this.x + 4, this.y, this.x + 4, this.y + 29);
		GameLib.drawLine(this.x + 5, this.y, this.x + 5, this.y + 28);
		GameLib.drawLine(this.x + 6, this.y, this.x + 6, this.y + 27);
		GameLib.drawLine(this.x + 7, this.y, this.x + 7, this.y + 26);
		GameLib.drawLine(this.x + 8, this.y, this.x + 8, this.y + 25);
		GameLib.drawLine(this.x + 9, this.y, this.x + 9, this.y + 24);
		GameLib.drawLine(this.x + 10, this.y, this.x + 10, this.y + 23);
		GameLib.drawLine(this.x + 11, this.y, this.x + 11, this.y + 22);
		GameLib.drawLine(this.x + 12, this.y, this.x + 12, this.y + 21);
		GameLib.drawLine(this.x + 13, this.y, this.x + 13, this.y + 20);
		GameLib.drawLine(this.x + 14, this.y, this.x + 14, this.y + 19);
		GameLib.drawLine(this.x + 15, this.y, this.x + 15, this.y + 18);
		GameLib.drawLine(this.x + 16, this.y, this.x + 16, this.y + 17);
		GameLib.drawLine(this.x + 17, this.y, this.x + 17, this.y + 16);
		GameLib.drawLine(this.x + 18, this.y, this.x + 18, this.y + 15);
		GameLib.drawLine(this.x + 19, this.y, this.x + 19, this.y + 14);

		GameLib.fillRect(this.x - 10, this.y + 10, 1.5 * radius, 0.5 * radius);
		GameLib.setColor(Color.GRAY);
		GameLib.fillCircle(this.x, this.y, radius);
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