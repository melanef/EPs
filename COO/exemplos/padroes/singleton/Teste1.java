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

public class Teste1 {

	public static void main(String [] args){
	
		Logger logger = new Logger("log.txt");

		logger.log("evento 1");
		logger.log("evento 2");
		logger.log("evento 3");
		logger.log("evento 4");
	}
}

