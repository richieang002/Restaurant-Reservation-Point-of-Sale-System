package cz2002.util;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Utility Class for Scanner object
 *     @author Abdul Siddiq
 *     @version 1.0
 *     @since 2021-11-08
 */
public class ScannerUtil {

    /**
     * Prompts user to select an index (starting from 1) from a list of given strings
     * This function will prompt user to enter again if the entered index is not within the list's range or if value is invalid
     * @param scanner Scanner object
     * @param prompt Prompt that is printed out
     * @param options List of string values that is printed out for user to select
     * @return Selected option from the given list
     */
    public static int CustomPrompt(Scanner scanner, String prompt, String... options) {
        System.out.println();
        System.out.println(prompt);
        for(int i = 1; i <= options.length; i++)
            System.out.printf("%d) %s\n", i, options[i-1]);
        System.out.print("> ");

        try {
            int option = scanner.nextInt();
            if(option > options.length)
                throw new Exception();

            return option;
        } catch (Exception e) {
            // Clear buffer if there's an error
            if(e instanceof InputMismatchException)
                scanner.next();

            System.out.println("You have selected an invalid option..");
            return CustomPrompt(scanner, prompt, options);
        }
    }

    /**
     * Prompt uses to select from a list of given options
     * Checks for invalid input and when user enter value that's not within the range of given values
     * @param scanner Scanner Object
     * @param options List of options for user to select from
     * @return Option that user has picked
     */
    public static int Prompt(Scanner scanner, String... options) {
        return CustomPrompt(scanner, "Please select one of the following options: ", options);
    }
}