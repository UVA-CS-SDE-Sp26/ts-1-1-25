import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



public class ProgramControlTest {

    String testFileList = "01 filea.txt \n 02 fileb.txt \n 03 filec.txt";
    String testFileContents = "This is a test file. Use it as a placeholder.";
    String errorMessageFile = "Error: Invalid File.";
    String errorMessageKey = "Error: Invalid Decipher Key.";

    @Test
    public void controlArguments0() {
        ProgramControl pc = new ProgramControl();
        String[] args = new String[]{};
        String result = pc.run(args);
        assertEquals(testFileList, result);
    }

    @Test
    public void controlArgumentsOneValid() {
        ProgramControl pc = new ProgramControl();
        String[] args = new String[]{"01"};
        String result = pc.run(args);
        assertEquals(testFileContents, result);
    }

    @Test
    public void controlArgumentsOneInvalid() {
        ProgramControl pc = new ProgramControl();
        String[] args = new String[]{"invalid"};
        String result = pc.run(args);
        assertEquals(errorMessageFile, result);
    }

    @Test
    public void controlArgumentsTwoValid() {
        ProgramControl pc = new ProgramControl();
        String[] args = new String[]{}; // idk what to put here
        String result = pc.run(args);
        assertEquals(testFileContents, result);
    }

    @Test
    public void controlArgumentsTwoInvalidFile() {

    }

    @Test
    public void controlArgumentsTwoInvalidKey() {

    }

    @Test
    public void controlArgumentsMoreThanTwo() {

    }


}
