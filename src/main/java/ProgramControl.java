import javax.crypto.Cipher;
import java.io.IOException;

public class ProgramControl {
    private static final String ERROR_FILE = "Error: Invalid File.";
    private static final String ERROR_KEY = "Error: Invalid Decipher Key.";

    private final FileHandler fileHandler;

    public ProgramControl() {
        this.fileHandler = new FileHandler("data", "ciphers");
    }

    public String listFiles() {
        try {
            return fileHandler.getListOfNumberedFiles();
        } catch (IOException e) {
            // data folder missing, unreadable, etc.
            return e.getMessage();
        }
    }

    public String defaultDecipher(int fileNumber) {
        try {
            return fileHandler.getDecipheredFile(String.valueOf(fileNumber),null); // default decipher key?
        } catch (IllegalArgumentException e) {
            // With a valid default key, IllegalArgumentException here should mean "bad file number"
            return ERROR_FILE;
        } catch (IOException e) {
            return ERROR_FILE;
        }
    }

    public String specialDecipher(int fileNumber, String key) {
        try {
            return fileHandler.getDecipheredFile(String.valueOf(fileNumber), key);
        } catch (IllegalArgumentException e) { // FileHandler throws Illegal Argumetn exception for both invalid key and filenum
            String msg = (e.getMessage() == null) ? "" : e.getMessage().toLowerCase();
            if (msg.contains("file number") || msg.contains("invalid file number")) {
                return ERROR_FILE;
            }
            return ERROR_KEY;
        } catch (IOException e) {
            return ERROR_FILE;
        }
    }
}




}