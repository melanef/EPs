abstract class Personagem {

	int pontosDeVida;
	int pontosDeAtaque;
	int pontosDeDefesa;

	int posX;
	int posY;
	
	public void andar() {

		// atualiza coordenadas (posX, posY) do personagem

		System.out.println(this + ": andando...");
	}

	public void atacar(Personagem alvo) {

		// ataca personagem alvo

		System.out.println(this + ": atacando " + alvo + "...");
	}

	public String toString(){

		return getClass().getName();
	}

	public abstract void exibir(); // método que desenha o personagem

	///////////////////////////////////////////////////////////
	// Novo recurso! Personagems podem lançar magias também. //
	///////////////////////////////////////////////////////////

	public void lancarMagia(){

		System.out.println(this + ": lançando magia...");
	}
}

// Ao inserir o novo comportamento de lançar magia em Personagem,
// Barbaro automaticamente herda este novo comportamento, porém de
// forma indesejada.

class Barbaro extends Personagem {

	public Barbaro(){

		this.pontosDeVida = 100;
		this.pontosDeAtaque = 10;
		this.pontosDeDefesa = 10;
	}

	public void exibir(){

		System.out.println(this +  ": exibindo um barbaro...");
	}
}

// o novo comportamento de lançar magia é desejável na classe Paladino,
// então tudo ok com o novo comportamento herdado de Personagem.

class Paladino extends Personagem {

	public Paladino(){

		this.pontosDeVida = 120;
		this.pontosDeAtaque = 8;
		this.pontosDeDefesa = 12;
	}

	public void exibir(){

		System.out.println(this + ": exibindo um paladino...");
	}
}

// o novo comportamento de lançar magia é desejável na classe Elfo,
// então tudo ok com o novo comportamento herdado de Personagem.

class Elfo extends Personagem {

	public Elfo(){

		this.pontosDeVida = 150;
		this.pontosDeAtaque = 7;
		this.pontosDeDefesa = 7;
	}

	public void exibir(){

		System.out.println(this + ": exibindo um elfo...");
	}
}

///////////////////////////////////////////
// Novo personagem! Este definitivamente //
// deve ser capaz de lançar magias!      //
///////////////////////////////////////////

class Mago extends Personagem {

	public Mago(){

		this.pontosDeVida = 50;
		this.pontosDeDefesa = 7;
	}

	// Magos não atacam, por isso o 
	// metodo atacar será sobreescrito
	// para não "fazer nada".

	public void atacar(Personagem alvo){

		System.out.println(this +  ": << nao ataca >>");		
	}

	public void exibir(){

		System.out.println(this +  ": exibindo um mago...");
	}
}

public class JogoRPG_V2 {

	public static void main(String [] args){

		Personagem [] personagens = new Personagem[4];

		personagens[0] = new Barbaro();
		personagens[1] = new Paladino();
		personagens[2] = new Elfo();
		personagens[3] = new Mago();

		for(int i = 0; i < personagens.length; i++) {

			System.out.println("--------------------------------------");

			Personagem p = personagens[i];
			Personagem alvo = personagens[(int)(Math.random() * personagens.length)];

			p.andar();
			p.atacar(alvo);
			p.lancarMagia();
			p.exibir();
		}

		System.out.println("--------------------------------------");
	}
}

