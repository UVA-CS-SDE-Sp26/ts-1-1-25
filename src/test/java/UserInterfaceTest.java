import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserInterfaceTest {

    String testFileList = "1 file1\n2 file 2\n3 file 3";
    String testFileContentsOne = "contents of a file";
    String testFileContentsTwo = "contents of another file";

    @Mock
    ProgramControl programControlMocked;

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
}