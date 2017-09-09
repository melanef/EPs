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

public abstract class Enemy extends Character {
	/**
	 * @var	double	ângulo
	 */
	protected double angle;
	
	/**
	 * @var	double	velocidade de deslocamento
	 */
	protected double v;
	
	/**
	 * @var double 	velocidade de rotação
	 */
	protected double rv;

	/**
	 * @var	Color	Código da cor
	 */
	public static Color color;

	/**
	 * @var	long	timestamp do próximo disparo
	 */
	protected long nextShot;

	/**
	 * @var	long	duração da explosão
	 */
	protected static final long explosionTime = 500;

	/**
	 * Construtor
	 */
	public Enemy() {
		super();
		this.state = ACTIVE;
		this.y = -10.0;
		this.angle = 3 * Math.PI / 2;
		this.rv = 0.0;
	}

	/**
	 * Getter para angle
	 *
	 * @return	double
	 */
	public double getAngle() {
		return this.angle;
	}

	/**
	 * Getter para color
	 *
	 * @return Color
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * Getter para a duração da explosão
	 *
	 * @return	long
	 */
	public long getExplosionTime() {
		return Enemy.explosionTime;
	}

	/**
	 * Setter para posição (X e Y)
	 *
	 * @param	double	x 	Coordenada X da posição
	 * @param	double	y 	Coordenada Y da posição
	 *
	 * @return	void
	 */
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Ações do turno do inimigo
	 *
	 * @return	void
	 */
	public void turn() {
		this.explode();
		if(this.state == ACTIVE && !this.outOfBounds()){
			this.move();
			this.shoot();
		}
	}

	/**
	 * Ação de explodir (consiste apenas na mudança de estado com o passar do tempo)
	 *
	 * @return	void
	 */
	protected void explode() {
		if (this.state == EXPLODING) {
			Timer timer = Game.getInstance().getTimer();
			if (timer.getTime() > this.explosionEnd) {
				this.deactivate();
			}
		}
	}

	/**
	 * Ação de deslocar-se
	 *
	 * @return	void
	 */
	protected void move() {
		Timer timer = Game.getInstance().getTimer();
		this.x += this.v * Math.cos(this.angle) * timer.getDelta();
		this.y += this.v * Math.sin(this.angle) * timer.getDelta() * (-1.0);
		this.angle += this.rv * timer.getDelta();
	}

	abstract void shoot();

	abstract boolean outOfBounds();

	/**
	 * Verifica se inimigo atingiu player e, se sim, inflige dano ao player
	 *
	 * @param	Player 	target	Objeto do jogador
	 *
	 * @return	void
	 */
	public void checkHit(Player target) {
		if (this.state == ACTIVE) {
			if (target.getState() == ACTIVE) {
				double dx = this.x - target.getX();
				double dy = this.y - target.getY();
				double dist = Math.sqrt((dx * dx) + (dy * dy));
				
				if (dist < (target.getRadius() + this.getRadius()) * 0.8) {
					target.hit();
					this.hit();
				}
			}
		}
	}
}