
public class ProgramControl {
    int argsNum;
    //stub: instantiate fileHandler object.
    String errorMessageFile = "Error: Invalid File.";
    String errorMessageKey = "Error: Invalid Decipher Key.";


    //stub: create constructor

    public static String run(String[] args) {
        return switch (args.length) {
            case 0 -> listFiles();
            case 1 -> defaultDecipher(args[0]);
            case 2 -> specialDecipher(args[0], args[1]);
            default -> "Error: Expected 0, 1, or 2 arguments."

        }

    }

    private String listFiles{
        String fileList =// stub: ask FileHandler for list of files
        // NOTE TO SELF: FileHandler may return String[]; can reformat to String
        return filelist;

    }

    private  String defaultDecipher(String fileNum){
            try {
                String fileContents = // stub: provide FileHandler with fileNum and default key, request deciphered file Contents
            } catch () { //stub: check what exception FileHandler throws when filenumber invalid
                return errorMessageFile;
            }


        return fileContents;
    }

    private String specialDecipher(String fileNum, String key){
        try {
            String fileContents = // stub: provide FileHandler with fileNum and special key, request deciphered file Contents

        } catch () { // catch invalid file error first
            return errorMessageFile;
        } catch () { // then catch invalid key error
            return errorMessageKey;
        }

    }




}