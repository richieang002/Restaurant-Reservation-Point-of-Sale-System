package cz2002.ui;

import cz2002.entity.*;
import cz2002.system.*;

import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Stream;

import javax.lang.model.util.ElementScanner14;

import cz2002.util.ScannerUtil;

/**
 * Represents the Order UI only
 * 
 * @author Richie Ang
 * @version 1.0
 * @since 2021-11-6
 */
public class OrderUI {

	/**
	 * Scanner obj
	 */
	private Scanner sc;
	/**
	 * OrderSystem obj
	 */
	private OrderSystem OrderSystem;
	/**
	 * RestaurantSystem obj
	 */
	private ReservationSystem ReservationSystem;
	/**
	 * TableSystem obj
	 */
	private TableSystem TableSystem;
	/**
	 * RestaurantMenu obj
	 */
	private RestaurantMenu RestaurantMenu;
	/**
	 * Restaurant obj
	 */
	private Restaurant Restaurant;

	/**
	 * OrderUI Constructor with the necessary systems to function
	 * 
	 * @param scanner           Scanner
	 * @param OrderSystem       Order System
	 * @param ReservationSystem Reservation System
	 * @param TableSystem       Table System
	 * @param RestaurantMenu    Restaurant Menu
	 * @param Restaurant        Restaurant
	 */
	public OrderUI(Scanner scanner, OrderSystem OrderSystem, ReservationSystem ReservationSystem,
			TableSystem TableSystem, RestaurantMenu RestaurantMenu, Restaurant Restaurant) {
		sc = scanner;
		this.OrderSystem = OrderSystem;
		this.ReservationSystem = ReservationSystem;
		this.TableSystem = TableSystem;
		this.RestaurantMenu = RestaurantMenu;
		this.Restaurant = Restaurant;
	}

	/**
	 * Order Management Options UI
	 * 
	 * @param staff  Staff
	 * @param tables List of tables
	 */
	public void manageOrders(Staff staff, List<Table> tables) {
		int uchoice;
		uchoice = ScannerUtil.CustomPrompt(sc, "-----------ORDER OPTIONS-----------", "View Current Orders",
				"View Order by ID", "New Orders", "Modify Existing Orders", "Remove Orders", "Complete Order");

		switch (uchoice) {
		case 1:
			OrderSystem.viewAllOrders();
			break;
		case 2:
			viewOrder();
			break;
		case 3:
			if ((LocalTime.now().isAfter(LocalTime.of(11, 0)) && LocalTime.now().isBefore(LocalTime.of(15, 0)))
					|| (LocalTime.now().isAfter(LocalTime.of(18, 0))
							&& LocalTime.now().isBefore(LocalTime.of(22, 0)))) {
				newOrder(staff, tables);
			} else {
				System.out.println("You can only order between 11am-3pm or 6pm-10pm.");
			}
			break;
		case 4:
			if ((LocalTime.now().isAfter(LocalTime.of(11, 0)) && LocalTime.now().isBefore(LocalTime.of(15, 0)))
					|| (LocalTime.now().isAfter(LocalTime.of(18, 0))
							&& LocalTime.now().isBefore(LocalTime.of(22, 0)))) {
				modifyOrders();
				OrderSystem.save();
			} else {
				System.out.println("You can only modify the order between 11am-3pm or 6pm-10pm.");
			}
			break;
		case 5:
			if ((LocalTime.now().isAfter(LocalTime.of(11, 0)) && LocalTime.now().isBefore(LocalTime.of(15, 0)))
					|| (LocalTime.now().isAfter(LocalTime.of(18, 0))
							&& LocalTime.now().isBefore(LocalTime.of(22, 0)))) {
				removeOrders();
			} else {
				System.out.println("You can only remove an order between 11am-3pm or 6pm-10pm.");
			}
			break;
		case 6:
			// Prints Order Invoice
			printOrderInvoice();
			break;
		}
	}

	/**
	 * Gets order ID and calls OrderSystem method to view the order
	 */
	private void viewOrder() {
		System.out.println("\nType in the order ID for viewing: ");
		int iinput = sc.nextInt();
		OrderSystem.viewOrder(iinput);
	}

