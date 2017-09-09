import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Color;

import javax.swing.JFrame;

import java.util.Collection;
import java.util.ArrayList;

// superclasse abstrata que contem
// tudo o que há de comum em um Shape.

abstract class Shape {

	protected Point center;

	public Point getCenter(){

		return center;
	}

	public abstract void draw(Graphics g);
	public abstract double getRadius();
}

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

	public double getRadius(){

		return Math.sqrt(Math.pow(size.width, 2) + Math.pow(size.height, 2)) / 2.0;
	}
}

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

	public double getRadius(){

		return radius;
	}
}

// Criar uma nova forma geometrica é trivial
// através do mecanismo de herança.

class Circle extends Shape {

	int radius;

	public Circle(Point center, int radius){

		this.center = center;
		this.radius = radius;
	}

	public void draw(Graphics g){

		int x, y, w, h;

		x = center.x - radius;
		y = center.y - radius;
		w = radius * 2;
		h = radius * 2;

		g.setColor(Color.RED);
		g.fillOval(x, y, w, h);
	}

	public double getRadius(){

		return radius;
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

public class ShapeTest1 {

	public static void main(String [] args){

		ShapeFrame frame = new ShapeFrame(600, 600);
		Shape s;

		int [] x = {50, 200, 450};
		
		for(int i = 0; i < 3; i++){
	
			s = new Rectangle(new Point(x[i], 120), new Dimension((i + 1) * 70, (i + 1) * 50));
			frame.add(s);

			s = new Triangle(new Point(x[i], 320), (i + 1) * 35);
			frame.add(s);

			s = new Circle(new Point(x[i], 490), (i + 1) * 30);
			frame.add(s);
		}

		frame.exibir();
	}
}

