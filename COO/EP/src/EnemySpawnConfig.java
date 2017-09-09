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

public abstract class EnemySpawnConfig {
	/**
	 * Palavra chave que indica a configuração de um inimigo comum
	 */
	public static final String COMMON_ENEMY_ENTRY = "INIMIGO";

	/**
	 * Palavra chave que indica a configuração de um inimigo "chefe"
	 */
	public static final String BOSS_ENEMY_ENTRY = "CHEFE";

	/**
	 * @var	double	coordenadas X e Y
	 */
	protected double x;
	protected double y;

	/**
	 * @var	long	instante de lançamento
	 */
	protected long spawnTime;

	/**
	 * @var int 	quantidade de pontos de vida
	 */
	protected int hitPoints = 1;

	/**
	 * @var	Class 	tipo (classe) a ser usado para o lançamento
	 */
	protected Class type;

	/**
	 * Construtor
	 */
	public EnemySpawnConfig() {}

	/**
	 * Atribui propriedades padrão
	 * @param	double	X 			coordenada X do lançamento
	 * @param	double	Y 			coordenada Y do lançamento
	 * @param	long	spawnTime	instante do lançamento
	 * @param	Class 	type		tipo a ser usado no lançamento
	 *
	 * @return 	void
	 */
	public void init(double x, double y, long spawnTime, Class type) {
		this.x = x;
		this.y = y;
		this.spawnTime = spawnTime;
		this.type = type;
	}

	/**
	 * Getter para o instante do lançamento
	 *
	 * @return	long
	 */
	public long getSpawnTime() {
		return this.spawnTime;
	}

	/**
	 * Factory para criação automática independente do tipo
	 *
	 * @param	String[]	configEntry	Array de Strings com valores de configuração do lançamento
	 *
	 * @return	EnemySpawnConfig
	 */
	public static EnemySpawnConfig factory(String configEntry[]) throws InvalidConfigException {
		EnemySpawnConfig config = null;
		if (configEntry[0].equals(EnemySpawnConfig.COMMON_ENEMY_ENTRY)) {
			config = (EnemySpawnConfig) new CommonEnemySpawnConfig(configEntry);
		}

		if (configEntry[0].equals(EnemySpawnConfig.BOSS_ENEMY_ENTRY)) {
			config = (EnemySpawnConfig) new BossEnemySpawnConfig(configEntry);
		}

		return config;
	}

	/**
	 * Lança o inimigo
	 *
	 * @return 	Enemy
	 */
	public Enemy spawn() throws InstantiationException, IllegalAccessException {
		Enemy spawn = (Enemy) (this.type).newInstance();
		spawn.setPosition(this.x, this.y);
		spawn.setHitPoints(this.hitPoints);

		return spawn;
	}
}