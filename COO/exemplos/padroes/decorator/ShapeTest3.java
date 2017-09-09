import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Color;

import javax.swing.JFrame;

import java.util.Collection;
import java.util.ArrayList;

// Uma alternativa a criar uma classe para cada combinação
// de forma geométrica e tipo de acabamento é colocar variáveis
// booleanas na superclasse Shape, indicando se cada um dos tipos
// de acabamento disponíveis devem ou não ser desenhados.
//
// Além de evitar uma explosão no número de classes, o que é positivo
// em termos de manutenção do código, essa abordagem é mais flexível
// pois permite ativar os dois tipos de acabamento para uma mesma forma
// geométrica. No exemplo anterior, a única forma de se atingir o mesmo 
// efeito seria criar novas versões de retângulo e triângulo, que 
// implementassem o desenho dos dois acabamentos de forma simultânea.
//
// Apesar de mais interessante, a adição de novos tipos de acabamento,
// ou modificação de acabamentos já existentes, demandam modificações
// na classe Shape, uma classe que já está pronta. Gostaríamos de ser
// capazes de acrescentar novos tipos de acabamento, mas sem modificar
// qualquer código pré-existente (classes abertas para extensão, mas 
// fechadas para modificação). O padrão Decorator servirá bem para
// atingir tal objetivo, como poderá ser visto na próxima versão deste
// exemplo.

abstract class Shape {

	protected Point center;

	private boolean drawCenter = false;
	private boolean drawCircle = false;

	public Point getCenter(){

		return center;
	}

	public boolean getDrawCenter(){

		return drawCenter;
	}
	
	public boolean getDrawCircle(){

		return drawCircle;
	}

	public void setDrawCenter(boolean b){

		drawCenter = b;
	}
	
	public void setDrawCircle(boolean b){

		drawCircle = b;
	}

	// o método draw na superclasse Shape tem o papel
	// de desenhar os acabamentos que estejam ativados

	public void draw(Graphics g){

		if(getDrawCenter()){

			g.setColor(Color.BLACK);
			g.drawLine(center.x - 5, center.y, center.x + 5, center.y);
			g.drawLine(center.x, center.y - 5, center.x, center.y + 5);
			g.drawString("(" + center.x + ", " + center.y + ")", center.x + 10, center.y + g.getFontMetrics().getHeight());
		}

		if(getDrawCircle()){

			int x, y, w, h;

			x = center.x - getRadius();
			y = center.y - getRadius();
			w = getRadius() * 2;
			h = getRadius() * 2;

			g.setColor(Color.BLACK);
			g.drawOval(x, y, w, h);
		}
	}

	public abstract int getRadius();
}

class Rectangle extends Shape {
	
	private Dimension size;

	public Rectangle(Point center, Dimension size){

		this.center = center;
		this.size = size;
	}

	// o método draw nas classes que correspondem às formas
	// geométricas concretas implementam o desenho "básico"
	// da forma, e chamam o método draw da superclasse para
	// desenhar eventuais acabamentos que tenham sido ativados.

	public void draw(Graphics g){

		int x, y, w, h;

		x = center.x - size.width/2;
		y = center.y - size.height/2;
		w = size.width;
		h = size.height;

		g.setColor(Color.BLUE);
		g.fillRect(x, y, w, h);

		super.draw(g);
	}

	public int getRadius(){

		return (int) Math.sqrt(Math.pow(size.width, 2) + Math.pow(size.height, 2)) / 2;
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

		super.draw(g);
	}

	public int getRadius(){

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

public class ShapeTest3 {

	public static void main(String [] args){

		ShapeFrame frame = new ShapeFrame(600, 600);
		Shape s;

		int [] x = {50, 200, 450};
		
		for(int i = 0; i < 3; i++){
	
			s = new Rectangle(new Point(x[i], 180), new Dimension((i + 1) * 70, (i + 1) * 50));
			if(i == 1) s.setDrawCenter(true);
			if(i == 2) s.setDrawCircle(true);
			
			frame.add(s);

			s = new Triangle(new Point(x[i], 440), (i + 1) * 35);
			if(i % 2 == 0) s.setDrawCenter(true);
			if(i >= 1) s.setDrawCircle(true);
			frame.add(s);
		}

		frame.exibir();
	}
}

