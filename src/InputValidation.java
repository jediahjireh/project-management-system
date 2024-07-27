
// import Java packages
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * class for validating various types of user inputs
 */
public class InputValidation {

    /**
     * create new scanner object to use throughout the
     * InputValidation class to read user input for validation
     * 
     * scanner initialised with the standard input stream to
     * facilitate interaction with the user via the console
     */
    private static Scanner scanner = new Scanner(System.in);

    /**
     * method to validate string data entries
     *
     * @param prompt the prompt message to display to the user
     * @return the validated string input
     */
    public static String validateStringInput(String prompt) {
        // declare input variable
        String strInput;

        // repeat prompt if input is null
        do {
            // print out prompt
            System.out.print(prompt);
            // use scanner to read user input
            strInput = scanner.nextLine().trim();

            // check if entry is null
            if (strInput.isEmpty()) {
                // notify user
                System.out.println("Please provide an input entry.");
            }
        } while (strInput.isEmpty());

        // return validated input
        return strInput;
    }

    /**
     * method to validate double data entries
     *
     * @param prompt the prompt message to display to the user
     * @return the validated double input
     */
    public static double validateDoubleInput(String prompt) {
        // declare variable
        String input;
        // initialise variable
        double doubleInput = 0;

        // repeat prompt until a valid number is entered (break encountered)
        do {
            // print out prompt
            System.out.print(prompt);
            // read user input and remove whitespace
            input = scanner.nextLine().trim();

            // try-catch block
            try {
                // check if input can be cast to a double data type
                doubleInput = Double.parseDouble(input);
                // exit do-while loop if casting succeeds
                break;
            } catch (NumberFormatException e) {
                // notify user
                System.out.println("Invalid input! Please enter a valid number.");
            }
        } while (true);

        // return validated input of a double data type
        return doubleInput;
    }

    /**
     * method to validate integer data entries
     *
     * @param prompt the prompt message to display to the user
     * @return the validated integer input
     */
    public static int validateIntegerInput(String prompt) {
        // declare variable
        String intInput;

        // repeat prompt until user inputs a positive integer
        do {
            // print out prompt
            System.out.print(prompt);
            // read user input and remove whitespace
            intInput = scanner.nextLine().trim();

            // check if input matches the pattern for a positive integer
            if (!intInput.matches("\\d+")) {
                // notify user about invalid input
                System.out.println("Invalid input! Please enter a valid integer.");
            }
        } while (!intInput.matches("\\d+"));

        // return validated input
        return Integer.parseInt(intInput);
    }

    /**
     * method to validate string-integer data entries
     * e.g. contact/ID numbers
     *
     * @param prompt the prompt message to display to the user
     * @return the validated string-integer input
     */
    public static String validateStringNumberInput(String prompt) {
        // declare variable
        String input;

        // repeat prompt until user inputs a positive integer
        do {
            // print out prompt
            System.out.print(prompt);
            // read user input and remove whitespace
            input = scanner.nextLine().trim();

            // check if input matches the pattern for a positive integer
            if (!input.matches("\\d+")) {
                // notify user about invalid input
                System.out.println("Invalid input! Please enter a valid integer.");
            }
        } while (!input.matches("\\d+"));

        // return validated input
        return input;
    }

    /**
     * method to validate date data entries
     *
     * @param prompt the prompt message to display to the user
     * @return the validated date input
     */
    public static LocalDate validateDateInput(String prompt) {
        // initialise variable
        LocalDate dateInput = null;

        // repeat prompt until user inputs a string
        do {
            System.out.print(prompt);
            // get user input
            String input = scanner.nextLine().trim();

            // try-catch block
            try {
                // check if input can be cast into a LocalDate object
                dateInput = LocalDate.parse(input);
                // exit loop if parsing successful
                break;
            } catch (DateTimeParseException e) {
                // notify user about invalid input
                System.out.println("Invalid input! Please enter a valid date in YYYY-MM-DD format.");
            }
        } while (true);

        // return validated input
        return dateInput;
    }

    /**
     * method to validate boolean data entries
     *
     * @param prompt the prompt message to display to the user
     * @return the validated boolean input
     */
    public static boolean validateBooleanInput(String prompt) {
        // initialise variable
        boolean booleanInput = false;

        // repeat prompt until user inputs a boolean
        do {
            System.out.print(prompt);
            // get user input and covert to lowercase for case-insensitivity
            String input = scanner.nextLine().trim().toLowerCase();

            // if user inputs true
            if (input.equals("true") || input.equals("t")) {
                booleanInput = true;
                // exit loop
                break;
            }
            // if user very well
            else if (input.equals("false") || input.equals("f")) {
                booleanInput = false;
                // exit loop
                break;
            } else {
                // notify user about invalid input
                System.out.println("Invalid input! Please enter 'true' (or 't') or 'false' (or 'f').");
            }
        } while (true);

        // return validated input
        return booleanInput;
    }
}