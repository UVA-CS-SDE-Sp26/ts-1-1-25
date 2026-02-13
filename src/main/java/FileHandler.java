import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private String dataFolder = "data";
    private String ciphersFolder = "ciphers";
    private final String defaultKeyFile = "key.txt";

    public FileHandler(String dataFolder, String ciphersFolder) {
        if (dataFolder == null || dataFolder.isBlank()) throw new IllegalArgumentException("dataFolder must not be blank");
        if (ciphersFolder == null || ciphersFolder.isBlank()) throw new IllegalArgumentException("ciphersFolder must not be blank");
        this.dataFolder = dataFolder;
        this.ciphersFolder = ciphersFolder;
    }

    /** Case 1: no args -> list .txt files numbered */
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
     * Case 2: args present -> decipher .cip file by number using key file
     * @param fileNum  must be a number (ex. "01")
     * @param cypherKey  if null/blank, use default key.txt
     *                   else, use the name of file (ex. "altkey.txt")
     */
    public String getDecipheredFile(String fileNum, String cypherKey) throws IOException {

        int idx = parseFileNumberToIndex(fileNum);
        // 1) sort .cip files and map fileNum
        List<String> cipNames = getSortedFiles(".cip");
        if (cipNames.isEmpty()) {
            throw new FileNotFoundException("No .cip files found in data directory.");
        }
        if (idx < 0 || idx >= cipNames.size()) {
            throw new IllegalArgumentException("Invalid file number: " + fileNum);
        }

        String cipFileName = cipNames.get(idx);
        File cipFile = new File(dataFolder, cipFileName);
        String cipherText = readWholeFile(cipFile);

        File keyFile = new File(ciphersFolder, defaultKeyFile);

        String[] keys = (cypherKey == null || cypherKey.isBlank())
                ? readTwoLineKeyFile(keyFile)
                : cypherKey.split("\\R");

        if (keys.length < 2) {
            throw new IllegalArgumentException("Cipher key must contain at least two lines.");
        }

        String textKey = keys[0];
        String cipheredTextKey = keys[1];
        try {
            Cipher cipher = new Cipher(textKey, cipheredTextKey);
            return cipher.DecipherString(cipherText);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid decipher key", e);
        }
    }

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
            int n = Integer.parseInt(fileNum);
            return n - 1; // 01 -> index 0
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("File number must be numeric: " + fileNum);
        }
    }

    private String readWholeFile(File file) throws IOException {
        return Files.readString(file.toPath());
    }

    // Are key files always two lines?
    private String[] readTwoLineKeyFile(File keyFile) throws IOException {
        if (!keyFile.exists() || !keyFile.isFile()) {
            throw new FileNotFoundException("Key file not found: " + keyFile.getPath());
        }

        List<String> lines = Files.readAllLines(keyFile.toPath());
        String textKey = lines.get(0);
        String cipheredTextKey = lines.get(1);

        return new String[]{textKey, cipheredTextKey};
    }
}