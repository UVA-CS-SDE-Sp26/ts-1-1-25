import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserInterfaceTest {

    String testFileList = "1 file1\n2 file 2\n3 file 3";
    String testFileContents = "contents of a file";

    @Test
    public void parseArgumentsZero() {
        UserInterface ui = new UserInterface();
        String[] arguments = new String[]{};
        String result = ui.parseArguments(arguments);
        assertEquals(testFileList, result);
    }

    @Test
    public void parseArgumentsOneValid() {
        UserInterface ui = new UserInterface();
        String[] arguments = new String[]{"1"};
        String result = ui.parseArguments(arguments);
        assertEquals(testFileContents, result);
    }

    @Test
    public void parseArgumentsOneInvalid() {
        UserInterface ui = new UserInterface();
        String[] arguments = new String[]{"invalid"};
        assertThrows(IllegalArgumentException.class, () -> ui.parseArguments(arguments), "Should throw IllegalArgumentException as the first argument is not a number");
    }

    @Test
    public void parseArgumentsTwoValid() {
        UserInterface ui = new UserInterface();
        String[] arguments = new String[]{"1", "key.txt"};
        String result = ui.parseArguments(arguments);
        assertEquals(testFileContents, result);
    }

    @Test
    public void parseArgumentsTwoInvalid() {
        UserInterface ui = new UserInterface();
        String[] arguments_first = new String[]{"invalid", "key.txt"};
        assertThrows(IllegalArgumentException.class, () -> ui.parseArguments(arguments_first), "Should throw IllegalArgumentException as the first argument is not a number");
        //String[] arguments_second = new String[]{"1", "not a cipher"};
        //assertThrows(IllegalArgumentException.class, () -> ui.parseArguments(arguments_second), "Should throw IllegalArgumentException as the second argument is not a cypher");
    }

    @Test
    public void parseArgumentsMany() {
        UserInterface ui = new UserInterface();
        String[] arguments = new String[]{"1", "key.txt", "too", "many", "arguments"};
        assertThrows(IllegalArgumentException.class, () -> ui.parseArguments(arguments), "Should throw IllegalArgumentException as there are too many arguments");
    }
}