/**
 * Commmand Line Utility
 */
public class TopSecret {
    public static void main(String[] args) {
        UserInterface userInterface = new UserInterface(new ProgramControl());
        try {
            String output = userInterface.parseArguments(args);
            System.out.println(output);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
