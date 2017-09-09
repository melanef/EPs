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

public class BossEnemySpawnConfig extends EnemySpawnConfig {
	/**
	 * Construtor
	 *
	 * @param	String[]	configEntry	Array de Strings com valores de configuração do lançamento
	 */
	public BossEnemySpawnConfig(String configEntry[]) throws InvalidConfigException {
		super();
		if  (configEntry.length < 6) {
			throw new InvalidConfigException("Diretiva " + BOSS_ENEMY_ENTRY + " incompleta. Esperados 6 parametros");
		}

		Class type = null;
		switch (Integer.valueOf(configEntry[1]).intValue()) {
			case 1:
				type = Boss1.class;
				break;
			case 2:
				type = Boss2.class;
				break;
			default:
				throw new InvalidConfigException("O valor '" + configEntry[1] + "' não é um valor válido para o tipo de diretivas " + BOSS_ENEMY_ENTRY);
		}

		double x = Double.valueOf(configEntry[4]).doubleValue();
		double y = Double.valueOf(configEntry[5]).doubleValue();
		long spawnTime = Long.valueOf(configEntry[3]).longValue();
		this.init(x, y, spawnTime, type);
		this.hitPoints = Integer.valueOf(configEntry[2]).intValue();
	}
}