package cz2002.db;

import cz2002.entity.FoodDish;
import cz2002.entity.FoodDish.menuItemType;
import cz2002.entity.RestaurantMenu;
import cz2002.entity.SetPackage;

/**
 * This class is responsible for initialising menu.dat on first run.
 */
public class InitialiseData {
	/**
	 * Initialises Restaurant Menu with default set of menu item.
	 */
	public static void initialiseMenu()
	{
		RestaurantMenu menu = new RestaurantMenu();
		FoodDish food1 = new FoodDish("Fish & Chips", "Fried fish in crispy batter, served with chips (French fries or wedges)", 6.00, menuItemType.MAIN_COURSE);
		FoodDish food2 = new FoodDish("Cheeseburger", "Juicy beef burger with cheese", 5.00, menuItemType.MAIN_COURSE);
		FoodDish food3 = new FoodDish("Ice Lemon Tea", "Homemade Ice Lemon Tea", 2.00, menuItemType.DRINKS);
		FoodDish food4 = new FoodDish("Plain Water", "Warm/Cold", 0.20, menuItemType.DRINKS);
		FoodDish food5 = new FoodDish("Ice Cream", "1x scoop. Choice of Vanilla, Chocolate, Strawberry", 2.00, menuItemType.DESSERT);
		FoodDish food6 = new FoodDish("Tiramisu", "Coffee-flavoured Italian dessert", 3.00, menuItemType.DESSERT);
		
		menu.alaCarteMenu.add(food1);
		menu.alaCarteMenu.add(food2);
		menu.alaCarteMenu.add(food3);
		menu.alaCarteMenu.add(food4);
		menu.alaCarteMenu.add(food5);
		menu.alaCarteMenu.add(food6);
		
		
		SetPackage package1 = new SetPackage("Burger Set Meal", "1x Cheeseburger & 1x Ice Lemon Tea", 6.50);
		package1.addFood(food2);
		package1.addFood(food3);
		
		SetPackage package2 = new SetPackage("Ice Cream Combo", "3x scoops of ice cream", 5.00);
		package2.addFood(food5);
		package2.addFood(food5);
		package2.addFood(food5);
		
		menu.setPackageMenu.add(package1);
		menu.setPackageMenu.add(package2);
		menu.save();
	}
}
