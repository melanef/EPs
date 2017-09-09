class Lista2 {

	private double [] elementos;
	private int livre;
	
	private int index;

	public Lista2(int tamanho){

		elementos = new double[tamanho];
		livre = 0;
	}

	public void add(double x){

		elementos[livre] = x;
		livre++;
	}

	public int tamanho(){

		return livre;
	}

	public void first(){

		index = 0;
	}

	public void next(){

		index++;
	}

	public boolean isDone(){

		return index == livre;
	}

	public double currentElement(){

		return elementos[index];
	}
}

public class TesteLista2 {

	public static void imprimeLista(Lista2 lista){

		System.out.print("Elementos: ");
		
		for(lista.first(); !lista.isDone(); lista.next()){
			 
			System.out.print(lista.currentElement() + " ");
		}

		System.out.println();
	}

	public static void main(String [] args){

		double [] valores = { 1.0, 5.5, 3.2, 2.3, 9.8 };		

		Lista2 lista = new Lista2(10);

		for(double d : valores) lista.add(d);
		
		imprimeLista(lista);
	}
}

