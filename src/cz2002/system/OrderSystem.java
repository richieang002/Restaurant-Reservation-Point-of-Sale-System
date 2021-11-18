package cz2002.system;

import cz2002.entity.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Manages all the data and inputs from OrderUI and Order ArrayList
 * @author Richie Ang
 * @version 1.0
 * @since 2021-11-6
 *
 */
public class OrderSystem {
	/**
	 * ArrayList of all the orders in the system
	 */
	private ArrayList<Order> orderList;

	/**
	 * Table System
	 */
	TableSystem tableSystem;

	/**
	 * Loads past order into orderSystem
	 * @param tableSystem TableSystem
	 */
	public OrderSystem(TableSystem tableSystem) {
		this.tableSystem = tableSystem;
		orderList = new ArrayList<Order>();
		load();
	}
	
	/**
	 * Get the whole ArrayList of orders
	 * @return ArrayList of order objects
	 */
	public ArrayList<Order> getOrderList() {
		return orderList;
	}
	
	/**
	 * Adds an order into the ArrayList with information
	 * from OrderUI and shows the total order
	 * @param order Order to add into OrderSystem
	 */
	public void addOrder(Order order) {
		if(order.getDishItems().size() == 0 && order.getPackItems().size() == 0) {
			
			System.out.println("\nOrder " + order.getID() + " is not created");
			System.out.println("Order cannot be empty!");
			order.getTable().freeTable();
			return;
		}
		orderList.add(order);
		System.out.println("\nOrder " + order.getID() + " Successfully Created");
		System.out.println("=================== Order " + order.getID() + " ===================");
		System.out.println("Order Items: ");
		if (order.getDishItems().size() < 1) {
			System.out.println("    <No Item>");
		}
		for(int i=0;i<order.getDishItems().size();i++) {
			System.out.println("   --" + order.getDishItems().get(i).getName() + " $" + order.getDishItems().get(i).getPrice());
		}
		System.out.println("Set Packages: ");
		if (order.getPackItems().size() < 1) {
			System.out.println("    <No Package>");
		}
		for(int i=0;i<order.getPackItems().size();i++) {
			System.out.println("   --" + order.getPackItems().get(i).getName() + " $" + order.getPackItems().get(i).getPrice());
		}
		System.out.println("Order created on " + order.getStart());
		System.out.println("==============================================");

		save();
	}
	
	/**
	 * Prints all the order details in the order ArrayList
	 */
	public void viewAllOrders() {
		int ori;
		
		if (orderList.size() < 1) {
			System.out.println("\nThere are no orders currently in the system\n");
			return;
		}
		
		ori = 0;
		System.out.println("\n----------------Current Order List----------------");
		for(Order order: orderList) {
			if(order.getStatus() == false) {
				System.out.println("[" + (ori++ + 1) + "] -Order ID: " + order.getID() + " -Created On: " + order.getStart());
				for(MenuItem item: order.getDishItems()) {
					System.out.println("       --" + item.getName() + " | " + item.getDescription() + " | $" + item.getPrice());
				}
				for(MenuItem item: order.getPackItems()) {
					System.out.println("       --" + item.getName() + " | " + item.getDescription() + " | $" + item.getPrice());
				}
			}
		}
	}
	
	/**
	 * View the specific order details from orderID
	 * @param iinput OrderID of Order to view order from
	 */
	public void viewOrder(int iinput) {
	
		for(Order order: orderList) {
			if(order.getID() == iinput) {
				System.out.println("Order ID: " + order.getID() + " - Created On: " + order.getStart());
				for(FoodDish fditem: order.getDishItems()) {
					System.out.println("  --" + fditem.getName() + " | " + fditem.getDescription() + " | $" + fditem.getPrice());
				}
				for(SetPackage pitem: order.getPackItems()) {
					System.out.println("  --" + pitem.getName() + " | " + pitem.getDescription() + " | $" + pitem.getPrice());
				}
				return;
			}
		}
		System.out.println("No order with ID " + iinput + " is found\n");
		
	}
		
	/**
	 * Removed order from the order ArrayList
	 * @param uinput OrderID of Order to remove order from
	 */
	public void removeOrder(int uinput) {
		
		Iterator<Order> it = orderList.iterator();
		
		while (it.hasNext()) {
		  Order order = it.next();
		  if (order.getID().equals(uinput)) {
			  order.setComplete();
			  order.getTable().freeTable();
			  System.out.println("Order ID " + uinput + " is removed");
			  return;
		  }
		}

		System.out.println("No order with ID " + uinput + " is found");
		save();
		return;
		
	}
	
