import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Color;

import javax.swing.JFrame;

import java.util.Collection;
import java.util.ArrayList;

// Aplicação do padrão Decorator.

// Interface comum a todos os Shapes

interface Shape {

	public void draw(Graphics g);
	public Point getCenter();
	public int getRadius();
}

// Superclasse abstrata que serve debase para as formas geométricas concretas

abstract class BasicShape implements Shape{

	protected Point center;
}

// Superclasse abastrata que serve de base para os decoradores

abstract class ShapeDecorator implements Shape {

	// decoradores mantem uma referência ao objeto a ser decorado
	// que será do tipo Shape. Dessa forma o objeto decorado pode
	// ser tanto uma forma geometrica concreta, quanto um outro
	// decorador. Isso possibilita a aplicação de múltiplos decoradores
	// a uma forma geométrica concreta.

	protected Shape shape; 

	public Point getCenter(){

		// o centro do decorador será o mesmo da forma
		// geometrica decorada. Por isso simplesmente
		// chamamos getCenter() no objeto decorado e
		// devolvemos o resultado.
		
		return shape.getCenter();
	}

	public int getRadius(){

		// o raio do decorador será o mesmo da forma
		// geometrica decorada. Por isso simplesmente
		// chamamos getRadius() no objeto decorado e
		// devolvemos o resultado.

		return shape.getRadius();
	}
}

// Formas geometricas concretas

class Rectangle extends BasicShape {
	
	private Dimension size;

	public Rectangle(Point center, Dimension size){

		this.center = center;
		this.size = size;
	}

	public void draw(Graphics g){

		int x, y, w, h;

		x = center.x - size.width/2;
		y = center.y - size.height/2;
		w = size.width;
		h = size.height;

		g.setColor(Color.BLUE);
		g.fillRect(x, y, w, h);
	}

	public Point getCenter(){
		
		return center;
	}

	public int getRadius(){

		return (int) Math.sqrt(Math.pow(size.width, 2) + Math.pow(size.height, 2)) / 2;
	}
}

class Triangle extends BasicShape {

	int radius;

	public Triangle(Point center, int radius){

		this.center = center;
		this.radius = radius;
	}

	public void draw(Graphics g){

		int aux_x = (int) ((Math.sqrt(3) * radius) / 2.0);
		
		int [] x = {center.x, center.x - aux_x, center.x + aux_x};
		int [] y = {center.y -radius, center.y + radius/2, center.y + radius/2 };

		g.setColor(Color.GREEN);
		g.fillPolygon(x, y, 3);
	}

	public int getRadius(){

		return radius;
	}

	public Point getCenter(){
		
		return center;
	}
}

// Implementações dos decoradores

class CenterDecorator extends ShapeDecorator {

	// O construtor do decorador recebe uma
	// referencia do objeto a ser decorado.
	
	public CenterDecorator(Shape s){

		shape = s;
	}

	// o método "draw" de um decorador invoca o metodo "draw"
	// do objeto decorado e acrescenta algum comportamento extra
	// como, no caso desta classe, fazer o desenho do centro.

	public void draw(Graphics g){
		
		shape.draw(g);

		Point center = getCenter();
		
		g.setColor(Color.BLACK);
		g.drawLine(center.x - 5, center.y, center.x + 5, center.y);
		g.drawLine(center.x, center.y - 5, center.x, center.y + 5);
		g.drawString("(" + center.x + ", " + center.y + ")", center.x + 10, center.y + g.getFontMetrics().getHeight());
	}
}

class CircleDecorator extends ShapeDecorator {

	// O construtor do decorador recebe uma
	// referencia do objeto a ser decorado.

	public CircleDecorator(Shape s){

		shape = s;
	}

	// o método "draw" de um decorador invoca o metodo "draw"
	// do objeto decorado e acrescenta algum comportamento extra
	// como, no caso desta classe, fazer o desenho do circulo
	// circunscrito.

	public void draw(Graphics g){

		int x, y, w, h;

		shape.draw(g);

		Point center = getCenter();

		x = center.x - getRadius();
		y = center.y - getRadius();
		w = getRadius() * 2;
		h = getRadius() * 2;

		g.setColor(Color.BLACK);
		g.drawOval(x, y, w, h);
	}
}

class ShapeFrame extends JFrame {

	Collection<Shape> shapes = null;

	public ShapeFrame(int width, int height){

		shapes = new ArrayList<Shape>();

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(width, height);
	}

	public void add(Shape s){

		shapes.add(s);
	}

	public void paint(Graphics g){

		System.out.println("paint");

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);

		for(Shape s : shapes){

			s.draw(g);
		}
	}

	public void exibir(){

		setVisible(true);			
	}
}

public class ShapeTest4 {

	public static void main(String [] args){

		ShapeFrame frame = new ShapeFrame(600, 600);
		Shape s;

		int [] x = {50, 200, 450};
		
		for(int i = 0; i < 3; i++){
	
			s = new Rectangle(new Point(x[i], 180), new Dimension((i + 1) * 70, (i + 1) * 50));
			if(i == 0) s = new CenterDecorator(s);
			if(i == 1) s = new CircleDecorator(s);
			if(i == 2) s = new CircleDecorator(new CenterDecorator(s));
			frame.add(s);

			s = new Triangle(new Point(x[i], 440), (i + 1) * 35);
			if(i == 0) s = new CenterDecorator(s);
			if(i == 1) s = new CircleDecorator(s);
			if(i == 2) s = new CircleDecorator(new CenterDecorator(s));
			frame.add(s);
		}

		frame.exibir();
	}
}

