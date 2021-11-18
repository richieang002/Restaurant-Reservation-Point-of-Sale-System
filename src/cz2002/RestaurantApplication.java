package cz2002;

import cz2002.db.InitialiseData;
import cz2002.entity.*;
import cz2002.system.OrderSystem;
import cz2002.system.ReservationSystem;
import cz2002.system.SaleRevenueSystem;
import cz2002.system.TableSystem;
import cz2002.ui.*;
import cz2002.util.ScannerUtil;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Restaurant Application Main entry class for the project
 * 
 * @author Abdul Siddiq
 * @version 1.0
 * @since 2021-11-08
 */
public class RestaurantApplication {

	/**
	 * Main entry point
	 * 
	 * @param args Arguments
	 */
	public static void main(String[] args) {

		Restaurant restaurant = new Restaurant("NTUCates", "50 Nanyang Ave, 639798", "1100 - 1500 | 1800 - 2200");

		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to Restaurant Reservation Management System");
		restaurant.printRestaurantDetails();

		ArrayList<Staff> staffList = new ArrayList<>();
		staffList.add(new Staff("Amy", Person.Gender.Female, "Manager"));
		staffList.add(new Staff("Bob", Person.Gender.Male, "Waiter"));
		staffList.add(new Staff("Cat", Person.Gender.Female, "Chef"));
		staffList.add(new Staff("Dom", Person.Gender.Male, "Waiter"));

		int staffSelection = ScannerUtil.CustomPrompt(sc, "Please select current Staff Account",
				staffList.stream().map(staff -> staff.getName()).toArray(String[]::new));
		sc.nextLine();
		System.out.printf("Please enter password: %s\n", "*".repeat(10));

		Staff currentStaff = staffList.get(staffSelection - 1);

//		 InitialiseData.initialiseMenu();

		RestaurantMenu menu = new RestaurantMenu();
		TableSystem tableSystem = new TableSystem();
		int capacity = 2;

		for (int i = 0; i < 5; i++) {
			tableSystem.addTable(capacity);
			tableSystem.addTable(capacity);
			capacity = capacity + 2;

			if (capacity > 10)
				capacity = 2;
		}
		ReservationSystem reservationSystem = new ReservationSystem(tableSystem.getTableList());
		OrderSystem orderSystem = new OrderSystem(tableSystem);
		SaleRevenueSystem saleRevenueSystem = new SaleRevenueSystem(orderSystem);

		ReservationUI reservationUI = new ReservationUI(sc, tableSystem.getTableList());
		RestaurantUI restaurantUI = new RestaurantUI(reservationSystem, tableSystem, sc);
		SaleRevenueUI saleRevenueUI = new SaleRevenueUI(saleRevenueSystem, sc);
		OrderUI orderUI = new OrderUI(sc, orderSystem, reservationSystem, tableSystem, menu, restaurant);

		while (true) {
			reservationSystem.removeExpiredReservations(LocalDate.now());
			orderSystem.autoCompleteOrder();
			int option = ScannerUtil.Prompt(sc, "Manage Menu Items", "Manage Promotion Sets", "Manage Orders",
					"Manage Reservations", "Check Table Availability", "Print Sale Revenue Report", "Quit");

			sc.nextLine();
			switch (option) {
			case 1:
				MenuUI menuManager = new FoodDishUI(sc, menu.alaCarteMenu);
				menuManager.run("Menu Item");
				menu.save();
				break;
			case 2:
				menuManager = new PromotionSetUI(sc, menu.setPackageMenu, menu.alaCarteMenu);
				menuManager.run("Set Package");
				menu.save();
				break;
			case 3:
				orderUI.manageOrders(currentStaff, tableSystem.getTableList());
				break;
			case 4:
				reservationUI.run();
				break;
			case 5:
				restaurantUI.checkTableAvailability();
				break;
			case 6:
				saleRevenueUI.printSaleRevenueReport();
				break;
			case 7:
				return;
			}
		}
	}
}
