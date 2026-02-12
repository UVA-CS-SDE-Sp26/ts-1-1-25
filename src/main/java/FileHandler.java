import javax.crypto.Cipher; // WARNING: not the API for this project. delete when Cipher class is created
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private final Cipher cipher; // assuming Team Member D's Cipher Class exists
    private final String dataFolder = "data";

    public FileHandler(Cipher cipher) {
        if (cipher == null) throw new IllegalArgumentException("cipher must not be null");
        this.cipher = cipher;
    }

    /** Case 1: Team Member A receives no arguments
      return string of filenames with numbers in data folder (format: "01 name.txt") */
    public String getListOfNumberedFiles() throws IOException {
        List<String> names = getSortedFiles(".txt");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < names.size(); i++) {
            sb.append(String.format("%02d %s", i + 1, names.get(i)))
                    .append(System.lineSeparator());
        }
        return sb.toString();
    }

    /**
     * Case 2: Team Member A receives additional arguments
     * sorts .cip files in data in alphabetical order
     * reads the .cip file based on the fileNum (ex. 01 -> .cip file in index 0)
     * gives Team Member B (Cipher) the content and cipherKey
     * returns the deciphered text
     */
    public String getDecipheredFile(String fileNum, String cipherKey) throws IOException {

        // the first argument fileNumber is used as the index number
        int idx = parseFileNumberToIndex(fileNum);

        // sort .cip files in alphabetical order
        List<String> cipNames = getSortedFiles(".cip");
        if (cipNames.isEmpty()) {
            throw new FileNotFoundException("No .cip files found in data directory.");
        }
        if (idx < 0 || idx >= cipNames.size()) {
            throw new IllegalArgumentException("Invalid file number: " + fileNum);
        }
        // match the .cip file and the index
        String cipFileName = cipNames.get(idx);
        // find that file in dataFolder and read its contents
        File cipFile = new File(dataFolder, cipFileName);
        String cipherText = readWholeFile(cipFile);
        return null; // when the cipher algorithm is complete, use that method to retrieve deciphered text
        // ex) cipher.decipher(cipherText, cipherKey);
    }

    // ----------------- internal helper methods -----------------

    private List<String> getSortedFiles(String fileType) throws IOException {
        File dir = new File(dataFolder);
        if (!dir.exists() || !dir.isDirectory()) {
            throw new IOException("data directory not found: " + dir.getPath());
        }

        File[] files = dir.listFiles();
        if (files == null) {
            throw new IOException("Failed to list files in: " + dir.getPath());
        }

        List<String> names = new ArrayList<>();
        for (File f : files) {
            if (f.isFile()) {
                String name = f.getName();
                if (name.toLowerCase().endsWith(fileType)) {
                    names.add(name);
                }
            }
        }

        names.sort(String.CASE_INSENSITIVE_ORDER);
        return names;
    }

    private int parseFileNumberToIndex(String fileNum) {
        if (fileNum == null || fileNum.isBlank()) {
            throw new IllegalArgumentException("File number must not be blank.");
        }
        try {
            int n = Integer.parseInt(fileNum); // "01"도 OK
            return n - 1; // 01 -> index 0
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("File number must be numeric: " + fileNum);
        }
    }

    private String readWholeFile(File file) throws IOException {
        return java.nio.file.Files.readString(file.toPath());
    }
}
