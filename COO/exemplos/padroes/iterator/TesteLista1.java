class Lista1 {

	private double [] elementos;
	private int livre;

	public Lista1(int tamanho){

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

	public double [] getElementos(){
	
		return elementos;
	}
}

public class TesteLista1 {

	public static void imprimeLista(Lista1 lista){

		double [] elementos = lista.getElementos();

		System.out.print("Elementos: ");

		for(int i = 0; i < lista.tamanho(); i++){
		
			System.out.print(elementos[i] + " ");
		}
		
		System.out.println();
	}

	public static void main(String [] args){

		double [] valores = { 1.0, 5.5, 3.2, 2.3, 9.8 };

		Lista1 lista = new Lista1(10);

		for(double d : valores) lista.add(d);

		imprimeLista(lista);
	}
}