	/**
	 * Check the type of order whether it is a reservation or a walk in, allowing
	 * user to add items and packages into order before calling the OrderSystem
	 * method to create a new order
	 * 
	 * @param staff  Current Staff
	 * @param tables List of tables
	 */
	private void newOrder(Staff staff, List<Table> tables) {
		int orderType, uc;
		String resId;
		Reservation resv;
		ReservationSystem reservationSystem = new ReservationSystem(tables);

		ArrayList<FoodDish> orDish = new ArrayList<FoodDish>();
		ArrayList<SetPackage> orPack = new ArrayList<SetPackage>();
		MenuItem item;

		do {
			orDish = new ArrayList<FoodDish>();
			orPack = new ArrayList<SetPackage>();

			System.out.println("Select type of order (1 or 2)");
			System.out.println("-----------------------------");
			System.out.println("1) From reservation");
			System.out.println("2) From walk-in order");
			orderType = sc.nextInt();
			sc.nextLine();

			switch (orderType) {
			case 1:
				do {
					System.out.println("Enter reservation ID ('n' to cancel): ");

					resId = sc.nextLine();

					if (resId.equals("n")) {
						return;
					}

					if (resId.length() < 8) {
						System.out.println("Invalid reservation ID");
						continue;
					}
					try {
						resv = reservationSystem.getReservation(resId);
					} catch (Exception e) {
						System.out.println("Reservation ID " + resId + " not found");
						continue;
					}

					if (resv == null) {
						System.out.println("Invalid reservation ID");
					} else {
						break;
					}
				} while (true);
				
				ReservationSystem.reservationArrival(resId);

				Table orTable = TableSystem.getTableByNo(resv.getTableNo());

				do {
					item = promptSelectMenuItem("Please select Set Package to add into order",
							RestaurantMenu.setPackageMenu);

					if (item != null)
						orPack.add((SetPackage) item);
				} while (item != null);

				do {
					item = promptSelectMenuItem("Please select Food Dish to add into order",
							RestaurantMenu.alaCarteMenu);

					if (item != null)
						orDish.add((FoodDish) item);
				} while (item != null);

				Order newOrder = new Order(staff, orDish, orPack, resv, orTable, LocalDateTime.now());
				OrderSystem.addOrder(newOrder);

				break;
			case 2:
				int pax;
				do {
					System.out.println("Enter the number of pax dining in");
					pax = sc.nextInt();
					if (pax < 1) {
						System.out.println("Number cannot be less than 1");
					}
					else break;
				} while (true);
				
				ArrayList<Table> availTable = TableSystem.getAvailableTables();
				
				int i;
				for (i = 0; i < availTable.size(); i++) {
					if (ReservationSystem.checkTableForReservation(availTable.get(i).getTableNo(), LocalDate.now(),
							LocalTime.now()) && availTable.get(i).getCapacity() >= pax) {
						break;
					}
				}
				
				if (i == availTable.size()) {
					System.out.println("There are no available tables that allow the number of pax requested");
					break;
				}
				
				do {
					item = promptSelectMenuItem("Please select Set Package to add into order",
							RestaurantMenu.setPackageMenu);

					if (item != null)
						orPack.add((SetPackage) item);
				} while (item != null);

				do {
					item = promptSelectMenuItem("Please select Food Dish to add into order",
							RestaurantMenu.alaCarteMenu);

					if (item != null)
						orDish.add((FoodDish) item);
				} while (item != null);

				Order newOrder2 = new Order(staff, orDish, orPack, null, availTable.get(i), LocalDateTime.now());

				availTable.get(i).reserveTable();

				OrderSystem.addOrder(newOrder2);
				
				break;
			default:
				System.out.println("Option entered is invalid, please try again\n");
			}

			uc = ScannerUtil.Prompt(sc, "Create Another Order", "End Creation");
			if (uc == 2) {
				return;
			}

		} while (true);

	}

