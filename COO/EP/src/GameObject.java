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

public abstract class GameObject {
	/**
	 * @var	int 	constantes dos estados
	 */
	public static final int INACTIVE = 0;
	public static final int ACTIVE = 1;
	public static final int EXPLODING = 2;

	/**
	 * @var	int		estado
	 */
	protected int state;

	/**
	 * @var	double	coordenadas X e Y
	 */
	protected double x;
	protected double y;

	/**
	 * Construtor
	 */
	public GameObject() {
		this.state = INACTIVE;
	}

	/**
	 * Getter para x
	 */
	public double getX() {
		return this.x;
	}

	/**
	 * Getter para y
	 */
	public double getY() {
		return this.y;
	}

	/**
	 * Getter para state
	 */
	public int getState() {
		return this.state;
	}

	/**
	 * Desativação de um objeto
	 */
	protected void deactivate() {
		this.state = INACTIVE;
	}

	abstract public void turn();
	
	abstract public void plot();
}