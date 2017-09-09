import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Color;

import javax.swing.JFrame;

import java.util.Collection;
import java.util.ArrayList;

// O que acontece se quisermos adicionar acabementos às formas
// geometricas? Vamos supor 2 tipos de acabamentos diferentes:
//
//   - desenho do centro da forma.
//   - desenho do circulo circunscrito à forma.

abstract class Shape {

	protected Point center;

	public Point getCenter(){

		return center;
	}

	public abstract void draw(Graphics g);
	public abstract int getRadius();
}

// Uma primeira ideia é criar uma subclasse de Shape para cada
// combinação de forma geometrica e acabamento possível. Assim
// teremos 3 versões diferentes de retangulos:

// Retangulo sem acabamento
class Rectangle extends Shape {
	
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

	public int getRadius(){

		return (int) Math.sqrt(Math.pow(size.width, 2) + Math.pow(size.height, 2)) / 2;
	}
}

// Retangulo com desenho do centro
class RectangleWithCenter extends Shape {
	
	private Dimension size;

	public RectangleWithCenter(Point center, Dimension size){

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

		g.setColor(Color.BLACK);
		g.drawLine(center.x - 5, center.y, center.x + 5, center.y);
		g.drawLine(center.x, center.y - 5, center.x, center.y + 5);
		g.drawString("(" + center.x + ", " + center.y + ")", center.x + 10, center.y + g.getFontMetrics().getHeight());
	}

	public int getRadius(){

		return (int) Math.sqrt(Math.pow(size.width, 2) + Math.pow(size.height, 2)) / 2;
	}
}

// retangulo com desenho do circulo circunscrito
class RectangleWithCircle extends Shape {
	
	private Dimension size;

	public RectangleWithCircle(Point center, Dimension size){

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
	
		x = center.x - getRadius();
		y = center.y - getRadius();
		w = getRadius() * 2;
		h = getRadius() * 2;

		g.setColor(Color.BLACK);
		g.drawOval(x, y, w, h);
	}

	public int getRadius(){

		return (int) Math.sqrt(Math.pow(size.width, 2) + Math.pow(size.height, 2)) / 2;
	}
}

// Observe que há repetição de código no método draw(...) das 3 versões de retângulos
// (a parte do código responsável pelo desenho do retângulo em si). Para as 3 versões 
// de triângulo vamos tentar fazer algo um pouco melhor. Ao invés de 3 versões que
// derivam diretamente da classe Shape, teremos:

// Classe Triangle que extende diretamente Shape e implementa o desenho
// "basico" de um triângulo (ou seja, um triângulo sem acabamento). 
class Triangle extends Shape {

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
}

// Classe TriangleWithCenter que extende Triangle e "acrescenta" o desenho
// do centro (o desenho do triângulo "básico" é herdado da superclasse).
class TriangleWithCenter extends Triangle {

	public TriangleWithCenter(Point center, int radius){

		super(center, radius);
	}

	public void draw(Graphics g){
		
		// chamando draw(...) da superclasse
		// para desenhar o triângulo "básico"
		super.draw(g);

		// desenhando o centro do triangulo
		g.setColor(Color.BLACK);
		g.drawLine(center.x - 5, center.y, center.x + 5, center.y);
		g.drawLine(center.x, center.y - 5, center.x, center.y + 5);
		g.drawString("(" + center.x + ", " + center.y + ")", center.x + 10, center.y + g.getFontMetrics().getHeight());
	}
}

// Classe TriangleWithCircle que extende Triangle e "acrescenta" 
// o desenho do circulo circunscrito. Note que o desenho do 
// triângulo "básico" é herdado da superclasse Triangle.
class TriangleWithCircle extends Triangle {

	public TriangleWithCircle(Point center, int radius){

		super(center, radius);
	}

	public void draw(Graphics g){
		
		int x, y, w, h;

		// chamando draw(...) da superclasse
		// para desenhar o triângulo "básico"
		super.draw(g);

		// desenho do circulo circunscrito
		x = center.x - getRadius();
		y = center.y - getRadius();
		w = getRadius() * 2;
		h = getRadius() * 2;

		g.setColor(Color.BLACK);
		g.drawOval(x, y, w, h);
	}
}

// Embora um pouco melhor, continuamos tendo que ter uma
// classe para cada combinação possível de forma geometrica e
// acabamento. Isso definitivamente não é adequado quando a 
// quantidade de formas geometricas e tipos de acabamento é
// grande. Imagine qual seria o impacto de se adicionar uma
// nova forma geométrica ou um novo tipo de acabamento.

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

public class ShapeTest2 {

	public static void main(String [] args){

		ShapeFrame frame = new ShapeFrame(600, 600);
		Shape s = null;

		int [] x = {50, 200, 450};
		
		for(int i = 0; i < 3; i++){
	
			if(i == 0) s = new Rectangle(new Point(x[i], 180), new Dimension((i + 1) * 70, (i + 1) * 50));
			else if(i == 1) s = new RectangleWithCenter(new Point(x[i], 180), new Dimension((i + 1) * 70, (i + 1) * 50));
			else s = new RectangleWithCircle(new Point(x[i], 180), new Dimension((i + 1) * 70, (i + 1) * 50));
			
			frame.add(s);

			if(i == 0) s = new Triangle(new Point(x[i], 440), (i + 1) * 35);
			else if(i == 1) s = new TriangleWithCenter(new Point(x[i], 440), (i + 1) * 35);
			else s = new TriangleWithCircle(new Point(x[i], 440), (i + 1) * 35);
			frame.add(s);
		}

		frame.exibir();
	}
}

