import java.io.IOException;

public class ProgramControl {
    private static final String ERROR_FILE = "Error: Invalid File.";
    private static final String ERROR_DATA = "Error: There is no data to display.";
    private final FileHandler fileHandler;

    public ProgramControl() {
        this.fileHandler = new FileHandler("data", "ciphers");
    }

    public String listFiles() {
        try {
            return fileHandler.getListOfNumberedFiles();
        } catch (IOException e) {
            // data folder missing, unreadable, etc.
            return ERROR_DATA;
        }
    }

    public String defaultDecipher(int fileNumber) {
        try {
            return fileHandler.getDecipheredFile(String.valueOf(fileNumber),null); // default decipher key?
        } catch (IllegalArgumentException e) {
            // With a valid default key, IllegalArgumentException here should mean "bad file number"
            throw new IllegalArgumentException(e);
        } catch (IOException e) {
            return ERROR_FILE;
        }
    }

    public String specialDecipher(int fileNumber, String key) {
        try {
            return fileHandler.getDecipheredFile(String.valueOf(fileNumber), key);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
            } catch (IOException e) {
            return ERROR_FILE;
        }
    }

}