package cz2002.entity;

/**
 *     Restaurant class to contain restaurant information
 *     @author Abdul Siddiq
 *     @version 1.0
 *     @since 2021-11-08
 */
public class Restaurant {
	/**
	 * Name of Restaurant
	 */
	private String nameOfRestaurant;

	/**
	 * Address of Restaurant
	 */
	private String address;

	/**
	 * Opening Hours of Restaurant
	 */
	private String openingHours;

	/**
	 * Creates a Restaurant object
	 * @param nameOfRestaurant Restaurant Name
	 * @param address Restaurant Address
	 * @param openingHours Restaurant Opening Hours
	 */
	public Restaurant(String nameOfRestaurant, String address, String openingHours) {
		this.nameOfRestaurant = nameOfRestaurant;
		this.address = address;
		this.openingHours = openingHours;
	}

	/**
	 * Return name of restaurant
	 * @return Restaurant Name
	 */
	public String getname() {
		return nameOfRestaurant;
	}
	
	/**
	 * Return address
	 * @return Restaurant Address
	 */
	public String getaddress() {
		return address;
	}
	
	/**
	 * Return opening hours
	 * @return Restaurant Opening Hours
	 */
	public String getopenhr() {
		return openingHours;
	}
	
	/**
	 * Print restaurant information
	 */
	public void printRestaurantDetails() {
		System.out.printf("%15s: %s\n", "Name", nameOfRestaurant);
		System.out.printf("%15s: %s\n", "Address", address);
		System.out.printf("%15s: %s\n", "Opening Hours", openingHours);
		System.out.println("-----------------------------------");
	}
}
