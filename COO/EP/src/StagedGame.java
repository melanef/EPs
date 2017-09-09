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

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class StagedGame extends Game {
	/**
	 * @var	String	Endereço relativo do arquivo de configuração
	 */
	private final String configPath = "../config";

	/**
	 * @var	ArrayList<Stage>	Coleção de fases
	 */
	private ArrayList<Stage> stages;

	/**
	 * @var	String[]			Lista dos path's das fases
	 */
	private String[] stageConfigs;

	/**
	 * @var	int 	fase atual
	 */
	private int currentStage = 0;

	/**
	 * Construtor
	 */
	public StagedGame() {
		super();
		this.readConfig();
	}

	/**
	 * Getter da instância do singleton
	 */
	public static synchronized Game getInstance() {
		if (uniqueGame == null) {
			uniqueGame = new StagedGame();
		}

		return uniqueGame;
	}

	/**
	 * Lê e aplica configurações do arquivo
	 *
	 * @return	void
	 */
	private void readConfig() {
		try{
			File configFile = new File(this.configPath);
			BufferedReader reader = new BufferedReader(new FileReader(configFile));
			
			/**
			 * Configura o player com os pontos de vida
			 */
			this.player.setHitPoints(Integer.valueOf(reader.readLine()).intValue());

			/**
			 * Popula o array de endereços dos arquivos de configuração
			 */
			this.stageConfigs = new String[Integer.valueOf(reader.readLine()).intValue()];
			for (int i = 0; i < this.stageConfigs.length; i++) {
				this.stageConfigs[i] = reader.readLine();
			}

			/**
			 * Popula a coleção de fases com a configuração de cada fase
			 */
			this.stages = new ArrayList<Stage>();
			for (int i = 0; i < this.stageConfigs.length; i++) {
				this.stages.add(new Stage(this.stageConfigs[i]));
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("Arquivo de configuracao nao encontrado: " + this.configPath);
		}
		catch (IOException e) {
			System.out.println("Falha na leitura do arquivo de configuração: " + e.getMessage());
		}
	}

	/**
	 * Sobrescrevendo a configuração do lançamento automático inicial, afinal, num jogo com fases, não precisa de 
	 * lançamento inicial automático, os lançamentos ocorrerão de acordo com a configuração da fase.
	 *
	 * @return	void
	 */
	public void setSpawns() {
	}

	/**
	 * Lançamento de novos inimigos
	 *
	 * @return	void
	 */
	protected void spawnNewEnemies() {
		Stage currentStage = this.stages.get(this.currentStage);

		ArrayList<EnemySpawnConfig> currentStageSpawns = currentStage.getSpawnConfig();
		for (int i = currentStageSpawns.size() - 1; i > -1 ; i--) {
			EnemySpawnConfig currentConfig = currentStageSpawns.get(i);
			if (this.timer.getTime() >= (this.timer.getInitialTime() + currentConfig.getSpawnTime())) {
				try {
					Enemy spawn = currentConfig.spawn();
					this.enemies.add(spawn);
					currentStageSpawns.remove(i);
				}
				catch (InstantiationException e) {
					System.out.println("Erro no lançamento de inimigo: " + e.getMessage());
				}
				catch (IllegalAccessException e) {
					System.out.println("Erro no lançamento de inimigo: " + e.getMessage());
				}
			}
		}

		if (this.enemies.size() == 0 && currentStageSpawns.size() == 0) {
			this.timer.resetInitialTime();
			this.currentStage++;
		}

		if (this.currentStage >= this.stages.size()) {
			this.restart();
		}
	}

	/**
	 * Reinicia o jogo. Volta à fase 1 e reinicia os spawns
	 *
	 * @return	void
	 */
	public void restart() {
		this.enemies = new ArrayList<Enemy>();
		this.playerProjectiles = new ArrayList<PlayerProjectile>();
		this.enemyProjectiles = new ArrayList<EnemyProjectile>();
		this.readConfig();
		this.currentStage = 0;
		this.timer.resetInitialTime();
	}
}