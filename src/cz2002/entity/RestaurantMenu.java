package cz2002.entity;

import java.io.*;
import java.util.ArrayList;

/**
 * Entity class for Restaurant Menu
 * 
 * @author Benjamin Cheong
 * @version 1.0
 * @since 2021-11-08
 *
 */
public class RestaurantMenu implements Serializable {
	/**
	 * ArrayList of all individual Food Dishes contained in this Restaurant Menu
	 */
	public ArrayList<FoodDish> alaCarteMenu;
	/**
	 * ArrayList of all Set Packages contained in this Restaurant Menu
	 */
	public ArrayList<SetPackage> setPackageMenu;
	
	/**
	 * Creates new Restaurant Menu
	 */
	public RestaurantMenu()
	{
		alaCarteMenu = new ArrayList<FoodDish>();
		setPackageMenu = new ArrayList<SetPackage>();
		load();
	}
	
	/**
	 * Saves this Restaurant Menu to menu.dat file
	 */
	public void save() {
		try {
			FileOutputStream f = new FileOutputStream("menu.dat");
			ObjectOutputStream out = new ObjectOutputStream(f);
			out.writeObject(this);
			out.flush();
			out.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Loads Restaurant Menu from menu.dat file
	 */
	public void load() {
		File f = new File("menu.dat");
		if(f.exists()) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
				var menu = (RestaurantMenu) ois.readObject();
				this.alaCarteMenu = menu.alaCarteMenu;
				this.setPackageMenu = menu.setPackageMenu;
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
