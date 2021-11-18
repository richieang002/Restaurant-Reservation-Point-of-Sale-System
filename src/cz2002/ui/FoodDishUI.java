package cz2002.ui;

import cz2002.entity.FoodDish;
import cz2002.entity.MenuItem;
import cz2002.util.ScannerUtil;

import java.util.*;

/**
 * User Interface for Managing individual Food Dishes
 * 
 * @author Benjamin Cheong
 * @version 1.0
 * @since 2021-11-08
 *
 */
public class FoodDishUI extends MenuUI {
	/**
	 * Creates new User Interface for managing individual Food Dishes
	 * @param scanner Scanner object
	 * @param restaurantMenu ArrayList of Food Dishes
	 */
	public FoodDishUI(Scanner scanner, ArrayList<FoodDish> restaurantMenu) {
        super(scanner, restaurantMenu);
        setFormatting("   %-5s %-20s %-15s %s", "Status", "Name", "Type", "Price ($S)");
    }
	
	/**
	 * User Interface for Creating individual Food Dish
	 */
    @Override
    void createMenuItem() {
        FoodDish.menuItemType type = FoodDish.menuItemType.MAIN_COURSE;
        String name;
        String description;
        double price;

        int typeSelection = ScannerUtil.CustomPrompt(sc, "Please enter type of dish", "Main Course", "Drinks", "Dessert");
        switch (typeSelection) {
            case 1 -> type = FoodDish.menuItemType.MAIN_COURSE;
            case 2 -> type = FoodDish.menuItemType.DRINKS;
            case 3 -> type = FoodDish.menuItemType.DESSERT;
        }

        System.out.println();
        sc.nextLine();

        System.out.print("Please enter desired Menu Item Name: ");
        name = sc.nextLine();

        System.out.printf("Please enter description for '%s': ", name);
        description = sc.nextLine();

        while(true) {
            try {
                System.out.printf("Please enter price for '%s': $", name);
                price = sc.nextDouble();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please enter valid price.");
                sc.next();
            }
        }

        System.out.println("You are currently adding the following dish into the restaurant menu..");
        FoodDish item = new FoodDish(name, description, price, type);
        printItemDetails(item);
        menu.add(item);

        System.out.println("Press enter to return to main menu..");
        sc.nextLine();
    }
    
    /**
     * User Interface for Editing individual Food Dish
     * @param item Food Dish to be edited
     */
    @Override
    void editMenuItem(MenuItem item) {

        FoodDish.menuItemType type = FoodDish.menuItemType.MAIN_COURSE;
        String name;
        String description;
        double price;

        while(true) {
            System.out.println("Item you have currently selected:");
            printItemDetails((FoodDish) item);
            int options = ScannerUtil.Prompt(sc,"Edit Name", "Edit Type", "Edit Description", "Edit Price", "Back");

            System.out.println();
            sc.nextLine();

            switch (options) {
                case 1:
                    System.out.printf("Please enter new Name: ");
                    name = sc.nextLine();

                    item.setName(name);
                    break;
                case 2:
                    int typeSelection = ScannerUtil.CustomPrompt(sc, "Please enter type of dish", "Main Course", "Drinks", "Dessert");
                    switch (typeSelection) {
                        case 1 -> type = FoodDish.menuItemType.MAIN_COURSE;
                        case 2 -> type = FoodDish.menuItemType.DRINKS;
                        case 3 -> type = FoodDish.menuItemType.DESSERT;
                    }

                    ((FoodDish) item).setType(type);

                    System.out.println();
                    sc.nextLine();
                    break;
                case 3:
                    System.out.printf("Please enter new Description: ");
                    description = sc.nextLine();

                    item.setDescription(description);
                    break;
                case 4:
                    while(true) {
                        try {
                            System.out.printf("Please enter new price: $");
                            price = sc.nextDouble();

                            item.setPrice(price);
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Please enter valid price.");
                            sc.next();
                        }
                    }
                    break;
                case 5:
                    return;
            }
        }
    }
    
    /**
     * Prints details of Food Dish
     * @param item Food Dish to be printed
     */
    private void printItemDetails(FoodDish item) {
        String typeString = "";
        switch (item.getType()) {
            case MAIN_COURSE    -> typeString = "Main Course";
            case DESSERT         -> typeString = "Dessert";
            case DRINKS         -> typeString = "Drinks";
        }

        System.out.printf("%-13s: %s\n", "Name", item.getName());
        System.out.printf("%-13s: %s\n", "Type", typeString);
        System.out.printf("%-13s: %s\n", "Description", item.getDescription());
        System.out.printf("%-13s: $%.2f\n", "Price", item.getPrice());
    }

}