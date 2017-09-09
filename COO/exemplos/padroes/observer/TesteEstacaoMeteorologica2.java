import java.util.*;

// Aplicação do padrão Observer, com notificações
// no estilo PUSH. Cada visualização passa a ser
// um observador da classe DadosMeteorologicos.

// Interface comum para objetos observados
interface Subject {

	void registerObserver(Observer o);
	void removeObserver(Observer o);
	void notifyObservers();
}

// Interface comum para os objetos observadores
interface Observer {

	void atualiza(double temp, double umidade, double pressao);
}

// Interface comum para todas as visualizações
interface Displayable {

	void display();
}

// DadosMeteorologicos passa a implementar a interface Subject
class DadosMeteorologicos implements Subject{

	private double t, u, p;
	
	private Collection<Observer> observers;
	
	public DadosMeteorologicos() {

		observers = new ArrayList<Observer>();
	}

	public void registerObserver(Observer o){

		observers.add(o);
	}

	public void removeObserver(Observer o){

		while(observers.remove(o));
	}

	public void notifyObservers(){

		for(Observer o : observers){

			o.atualiza(getTemperatura(), getUmidade(), getPressao());
		}
	}

	public void medidasMudaram(){

		// chamado sempre que as medidas meteorologicas forem atualizadas
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

// Cada visualização passa a implementar a interface Observer, além de Displayable
class DisplayCondicoesAtuais implements Observer, Displayable{

	private double temp, umidade, pressao;

	public DisplayCondicoesAtuais(Subject subject){

		subject.registerObserver(this);
	}

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

class DisplayEstatisticas implements Observer, Displayable {

	private List<Double> amostras;
	double min, max, media;

	public DisplayEstatisticas(Subject subject){

		subject.registerObserver(this);
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

class DisplayPrevisao implements Observer, Displayable {

	private double pressaoAnterior = -1;
	private String previsao;

	public DisplayPrevisao(Subject subject){

		subject.registerObserver(this);
	}

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

public class TesteEstacaoMeteorologica2 {

	public static void main(String [] args){

		DadosMeteorologicos dm = new DadosMeteorologicos();

		DisplayCondicoesAtuais dispAtuais = new DisplayCondicoesAtuais(dm);
		DisplayEstatisticas dispEstat = new DisplayEstatisticas(dm);
		DisplayPrevisao dispPrev = new DisplayPrevisao(dm);

		dm.setMedidas(23.0, 65, 1.010);
		System.out.println("------------------------------------------------");
		dm.setMedidas(27.0, 55, 1.013);
		System.out.println("------------------------------------------------");
		dm.setMedidas(25.0, 61, 1.013);
		System.out.println("------------------------------------------------");
		dm.setMedidas(22.0, 61, 1.011);
	}
}

