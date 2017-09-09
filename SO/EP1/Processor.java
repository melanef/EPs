
import java.lang.Integer;
import java.lang.String;
import java.lang.NoSuchFieldException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Processor
{
    public static final String INSTRUCTION_COM = "COM";
    public static final String INSTRUCTION_ES = "E/S";
    public static final String INSTRUCTION_SAIDA = "SAIDA";

    public static final String PARSE_EXCEPTION_MESSAGE_PREFIX = "Erro na interpretação do comando:\n";
    public static final String INVALID_REGISTER_EXCEPTION_MESSAGE_PREFIX = "Não há registrador com o nome indicado no comando de atribuição:\n";

    private Pattern instructionPattern = Pattern.compile("([A-Z]=(\d+)");

    private static Processor instance = null;

    protected Processor()
    {
        //
    }

    public static Processor getInstance() {
      if(instance == null) {
         instance = new Processor();
      }

      return instance;
   }

   protected void executeInstruction(Process process, String instruction)
    {
        if (instruction.equals(Processor.INSTRUCTION_COM)) {
            return;
        }

        if (instruction.equals(Processor.INSTRUCTION_ES)) {
            return;
        }

        if (instruction.equals(Processor.INSTRUCTION_SAIDA)) {
            return;
        }

        this.executeAttributionInstruction(instruction);
    }

    protected void executeAttributionInstruction(String instruction)
    {
        Matcher instructionMatcher = this.instructionPattern.matcher(instruction);

        if (!instructionMatcher.find()) {
            String exceptionMessage = Processor.PARSE_EXCEPTION_MESSAGE_PREFIX + instruction;
            throw new ParseException(exceptionMessage, index);
        }

        String registerName = instructionMatcher.group(0);
        Integer value = Integer.decode(instructionMatcher.group(0));

        switch(registerName.toLowerCase().charAt(0)) {
            case "x":
                this.x = value.intValue();
                break;
            case "y":
                this.y = value.intValue();
                break;
            default:
                String exceptionMessage = Processor.INVALID_REGISTER_EXCEPTION_MESSAGE_PREFIX + instruction;
                throw new NoSuchFieldException(exceptionMessage);
        }
    }
}
