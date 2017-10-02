
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Logger {
    public static final String LOGFILE_PATH = "./log.txt";

    protected BufferedWriter writer;

    private static Logger instance = null;

    protected Logger()
    {
        try {
            File file = new File(Logger.LOGFILE_PATH);

            if (!file.exists()) {
                file.createNewFile();
            }

            this.writer = new BufferedWriter(new FileWriter(file));
        }
        catch (IOException e) {
            System.out.println("Impossível escrever no arquivo de log: " + e.getMessage());
        }
    }

    public static Logger getInstance()
    {
        if (instance == null) {
            instance = new Logger();
        }

        return instance;
    }

    public static void log(String message)
    {
        Logger instance = Logger.getInstance();
        try {
            instance.writer.write(message);
        }
        catch (IOException e) {
            System.out.println("Impossível escrever no arquivo de log: " + e.getMessage());
        }
    }

    public static void flush()
    {
        Logger instance = Logger.getInstance();
        try {
            instance.writer.flush();
            instance.writer.close();
        }
        catch (IOException e) {
            System.out.println("Impossível escrever no arquivo de log: " + e.getMessage());
        }
    }
}
