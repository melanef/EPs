
import java.lang.Integer;
import java.lang.String;
import java.lang.NoSuchFieldException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Process
{
    public static final String INSTRUCTION_COM = "COM";
    public static final String INSTRUCTION_ES = "E/S";
    public static final String INSTRUCTION_SAIDA = "SAIDA";

    public static final int PROCESS_STATUS_AVAILABLE = 0;
    public static final int PROCESS_STATUS_RUNNING = 1;
    public static final int PROCESS_STATUS_BLOCKED = 2;

    private String name;
    private int x;
    private int y;
    private int pc;
    private ArrayList<String> programInstructions;
    private int size;
    private int status;
    private int priority;
    private int credits;

    public Process(String name)
    {
        this.pc = 0;
        this.name = name;
        this.size = 0;
        this.status = Process.PROCESS_STATUS_AVAILABLE;
    }

    public Process(String name, ArrayList<String> programInstructions)
    {
        this.pc = 0;
        this.name = name;
        this.programInstructions = programInstructions;
        this.size = this.programInstructions.size();
        this.status = Process.PROCESS_STATUS_AVAILABLE;
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
        this.credits = priority;
    }

    public void run()
    {
        if (this.size > this.pc) {
            Processor processor = Processor.getInstance();
            processor.executeInstruction(this, this.programInstructions.get(this.pc++));
        }
    }
}
