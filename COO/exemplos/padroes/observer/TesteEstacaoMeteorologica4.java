import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;

// Neste exemplo, também é aplicado o padrão Observer, mas usando
// classes e interfaces já disponibilizadas na API do java para
// esta finalidade. As classes/interfaces disponibilizadas são
// flexíveis o suficiente para que possa-se usar ambos os estilos
// de notificação (PUSH ou PULL). Neste exemplo estamos usando o
// estilo de notificação PULL.

interface Displayable {

	void display();
}

// A superclasse Observable já possui toda a implementação necessária
// para fazer o gerenciamento dos observadores (registro, remoção) e
// notificá-los quando o objeto é atualizado.

class DadosMeteorologicos extends Observable{

	private double t, u, p;	
	
	public void medidasMudaram(){

		// Chamado sempre que as medidas meteorologicas forem atualizadas

		// Os metodos abaixo estão implementados na superclasse Observable
		setChanged();
		notifyObservers();
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

}

// A interface Observer define que todos os observadores devem
// implementar o método update(...). Em update(...) são recebidos
// dois argumentos, o objeto observado que disparou a notificação
// e um objeto qualquer que pode ser usado para receber parâmetros
// adicionais no instante da notificação. Desta forma é possível
// implementar notificações tanto no estilo PUSH como no estilo
// PULL (neste exemplo estamos utilizando o estilo PULL).

class DisplayCondicoesAtuais implements Observer, Displayable{

	private double temp, umidade, pressao;

	public DisplayCondicoesAtuais(Observable subject){

		// addObserver é o método já implementado pela
		// superclasse Observable que faz o registro
		// dos observadores.

		subject.addObserver(this);
	}

	public void update(Observable obs, Object arg){

		// Como recebemos uma referência ao objeto observado
		// que disparou a notificação, não é preciso manter
		// uma referência para ele nesta classe.

		if(obs instanceof DadosMeteorologicos){

			DadosMeteorologicos dm = (DadosMeteorologicos) obs;

			this.temp = dm.getTemperatura();
			this.umidade = dm.getUmidade();
			this.pressao = dm.getPressao();

			display();
		}
	}

	public void display(){
	
		System.out.println("Condições atuais:");
		System.out.println("\tTemperatura: " + temp + "°C");
		System.out.println("\tUmidade: " + umidade + "%");
		System.out.println("\tPressão: " + pressao + " bar");
	}
}

class DisplayEstatisticas implements Observer, Displayable {

	private List<Double> amostras;
	double min, max, media;

	public DisplayEstatisticas(Observable subject){

		subject.addObserver(this);
		amostras = new ArrayList<Double>();
	}

	public void update(Observable obs, Object arg){

		if(obs instanceof DadosMeteorologicos){

			DadosMeteorologicos dm = (DadosMeteorologicos) obs;

			amostras.add(dm.getTemperatura());
			min = Collections.min(amostras);
			max = Collections.max(amostras);

			double soma = 0;	
			for(double d : amostras) soma += d;
			media = soma / amostras.size();

			display();
		}
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

class DisplayPrevisao implements Observer, Displayable {

	private double pressaoAnterior = -1;
	private String previsao;

	public DisplayPrevisao(Observable subject){

		subject.addObserver(this);
	}

	public void update(Observable obs, Object arg){

		if(obs instanceof DadosMeteorologicos){

			DadosMeteorologicos dm = (DadosMeteorologicos) obs;
			double pressao = dm.getPressao();

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
	}

	public void display(){
	
		System.out.println("Previsão: " + previsao);
	}
}

public class TesteEstacaoMeteorologica4 {

	public static void main(String [] args){

		DadosMeteorologicos dm = new DadosMeteorologicos();

		Displayable dispAtuais = new DisplayCondicoesAtuais(dm);
		Displayable dispEstat = new DisplayEstatisticas(dm);
		Displayable dispPrev = new DisplayPrevisao(dm);

		dm.setMedidas(23.0, 65, 1.010);
		System.out.println("------------------------------------------------");
		dm.setMedidas(27.0, 55, 1.013);
		System.out.println("------------------------------------------------");
		dm.setMedidas(25.0, 61, 1.013);
		System.out.println("------------------------------------------------");
		dm.setMedidas(22.0, 61, 1.011);
	}
}

