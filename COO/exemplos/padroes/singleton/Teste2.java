import java.io.*;
import java.util.*;

class Logger {

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

		Logger logger = new Logger("log.txt");

		for(int i = 0; i < 4; i++){

			logger.log(id + ": " + i);

			try{
				Thread.sleep(1000);
			}
			catch(InterruptedException e){

				e.printStackTrace();
			}
		}
	}
}

public class Teste2 {

	public static void main(String [] args){

		// Será que duas instâncias de Logger são 
		// capazes de coexistir pacificamente?
	
		Thread t1 = new MyThread("Thread A");
		Thread t2 = new MyThread("Thread B");
		
		t1.start();
		t2.start();

		System.out.println("retomando execução na thread main...");
	}
}

