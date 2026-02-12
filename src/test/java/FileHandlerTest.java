import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {

    private FileHandler fh = new FileHandler();

    @Test
    void readDataFile_NullOrBlank() {
        assertThrows(IllegalArgumentException.class, () -> fh.readDataFile(null));
        assertThrows(IllegalArgumentException.class, () -> fh.readDataFile(""));
        assertThrows(IllegalArgumentException.class, () -> fh.readDataFile("   "));
    }

    @Test
    void readDataFile_EmptyFiles() {
        assertThrows(FileNotFoundException.class,
                () -> fh.readDataFile("data/missing.txt"));
    }

    @Test
    void readDataFile_FilesAreValid() throws IOException {
        String ls = System.lineSeparator();
        String result = fh.readDataFile("data/sample.txt");
        assertEquals("hello" + ls + "world" + ls, result);
    }
}
