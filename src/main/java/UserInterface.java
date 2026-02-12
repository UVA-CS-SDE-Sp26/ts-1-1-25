public class UserInterface {

    public UserInterface() {}

    public String parseArguments(String[] arguments) throws IllegalArgumentException {
        // we can accept a maximum of 2 arguments
        if (arguments.length > 2) {
            throw new IllegalArgumentException("Too many arguments");
        }
        // no arguments means we print out a file list
        if (arguments.length == 0) {
            String fileList = "";
            // stub: ask ProgramControl for file list
            return fileList;
        }

        String cipherFile = "key.txt";
        // if we received two arguments, we need to use the second as the cipherFile instead of the default
        if (arguments.length == 2) {
            // verify that this is actually a file?
            cipherFile = arguments[1];
        }
        // try to get the file contents
        try {
            int fileNumber = Integer.parseInt(arguments[0]);
            String fileContents = "";
            // stub: call ProgramControl with fileNumber and cypherFile to get file contents
            return fileContents;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("First argument should be a number");
        }
    }
}
