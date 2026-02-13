import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {

    @TempDir
    Path tempRoot;

    private Path dataDir;
    private Path ciphersDir;
    private FileHandler fh;

    @BeforeEach
    void setUp() throws IOException {
        dataDir = tempRoot.resolve("data");
        ciphersDir = tempRoot.resolve("ciphers");
        Files.createDirectories(dataDir);
        Files.createDirectories(ciphersDir);

        fh = new FileHandler(dataDir.toString(), ciphersDir.toString());
    }

    @Test
    void getListOfNumberedFiles_listsTxtInOrderAlphabetically() throws IOException {
        Files.writeString(dataDir.resolve("b.txt"), "B");
        Files.writeString(dataDir.resolve("a.txt"), "A");
        Files.writeString(dataDir.resolve("ignore.cip"), "X");

        String result = fh.getListOfNumberedFiles();
        String ls = System.lineSeparator();

        assertEquals("01 a.txt" + ls + "02 b.txt" + ls, result);
    }

    @Test
    void getDecipheredFile_usesDefaultKey() throws IOException {
        Files.writeString(dataDir.resolve("a.cip"), "bca");
        Files.writeString(ciphersDir.resolve("key.txt"), "abc\nbca\n");

        String result = fh.getDecipheredFile("01", null);
        assertEquals("abc", result);
    }

    @Test
    void getDecipheredFile_usesAlternateKey() throws IOException {
        Files.writeString(dataDir.resolve("a.cip"), "bca");
        Files.writeString(ciphersDir.resolve("altkey.txt"), "abc\nbca\n");

        String result = fh.getDecipheredFile("01", "altkey.txt");
        assertEquals("abc", result);
    }

    @Test
    void getDecipheredFile_NoCipFiles() throws IOException {
        Files.writeString(ciphersDir.resolve("key.txt"), "abc\nbca\n");

        assertThrows(FileNotFoundException.class, () -> fh.getDecipheredFile("01", null));
    }

    @Test
    void getDecipheredFile_FileNumberOutOfRange() throws IOException {
        Files.writeString(dataDir.resolve("a.cip"), "bca");
        Files.writeString(ciphersDir.resolve("key.txt"), "abc\nbca\n");

        assertThrows(IllegalArgumentException.class, () -> fh.getDecipheredFile("99", null));
    }

    @Test
    void getDecipheredFile_KeyFileMissing() throws IOException {
        Files.writeString(dataDir.resolve("a.cip"), "bca");

        assertThrows(FileNotFoundException.class, () -> fh.getDecipheredFile("01", "missing.txt"));
    }

    @Test
    void getDecipheredFile_KeyFileHasLessThanTwoLines() throws IOException {
        Files.writeString(dataDir.resolve("a.cip"), "bca");
        Files.writeString(ciphersDir.resolve("key.txt"), "onlyOneLine\n");

        assertThrows(IllegalArgumentException.class, () -> fh.getDecipheredFile("01", null));
    }
}
