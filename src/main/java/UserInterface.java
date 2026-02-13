public class UserInterface {

    private ProgramControl programControl;

    public UserInterface(ProgramControl programControl) {
        this.programControl = programControl;
    }

    public String parseArguments(String[] arguments) throws IllegalArgumentException {
        // we can accept a maximum of 2 arguments
        if (arguments.length > 2) {
            throw new IllegalArgumentException("Too many arguments");
        }
        // no arguments means we print out a file list
        if (arguments.length == 0) {
            return this.programControl.listFiles();
        }

        // try to get the file contents
        try {
            int fileNumber = Integer.parseInt(arguments[0]);
            // if we received two arguments, we need to use the second as the cipherFile instead of the default
            if (arguments.length == 2) {
                return this.programControl.specialDecipher(fileNumber, arguments[1]);
            } else {
                return this.programControl.defaultDecipher(fileNumber);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("First argument should be a number");
        }
    }
}
