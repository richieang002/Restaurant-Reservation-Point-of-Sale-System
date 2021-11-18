package cz2002.entity;

/**
 * Entity class for individual Food Dishes
 * 
 * @author Benjamin Cheong
 * @version 1.0
 * @since 2021-11-08
 *
 */
public class FoodDish extends MenuItem {
	/**
	 * 
	 * Available Menu Item Types
	 *
	 */
	public enum menuItemType
	{
		/**
		 * Menu Item Type is Main Course
		 */
		MAIN_COURSE,
		/**
		 * Menu Item Type is Drinks
		 */
		DRINKS,
		/**
		 * Menu Item Type is Dessert
		 */
		DESSERT
	}
	
	/**
	 * Menu Item Type of this food dish
	 */
	private menuItemType type;
	
	/**
	 * Creates new Food Dish
	 * @param name Name of new food dish
	 * @param description Description of new food dish
	 * @param price Price of new food dish
	 * @param type Type of new food dish
	 */
	public FoodDish(String name, String description, double price, menuItemType type)
	{
		super(name, description, price);
		this.type = type;
	}
	
	/**
	 * Set type attribute
	 * @param newType New type
	 */
	public void setType(menuItemType newType)
	{
		type = newType;
	}
	/**
	 * Retrieve type attribute
	 * @return Type
	 */
	public menuItemType getType()
	{
		return type;
	}
	
	/**
	 * Combines this food dish's attributes into a string
	 * @return Formatted string containing Enabled, Name, Type and Price  
	 */
	@Override
	public String toString() {
		String typeString = "";
		switch (type) {
			case MAIN_COURSE    -> typeString = "Main Course";
			case DESSERT         -> typeString = "Dessert";
			case DRINKS         -> typeString = "Drinks";
		}

		return String.format("%6s %-20s %-15s $%.2f", getEnabled() ? "[ ]": "[*]", getName(), typeString, getPrice());
	}
}
