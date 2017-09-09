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

import java.util.ArrayList;

public class Game {
	/**
	 * @var	long	Configuração de tempo de espera entre passos do loop de execução do jogo
	 */
	public static final long waitTime = 5;

	/**
	 * @var	Game 	Instância única (singleton) do encapsulador
	 */
	protected static Game uniqueGame;

	/**
	 * @var boolean	Flag de rodando ou não
	 */
	protected boolean running;

	/**
	 * @var	Timer 	Instância do objeto temporizador
	 */
	protected Timer timer;

	/**
	 * @var	Player	Instância do objeto do jogador
	 */
	protected Player player;

	/**
	 * @var	ArrayList<Enemy>	Coleção de inimigos
	 */
	protected ArrayList<Enemy> enemies;

	/**
	 * @var	ArrayList<PlayerProjectile>	Coleção de disparos realizados pelo player
	 */
	protected ArrayList<PlayerProjectile> playerProjectiles;

	/**
	 * @var	ArrayList<EnemyProjectile>	Coleção de disparos realizados pelos inimigos
	 */
	protected ArrayList<EnemyProjectile> enemyProjectiles;

	/**
	 * @var	ArrayList<Background1>	Coleção de elementos de background
	 */
	protected ArrayList<Background> backgrounds;

	/**
	 * Construtor
	 */
	protected Game() {
		this.running = true;
		this.timer = Timer.getInstance();
		this.player = new Player(this.timer);
		this.enemies = new ArrayList<Enemy>();
		this.playerProjectiles = new ArrayList<PlayerProjectile>();
		this.enemyProjectiles = new ArrayList<EnemyProjectile>();
		this.backgrounds = new ArrayList<Background>();

		this.setSpawns();

		/* estrelas que formam o fundo de primeiro plano */
		this.backgrounds = new ArrayList<Background>();
		for (int i = 0; i < Background1.quantity; i++) {
			this.backgrounds.add((Background) Background1.create());
		}

		for (int i = 0; i < Background2.quantity; i++) {
			this.backgrounds.add((Background) Background2.create());
		}

		/* iniciado interface gráfica */
		GameLib.initGraphics();
	}

	/**
	 * Getter da instância do singleton
	 */
	public static synchronized Game getInstance() {
		if (uniqueGame == null) {
			uniqueGame = new Game();
		}

		return uniqueGame;
	}

	/**
	 * Getter para objeto temporizador
	 *
	 * @return	Timer
	 */
	public Timer getTimer() {
		return this.timer;
	}

	/**
	 * Getter para o objeto do jogador
	 *
	 * @return	Player
	 */
	public Player getPlayer() {
		return this.player;
	}

	/**
	 * Getter para inimigos
	 *
	 * @return 	ArrayList<Enemy>
	 */
	public ArrayList<Enemy> getEnemies() {
		return this.enemies;
	}

	/**
	 * Getter para disparos do jogador
	 *
	 * @return	ArrayList<PlayerProjectile>
	 */
	public ArrayList<PlayerProjectile> getPlayerProjectiles() {
		return this.playerProjectiles;
	}

	/**
	 * Getter para disparos dos inimigos
	 *
	 * @return	ArrayList<EnemyProjectile>
	 */
	public ArrayList<EnemyProjectile> getEnemyProjectiles() {
		return this.enemyProjectiles;
	}

	/**
	 * Ação de sair do jogo
	 *
	 * @return	void
	 */
	public void exit() {
		this.running = false;
	}

	/**
	 * Verifica estado da flag de execução
	 *
	 * @return boolean
	 */
	public boolean isRunning() {
		return this.running;
	}

	/**
	 * Define os lançamentos automáticos iniciais.
	 * Esse método deve ser sobrescrito e anulado em um jogo com fases definidas, onde não há spawn aleatório.
	 *
	 * @return	void
	 */
	public void setSpawns() {
		Enemy1.setNextSpawn(this.timer);
		Enemy2.setNextSpawn(this.timer);
	}

	/**
	 * Execução do jogo propriamente dita em loop
	 */
	public void run() {
		/*************************************************************************************************/
		/*                                                                                               */
		/* Main loop do jogo                                                                             */
		/*                                                                                               */
		/* O main loop do jogo possui executa as seguintes operações:                                    */
		/*                                                                                               */
		/* 1) Verifica se há colisões e atualiza estados dos elementos conforme a necessidade.           */
		/*                                                                                               */
		/* 2) Atualiza estados dos elementos baseados no tempo que correu desde a última atualização     */
		/*    e no timestamp atual: posição e orientação, execução de disparos de projéteis, etc.        */
		/*                                                                                               */
		/* 3) Processa entrada do usuário (teclado) e atualiza estados do player conforme a necessidade. */
		/*                                                                                               */
		/* 4) Desenha a cena, a partir dos estados dos elementos.                                        */
		/*                                                                                               */
		/* 5) Espera um período de tempo (de modo que delta seja aproximadamente sempre constante).      */
		/*                                                                                               */
		/*************************************************************************************************/
		while (this.running) {
			/* Atualizando o timer */
			this.timer.loopStep();

			/* Verificação de colisões */	
			this.executeColisions();

			/* Atualização de estados */
			this.updateStates();

			/* Limpa as coleções dos elementos inativos */
			this.removeInactives();

			/* Lançando novos inimigos */
			this.spawnNewEnemies();

			/* Desenha a cena */
			this.plot();

			/* faz uma pausa de modo que cada execução do laço do main loop demore aproximadamente 5 ms. */
			this.timer.waitStep();
		}
	}

