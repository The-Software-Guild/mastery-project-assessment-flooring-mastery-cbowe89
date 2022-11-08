package FlooringMastery.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class UserIOConsoleImpl implements UserIO {
    Scanner scanner;

    /**
     * Constructor for UserIOConsoleImpl
     */
    public UserIOConsoleImpl() {
        scanner = new Scanner(System.in);
    }

    /**
     * Takes in a message to display to the console
     * @param message String of info to display to the user
     */
    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public void printf(String message, LocalDate date) {
        System.out.printf(message, date);
    }

    @Override
    public void printf(String message, int min, int max) {
        System.out.printf(message, min, max);
    }

    @Override
    public void printf(String message, BigDecimal min) {
        System.out.printf(message, min);
    }

    @Override
    public void printf(String message, BigDecimal min, BigDecimal max) {
        System.out.printf(message, min, max);
    }

    /**
     * Takes in a prompt to display to the console, waits
     * for an answer (String) from the user to return
     * @param prompt String, explains what info is wanted
     *               from the user
     * @return Answer to the prompt as a String
     */
    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    /**
     * Simple Method - Takes in a prompt to display to the
     * console, continually re-prompts the user with prompt
     * until they enter an integer to be returned
     * as the answer to the prompt.
     * @param prompt String, explains what info is wanted from the user
     * @return Answer to the prompt as an integer
     */
    @Override
    public int readInt(String prompt) {
        boolean invalidInput = true;
        int num = 0;
        while (invalidInput) {
            try {
                // Print prompt, get input, try to parse
                num = Integer.parseInt(readString(prompt));
                invalidInput = false;
            } catch (NumberFormatException e) {
                this.print("Input error. Please enter an integer value.");
            }
        }
        return num;
    }

    /**
     * Complex method - takes in a prompt to display to the
     * console, continually re-prompts the user with prompt until
     * they enter an integer within the specified min/max range
     * to be returned as the answer to the prompt.
     * @param prompt String, explains what info is wanted from the user
     * @param min Minimum acceptable value for return
     * @param max Maximum acceptable value for return
     * @return An integer value as an answer to the prompt within
     * the min/max range
     */
    @Override
    public int readInt(String prompt, int min, int max) {
        boolean invalidInput = true;
        int num = 0;

        while (invalidInput) {
            num = readInt(prompt);
            if (num >= min && num <= max)
                invalidInput = false;
            else
                printf("Selection must be between %d and %d.\n", min, max);
        }

        return num;
    }

    /**
     * Simple Method - Takes in a prompt to display to the
     * console, continually re-prompts the user with prompt
     * until they enter a BigDecimal to be returned as an answer
     * to the prompt.
     * @param prompt String, explains what info is wanted from the user
     * @return Answer to the prompt as a BigDecimal
     */
    @Override
    public BigDecimal readBigDecimal(String prompt) {
        boolean invalidInput = true;
        BigDecimal num = new BigDecimal("0.0");
        while (invalidInput) {
            try {
                num = new BigDecimal(readString(prompt));
                invalidInput = false;
            } catch (NumberFormatException e) {
                this.print("Input error. Please enter a number.");
            }
        }
        return num;
    }

    @Override
    public BigDecimal readBigDecimal(String prompt, BigDecimal min) {
        boolean invalidInput = true;
        BigDecimal num = null;

        while (invalidInput) {
            num = readBigDecimal(prompt);
            if (num.doubleValue() >= min.doubleValue())
                invalidInput = false;
            else
                printf("Selection must be greater than %d\n", min);
        }

        return num;
    }

    /**
     * Complex method - takes in a prompt to display to the
     * console, continually re-prompts the user with prompt until
     * they enter a BigDecimal within the specified min/max range
     * to be returned as the answer to the prompt.
     * @param prompt String, explains what info is wanted from the user
     * @param min Minimum acceptable value for return
     * @param max Maximum acceptable value for return
     * @return A BigDecimal value as an answer to the prompt within
     * the min/max range
     */
    @Override
    public BigDecimal readBigDecimal(String prompt, BigDecimal min, BigDecimal max) {
        boolean invalidInput = true;
        BigDecimal num = null;

        while (invalidInput) {
            num = readBigDecimal(prompt);
            if (num.doubleValue() >= min.doubleValue()
                    && num.doubleValue() <= max.doubleValue())
                invalidInput = false;
            else
                printf("Selection must be between %d and %d.\n", min, max);
        }

        return num;
    }

    @Override
    public LocalDate readDate(String prompt) {
        boolean invalidInput = true;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMddyyyy");
        LocalDate dateEntered = null;

        while (invalidInput) {
            try {
                String stringValue = this.readString(prompt);
                dateEntered = LocalDate.parse(stringValue, dateTimeFormatter);
                invalidInput = false;
            } catch (DateTimeParseException e) {
                this.print("Input error. Please enter a date in the format of MMddyyyy.");
            }
        }

        return dateEntered;
    }

    @Override
    public LocalDate readDate(String prompt, LocalDate minDate) {
        boolean invalidInput = true;
        LocalDate dateEntered = null;

        while (invalidInput) {
            try {
                dateEntered = readDate(prompt);
                if (dateEntered.isAfter(minDate))
                    invalidInput = false;
                else
                    printf("Date must be in the future. (After %s)", minDate);
            } catch (DateTimeParseException e) {
                this.print("Input error. Please enter a date in the format of MMddyyyy.");
            }
        }

        return dateEntered;
    }
}
