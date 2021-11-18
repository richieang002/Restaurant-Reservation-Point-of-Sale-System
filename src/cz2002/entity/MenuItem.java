package cz2002.entity;

import java.io.Serializable;

/**
 * Entity class for Menu Items
 * 
 * @author Benjamin Cheong
 * @version 1.0
 * @since 2021-11-08
 *
 */
public class MenuItem implements Serializable {
	/**
	 * Name of this menu item
	 */
	private String name;
	/**
	 * Description of this menu item
	 */
	private String description;
	/**
	 * Price of this menu item
	 */
	private double price;
	/**
	 * Indicator whether this menu item is enabled or not
	 * True if menu item is enabled
	 * False if menu item is deleted
	 */
	private boolean enabled;
	
	/**
	 * Creates new Menu Item
	 * @param name Name of new menu item
	 * @param description Description of new menu item
	 * @param price Price of new menu item
	 */
	public MenuItem(String name, String description, double price)
	{
		this.name = name;
		this.description = description;
		this.price = price;
		this.enabled = true;
	}
	
	/**
	 * Set name attribute
	 * @param newName New name
	 */
	public void setName(String newName)
	{
		name = newName;
	}
	/**
	 * Set description attribute
	 * @param newDesc New description
	 */
	public void setDescription(String newDesc)
	{
		description = newDesc;
	}
	/**
	 * Set price attribute
	 * @param newPrice New price
	 */
	public void setPrice(double newPrice)
	{
		price = newPrice;
	}
	/**
	 * Toggle enabled attribute
	 * @return Updated enabled attribute
	 */
	public boolean toggleEnable()
	{
		enabled = !enabled;
		return enabled;
	}
	
	/**
	 * Retrieve name attribute
	 * @return Name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * Retrieve description attribute
	 * @return Description
	 */
	public String getDescription()
	{
		return description;
	}
	/**
	 * Retrieve price attribute
	 * @return Price
	 */
	public double getPrice()
	{
		return price;
	}
	/**
	 * Retrieve enabled attribute
	 * @return Enabled
	 */
	public boolean getEnabled()
	{
		return enabled;
	}
}
