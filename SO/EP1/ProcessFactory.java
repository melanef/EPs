
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.lang.String;
import java.util.ArrayList;

public class ProcessFactory {
    public static Process createProcess(String filePath)
    {
        String name = "";
        ArrayList<String> instructions = new ArrayList<String>();

        try {
            File processFile = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(processFile));

            name = reader.readLine();

            String instruction = reader.readLine();
            while (instruction != null) {
                instructions.add(instruction);
                instruction = reader.readLine();
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Arquivo descritivo do processo nao encontrado: " + filePath);
        }
        catch (IOException e) {
            System.out.println("Falha na leitura do arquivo descritivo do processo: " + e.getMessage());
        }

        return new Process(name, instructions);
    }
}
