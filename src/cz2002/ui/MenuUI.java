package cz2002.ui;

import cz2002.entity.MenuItem;
import cz2002.util.ScannerUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * User Interface for Managing Menu Items
 * 
 * @author Benjamin Cheong
 * @version 1.0
 * @since 2021-11-08
 *
 */
public abstract class MenuUI {
    /**
     * Scanner
     */
    protected Scanner sc;
    /**
     * List of Menu Items
     */
    protected List<MenuItem> menu;

    /**
     * String of the formatting for headers
     */
    private String format = "";
    /**
     * String array of headers to be printed
     */
    private String[] headers = {};

    /**
     * Creates new User Interface for managing Menu Items
     * 
     * @param scanner        Scanner object
     * @param restaurantMenu List of Menu Items
     */
    public MenuUI(Scanner scanner, List<? extends MenuItem> restaurantMenu) {
        sc = scanner;
        menu = (List<MenuItem>) restaurantMenu;
    }

    /**
     * Sets format and headers attribute
     * 
     * @param format  New format
     * @param headers New headers
     */
    public void setFormatting(String format, String... headers) {
        this.format = format;
        this.headers = headers;
    }

    /**
     * User Interface for Creating Menu Item
     */
    abstract void createMenuItem();

    /**
     * User Interface for Editing Menu Item
     * 
     * @param item Menu Item to be edited
     */
    abstract void editMenuItem(MenuItem item);

    /**
     * User Interface for Managing Menu Item Invokes corresponding User Interfaces
     * upon selection
     * 
     * @param title Title that indicates whether managing individual food dish or
     *              set package
     */
    public void run(String title) {
        printMenu();
        int option = ScannerUtil.Prompt(sc, "Create " + title, "Edit " + title, "Remove " + title);

        switch (option) {
        case 1 -> createMenuItem();
        case 2 -> selectEditMenuItem();
        case 3 -> selectRemoveMenuItem();
        }

    }

    /**
     * User Interface for selecting which menu item to edit Invokes user interface
     * for editing selected menu item
     */
    protected void selectEditMenuItem() {
        while (true) {
            String[] menuItems = menu.stream().map(item -> item.toString()).toArray(String[]::new);
            String[] optionSelection = Stream.concat(Arrays.stream(menuItems), Arrays.stream(new String[] { "Back " }))
                    .toArray(String[]::new);
            String prompt = "Please select item to edit\n " + String.format(format + "\n", headers) + "=".repeat(55)
                    + "";

            int option = ScannerUtil.CustomPrompt(sc, prompt, optionSelection);

            if (option <= menuItems.length) {
                editMenuItem(menu.get(option - 1));
            } else {
                break;
            }
        }
    }

    /**
     * User Interface for selecting which menu item to remove Removes menu item upon
     * selection
     */
    protected void selectRemoveMenuItem() {
        while (true) {
            String[] menuItems = menu.stream().map(item -> item.toString()).toArray(String[]::new);
            String[] optionSelection = Stream.concat(Arrays.stream(menuItems), Arrays.stream(new String[] { "Back " }))
                    .toArray(String[]::new);
            String prompt = "Please select item to remove\n " + String.format(format + "\n", headers) + "=".repeat(55)
                    + "";

            int option = ScannerUtil.CustomPrompt(sc, prompt, optionSelection);

            if (option <= menuItems.length) {
                MenuItem item = menu.get(option - 1);
                item.toggleEnable();
            } else {
                break;
            }
        }
    }

    /**
     * Prints all menu items in menu
     */
    protected void printMenu() {
        System.out.println("\nContents of Restaurant Menu Dishes: ");
        System.out.printf(format + "\n", headers);
        System.out.println("=".repeat(55));

        if (!menu.isEmpty()) {
            int i = 1;
            for (MenuItem item : menu) {
                System.out.printf("%d) %s\n", i++, item.toString());
            }
        } else {
            System.out.println("-- No Dishes --");
        }
    }
}