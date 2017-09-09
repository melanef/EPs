class Lista3 {

	private double [] elementos;
	private int livre;

	public Lista3(int tamanho){

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

	public double getElemento(int index){

		return elementos[index];
	}
}

class ListaIterator3 {

	private Lista3 lista;
	private int index;

	public ListaIterator3(Lista3 lista){

		this.lista = lista;
	}

	public void first(){

		index = 0;
	}

	public void next(){

		index++;
	}

	public boolean isDone(){

		return index == lista.tamanho();
	}

	public double currentElement(){

		return lista.getElemento(index);
	}
}

class ReverseIterator3 {

	private Lista3 lista;
	private int index;

	public ReverseIterator3(Lista3 lista){

		this.lista = lista;
	}

	public void first(){

		index = lista.tamanho() - 1;
	}

	public void next(){

		index--;
	}

	public boolean isDone(){

		return index < 0;
	}

	public double currentElement(){

		return lista.getElemento(index);
	}
}

public class TesteLista3 {

	public static void imprimeLista(Lista3 lista){

		System.out.print("Elementos: ");

		ListaIterator3 it = new ListaIterator3(lista);
		
		for(it.first(); !it.isDone(); it.next()){
			 
			System.out.print(it.currentElement() + " ");
		}

		System.out.println();
	}

	public static void imprimeReverso(Lista3 lista){

		System.out.print("Elementos: ");

		ReverseIterator3 it = new ReverseIterator3(lista);
		
		for(it.first(); !it.isDone(); it.next()){
			 
			System.out.print(it.currentElement() + " ");
		}

		System.out.println();
	}

	public static void imprimeSimultaneo(Lista3 lista){

		System.out.print("Elementos: ");

		ListaIterator3 it1 = new ListaIterator3(lista);
		ReverseIterator3 it2 = new ReverseIterator3(lista);
		
		it1.first();
		it2.first();

		while(!it1.isDone() && !it2.isDone()){
			 
			if(Math.random() > 0.5){ 
				System.out.print(it1.currentElement() + " ");
				it1.next();
			}
			else { 
				System.out.print(it2.currentElement() + " ");
				it2.next();
			}
		}

		while(!it1.isDone()){
			System.out.print(it1.currentElement() + " ");
			it1.next();
		}

		while(!it2.isDone()){
			System.out.print(it2.currentElement() + " ");
			it2.next();
		}

		System.out.println();
	}

	public static void main(String [] args){

		double [] valores = { 1.0, 5.5, 3.2, 2.3, 9.8 };
		
		Lista3 lista = new Lista3(10);

		for(double d : valores) lista.add(d);

		imprimeLista(lista);
		imprimeReverso(lista);
		imprimeSimultaneo(lista);
	}
}


