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

public class Timer {
	/**
	 * @var	Timer 	Instância única (singleton) do temporizador
	 */
	private static Timer uniqueTimer;

	/**
	 * @var long	Instante inicial do jogo
	 */
	private long initialTime;

	/**
	 * @var	long	Instante atual em millissegundos (EPOCH)
	 */
	private long currentTime;

	/**
	 * @var	long	Variação de tempo entre o último passo do loop e o atual
	 */
	private long delta;

	/**
	 * Construtor
	 */
	private Timer() {
		this.currentTime = System.currentTimeMillis();
		this.initialTime = this.currentTime;
	}

	/**
	 * Getter da instância do singleton
	 */
	public static synchronized Timer getInstance() {
		if (uniqueTimer == null) {
			uniqueTimer = new Timer();
		}

		return uniqueTimer;
	}

	/**
	 * Getter para o instante inicial
	 *
	 * @return	long
	 */
	public long getInitialTime() {
		return this.initialTime;
	}

	/**
	 * Getter para o instante atual
	 *
	 * @return	long
	 */
	public long getTime() {
		return this.currentTime;
	}

	/**
	 * Getter para a variação de tempo
	 *
	 * @return	long
	 */
	public long getDelta() {
		return this.delta;
	}

	/**
	 * Atualização de cada passo do loop
	 *
	 * @return	void
	 */
	public void loopStep() {
		long now = System.currentTimeMillis();
		this.delta = now - this.currentTime;
		this.currentTime = now;
	}

	/**
	 * Espera para normalizar o tempo de cada passo do loop
	 *
	 * @return void
	 */
	public void waitStep() {
		while (System.currentTimeMillis() < (this.currentTime + Game.waitTime)) {
			Thread.yield();
		}
	}

	/**
	 * Atualiza o instante inicial (útil em jogos com fases)
	 *
	 * @return	void
	 */
	public void resetInitialTime() {
		this.initialTime = this.currentTime;
	}

}