import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserInterfaceTest {

    String testFileList = "1 file1\n2 file 2\n3 file 3";
    String testFileContentsOne = "contents of a file";
    String testFileContentsTwo = "contents of another file";
    String carnivoreText = """
Carnivore, later renamed DCS1000, was a system implemented by the Federal Bureau of Investigation (FBI) that was
designed to monitor email and electronic communications. It used a customizable packet sniffer that could monitor all
of a target user's Internet traffic. Carnivore was implemented in October 1997. By 2005 it had been replaced with
improved commercial software.""";

    @Mock
    ProgramControl programControlMocked;

    @BeforeEach
    public void setup() {
        programControlMocked = mock(ProgramControl.class);
    }

    @Test
    public void parseArgumentsZero() {
        when(programControlMocked.listFiles()).thenReturn(testFileList);
        UserInterface ui = new UserInterface(programControlMocked);
        String[] arguments = new String[]{};
        String result = ui.parseArguments(arguments);
        assertEquals(testFileList, result);
    }

    @Test
    public void parseArgumentsOneValid() {
        when(programControlMocked.defaultDecipher(1)).thenReturn(testFileContentsOne);
        when(programControlMocked.defaultDecipher(2)).thenReturn(testFileContentsTwo);
        UserInterface ui = new UserInterface(programControlMocked);
        String[] arguments = new String[]{"1"};
        String result = ui.parseArguments(arguments);
        assertEquals(testFileContentsOne, result);
        arguments = new String[]{"2"};
        result = ui.parseArguments(arguments);
        assertEquals(testFileContentsTwo, result);
    }

    @Test
    public void parseArgumentsOneInvalid() {
        verifyNoInteractions(programControlMocked);
        UserInterface ui = new UserInterface(programControlMocked);
        String[] arguments = new String[]{"invalid"};
        assertThrows(IllegalArgumentException.class, () -> ui.parseArguments(arguments), "Should throw IllegalArgumentException as the first argument is not a number");
    }

    @Test
    public void parseArgumentsTwoValid() {
        when(programControlMocked.specialDecipher(anyInt(), anyString())).thenReturn(testFileContentsOne);
        UserInterface ui = new UserInterface(programControlMocked);
        String[] arguments = new String[]{"1", "key.txt"};
        String result = ui.parseArguments(arguments);
        assertEquals(testFileContentsOne, result);
    }

    @Test
    public void parseArgumentsTwoInvalid() {
        verifyNoInteractions(programControlMocked);
        UserInterface ui = new UserInterface(programControlMocked);
        String[] arguments_first = new String[]{"invalid", "key.txt"};
        assertThrows(IllegalArgumentException.class, () -> ui.parseArguments(arguments_first), "Should throw IllegalArgumentException as the first argument is not a number");
    }

    @Test
    public void parseArgumentsMany() {
        verifyNoInteractions(programControlMocked);
        UserInterface ui = new UserInterface(programControlMocked);
        String[] arguments = new String[]{"1", "key.txt", "too", "many", "arguments"};
        assertThrows(IllegalArgumentException.class, () -> ui.parseArguments(arguments), "Should throw IllegalArgumentException as there are too many arguments");
    }

    @Test
    public void parseArgumentsIntegrationInvalid() {
        UserInterface ui = new UserInterface(new ProgramControl());
        String[] arguments = new String[]{"Invalid arguments"};
        assertThrows(IllegalArgumentException.class, () -> ui.parseArguments(arguments), "Should throw IllegalArgumentException as the first argument is not a number.");
    }

    @Test
    public void parseArgumentsIntegrationValid() {
        UserInterface ui = new UserInterface(new ProgramControl());
        String[] arguments = new String[]{"1", "key.txt"};
        assertEquals(carnivoreText, ui.parseArguments(arguments), "Output should be the contents of carnivore.txt");
    }
}