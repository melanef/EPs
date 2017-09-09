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

class Barbaro extends Personagem {

	public Barbaro(){

		this.pontosDeVida = 100;
		this.pontosDeAtaque = 10;
		this.pontosDeDefesa = 10;
	}

	public void exibir(){

		System.out.println(this +  ": exibindo um barbaro...");
	}

	///////////////////////////////////////////////
	// sobreescrevendo lancarMagia() para        //
	// neutralizar um comportamento não desejado //
	// obtido através de herança.                //
	///////////////////////////////////////////////

	public void lancarMagia(){

		System.out.println(this +  ": << nao lanca magia >>");
	}
}

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

class Mago extends Personagem {

	public Mago(){

		this.pontosDeVida = 50;
		this.pontosDeAtaque = 3;
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

////////////////////////////////////////
// Qual o trabalho que temos ao criar // 
// um novo tipo de personagem que não // 
// ataca e nem lança magia?           //
////////////////////////////////////////

class Princesa extends Personagem {

	public Princesa(){

		this.pontosDeVida = 40;
		this.pontosDeAtaque = 0;
		this.pontosDeDefesa = 3;
	}

	public void exibir(){

		System.out.println(this +  ": exibindo uma princesa...");
	}

	//////////////////////////////////////////////////////////////////////
	// Princesa irá herdar comportamentos "atacar" e "lançarMagia"      //
	// através do mecanismo de herança. Esses comportamentos herdados   //
	// são desejáveis? Pensando na manutenção do código, qual o impacto //
	// causado pela adição de um novo comportamento em Personagem?      //
	//////////////////////////////////////////////////////////////////////
}


public class JogoRPG_V3 {

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
			p.atacar(alvo);
			p.lancarMagia();
			p.exibir();
		}

		System.out.println("--------------------------------------");
	}
}

