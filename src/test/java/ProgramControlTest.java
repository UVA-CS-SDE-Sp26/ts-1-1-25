import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedConstruction;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;





public class ProgramControlTest {

    private String testFileList;
    private String testFileContents;
    private String TEST_ERROR_DATA;
    private  String TEST_ERROR_FILE;

    private ProgramControl pc;
    private MockedConstruction<FileHandler> mockedFileHandler;
    FileHandler fh = mock(FileHandler.class);

    @BeforeEach
    public void setUp() throws IOException {
        testFileList = "01 filea.txt \n 02 fileb.txt \n 03 filec.txt";
        testFileContents = "This is a test file. Use it as a placeholder.";
        TEST_ERROR_DATA = "Error: There is no data to display.";
        TEST_ERROR_FILE = "Error: Invalid File.";
    }

    @AfterEach
    public void tearDown() {
        mockedFileHandler.close();
    }

    @Test
    public void listFilesTestTypical() {

        mockedFileHandler = mockConstruction(FileHandler.class, (fh, ctx) -> {
            when(fh.getListOfNumberedFiles()).thenReturn(testFileList);
        } );

        pc = new ProgramControl();
        String result = pc.listFiles();
        assertEquals(testFileList, result);
    }

    @Test
    public void listFilesTestNoDirectoryFound() {

        mockedFileHandler = mockConstruction(FileHandler.class, (fh, ctx) -> {
            when(fh.getListOfNumberedFiles()).thenThrow( new IOException() );
        } ) ;
        ProgramControl pc = new ProgramControl();
        String result = pc.listFiles();
        assertEquals(TEST_ERROR_DATA, result);
    }

    @Test
    public void TestDefaultDecipherTypical() {
        mockedFileHandler = mockConstruction(FileHandler.class, (fh, ctx) -> {
            when(fh.getDecipheredFile("1", null)).thenReturn( testFileContents );
        } ) ;
        ProgramControl pc = new ProgramControl();
        String result = pc.defaultDecipher(01);
        assertEquals(testFileContents, result);
    }

    @Test
    public void TestDefaultDecipherInvalidFile() {

        mockedFileHandler = mockConstruction(FileHandler.class, (fh, ctx) -> {
            when(fh.getDecipheredFile("14", null)).thenThrow( new IOException() );
        } ) ;
        ProgramControl pc = new ProgramControl();
        String result = pc.defaultDecipher(14);
        assertEquals(TEST_ERROR_FILE, result);
    }

    @Test
    public void TestSpecialDecipherTypical() {

        mockedFileHandler = mockConstruction(FileHandler.class, (fh, ctx) -> {
            when(fh.getDecipheredFile("1", "someKey")).thenReturn( testFileContents );
        });

        ProgramControl pc = new ProgramControl();
        String result = pc.specialDecipher(1, "someKey");
        assertEquals(testFileContents, result);
    }

    @Test
    public void TestSpecialDecipherInvalidFile() {

        mockedFileHandler = mockConstruction(FileHandler.class, (fh, ctx) -> {
            when(fh.getDecipheredFile("14", "someValidKey")).thenThrow( new IOException() );
        } ) ;

        ProgramControl pc = new ProgramControl();
        String result = pc.specialDecipher(14, "someValidKey");
        assertEquals(TEST_ERROR_FILE, result);

    }

    @Test
    public void TestSpecialDecipherInvalidKey() {

        mockedFileHandler = mockConstruction(FileHandler.class, (fh, ctx) -> {
            when(fh.getDecipheredFile("1", "someInvalidKey")).thenThrow( new IllegalArgumentException() );
        } ) ;

        ProgramControl pc = new ProgramControl();
        assertThrows(IllegalArgumentException.class, () -> pc.specialDecipher(1, "someInvalidKey"));
    }


}