	/**
	 * Verificação de colisões
	 *
	 * @return	void
	 */
	private void executeColisions() {
		if (this.player.getState() == GameObject.ACTIVE) {
			/* colisões player - projeteis (inimigo) */
			for (EnemyProjectile currentEnemyProjectile : this.enemyProjectiles) {
				currentEnemyProjectile.checkHit(this.player);
			}
		
			/* colisões player - inimigos*/
			for (Enemy currentEnemy : this.enemies) {
				currentEnemy.checkHit(this.player);
			}
		}
		
		for (PlayerProjectile currentPlayerProjectile : this.playerProjectiles) {
			/* colisões projeteis (player) - inimigos */
			for (Enemy currentEnemy : this.enemies) {
				currentPlayerProjectile.checkHit(currentEnemy);
			}
		}
	}

	/**
	 * Atualização de estados
	 * Onde ocorre o turno de cada objeto do jogo (explodir, se for o caso; movimentar-se; disparar, se for o caso)
	 *
	 * @return	void
	 */
	private void updateStates() {
		/* projeteis (player) */
		for (PlayerProjectile currentPlayerProjectile : this.playerProjectiles) {
			currentPlayerProjectile.turn();
		}
		
		/* projeteis (inimigos) */
		for (EnemyProjectile currentEnemyProjectile : this.enemyProjectiles) {
			currentEnemyProjectile.turn();
		}
		
		/* inimigos */
		for (Enemy currentEnemy : this.enemies) {
			currentEnemy.turn();
		}
		
		/* Processa a entrada do jogador */
		this.player.turn();

		/* backgrounds */
		for (Background currentBackground : this.backgrounds) {
			currentBackground.turn();
		}
	}

	/**
	 * Limpa as coleções dos elementos inativos
	 *
	 * @return	void
	 */
	private void removeInactives() {
		for (int i = this.enemies.size() - 1; i > -1 ; i--) {
			if (this.enemies.get(i).getState() == GameObject.INACTIVE) {
				this.enemies.remove(i);
			}
		}
		
		for (int i = this.playerProjectiles.size() - 1; i > -1; i--) {
			if (this.playerProjectiles.get(i).getState() == GameObject.INACTIVE) {
				this.playerProjectiles.remove(i);
			}
		}

		for (int i = this.enemyProjectiles.size() - 1; i > -1; i--) {
			if (this.enemyProjectiles.get(i).getState() == GameObject.INACTIVE) {
				this.enemyProjectiles.remove(i);
			}
		}
	}

	/**
	 * Lançamento de novos inimigos
	 *
	 * @return	void
	 */
	protected void spawnNewEnemies() {
		/* verificando se novos inimigos (tipo 1) devem ser "lançados" */
		Enemy1 newEnemy1 = Enemy1.spawn();
		if (newEnemy1 != null) {
			this.enemies.add((Enemy) newEnemy1);
		}
		
		/* verificando se novos inimigos (tipo 2) devem ser "lançados" */
		Enemy2 newEnemy2 = Enemy2.spawn();
		if (newEnemy2 != null) {
			this.enemies.add((Enemy) newEnemy2);
		}
	}

	/**
	 * Desenha a cena
	 *
	 * @return void
	 */
	private void plot() {
		/* desenhando plano fundo distante e próximo */
		for (Background currentBackground : this.backgrounds) {
			currentBackground.plot();
		}
		
		/* desenhando player */
		this.player.plot();
		
		/* desenhando projeteis (player) */
		for (PlayerProjectile currentPlayerProjectile : this.playerProjectiles) {
			currentPlayerProjectile.plot();
		}
		
		/* desenhando projeteis (inimigos) */
		for (EnemyProjectile currentEnemyProjectile : this.enemyProjectiles) {
			currentEnemyProjectile.plot();
		}
		
		/* desenhando inimigos */
		for (Enemy currentEnemy : this.enemies) {
			currentEnemy.plot();
		}
		
		/* chamama a display() da classe GameLib atualiza o desenho exibido pela interface do jogo. */
		GameLib.display();
	}

	/**
	 * Reinicia o jogo.
	 * Deve ser sobrescrito se houver um jogo com fases
	 *
	 * @return	void
	 */
	public void restart() {}
}