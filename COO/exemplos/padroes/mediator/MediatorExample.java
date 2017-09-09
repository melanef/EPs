import java.util.*;

// Aplicação do padrão Mediator

public class MediatorExample {
	
	public static void main(String [] args){

		UmDialogo d = new UmDialogo();

		d.criaDialogo();
		d.imprimeEstado();
		d.simulaOperacaoLista(1);
		d.imprimeEstado();
		d.simulaOperacaoLista(4);
		d.imprimeEstado();
		d.simulaOperacaoOK();
	}
}

// Superclasse comum a qualquer mediador de
// componentes que fazem parte de um diálogo

abstract class Mediator {

	public void mostraDialogo(){

		// Exibe a interface gráfica do diálogo.
		// O processo de exibição será sempre o
		// mesmo, independente de como o diálogo
		// foi estruturado. Por isso esta 
		// implementação pode ficar na superclasse.
		
		System.out.println("exibindo a interface grafica...");
		System.out.println();
	}

	// Cada diálogo particular deve criar sua
	// própria interface, portanto, a implementação
	// deste método é responsabilidade das subclasses 
	// de Mediator.

	public abstract void criaDialogo();

	// Método através do qual o mediador é notificado de
	// que algum componente mudou. Com as as interações
	// (gerenciadas pelo mediador) que devem acontecer 
	// entre os componentes são particulares de cada
	// diálogo, tambem faz sentido que a implementação
	// deste método fique a cargo das subclasses.

	public abstract void componenteMudou(Componente c);
}

// Classe que implementa o diálogo para seleção de cor.
// Neste versão ela é uma subclasse de Mediator.

class UmDialogo extends Mediator {

	Botao ok, cancel;
	CampoTexto cor;
	Lista listaCores;

	public void criaDialogo(){

		// cria o diálogo...
		System.out.println("criando a interface grafica...");
	
		String [] cores = { "azul", "verde", "amarelo", "vermelho", "laranja" };

		ok = new Botao(this, "ok", false);
		cancel = new Botao(this, "cancel", true);
		cor = new CampoTexto(this, "");
		listaCores = new Lista(this, cores);

		mostraDialogo();
	}

	// Método invocado quando algum componente muda.
	// Este método implementa o comportamento coletivo dos
	// componentes, ou seja como eles interagem entre si.

	public void componenteMudou(Componente modificado){
	
		if(modificado == listaCores){

			if( listaCores.verificaIndice() ){

				// interação lista/campo texto
				cor.setTexto(listaCores.getSelecionado());
				
				// interação lista/botão
				ok.setHabilitado(true);
			}
		}

		else if(modificado == ok){

			System.out.print(ok + " pressionado: ");
		
			if(ok.getHabilitado()){

				// interação botão/lista
				System.out.println("Aplicando cor '" + listaCores.getSelecionado() + "' a um outro objeto...");
			}
			else{

				System.out.println("Nenhuma ação executada. Botão desabilitado...");
			}
		}
	
		else if(modificado == cancel){
	
			System.out.println("cancelando selecao.");
		}
	}

	public void simulaOperacaoLista(int i){

		System.out.println(">>>> Selecionando item " + i);
		System.out.println();
		listaCores.setSelecionado(i);
	}

	public void simulaOperacaoOK(){
	
		System.out.println(">>>> Pressionando botao OK.");
		System.out.println();
		ok.pressionado();
		System.out.println();
	}

	public void imprimeEstado(){

		System.out.println("Estado:");
		System.out.println("  " + ok);
		System.out.println("  " + cancel);
		System.out.println("  " + cor);				
		System.out.println("  " + listaCores);
		System.out.println();		
	}
}

// Superclasse comum a todos os componentes.

abstract class Componente {

	// todos os componentes mantem 
	// uma referência ao mediador

	private Mediator mediator;

	public Componente(Mediator m){

		mediator = m;
	}

	// método invocado sempre que um componente
	// quer avisar o mediador de que algo aconteceu 
	// e que cabe ao mediador decidir o que fazer
	// em seguida.

	public void mudou(){

		mediator.componenteMudou(this);
	}
}

// Cada elemento da interface é subclasse de Componente

class Botao extends Componente {

	private String rotulo;
	private boolean habilitado;

	public Botao(Mediator m, String s, boolean b){

		super(m);
		rotulo = s;
		habilitado = b;
	}

	public void setHabilitado(boolean b){

		habilitado = b;
	}

	public boolean getHabilitado(){

		return habilitado;
	}

	// método executado quando o botão exibido
	// na interface é pressionado pelo usuário

	public void pressionado(){

		// invocando método da superclasse
		// que notifica o mediador

		mudou();
	}

	public String toString(){

		return "[Botao: " + rotulo + " " + habilitado + "]";
	}
}

class CampoTexto extends Componente {

	private String texto;

	public CampoTexto(Mediator m, String s){

		super(m);
		setTexto(s);
	}

	public void setTexto(String s){

		texto = s;
	}

	public String getTexto(){

		return texto;
	}

	public String toString(){
		return "[Campo de texto: '" + getTexto() + "']";
	}
}

class Lista extends Componente {

	List<String> lista = new ArrayList<String>();
	int selecionado;

	public Lista(Mediator m, String [] valores){

		super(m);
		for(String s : valores) lista.add(s);
		selecionado = -1;
	}

	public boolean verificaIndice(){

		if(selecionado >= 0 && selecionado < lista.size()) return true;
		
		return false;
	}

	// método executado quando um item da
	// lista é selecionado pelo usuário

	public void setSelecionado(int i){

		selecionado = i;

		// invocando método da superclasse
		// que notifica o mediador

		mudou();
	}

	public String getSelecionado(){

		if(verificaIndice()){

			return lista.get(selecionado);
		}
		
		return null;
	}

	public String toString(){
		return "[Lista: conteudo: " + lista + ", selecionado = *" + getSelecionado() + "*]";
	}
}

