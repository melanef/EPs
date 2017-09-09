import java.util.*;

// Máquina de Café sem aplicação do padrão State.

public class Teste1 {

	public static void main(String [] args) throws InterruptedException {
	
		MaquinaCafe maquina = new MaquinaCafe();

		System.out.println(maquina);

		maquina.insereMoeda(1.0);
		maquina.insereMoeda(1.0);
		maquina.insereMoeda(1.0);
		maquina.insereMoeda(1.0);

		System.out.println(maquina);

		maquina.escolheBebida(2);

		System.out.println(maquina);

		while(!maquina.estaPronto()) Thread.sleep(100);

		System.out.println(maquina);
		
		maquina.retira();

		System.out.println(maquina);
	}
}

class MaquinaCafe {

	public static final int SEM_DINHEIRO = 0;
	public static final int COM_DINHEIRO = 1;
	public static final int PREPARANDO_CAFE = 2;
	public static final int CAFE_PRONTO = 3;

	private double saldo;
	private int estado;

	private List<Bebida> bebidas;

	public MaquinaCafe(){

		saldo = 0.0;
		estado = SEM_DINHEIRO;

		bebidas = new ArrayList<Bebida>();
		bebidas.add(new Bebida("Café Expresso", 2.5));
		bebidas.add(new Bebida("Café descafeinado", 3.0));
		bebidas.add(new Bebida("Cappuccino", 3.5));
		bebidas.add(new Bebida("Cappuccino grande", 5.0));
		bebidas.add(new Bebida("Chocolate quente", 3.0));
	}


	/* 
	 * Metodos que respondem diretamente a ações do usuário.
	 * As ações do usuário podem ser invocadas independentemente
	 * do estado da máquina de café.
	 */

	public void insereMoeda(double valor){

		if(estado == SEM_DINHEIRO || estado == COM_DINHEIRO){

			System.out.println("Adicionando " + valor + ".");

			saldo += valor;
			estado = COM_DINHEIRO;

			System.out.println("Saldo atual = " + saldo + ".");
		}
		else if(estado == PREPARANDO_CAFE){

			System.out.println("Seu cafe já está sendo preparado.");
		}
		else if(estado == CAFE_PRONTO){

			System.out.println("Seu cafe já está pronto. Favor retira-lo primeiro.");
		}
	}
	
	public void ejetaMoedas() {

		if(estado == SEM_DINHEIRO){

			System.out.println("Não há o que devolver...");		
		}
		else if(estado == COM_DINHEIRO){

			System.out.println("Devolvendo " + saldo + ".");
			devolve();
			estado = SEM_DINHEIRO;
		}
		else if(estado == PREPARANDO_CAFE){

			System.out.println("Seu café já está sendo preparado. Não é possível mais desistir da compra.");
		}
		else if(estado == CAFE_PRONTO){

			System.out.println("Seu café já está pronto. Não é possível mais desistir da compra.");
		}
		
	}

	public void escolheBebida(int opcao) {

		if(estado == SEM_DINHEIRO){
		
			System.out.println("Você deve inserir as moedas antes de escolher a bebida"); 
		}
		else if(estado == COM_DINHEIRO){

			Bebida bebida = bebidas.get(opcao);

			if(saldo >= bebida.getPreco()){

				saldo = saldo - bebida.getPreco();
				trava();
				prepara(bebida);
				estado = PREPARANDO_CAFE;
			}
			else{

				System.out.println("Saldo insuficiente. Favor adicionar mais moedas.");
			}
		}
		else if(estado == PREPARANDO_CAFE){

			System.out.println("Seu café já está sendo preparado. Não é possível mudar sua escolha.");
		}
		else if(estado == CAFE_PRONTO){

			System.out.println("Seu café já está pronto. Não é possível mudar sua escolha.");
		}
	}

	public void retira(){

		if(estado == SEM_DINHEIRO){

			System.out.println("O café não irá se materializar magicamente. Insira moedas e escolha uma bebida");
		}
		else if(estado == COM_DINHEIRO){

			System.out.println("Escolha uma bebida para ser preparada primeiro.");
		}
		else if(estado == PREPARANDO_CAFE){

			System.out.println("...");
		}
		else if(estado == CAFE_PRONTO){

			devolve();			
			System.out.println("Obrigado e volte sempre!");
			estado = SEM_DINHEIRO;
		}
	}


	/*
	 * Outros métodos da máquina de café.
         */

	public void devolve(){
	
		System.out.println("Devolvendo " + saldo + ".");
		saldo = 0.0;
	}

	public void trava(){
	
		System.out.println("Travando compartimento do café.");
	}

	public void destrava(){

		System.out.println("Destravando compartimento do café.");
	}

	public boolean estaPronto(){

		return estado == CAFE_PRONTO;
	}

	public void prepara(Bebida bebida){

		PreparadorCafe preparador = new PreparadorCafe(bebida, this);
		Thread thread = new Thread(preparador);
		thread.start();
	}

	/*
	 * Método invocado quando a máquina finaliza o preparo de uma bebida.
	 */ 

	public void cafePronto(){

		System.out.println("Seu café está pronto. Favor retira-lo da máquina.");
		destrava();
		estado = CAFE_PRONTO;
	}

	public String toString(){

		String estadoString = null;

		if(estado == SEM_DINHEIRO) estadoString = "SEM $$$";
		if(estado == COM_DINHEIRO) estadoString = "COM $$$";
		if(estado == PREPARANDO_CAFE) estadoString = "PREPARANDO";
		if(estado == CAFE_PRONTO) estadoString = "CAFE PRONTO";
		
		return ">>>>> Maquina de Café: estado = \"" + estadoString + "\" saldo = " + saldo /*+ " troco = " + troco*/;
	}
}

class PreparadorCafe implements Runnable {

	private Bebida bebida;
	private MaquinaCafe maquina;

	public PreparadorCafe(Bebida bebida, MaquinaCafe maquina){

		this.bebida = bebida;
		this.maquina = maquina;
	}

	public void run(){
		
		try { 
			System.out.println("Preparando " + bebida.getTipo() + "...");

			for(int i = 0; i < 10; i++){

				System.out.println("Preparando... (" + i + ")");
				Thread.sleep(1000); 
			}

			maquina.cafePronto();
		}
		catch(InterruptedException e) { 
		
			e.printStackTrace();
		}
	}
}

class Bebida {

	private String tipo;
	private double preco;

	public Bebida(String tipo, double preco){

		this.tipo = tipo;
		this.preco = preco;
	}

	public String getTipo(){
	
		return tipo;
	}

	public double getPreco(){
		
		return preco;
	}
}
