package cz2002.entity;

import java.util.ArrayList;

/**
 * Entity class for Set Packages
 * 
 * @author Benjamin Cheong
 * @version 1.0
 * @since 2021-11-08
 *
 */
public class SetPackage extends MenuItem {
	/**
	 * ArrayList of food dishes contained in this set package
	 */
	private ArrayList<FoodDish> listOfDishes;
	
	/**
	 * Creates new Set Package
	 * @param name Name of new set package
	 * @param description Description of new set package
	 * @param price Price of new set package
	 */
	public SetPackage(String name, String description, double price)
	{
		super(name, description, price);
		listOfDishes = new ArrayList<FoodDish>();
	}
	
	/**
	 * Adds Food Dish to this Set Package
	 * @param food Food Dish to be added
	 */
	public void addFood(FoodDish food)
	{
		listOfDishes.add(food);
	}
	/**
	 * Removes Food Dish from this Set Package
	 * @param food Food Dish to be removed
	 */
	public void removeFood(FoodDish food)
	{
		listOfDishes.remove(food);
	}
	/**
	 * Retrieve Food Dishes in this Set Package
	 * @return ArrayList of Food Dishes in this Set Package
	 */
	public ArrayList<FoodDish> getPackageItems()
	{
		return listOfDishes;
	}
	
	/**
	 * Combines this set package's attributes into a string
	 * @return Formatted string containing Enabled, Name and Price  
	 */
	@Override
	public String toString() {
		return String.format("%6s %-20s $%.2f", getEnabled() ? "[ ]": "[*]", getName(), getPrice());
	}
}
