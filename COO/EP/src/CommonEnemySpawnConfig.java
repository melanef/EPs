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

public class CommonEnemySpawnConfig extends EnemySpawnConfig {
	/**
	 * @var	Class
	 */
	private Class type;

	/**
	 * Construtor
	 *
	 * @param	String[]	configEntry	Array de Strings com valores de configuração do lançamento
	 */
	public CommonEnemySpawnConfig(String configEntry[]) throws InvalidConfigException {
		super();
		if  (configEntry.length < 5) {
			throw new InvalidConfigException("Diretiva " + COMMON_ENEMY_ENTRY + " incompleta. Esperados 5 parametros");
		}

		Class type = null;
		switch (Integer.valueOf(configEntry[1]).intValue()) {
			case 1:
				type = Enemy1.class;
				break;
			case 2:
				type = Enemy2.class;
				break;
			default:
				throw new InvalidConfigException("O valor '" + configEntry[1] + "' não é um valor válido para o tipo de diretivas " + COMMON_ENEMY_ENTRY);
		}

		double x = Double.valueOf(configEntry[3]).doubleValue();
		double y = Double.valueOf(configEntry[4]).doubleValue();
		long spawnTime = Long.valueOf(configEntry[2]).longValue();
		this.init(x, y, spawnTime, type);
	}
}