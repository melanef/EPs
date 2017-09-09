import java.io.*;
import java.util.*;

class Logger {

	// Aplicação do padrão Singleton com instanciação imediata.

	private static Logger theLogger = new Logger("log.txt");

	// Outra forma de implementar a instanciação imediata seria
	// através de um bloco static, executado no instante em que
	// a definição da classe Logger é carregada. Em situações que 
	// o construtor da classe singleton pode lançar exceções, essa 
	// é única forma de se fazer a instanciação imediata.

	/*
	private static Logger theLogger = null;

	static {

		theLogger = new Logger("log.txt");
	}
	*/

	// Usando instanciação imediata não há o risco de ocorrer 
        // instanciações multiplas mesmo quando existem multiplas 
	// threads. Assim, não é necessário o uso do synchronized 
	// no método getLogger().
	
	public static Logger getLogger(){

		System.out.println("getLogger()");

		return theLogger;
	}

	private Writer writer;

	private Logger(String fileName){

		System.out.println(">>>> Construtor - inicio");

		try {
			writer = new FileWriter(fileName);
		}
		catch(IOException e){

			e.printStackTrace();
		}

		System.out.println(">>>> Construtor - fim");
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

		Logger logger = Logger.getLogger();

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

public class Teste5 {

	public static void main(String [] args){
	
		Thread t1 = new MyThread("Thread A");
		Thread t2 = new MyThread("Thread B");
		
		t1.start();
		t2.start();

		System.out.println("retomando execução na thread main...");
	}
}