	/**
	 * Gets the order ID and modifies the order according to user request
	 */
	private void modifyOrders() {
		int uc;
		do {
			System.out.println("\nType in the order ID to modify: ");
			int uinput = sc.nextInt();
			int ucho, uadd;
			MenuItem item;
			ArrayList<Order> orderList = OrderSystem.getOrderList();

			for (Order order : orderList) {
				if (order.getID() == uinput) {
					System.out.println("=================== Order " + order.getID() + " ===================");
					System.out.println("Order Items: ");
					for (int i = 0; i < order.getDishItems().size(); i++) {
						System.out.println("   --" + order.getDishItems().get(i).getName() + " $"
								+ order.getDishItems().get(i).getPrice());
					}
					System.out.println("Set Packages: ");
					for (int i = 0; i < order.getPackItems().size(); i++) {
						System.out.println("   --" + order.getPackItems().get(i).getName() + " $"
								+ order.getPackItems().get(i).getPrice());
					}
					System.out.println("Total Cost: $" + order.totalPrice());
					System.out.println("Order created on " + order.getStart());
					System.out.println("==============================================");

					do {
						System.out.println("Select one of the options: ");
						System.out.println("1) Add menu item");
						System.out.println("2) Add set package");
						System.out.println("3) Remove menu item");
						System.out.println("4) Remove set package");
						System.out.println("5) End Modify");

						ucho = sc.nextInt();

						switch (ucho) {
						case 1:
							do {
								item = promptSelectMenuItem("Please select Food Dish to add into order",
										RestaurantMenu.alaCarteMenu);

								if (item != null)
									order.addDishItem((FoodDish) item);
							} while (item != null);

							break;
						case 2:
							do {
								item = promptSelectMenuItem("Please select Set Package to add into order",
										RestaurantMenu.setPackageMenu);

								if (item != null)
									order.addPackItem((SetPackage) item);
							} while (item != null);

							break;
						case 3:
							do {
								System.out.println("Order Items: ");
								if (order.getDishItems().size() < 1) {
									System.out.println("  --No Item Left");
									break;
								}
								for (int i = 0; i < order.getDishItems().size(); i++) {
									System.out.println((i + 1) + ") " + order.getDishItems().get(i).getName() + " $"
											+ order.getDishItems().get(i).getPrice());
								}

								System.out.println("Choose menu items to remove from order");
								System.out.println("Enter -1 to stop");
								uadd = sc.nextInt();

								if (uadd == -1) {
									break;
								}

								if (uadd <= order.getDishItems().size()) {
									order.getDishItems().remove(uadd - 1);
								} else if (uadd == 0 || uadd > order.getDishItems().size()) {
									System.out.println("Choice is invalid");
								}

							} while (true);
							break;
						case 4:
							do {
								System.out.println("Set Packages: ");
								if (order.getPackItems().size() < 1) {
									System.out.println("  --No Item Left");
									break;
								}
								for (int i = 0; i < order.getPackItems().size(); i++) {
									System.out.println((i + 1) + ") " + order.getPackItems().get(i).getName() + " $"
											+ order.getPackItems().get(i).getPrice());
								}

								System.out.println("Choose set packages to remove from order");
								System.out.println("Enter -1 to stop");
								uadd = sc.nextInt();

								if (uadd == -1) {
									break;
								}

								if (uadd <= order.getPackItems().size()) {
									order.getPackItems().remove(uadd - 1);
								} else if (uadd == 0 || uadd > order.getPackItems().size()) {
									System.out.println("Choice is invalid");
								}

							} while (true);
							break;
						case 5:
							return;
						}

					} while (true);

				}
			}
			System.out.println("No order with ID " + uinput + " is found\n");

			uc = ScannerUtil.Prompt(sc, "Modify Another Orders", "End Modification");
			if (uc == 2) {
				return;
			}
		} while (true);
	}

	/**
	 * Gets the order ID and calls the OrderSystem method to remove the order
	 */
	private void removeOrders() {
		int uc;
		do {
			System.out.println("\nType in the order ID to remove from system: ");
			int uinput = sc.nextInt();
			OrderSystem.removeOrder(uinput);

			uc = ScannerUtil.Prompt(sc, "Remove Another Orders", "End Removal");
			if (uc == 2) {
				return;
			}
		} while (true);
	}

	/**
	 * Gets the order ID and membership status before calling the OrderSystem method
	 * to complete and print order invoice
	 */
	private void printOrderInvoice() {
		int uc;
		do {
			System.out.println("\nType in the order ID to pay: ");
			int uinput = sc.nextInt();

			do {
				System.out.println("Is the customer a member? (y/n) ");
				String mem = sc.next();

				if (mem.equalsIgnoreCase("y")) {
					OrderSystem.completeOrder(uinput, 0.1, Restaurant);
					break;
				} else {
					OrderSystem.completeOrder(uinput, 0, Restaurant);
					break;
				}
			} while (true);

			uc = ScannerUtil.Prompt(sc, "Pay Another Order", "End Pay");
			if (uc == 2) {
				return;
			}
		} while (true);
	}

	/**
	 * Prompts user to select menu item
	 * 
	 * @param prompt Prompt string to print out
	 * @param menu   Menu Item to display (Ala Carte or Set Package)
	 * @return Selected Menu Item
	 */
	private MenuItem promptSelectMenuItem(String prompt, List<? extends MenuItem> menu) {
		while (true) {
			String[] menuItems = menu.stream().filter(item -> item.getEnabled()).map(item -> item.toString())
					.toArray(String[]::new);
			String[] optionSelection = Stream.concat(Arrays.stream(menuItems), Arrays.stream(new String[] { "Done" }))
					.toArray(String[]::new);
			String header = String.format("   %-5s %-20s %-15s %s\n", "Status", "Name", "Type", "Price ($S)")
					+ "=".repeat(55) + "";

			int option = ScannerUtil.CustomPrompt(sc, prompt + "\n" + header, optionSelection);

			if (option <= menuItems.length) {

				Optional<? extends MenuItem> toAdd = menu.stream()
						.filter(item -> item.toString().equals(menuItems[option - 1])).findFirst();

				if (toAdd.isPresent())
					return toAdd.get();
			}

			return null;
		}

	}
}
