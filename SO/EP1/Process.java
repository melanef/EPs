
import java.lang.Integer;
import java.lang.String;
import java.lang.NoSuchFieldException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* BCP */
public class Process implements Comparable<Process>
{
    /* Flags de Status */
    public static final int PROCESS_STATUS_AVAILABLE = 0;
    public static final int PROCESS_STATUS_RUNNING = 1;
    public static final int PROCESS_STATUS_BLOCKED = 2;
    public static final int PROCESS_STATUS_DONE = 3;

    private String name;
    /* Registradores */
    private int x;
    private int y;
    /* Contador de Programa */
    private int pc;
    /* Segmento de texto */
    private ArrayList<String> programInstructions;
    private int size;
    /* Estado do processo */
    private int status;
    /* Prioridade */
    private int priority;
    /* Créditos */
    private int credits;
    /* Tempo de espera para operações de E/S */
    private int waitTime;

    public Process(String name)
    {
        this.pc = 0;
        this.name = name;
        this.size = 0;
        this.status = Process.PROCESS_STATUS_AVAILABLE;
        this.credits = 0;
        this.priority = 1;
        this.waitTime = 0;
    }

    public Process(String name, ArrayList<String> programInstructions)
    {
        this.pc = 0;
        this.name = name;
        this.size = 0;
        this.status = Process.PROCESS_STATUS_AVAILABLE;
        this.credits = 0;
        this.priority = 1;
        this.waitTime = 0;

        this.programInstructions = programInstructions;
        this.size = this.programInstructions.size();
    }

    public String getName()
    {
        return this.name;
    }

    public int getPriority()
    {
        return this.priority;
    }

    public void setPriority(int priority)
    {
        this.priority = priority;
    }

    public int getCredits()
    {
        return this.credits;
    }

    public void setCredits()
    {
        this.credits = this.getPriority();
    }

    public void block()
    {
        this.status = Process.PROCESS_STATUS_BLOCKED;
        this.waitTime = Processor.ES_WAIT_QUANTA;
    }

    public void done()
    {
        this.status = Process.PROCESS_STATUS_DONE;
    }

    public void waitStep()
    {
        this.waitTime--;
        if (this.waitTime <= 0) {
            this.waitTime = 0;
            this.status = Process.PROCESS_STATUS_AVAILABLE;
        }
    }

    public boolean isAvailable()
    {
        return this.status == Process.PROCESS_STATUS_AVAILABLE;
    }

    public boolean isDone()
    {
        return this.status == Process.PROCESS_STATUS_DONE;
    }

    public boolean isBlocked()
    {
        return this.status == Process.PROCESS_STATUS_BLOCKED;
    }

    public void runInstruction() throws IOInstructionException, ExitInstructionException, ParseException, NoSuchFieldException
    {
        if (this.size > this.pc) {
            Processor processor = Processor.getInstance();
            int currentInstruction = this.pc;
            this.pc++;
            processor.executeInstruction(this, this.programInstructions.get(currentInstruction), currentInstruction);
        }
    }

    public int runQuantum(int quantumSize)
    {
        this.status = Process.PROCESS_STATUS_RUNNING;
        this.credits--;
        int i = 0;

        for (; i < quantumSize; i++) {
            try {
                this.runInstruction();
            }
            catch (IOInstructionException e) {
                this.block();
                return i + 1;
            }
            catch (ExitInstructionException e) {
                this.done();
                return i + 1;
            }
            catch (ParseException e) {
                System.out.println(e.getMessage());
            }
            catch (NoSuchFieldException e) {
                System.out.println(e.getMessage());
            }
        }

        this.status = Process.PROCESS_STATUS_AVAILABLE;

        return i;
    }

    @Override
    public int compareTo(Process otherProcess)
    {
        if (this.getCredits() > otherProcess.getCredits()) {
            return -1;
        } else if (this.getCredits() < otherProcess.getCredits()) {
            return 1;
        } else {
            return 0;
        }
    }

    public boolean equals(Process otherProcess)
    {
        return this.getName().equals(otherProcess.getName());
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getX()
    {
        return this.x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public int getY()
    {
        return this.y;
    }
}
