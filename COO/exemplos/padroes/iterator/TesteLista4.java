interface Colecao {

	public void add(double x);
	public int tamanho();
	public Iterator createIterator();
}

interface Iterator {

	public void first();
	public void next();
	public boolean isDone();
	public double currentElement();
}

class Lista4 implements Colecao{

	private double [] elementos;
	private int livre;

	public Lista4(int tamanho){

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

	public Iterator createIterator(){

		return new ListaIterator4(this);
		//return new ReverseIterator4(this);
	}
}

class ListaIterator4 implements Iterator {

	private Lista4 lista;
	private int index;

	public ListaIterator4(Lista4 lista){

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

class ReverseIterator4 implements Iterator{

	private Lista4 lista;
	private int index;

	public ReverseIterator4(Lista4 lista){

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

public class TesteLista4 {

	public static void imprimeLista(Colecao colecao){

		System.out.print("Elementos: ");

		Iterator it = colecao.createIterator();
		
		for(it.first(); !it.isDone(); it.next()){
			 
			System.out.print(it.currentElement() + " ");
		}

		System.out.println();
	}

	public static void main(String [] args){

		double [] valores = { 1.0, 5.5, 3.2, 2.3, 9.8 };	
		
		Colecao colecao = new Lista4(10);

		for(double d : valores) colecao.add(d);

		imprimeLista(colecao);
	}
}

