///////////////////////////////////////////
// Solução que aplica o padrão Stratety. //
///////////////////////////////////////////

abstract class Personagem {

	int pontosDeVida;
	int pontosDeAtaque;
	int pontosDeDefesa;

	int posX;
	int posY;

	////////////////////////////////////////////
	// Objetos que encapsulam o comportamento //
	// que varia em um personagem.            //
	////////////////////////////////////////////

	ComportamentoDeAtaque ca = null;
	ComportamentoDeMagia cm = null;
	
	public void andar() {

		// atualiza coordenadas (posX, posY) do personagem

		System.out.println(this + ": andando...");
	}

	public String toString(){

		return getClass().getName();
	}


	public abstract void exibir(); // método que desenha o personagem

	////////////////////////////////////
	// setters para os comportamentos //
	////////////////////////////////////

	public void setComportamentoDeAtaque(ComportamentoDeAtaque ca){

		this.ca = ca;
	}
	
	public void setComportamentoDeMagia(ComportamentoDeMagia cm){
	
		this.cm = cm;
	}

	/////////////////////////////////////////////
	// metodos para executar os comportamentos //
	/////////////////////////////////////////////

	public void executarAtaque(Personagem alvo){

		if(this.ca != null) this.ca.atacar(alvo);
	}

	public void executarMagia(){

		if(this.cm != null) this.cm.lancarMagia();
	}
}

// Interfaces para comportamentos

interface ComportamentoDeAtaque {

	public void atacar(Personagem alvo);
}

interface ComportamentoDeMagia {

	public void lancarMagia();
}

// Implementações de comportamentos

class AtaqueSimples implements ComportamentoDeAtaque {

	public void atacar(Personagem alvo){

		System.out.println(getClass().getName() + ": atacando " + alvo + "...");
	}
}

class AtaqueDuplo implements ComportamentoDeAtaque {

	public void atacar(Personagem alvo){

		System.out.println(getClass().getName() + ": atacando " + alvo + "...");
	}
}

class AtaqueComArco implements ComportamentoDeAtaque {

	public void atacar(Personagem alvo){

		System.out.println(getClass().getName() + ": atacando " + alvo + "...");
	}
}

class BolaDeFogo implements ComportamentoDeMagia {

	public void lancarMagia(){

		System.out.println(getClass().getName() + ": lancando magia...");
	}
}

class Cura implements ComportamentoDeMagia {

	public void lancarMagia(){

		System.out.println(getClass().getName() + ": lancando magia...");
	}
}

// Personagens

class Barbaro extends Personagem {

	public Barbaro(){

		this.pontosDeVida = 100;
		this.pontosDeAtaque = 10;
		this.pontosDeDefesa = 10;

		setComportamentoDeAtaque(new AtaqueDuplo());
	}

	public void exibir(){

		System.out.println(this +  ": exibindo um barbaro...");
	}
}

class Paladino extends Personagem {

	public Paladino(){

		this.pontosDeVida = 120;
		this.pontosDeAtaque = 8;
		this.pontosDeDefesa = 12;

		setComportamentoDeAtaque(new AtaqueSimples());
		setComportamentoDeMagia(new Cura());
	}

	public void exibir(){

		System.out.println(this + ": exibindo um paladino...");
	}
}

class Elfo extends Personagem {

	public Elfo(){

		this.pontosDeVida = 150;
		this.pontosDeAtaque = 7;
		this.pontosDeDefesa = 7;

		setComportamentoDeAtaque(new AtaqueComArco());
		setComportamentoDeMagia(new Cura());
	}

	public void exibir(){

		System.out.println(this + ": exibindo um elfo...");
	}
}

class Mago extends Personagem {

	public Mago(){

		this.pontosDeVida = 50;
		this.pontosDeAtaque = 3;
		this.pontosDeDefesa = 7;

		setComportamentoDeMagia(new BolaDeFogo());
	}

	public void exibir(){

		System.out.println(this +  ": exibindo um mago...");
	}
}

class Princesa extends Personagem {

	public Princesa(){

		this.pontosDeVida = 200;
		this.pontosDeAtaque = 5;
		this.pontosDeDefesa = 15;
	}

	public void exibir(){

		System.out.println(this +  ": exibindo uma princesa...");
	}	
}

public class JogoRPG_V5 {

	public static void main(String [] args){

		Personagem [] personagens = new Personagem[5];

		personagens[0] = new Barbaro();
		personagens[1] = new Paladino();
		personagens[2] = new Elfo();
		personagens[3] = new Mago();
		personagens[4] = new Princesa();

		for(int i = 0; i < personagens.length; i++) {

			System.out.println("--------------------------------------");

			Personagem p = personagens[i];
			Personagem alvo = personagens[(int)(Math.random() * personagens.length)];

			p.andar();
			p.executarAtaque(alvo);
			p.executarMagia();
			p.exibir();
		}

		System.out.println("--------------------------------------");
		System.out.println("--------------------------------------");
		System.out.println("--------------------------------------");

		// É possível alterar o comportamento de um Personagem em tempo de execução!
		Personagem princesa = personagens[4];
		princesa.setComportamentoDeAtaque(new AtaqueDuplo());
		princesa.setComportamentoDeMagia(new BolaDeFogo());

		for(int i = 0; i < personagens.length; i++) {

			System.out.println("--------------------------------------");

			Personagem p = personagens[i];
			Personagem alvo = personagens[(int)(Math.random() * personagens.length)];

			p.andar();
			p.executarAtaque(alvo);
			p.executarMagia();
			p.exibir();
		}

		System.out.println("--------------------------------------");
	}
}

