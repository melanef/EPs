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

	public String toString(){

		return getClass().getName();
	}

	public abstract void exibir(); // método que desenha o personagem
}

/////////////////////////////////////////////////////////////////
// Definição de uma interface para cada tipo de comportamento. //
// Cada personagem implementa as interfaces de comportamento   //
// conforme suas características. Por que esta solução também  //
// não é tão interessante, embora não exista mais o risco de   //
// classes já existentes herdarem novos comportamentos não     //
// desejados?                                                  //
/////////////////////////////////////////////////////////////////

// Interface para personagens que atacam

interface Atacante {

	public void atacar(Personagem alvo);
}

// Interface para personagens que lançam magia

interface LancadorDeMagia {

	public void lancarMagia();
}

class Barbaro extends Personagem implements Atacante {

	public Barbaro(){

		this.pontosDeVida = 100;
		this.pontosDeAtaque = 10;
		this.pontosDeDefesa = 10;
	}

	public void exibir(){

		System.out.println(this +  ": exibindo um barbaro...");
	}

	public void atacar(Personagem alvo) {

		System.out.println(this + ": atacando " + alvo + "...");
	}
}

class Paladino extends Personagem implements Atacante, LancadorDeMagia {

	public Paladino(){

		this.pontosDeVida = 120;
		this.pontosDeAtaque = 8;
		this.pontosDeDefesa = 12;
	}

	public void exibir(){

		System.out.println(this + ": exibindo um paladino...");
	}

	public void atacar(Personagem alvo) {

		System.out.println(this + ": atacando " + alvo + "...");
	}

	public void lancarMagia(){

		System.out.println(this + ": lançando magia...");
	}
}

class Elfo extends Personagem implements Atacante, LancadorDeMagia{

	public Elfo(){

		this.pontosDeVida = 150;
		this.pontosDeAtaque = 7;
		this.pontosDeDefesa = 7;
	}

	public void exibir(){

		System.out.println(this + ": exibindo um elfo...");
	}

	public void atacar(Personagem alvo) {

		System.out.println(this + ": atacando " + alvo + "...");
	}

	public void lancarMagia(){

		System.out.println(this + ": lançando magia...");
	}
}

class Mago extends Personagem implements LancadorDeMagia {

	public Mago(){

		this.pontosDeVida = 50;
		this.pontosDeAtaque = 3;
		this.pontosDeDefesa = 7;
	}

	public void exibir(){

		System.out.println(this +  ": exibindo um mago...");
	}

	public void lancarMagia(){

		System.out.println(this + ": lançando magia...");
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

public class JogoRPG_V4 {

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

			if(p instanceof Atacante){

				((Atacante)p).atacar(alvo);
			}
	
			if(p instanceof LancadorDeMagia){

				((LancadorDeMagia)p).lancarMagia();
			}			
	
			p.exibir();
		}

		System.out.println("--------------------------------------");
	}
}

