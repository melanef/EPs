import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

/***********************************************************************/
/*                                                                     */
/* Classe com métodos úteis para implementação de um jogo. Inclui:     */
/*                                                                     */
/* - Método para iniciar modo gráfico.                                 */
/*                                                                     */
/* - Métodos para desenhos de formas geométricas.                      */
/*                                                                     */
/* - Método para atualizar o display.                                  */
/*                                                                     */
/* - Método para verificar o estado (pressionada ou não pressionada)   */
/*   das teclas usadas no jogo:                                        */
/*                                                                     */
/*   	- up, down, left, right: movimentação do player.               */
/*		- control: disparo de projéteis.                               */
/*		- ESC: para sair do jogo.                                      */
/*                                                                     */
/***********************************************************************/

public class GameLib {
	
	public static final int WIDTH = 480;
	public static final int HEIGHT = 720;
	
	public static final int KEY_UP = 0;
	public static final int KEY_DOWN = 1;
	public static final int KEY_LEFT = 2;
	public static final int KEY_RIGHT = 3;
	public static final int KEY_CONTROL = 4;
	public static final int KEY_ESCAPE = 5;

	private static MyFrame frame = null;
	private static Graphics g = null;
	private static MyKeyAdapter keyboard = null;
	
	public static void initGraphics(){
		
		frame = new MyFrame("Projeto COO");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(false);
		frame.setVisible(true);
		
		keyboard = new MyKeyAdapter();
		frame.addKeyListener(keyboard);
		frame.requestFocus();
		
		frame.createBufferStrategy(2);		
		g = frame.getBufferStrategy().getDrawGraphics();
	}
	
	public static void setColor(Color c){
		
		g.setColor(c);
	}
	
	public static void drawLine(double x1, double y1, double x2, double y2){
		
		g.drawLine((int) Math.round(x1), (int) Math.round(y1), (int) Math.round(x2), (int) Math.round(y2));
	}
	
	public static void drawCircle(double cx, double cy, double radius){
		
		int x = (int) Math.round(cx - radius);
		int y = (int) Math.round(cy - radius);
		int width = (int) Math.round(2 * radius);
		int height = (int) Math.round(2 * radius);
		
		g.drawOval(x, y, width, height);
	}

	public static void fillCircle(double cx, double cy, double radius){
		
		int x = (int) Math.round(cx - radius);
		int y = (int) Math.round(cy - radius);
		int width = (int) Math.round(2 * radius);
		int height = (int) Math.round(2 * radius);
		
		g.fillOval(x, y, width, height);
	}
	
	public static void drawDiamond(double x, double y, double radius){
		
		int x1 = (int) Math.round(x);
		int y1 = (int) Math.round(y - radius);
		
		int x2 = (int) Math.round(x + radius);
		int y2 = (int) Math.round(y);
		
		int x3 = (int) Math.round(x);
		int y3 = (int) Math.round(y + radius);
		
		int x4 = (int) Math.round(x - radius);
		int y4 = (int) Math.round(y);
		
		drawLine(x1, y1, x2, y2);
		drawLine(x2, y2, x3, y3);
		drawLine(x3, y3, x4, y4);
		drawLine(x4, y4, x1, y1);
	}
	
	public static void drawPlayer(double player_X, double player_Y, double player_size){
		
		GameLib.drawLine(player_X - player_size, player_Y + player_size, player_X, player_Y - player_size);
		GameLib.drawLine(player_X + player_size, player_Y + player_size, player_X, player_Y - player_size);
		GameLib.drawLine(player_X - player_size, player_Y + player_size, player_X, player_Y + player_size * 0.5);
		GameLib.drawLine(player_X + player_size, player_Y + player_size, player_X, player_Y + player_size * 0.5);
	}
	
	public static void drawExplosion(double x, double y, double alpha){

		int p = 5;
		int r = (int) (255 - Math.pow(alpha, p) * 255);
		int g = (int) (128 - Math.pow(alpha, p) * 128);
		int b = 0;

		GameLib.setColor(new Color(r, g, b));
		GameLib.drawCircle(x, y, alpha * alpha * 40);
		GameLib.drawCircle(x, y, alpha * alpha * 40 + 1);
	}
	
	public static void drawRect(double cx, double cy, double width, double height){
		
		int x = (int) Math.round(cx - width/2);
		int y = (int) Math.round(cy - height/2);
		
		g.drawRect(x, y, (int) Math.round(width), (int) Math.round(height));
	}

	public static void fillRect(double cx, double cy, double width, double height){
		
		int x = (int) Math.round(cx - width/2);
		int y = (int) Math.round(cy - height/2);
		
		g.fillRect(x, y, (int) Math.round(width), (int) Math.round(height));
	}
	
	public static void display(){
									
		g.dispose();
		frame.getBufferStrategy().show();
		Toolkit.getDefaultToolkit().sync();
		g = frame.getBufferStrategy().getDrawGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, frame.getWidth() - 1, frame.getHeight() - 1);
		g.setColor(Color.WHITE);
	}
	
	public static boolean iskeyPressed(int index){
		
		return keyboard.isKeyPressed(index);
	}
	
	public static void debugKeys(){
		
		keyboard.debug();
	}
}

@SuppressWarnings("serial")
class MyFrame extends JFrame {
	
	public MyFrame(String title){
		
		super(title);
	}

	public void paint(Graphics g){ }
	
	public void update(Graphics g){ }
	
	public void repaint(){ }
}

class MyKeyAdapter extends KeyAdapter{
	
	private int [] codes = {	KeyEvent.VK_UP,
								KeyEvent.VK_DOWN,
								KeyEvent.VK_LEFT,
								KeyEvent.VK_RIGHT, 
								KeyEvent.VK_CONTROL,
								KeyEvent.VK_ESCAPE
							};
	
	private boolean [] keyStates = null;
	private long [] releaseTimeStamps = null;
	
	public MyKeyAdapter(){
		
		keyStates = new boolean[codes.length];
		releaseTimeStamps = new long[codes.length];
	}
	
	public int getIndexFromKeyCode(int keyCode){
		
		for(int i = 0; i < codes.length; i++){
			
			if(codes[i] == keyCode) return i;
		}
		
		return -1;
	}
	
	public void keyPressed(KeyEvent e){
		
		//System.out.println("KeyPressed " + e.getWhen() + " " + System.currentTimeMillis());
		
		int index = getIndexFromKeyCode(e.getKeyCode());
		
		if(index >= 0){
			
			keyStates[index] = true;
		}
	}
	
	public void keyReleased(KeyEvent e){
	
		//System.out.println("KeyReleased " + e.getWhen() + " " + System.currentTimeMillis());
		
		int index = getIndexFromKeyCode(e.getKeyCode());
		
		if(index >= 0){
			
			keyStates[index] = false;
			releaseTimeStamps[index] = System.currentTimeMillis();
		}
	}
	
	public boolean isKeyPressed(int index){
		
		boolean keyState = keyStates[index];
		long keyReleaseTime = releaseTimeStamps[index];
		
		if(keyState == false){

			if(System.currentTimeMillis() - keyReleaseTime > 5) return false;
		}
		
		return true;		
	}
	
	public void debug(){
		
		System.out.print("Key states = {");
		
		for(int i = 0; i < codes.length; i++){
			
			System.out.print(" " + keyStates[i] + (i < (codes.length - 1) ? "," : ""));
		}
		
		System.out.println(" }");
	}
}

