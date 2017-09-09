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

public class Stage{
	/**
	 * @var	ArrayList<EnemySpawnConfig>	lista de configurações de lançamento de inimigos obtida do arquivo de configuração
	 */
	protected ArrayList<EnemySpawnConfig> configs;

	/**
	 * Construtor
	 *
	 * @param	String	configPath	endereço do arquivo de configuração da fase
	 */
	public Stage(String configPath) {
		try {
			File configFile = new File(configPath);
			BufferedReader reader = new BufferedReader(new FileReader(configFile));

			this.configs = new ArrayList<EnemySpawnConfig>();

			String spawnConfigEntry = reader.readLine();
			int i = 0;
			while (spawnConfigEntry != null) {
				String spawnConfigParts[] = spawnConfigEntry.split("\\s");
				try {
					EnemySpawnConfig config = EnemySpawnConfig.factory(spawnConfigParts);
					if (config != null) {
						this.configs.add(config);
					}
				}
				catch (InvalidConfigException e) {
					System.out.println("Erro no carregamento das fases: Linha " + (i + 1) + " - " + e.getMessage());
				}

				spawnConfigEntry = reader.readLine();
				i++;
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("Arquivo de configuracao de fase nao encontrado: " + configPath);
		}
		catch (IOException e) {
			System.out.println("Falha na leitura do arquivo de configuração de fase: " + e.getMessage());
		}
	}

	/**
	 * Getter para a coleção de configurações de lançamentos
	 *
	 * @return	ArrayList<EnemySpawnConfig>
	 */
	public ArrayList<EnemySpawnConfig> getSpawnConfig() {
		return this.configs;
	}
}