import java.util.*;

// Aplicação do padrão State ao código da Máquina de Café. 

public class Teste2 {

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

		while(!maquina.getPronto()) Thread.sleep(100);
		
		System.out.println(maquina);
		
		maquina.retira();

		System.out.println(maquina);
	}
}

interface Estado {

	/* 
	 * Metodos que respondem diretamente a ações do usuário.
	 * As ações do usuário podem ser invocadas independentemente
	 * do estado da máquina de café.
	 */

	public void insereMoeda(double valor);
	public void ejetaMoedas();
	public void escolheBebida(int opcao);
	public void retira();

}

class EstadoSemDinheiro implements Estado {

	private MaquinaCafe maquina;

	public EstadoSemDinheiro(MaquinaCafe maquina){

		this.maquina = maquina;
	}

	public void insereMoeda(double valor){

		maquina.incrementaSaldo(valor);
		maquina.mudaEstado(new EstadoComDinheiro(maquina));
	}

	public void ejetaMoedas(){

		System.out.println("Não há o que devolver...");		
	}

	public void escolheBebida(int opcao){

		System.out.println("Você deve inserir as moedas antes de escolher a bebida.");
	}

	public void retira(){

		System.out.println("O café não irá se materializar magicamente. Insira moedas e escolha uma bebida");
	}

}

class EstadoComDinheiro implements Estado {

	private MaquinaCafe maquina;

	public EstadoComDinheiro(MaquinaCafe maquina){

		this.maquina = maquina;
	}

	public void insereMoeda(double valor){

		maquina.incrementaSaldo(valor);
	}

	public void ejetaMoedas(){

		double valor = maquina.getSaldo();
		maquina.devolve();
		maquina.mudaEstado(new EstadoSemDinheiro(maquina));
	}

	public void escolheBebida(int opcao){

		Bebida bebida = maquina.getBebida(opcao);

		if(maquina.getSaldo() >= bebida.getPreco()){

			maquina.decrementaSaldo(bebida.getPreco());
			maquina.trava();
			maquina.setPronto(false);
			maquina.prepara(bebida);
			maquina.mudaEstado(new EstadoPreparandoCafe(maquina));
		}
		else{
			System.out.println("Saldo insuficiente. Favor adicionar mais moedas.");
		}
	}

	public void retira(){

		System.out.println("Escolha uma bebida para ser preparada primeiro.");
	}
}

class EstadoPreparandoCafe implements Estado {

	private MaquinaCafe maquina;

	public EstadoPreparandoCafe(MaquinaCafe maquina){

		this.maquina = maquina;
	}

	public void insereMoeda(double valor){

		System.out.println("Seu cafe já está sendo preparado.");
	}

	public void ejetaMoedas(){

		System.out.println("Seu café já está sendo preparado. Não é possível mais desistir da compra.");
	}

	public void escolheBebida(int opcao){

		System.out.println("Seu café já está sendo preparado. Não é possível mudar sua escolha.");
	}

	public void retira(){

		System.out.println("...");
	}
}

class EstadoCafePronto implements Estado {

	private MaquinaCafe maquina;

	public EstadoCafePronto(MaquinaCafe maquina){

		this.maquina = maquina;
	}

	public void insereMoeda(double valor){

		System.out.println("Seu cafe já está pronto. Favor retira-lo primeiro.");
	}

	public void ejetaMoedas(){

		System.out.println("Seu café já está pronto. Não é possível mais desistir da compra.");
	}

	public void escolheBebida(int opcao){

		System.out.println("Seu café já está pronto. Não é possível mudar sua escolha.");
	}

	public void retira(){

		maquina.devolve();
		System.out.println("Obrigado e volte sempre!");
		maquina.mudaEstado(new EstadoSemDinheiro(maquina));
	}
}

class MaquinaCafe {

	private double saldo;
	private Estado estado;
	private boolean pronto;

	private List<Bebida> bebidas;

	public MaquinaCafe(){

		saldo = 0.0;
		estado = new EstadoSemDinheiro(this);

		bebidas = new ArrayList<Bebida>();
		bebidas.add(new Bebida("Café Expresso", 2.5));
		bebidas.add(new Bebida("Café descafeinado", 3.0));
		bebidas.add(new Bebida("Cappuccino", 3.5));
		bebidas.add(new Bebida("Cappuccino grande", 5.0));
		bebidas.add(new Bebida("Chocolate quente", 3.0));

		setPronto(false);
	}

	public void mudaEstado(Estado e){

		estado = e;
	}


	/* 
	 * Metodos que respondem diretamente a ações do usuário.
	 * As ações do usuário podem ser invocadas independentemente
	 * do estado da máquina de café.
	 */

	public void insereMoeda(double valor){

		estado.insereMoeda(valor);
	}
	
	public void ejetaMoedas() {

		estado.ejetaMoedas();		
	}

	public void escolheBebida(int opcao) {

		estado.escolheBebida(opcao);
	}

	public void retira(){
	
		estado.retira();
	}


	/*
	 * Outros métodos da máquina de café.
         */

	public void incrementaSaldo(double valor){

		System.out.println("Adicionando " + valor + ".");
		saldo += valor;
		System.out.println("Saldo atual = " + saldo + ".");
	}

	public void decrementaSaldo(double valor){
	
		saldo -= valor;
	}

	public double getSaldo(){

		return saldo;
	}

	public void devolve(){
	
		System.out.println("Devolvendo " + saldo + ".");
		decrementaSaldo(saldo);
	}

	public void trava(){
	
		System.out.println("Travando compartimento do café.");
	}

	public void destrava(){

		System.out.println("Destravando compartimento do café.");
	}

	public void setPronto(boolean pronto){

		this.pronto = pronto;
	}

	public boolean getPronto(){

		return pronto;
	}

	public Bebida getBebida(int opcao){

		return bebidas.get(opcao);
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
		setPronto(true);
		destrava();
		mudaEstado(new EstadoCafePronto(this));
	}

	public String toString() {

		return ">>>>> Maquina de Café: estado = \"" + estado.getClass().getName() + "\" saldo = " + saldo;
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
