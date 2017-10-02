
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.lang.String;
import java.lang.NoSuchFieldException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

public class Scheduler
{
    /* Tabela de processos */
    protected ArrayList<Process> processes;

    /* Lista de processos prontos */
    protected ArrayList<Process> available;

    /* Lista de processos bloqueados */
    protected ArrayList<Process> blocked;
    protected Process current;

    private int quantum;

    protected int instructionCounter = 0;
    protected int changeCounter = 0;

    public Scheduler()
    {
        this.processes = new ArrayList<Process>();
        this.available = new ArrayList<Process>();
        this.blocked = new ArrayList<Process>();
    }

    public void loadQuantum(String filePath) throws FileNotFoundException , IOException
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

    public void loadPriorities(String filePath) throws FileNotFoundException , IOException
    {
        try {
            File prioritiesFile = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(prioritiesFile));

            int i = 0;
            String processPriorityString = reader.readLine();
            while (processPriorityString != null && i < this.processes.size()) {
                Process process = this.processes.get(i++);
                process.setPriority((new Integer(processPriorityString)).intValue());
                processPriorityString = reader.readLine();
            }

            this.setCredits();
            this.sortAvailable();
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
            this.available.add(process);
        }
    }

    public void run()
    {
        while (this.processes.size() > 0) {
            if (this.allCreditsGone()) {
                this.setCredits();
            }

            this.runProcess();
        }
    }

    protected void runProcess()
    {
        if (this.available.size() > 0 && this.current == null) {
            this.current = this.available.get(0);
            this.available.remove(0);
        }

        this.waitStep();

        if (this.current != null) {
            Logger.log("Executando " + this.current.getName() + "\n");
            int instructionsGone = this.current.runQuantum(this.quantum);
            this.instructionCounter += instructionsGone;

            if (this.current.isAvailable()) {
                this.available.add(this.current);
            }

            if (this.current.isBlocked()) {
                this.blocked.add(this.current);
                Logger.log("E/S iniciada em " + this.current.getName() + "\n");
            }

            if (this.current.isDone()) {
                this.processes.remove(this.current);
                Logger.log(this.current.getName() + " terminado. X=" + this.current.getX() + ". Y=" + this.current.getY() + "\n");
            }

            String instructions = "instrução";
            if (instructionsGone > 0) {
                instructions = "instruções";
            }

            if (!this.current.isDone()) {
                Logger.log("Interrompendo " + this.current.getName() + " após " + instructionsGone + " " + instructions + "\n");
            }

            this.sortAvailable();
            if (this.available.size() > 0) {
                Process next = this.available.get(0);

                if (this.equals(next)) {
                    this.available.remove(0);
                }
                else {
                    this.current = null;
                    this.changeCounter++;
                }
            }
            else {
                this.current = null;
                this.changeCounter++;
            }
        }
    }

    protected void waitStep()
    {
        for (int i = 0; i < this.blocked.size(); i++) {
            Process current = this.blocked.get(i);
            current.waitStep();

            if (current.isAvailable()) {
                this.available.add(current);
                this.blocked.remove(current);
            }
        }
    }

    protected boolean allCreditsGone()
    {
        for (int i = 0; i < this.available.size(); i++) {
            if (this.available.get(i).getCredits() > 0) {
                return false;
            }
        }

        return true;
    }

    protected void setCredits()
    {
        for (int i = 0; i < this.available.size(); i++) {
            this.available.get(i).setCredits();
        }

        this.sortAvailable();
    }

    protected void sortAvailable()
    {
        Collections.sort(this.available);
    }

    public void logProcesses()
    {
        for (int i = 0; i < this.available.size(); i++) {
            Logger.log("Carregando " + this.available.get(i).getName() + "\n");
        }
    }

    public void logStatistics()
    {
        Logger.log("MEDIA DE TROCAS: " + ((double) this.changeCounter / 10) + "\n");
        Logger.log("MEDIA DE INSTRUÇÕES: " + ((double) this.instructionCounter / this.changeCounter) + "\n");
        Logger.log("QUANTUM: " + this.quantum + "\n");
    }

    public int getInstructionCounter()
    {
        return this.instructionCounter;
    }

    public void incrementInstructionCounter()
    {
        this.instructionCounter++;
    }

    public int getChangeCounter()
    {
        return this.changeCounter;
    }

    public void incrementChangeCounter()
    {
        this.changeCounter++;
    }
}
