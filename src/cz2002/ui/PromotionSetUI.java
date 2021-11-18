package cz2002.ui;

import cz2002.entity.FoodDish;
import cz2002.entity.MenuItem;
import cz2002.entity.SetPackage;
import cz2002.util.ScannerUtil;

import java.util.*;
import java.util.stream.Stream;

/**
 * User Interface for Managing Set Packages
 * 
 * @author Benjamin Cheong
 * @version 1.0
 * @since 2021-11-08
 *
 */
public class PromotionSetUI extends MenuUI {
	/**
	 * List of Food Dishes
	 */
    private List<FoodDish> foodDishMenu;
    
    /**
     * Creates new User Interface for managing Set Packages
     * @param scanner Scanner object
     * @param setPackageMenu ArrayList of Set Packages
     * @param foodDishMenu ArrayList of Food Dishes
     */
    public PromotionSetUI(Scanner scanner, ArrayList<SetPackage> setPackageMenu, ArrayList<FoodDish> foodDishMenu) {
        super(scanner, setPackageMenu);
        this.foodDishMenu = foodDishMenu;
        setFormatting("   %-5s %-20s %s", "Status", "Name", "Price ($S)");
    }
    
    /**
     * User Interface for Creating Set Package
     */
    @Override
    void createMenuItem() {

        System.out.println();
        sc.nextLine();

        String name;
        String description;
        double price;

        System.out.print("Please enter desired Set Package Name: ");
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

        SetPackage newSet = new SetPackage(name, description, price);

        while (true) {
            String[] menuItems = foodDishMenu.stream()
                    .filter(item -> item.getEnabled())
                    .map(item -> item.toString()).toArray(String[]::new);
            String[] optionSelection = Stream.concat(Arrays.stream(menuItems), Arrays.stream(new String[] { "Done"})).toArray(String[]::new);
            String prompt = "Please select dish to include into Set Package\n " + String.format("   %-5s %-20s %-15s %s\n", "Status", "Name", "Type", "Price ($S)") + "=".repeat(55) + "";

            int option = ScannerUtil.CustomPrompt(sc, prompt, optionSelection);

            if (option <= menuItems.length) {

                Optional<FoodDish> toAdd = foodDishMenu.stream()
                                                .filter(item -> item.toString().equals(menuItems[option-1]))
                                                .findFirst();

                newSet.addFood(toAdd.get());

                System.out.printf("Added '%s' into Set Package\n", toAdd.get().getName());
            } else {
                if(newSet.getPackageItems().isEmpty()) {
                    System.out.println("Set Package cannot be empty");
                    continue;
                }
                break;
            }
        }

        System.out.println("\nYou are currently adding the following Set Package into the restaurant menu..");
        printItemDetails(newSet);
        menu.add(newSet);

        System.out.println("\nPress enter to return to main menu..");
        sc.nextLine();
    }
    
    /**
     * User Interface for Editing Set Package
     * @param item Set Package to be edited
     */
    @Override
    void editMenuItem(MenuItem item) {

        String name;
        String description;
        double price;

        System.out.println();
        sc.nextLine();

        while(true) {
            System.out.println("Item you have currently selected:");
            printItemDetails((SetPackage) item);
            int options = ScannerUtil.Prompt(sc,"Edit Name", "Edit Description", "Edit Price", "Edit Package Contents", "Back");

            System.out.println();
            sc.nextLine();

            switch (options) {
                case 1:
                    System.out.printf("Please enter new Name: ");
                    name = sc.nextLine();

                    item.setName(name);
                    break;
                case 2:
                    System.out.printf("Please enter new Description: ");
                    description = sc.nextLine();

                    item.setDescription(description);
                    break;
                case 3:
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
                case 4:
                    int addRemoveOption = ScannerUtil.Prompt(sc, "Add Dish into Set Package", "Remove Dish from Set Package");
                    System.out.println();
                    sc.nextLine();

                    if(addRemoveOption == 1) {
                        while (true) {
                            String[] menuItems = foodDishMenu.stream()
                                    .filter(foodDish -> foodDish.getEnabled())
                                    .map(foodDish -> foodDish.toString()).toArray(String[]::new);
                            String[] optionSelection = Stream.concat(Arrays.stream(menuItems), Arrays.stream(new String[] { "Done"})).toArray(String[]::new);
                            String prompt = "Please select dish to include into Set Package\n " + String.format("   %-5s %-20s %-15s %s\n", "Status", "Name", "Type", "Price ($S)") + "=".repeat(55) + "";

                            int option = ScannerUtil.CustomPrompt(sc, prompt, optionSelection);

                            if (option <= menuItems.length) {

                                Optional<FoodDish> toAdd = foodDishMenu.stream()
                                        .filter(foodDish -> foodDish.toString().equals(menuItems[option-1]))
                                        .findFirst();

                                ((SetPackage) item).addFood(toAdd.get());

                                System.out.printf("Added '%s' into Set Package\n", toAdd.get().getName());
                            } else {
                                break;
                            }
                        }
                    } else {
                        while (true) {
                            // if contains only 1 item in package -> exit
                            if(((SetPackage) item).getPackageItems().size() == 1) {
                                System.out.println("Returning to edit menu..\n");
                                break;
                            }

                            String[] menuItems = ((SetPackage) item).getPackageItems().stream()
                                    .map(foodDish -> foodDish.toString()).toArray(String[]::new);
                            String[] optionSelection = Stream.concat(Arrays.stream(menuItems), Arrays.stream(new String[] {"Done"})).toArray(String[]::new);
                            String prompt = "Please select dish to remove from Set Package\n " + String.format("   %-5s %-20s %-15s %s\n", "Status", "Name", "Type", "Price ($S)") + "=".repeat(55) + "";

                            int option = ScannerUtil.CustomPrompt(sc, prompt, optionSelection);

                            if (option <= menuItems.length) {

                                Optional<FoodDish> toRemove = foodDishMenu.stream()
                                        .filter(foodDish -> foodDish.toString().equals(menuItems[option-1]))
                                        .findFirst();

                                ((SetPackage) item).removeFood(toRemove.get());

                                System.out.printf("Removed '%s' from Set Package\n", toRemove.get().getName());
                            } else {
                                break;
                            }
                        }
                    }

                    break;
                case 5:
                    return;
            }
        }
    }
    
    /**
     * Prints details and items contained in Set Package
     * @param item Set Package to be printed
     */
    private void printItemDetails(SetPackage item) {
        System.out.printf("%-13s: %s\n", "Name", item.getName());
        System.out.printf("%-13s: %s\n", "Description", item.getDescription());
        System.out.printf("%-13s: $%.2f\n", "Price", item.getPrice());

        System.out.println("The following items are included inside the set Package:");

        List<FoodDish> includedInSet = item.getPackageItems();

        for(FoodDish dish : includedInSet) {
            System.out.println("\t- " + dish.getName());
        }
    }
}