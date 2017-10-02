
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.lang.String;
import java.util.ArrayList;

public class Scheduler
{
    protected ArrayList<Process> processes;

    protected ArrayList<Integer> available;
    protected ArrayList<Integer> blocked;
    protected Process current;

    private int quantum;

    public Scheduler()
    {
        this.processes = new ArrayList<Process>();
        this.available = new ArrayList<Integer>();
        this.blocked = new ArrayList<Integer>();
    }

    public void loadQuantum(String filePath)
    {
        try {
            File quantumFile = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(quantumFile));

            this.quantum = (new Integer(reader.readLine())).intValue();
        }
        catch (FileNotFoundException e) {
            System.out.println("Arquivo descritivo do quantum nao encontrado: " + filePath);
        }
        catch (IOException e) {
            System.out.println("Falha na leitura do arquivo descritivo do quantum: " + e.getMessage());
        }
    }

    public void loadPriorities(String filepath)
    {
        try {
            File prioritiesFile = new File(filepath);
            BufferedReader reader = new BufferedReader(new FileReader(prioritiesFile));

            int i = 0;
            String processPriorityString = reader.readLine();
            while (processPriorityString != null && i < this.processes.size()) {
                Process process = this.processes.get(i++);
                process.setPriority((new Integer(processPriorityString)).intValue);
                processPriorityString = reader.readLine();
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Arquivo de prioridades nao encontrado: " + filePath);
        }
        catch (IOException e) {
            System.out.println("Falha na leitura do arquivo de prioridades: " + e.getMessage());
        }
    }

    public void loadProcesses(String [] filePaths)
    {
        for (int i = 0; i < filePaths.length; i++) {
            Process process = ProcessFactory.createProcess(filePaths[i]);
            this.processes.add(process);
            this.available.add(new Integer(i));
        }
    }

    public void run()
    {

    }
}
