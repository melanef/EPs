import java.util.*;

////////////////////////////////////////////////
// Classe original disponibilizada para você: //
////////////////////////////////////////////////
/*
class DadosMeteorologicos {

	// Para nós, não importa de onde e como vem as medidas devolvidas pelos 
	// getters abaixo. O que importa é que o objeto DadosMeteorologicos sabe 
	// como obte-las da EstacaoMeteorologica.

	public double getTemperatura(){

		// devolve medida de temperatura mais recente
	}

	public double getUmidade(){

		// devolve medidade de umidade mais recente
	}

	public double getPressao(){

		// devolve medida de pressao mais recente
	}

	public void medidasMudaram(){

		// chamado sempre que as medidas meteorologicas forem atualizadas
	}
}
*/

/////////////////////////////////////////////////////
// Versão modificada para atingir nossos objetivos //
/////////////////////////////////////////////////////

class DadosMeteorologicos {

	// variaveis usadas na simulação da "conversa" entre 
	// DadosMeteorologicos e EstacaoMeteorologica.
	private double t, u, p;
	

	// Referencias para objetos que implementam as diferentes visualizações.
	private DisplayCondicoesAtuais dispAtuais;
	private DisplayEstatisticas dispEstat;	
	private DisplayPrevisao dispPrev;

	public DadosMeteorologicos(	DisplayCondicoesAtuais dispAtuais,
					DisplayEstatisticas dispEstat,
					DisplayPrevisao dispPrev){

		this.dispAtuais = dispAtuais;
		this.dispEstat = dispEstat;
		this.dispPrev = dispPrev;
	}

	// Metodo para simular uma "conversa" entre DadosMeteorologicos
	// e EstacaoMeteorologica, de onde DadosMeteorologicos obtem as
	// medidas atualizadas.

	public void setMedidas(double t, double u, double p){

		this.t = t;
		this.u = u;
		this.p = p;

		medidasMudaram();
	}

	public double getTemperatura(){

		return t;
	}

	public double getUmidade(){

		return u;
	}

	public double getPressao(){

		return p;
	}

	public void medidasMudaram(){

		// chamado sempre que as medidas meteorologicas forem atualizadas

		double temperatura = getTemperatura();
		double umidade = getUmidade();
		double pressao = getPressao();

		dispAtuais.atualiza(temperatura, umidade, pressao);
		dispEstat.atualiza(temperatura, umidade, pressao);
		dispPrev.atualiza(temperatura, umidade, pressao);
	}
}

class DisplayCondicoesAtuais {

	private double temp, umidade, pressao;

	public void atualiza(double temp, double umidade, double pressao){

		this.temp = temp;
		this.umidade = umidade;
		this.pressao = pressao;

		display();
	}

	public void display(){
	
		System.out.println("Condições atuais:");
		System.out.println("\tTemperatura: " + temp + "°C");
		System.out.println("\tUmidade: " + umidade + "%");
		System.out.println("\tPressão: " + pressao + " bar");
	}
}

class DisplayEstatisticas {

	private List<Double> amostras;
	double min, max, media;

	public DisplayEstatisticas(){

		amostras = new ArrayList<Double>();
	}

	public void atualiza(double temp, double umidade, double pressao){

		amostras.add(temp);
		min = Collections.min(amostras);
		max = Collections.max(amostras);

		double soma = 0;	
		for(double d : amostras) soma += d;
		media = soma / amostras.size();

		display();
	}

	public void display(){

		if(amostras.size() == 0){

			System.out.println("Nenhuma temperatura registrada por enquanto.");
		}
		else {
			System.out.println("Estatísticas:");
			System.out.println("\tMin: " + min + "°C");
			System.out.println("\tMax: " + max + "°C");
			System.out.println("\tMedia: " + media + "°C");			
		}
	}

	
}

class DisplayPrevisao {

	private double pressaoAnterior = -1;
	private String previsao;

	public void atualiza(double temp, double umidade, double pressao){

		if(pressaoAnterior >= 0){

			double delta = pressao - pressaoAnterior;

			if(Math.abs(delta) < 0.001) previsao = "Manutenção das condições.";
			else if(delta > 0) previsao = "Tempo firme.";
			else previsao = "Possibilidade de chuva.";	
		}
		else{

			previsao = "Indefinido...";
		}

		pressaoAnterior = pressao;

		display();
	}

	public void display(){
	
		System.out.println("Previsão: " + previsao);
	}
}

public class TesteEstacaoMeteorologica1 {

	public static void main(String [] args){

		DisplayCondicoesAtuais dispAtuais = new DisplayCondicoesAtuais();
		DisplayEstatisticas dispEstat = new DisplayEstatisticas();
		DisplayPrevisao dispPrev = new DisplayPrevisao();

		DadosMeteorologicos dm = new DadosMeteorologicos(dispAtuais, dispEstat, dispPrev);
		
		dm.setMedidas(23.0, 65, 1.010);
		System.out.println("------------------------------------------------");
		dm.setMedidas(27.0, 55, 1.013);
		System.out.println("------------------------------------------------");
		dm.setMedidas(25.0, 61, 1.013);
		System.out.println("------------------------------------------------");
		dm.setMedidas(22.0, 61, 1.011);
	}
}

