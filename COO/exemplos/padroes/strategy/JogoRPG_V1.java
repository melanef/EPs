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

public class JogoRPG_V1 {

	public static void main(String [] args){

		Personagem [] personagens = new Personagem[3];

		personagens[0] = new Barbaro();
		personagens[1] = new Paladino();
		personagens[2] = new Elfo();

		for(int i = 0; i < personagens.length; i++) {

			System.out.println("--------------------------------------");

			Personagem p = personagens[i];
			Personagem alvo = personagens[(int)(Math.random() * personagens.length)];

			p.andar();
			p.atacar(alvo);
			p.exibir();
		}

		System.out.println("--------------------------------------");
	}
}

