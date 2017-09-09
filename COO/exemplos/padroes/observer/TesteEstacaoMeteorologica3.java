import java.util.*;

// Aplicação do padrão Observer, com notificações
// no estilo PULL. Cada visualização passa a ser
// um observador da classe DadosMeteorologicos.

// Interface comum para objetos observados
interface Subject {

	void registerObserver(Observer o);
	void removeObserver(Observer o);
	void notifyObservers();
}

// Interface comum para os objetos observadores
interface Observer {

	void atualiza();
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

			o.atualiza();
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

	// Usando o estilo de notificação PULL, o observador apenas
	// é avisado que algo mudou no objeto observado, sem receber
	// qualquer informação adicional. Para que o observador possa
	// puxar a informação de que necessita, passa a ser necessário 
	// manter uma referência ao objeto observado.

	private Subject subject;

	public DisplayCondicoesAtuais(Subject subject){

		this.subject = subject;
		this.subject.registerObserver(this);
	}

	public void atualiza(){

		// No momento em que o observador é notificado de que
		// houve alguma mudança, ele deve requisitar ao objeto 
		// observado as informações de que necessita. Para isso,
		// não basta o observador saber que trata-se de um Subject,
		// mas também é preciso saber seu tipo concreto.

		DadosMeteorologicos dm = (DadosMeteorologicos) subject;

		this.temp = dm.getTemperatura();
		this.umidade = dm.getUmidade();
		this.pressao = dm.getPressao();

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

	private Subject subject;

	public DisplayEstatisticas(Subject subject){

		amostras = new ArrayList<Double>();

		this.subject = subject;
		this.subject.registerObserver(this);
	}

	public void atualiza(){

		DadosMeteorologicos dm = (DadosMeteorologicos) subject;

		amostras.add(dm.getTemperatura());
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

	private Subject subject;

	public DisplayPrevisao(Subject subject){

		this.subject = subject;
		this.subject.registerObserver(this);
	}

	public void atualiza(){

		DadosMeteorologicos dm = (DadosMeteorologicos) subject;
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

	public void display(){
	
		System.out.println("Previsão: " + previsao);
	}
}

public class TesteEstacaoMeteorologica3 {

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