	/**
	 * Prints out order invoice and total amount based on membership status
	 * Deletes order from the order ArrayList
	 * @param uinput OrderID of Order to complete Order
	 * @param discountamt Discount Amount offered to Customer
	 * @param Restaurant Restaurant Object used print restaurant details in receipt
	 */
	public void completeOrder(int uinput, double discountamt, Restaurant Restaurant) {
		double discount = 1 - discountamt;
		Iterator<Order> it = orderList.iterator();
		
		while (it.hasNext()) {
		  Order order = it.next();
		  if (order.getID().equals(uinput)) {
			if (order.getStatus()) {
				System.out.println("Order " + order.getID() + " is already completed");
				return;
			}
			System.out.println("************************************************");
		    System.out.printf("%15s: %s\n", "Name", Restaurant.getname());
			System.out.printf("%15s: %s\n", "Address", Restaurant.getaddress());
			System.out.printf("%15s: %s\n", "OpeningHours", Restaurant.getopenhr());
			System.out.println();
			System.out.printf("%7s: %3s", "Server", order.getCreator().getName());
			System.out.printf("%15s: %10s\n", "Date", order.getStart().toLocalDate());
			System.out.printf("%7s: %3s", "Table", order.getTable().getTableNo());
			System.out.printf("%15s: %10s\n", "Time", order.getStart().toLocalTime());
			System.out.println();
			System.out.printf("%25s %s\n", "Order", order.getID());
			System.out.println("===============================================");
			System.out.println("Order Items: ");
			for(int i=0;i<order.getDishItems().size();i++) {
				System.out.println("   --" + order.getDishItems().get(i).getName() + " $" + order.getDishItems().get(i).getPrice());
			}
			System.out.println("Set Packages: ");
			for(int i=0;i<order.getPackItems().size();i++) {
				System.out.println("   --" + order.getPackItems().get(i).getName() + " $" + order.getPackItems().get(i).getPrice());
			}
			System.out.println();
			System.out.println("----------------------------------------------");
			double subtotal = order.totalPrice();
			double serviceCharge = (subtotal*discount)*0.1;
			double gst = (subtotal*discount*1.1)*0.07;
			double totalPay = (subtotal*discount)+serviceCharge+gst;
			order.setTotalPayable(totalPay);
			System.out.printf("Sub-Total: %29s$%.2f\n", "", subtotal);
			System.out.printf("Discount: %30s-$%.2f\n", "", subtotal*discountamt);
			System.out.printf("10%% Service Charge: %20s+$%.2f\n", "", (subtotal*discount)*0.1);
			System.out.printf("7%% GST: %32s+$%.2f\n", "", (subtotal*discount*1.1)*0.07);
			System.out.printf("Total Payable: %25s$%.2f\n", "", totalPay);
			
			System.out.println("----------------------------------------------");
			System.out.println();
			System.out.printf("Order ended on %s %s\n", LocalDate.now(), LocalTime.now());
			System.out.println("==============================================");
			System.out.println("************************************************");
			order.setComplete();
			tableSystem.getTableByNo(order.getTable().getTableNo()).freeTable();
			save();
			return;
		  }
		}

		System.out.println("No order with ID " + uinput + " is found");
		return;
		
	}
	
	/**
	 * Assumes any order that has existed for at least 1.5hrs and is still not completed to be automatically completed from the system
	 */
	public void autoCompleteOrder() {
		Iterator<Order> it = orderList.iterator();
		while (it.hasNext()) {
			Order order = it.next();
			if(order.getStatus() == false && order.getStart().plusHours(1).plusMinutes(30).isBefore(LocalDateTime.now())) {
				order.setComplete();
				order.getTable().freeTable();
			}
		}
		save();
	}

	/**
	 * Serialiser to save to order
	 */
	public void save() {
		try {
			if(!orderList.isEmpty()) {
				FileOutputStream f = new FileOutputStream("order.dat");
				ObjectOutputStream out = new ObjectOutputStream(f);
				out.writeObject(orderList);
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Serialiser to load to order
	 */
	public void load() {
		File f = new File("order.dat");
		if(f.exists()) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
				var orderList = (ArrayList<Order>) ois.readObject();
				this.orderList = orderList;

				for(Order order : orderList) {
					if(!order.getStatus()) {
						int orderTableNo = order.getTable().getTableNo();
						Table orderTable = tableSystem.getTableByNo(orderTableNo);
						orderTable.reserveTable();
					}
				}

				int id = orderList.get(orderList.size()-1).getID();
				Order.setOrderIDCounter(id+1);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
