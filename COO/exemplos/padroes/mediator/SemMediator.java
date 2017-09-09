import java.util.*;

public class SemMediator {
	
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

// Classe que implementa o diálogo para seleção de cor.

class UmDialogo {

	Botao ok, cancel;
	CampoTexto cor;
	Lista listaCores;

	public void criaDialogo(){

		// cria o diálogo...
		System.out.println("criando a interface grafica...");
	
		String [] cores = { "azul", "verde", "amarelo", "vermelho", "laranja" };

		ok = new Botao("ok", false);
		cancel = new Botao("cancel", true);
		cor = new CampoTexto("");
		listaCores = new Lista(cores);
		
		listaCores.setCampoTexto(cor); // listaCores precisa ter uma referencia a um CampoTexto.
		listaCores.setBotao(ok);  // listaCores também precisa ter uma referencia a um botao.

		ok.setLista(listaCores); // ok precisa ter uma referencia a uma Lista.

		mostraDialogo();
	}

	public void mostraDialogo(){

		// exibe o dialogo...
		System.out.println("exbindo a interface grafica...");
		System.out.println();
	}

	public void simulaOperacaoLista(int i){

		System.out.println(">>>> Selecionando item " + i + ".");
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

class Botao {

	private String rotulo;
	private boolean habilitado;

	Lista lista; // referência a Lista.

	public Botao(String s, boolean b){

		rotulo = s;
		habilitado = b;
	}

	// método para que a classe Botão 
	// obtenha uma referência a uma Lista

	public void setLista(Lista l){

		lista = l;
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

		System.out.print(this + " pressionado: ");
		
		if(rotulo.equals("ok")){

			if(habilitado){

				// interação entre Botão e Lista.
				System.out.println("Aplicando cor '" + lista.getSelecionado() + "' a um outro objeto...");
			}
			else {

				System.out.println("Nenhuma ação executada (OK desabilitado).");
			}
		}
		else if(rotulo.equals("cancel")){
			
			System.out.println("Fechando diálogo sem aplicar qualquer cor...");
		} 
	}

	public String toString(){

		return "[Botao: " + rotulo + " " + habilitado + "]";
	}
}

class CampoTexto {

	private String texto;

	public CampoTexto(String s){

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

class Lista {

	List<String> lista = new ArrayList<String>();
	int selecionado;

	CampoTexto campoTexto; // referência a CampoTexto.
	Botao ok; // referência ao botão OK

	public Lista(String [] valores){

		for(String s : valores) lista.add(s);
		selecionado = -1;
	}

	// método para que a classe Lista 
	// obtenha uma referência a um CampoTexto

	public void setCampoTexto(CampoTexto ct){

		campoTexto = ct;
	}

	// método para que a classe Lista 
	// obtenha uma referência a um Botão

	public void setBotao(Botao b){

		ok = b;
	}

	private boolean verificaIndice(){

		if(selecionado >= 0 && selecionado < lista.size()) return true;
		
		return false;
	}

	// método executado quando um item da
	// lista é selecionado pelo usuário

	public void setSelecionado(int i){

		selecionado = i;

		if(verificaIndice()){

			// interação entre Lista e CampoTexto

			campoTexto.setTexto(getSelecionado());

			// interação entre Lista e Botão

			ok.setHabilitado(true);
		}
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

