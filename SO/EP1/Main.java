
import java.lang.String;

public class Main {
    public static final String QUANTUM_FILEPATH = "./processos/quantum.txt";
    public static final String PRIORITIES_FILEPATH = "./processos/prioridades.txt";
    public static final String [] PROCESSES_FILEPATHS = {
        "./processos/01.txt",
        "./processos/02.txt",
        "./processos/03.txt",
        "./processos/04.txt",
        "./processos/05.txt",
        "./processos/06.txt",
        "./processos/07.txt",
        "./processos/08.txt",
        "./processos/09.txt",
        "./processos/10.txt",
    };

    public static void main(String [] args)
    {
        try {
            Scheduler scheduler = new Scheduler();
            scheduler.loadProcesses(Main.PROCESSES_FILEPATHS);
            scheduler.loadQuantum(Main.QUANTUM_FILEPATH);
            scheduler.loadPriorities(Main.PRIORITIES_FILEPATH);
            scheduler.logProcesses();
            scheduler.run();
            scheduler.logStatistics();

            Logger.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
