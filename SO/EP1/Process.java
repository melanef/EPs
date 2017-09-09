
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

    public static final String PARSE_EXCEPTION_MESSAGE_PREFIX = "Erro na interpretação do comando:\n";
    public static final String INVALID_REGISTER_EXCEPTION_MESSAGE_PREFIX = "Não há registrador com o nome indicado no comando de atribuição:\n";

    private String name;
    private int x;
    private int y;
    private int pc;
    private ArrayList<String> programInstructions;
    private int size;
    private Pattern instructionPattern = Pattern.compile("([A-Z]=(\d+)");

    public Process(String name)
    {
        this.pc = 0;
        this.name = name;
        this.size = 0;
    }

    public Process(String name, ArrayList<String> programInstructions)
    {
        this.pc = 0;
        this.name = name;
        this.programInstructions = programInstructions;
        this.size = this.programInstructions.size();
    }

    public String getName()
    {
        return this.name;
    }

    public void run()
    {
        if (this.size > this.pc) {
            Processor processor = Processor.getInstance();
            processor.executeInstruction(this, this.programInstructions.get(this.pc++));
        }
    }
}
