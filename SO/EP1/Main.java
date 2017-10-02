
import java.lang.String;

public class Main {
    public static final String QUANTUM_FILEPATH = './quantum.txt';
    public static final String PRIORITIES_FILEPATH = './prioridades.txt';
    public static final String [] PROCESSES_FILEPATHS = [
        './01.txt',
        './02.txt',
        './03.txt',
        './04.txt',
        './05.txt',
        './06.txt',
        './07.txt',
        './08.txt',
        './09.txt',
        './10.txt',
    ];

    public static void main(String [] args)
    {
        Scheduler scheduler = new Scheduler();
        scheduler.loadProcesses(this.PROCESSES_FILEPATHS);
        scheduler.loadQuantum(this.QUANTUM_FILEPATH);
        scheduler.loadPriorities(this.PRIORITIES_FILEPATH);

        scheduler.run();
    }
}
