import java.io.*;
import java.util.*;

class Logger {

	public static Logger theLogger = new Logger("log.txt");

	private Writer writer;

	public Logger(String fileName){

		try {
			writer = new FileWriter(fileName);
		}
		catch(IOException e){

			e.printStackTrace();
		}
	}

	public void log(String msg){

		try {
			writer.write(new Date() + ": " + msg + "\n");
			writer.flush();
		}
		catch(IOException e){

			e.printStackTrace();
		}
	}
}

class MyThread extends Thread {
	
	private String id;

	public MyThread(String id){

		this.id = id;
	}

	public void run(){

		for(int i = 0; i < 4; i++){

			Logger.theLogger.log(id + ": " + i);

			try{
				Thread.sleep(1000);
			}
			catch(InterruptedException e){

				e.printStackTrace();
			}
		}
	}
}

public class Teste3 {

	public static void main(String [] args){
	
		Thread t1 = new MyThread("Thread A");
		Thread t2 = new MyThread("Thread B");
		
		t1.start();
		t2.start();

		System.out.println("retomando execução na thread main...");

		// E se descomentarmos o trecho abaixo? O que acontece?
		/*		
		try{
			Thread.sleep(2000);
		}
		catch(InterruptedException e){

			e.printStackTrace();
		}
		
		Logger l = new Logger("log.txt");
		l.log("Thread main: 0");
		l.log("Thread main: 1");
		l.log("Thread main: 2");
		l.log("Thread main: 3");
		*/
	}
}

