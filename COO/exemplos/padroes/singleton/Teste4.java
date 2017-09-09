import java.io.*;
import java.util.*;

class Logger {

	// Aplicação do padrão Singleton com instanciação tardia (lazy instantiation)

	private static Logger theLogger = null;

	// Experimente remover o modificador synchronized da declaração
	// de getLogger() e veja quantas vezes um Logger é instanciado,
	// e qual a saida gerada pelo programa. Observe a importância do 
	// synchronized nos cenários em que multiplas threads podem chamar 
	// o método getLogger() de forma simultânea. 

	public static synchronized Logger getLogger(){

		System.out.println("getLogger() - inicio");
		
		if(theLogger == null){

			System.out.println("getLogger() - antes de instanciar Logger");
			
			theLogger = new Logger("log.txt");

			System.out.println("getLogger() - depois de instanciar Logger");
		}

		System.out.println("getLogger() - fim");

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

	// Seria interessante também declarar o método "log" como synchronized?	
	
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

public class Teste4 {

	public static void main(String [] args){
	
		Thread t1 = new MyThread("Thread A");
		Thread t2 = new MyThread("Thread B");
		
		t1.start();
		t2.start();

		System.out.println("retomando execução na thread main...");

		/* Tente instanciar um Logger e verifique o que acontece */
	
		//new Logger("log.txt");

		/* E se descomentarmos o trecho abaixo? O que acontece? */
		/*
		try{
			Thread.sleep(2000);
		}
		catch(InterruptedException e){

			e.printStackTrace();
		}
	
		Logger l = Logger.getLogger();		
		l.log("Thread main: 0");
		l.log("Thread main: 1");
		l.log("Thread main: 2");
		l.log("Thread main: 3");
		*/
	}
}

