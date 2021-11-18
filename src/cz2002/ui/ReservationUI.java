package cz2002.ui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cz2002.entity.Reservation;
import cz2002.entity.Table;
import cz2002.system.ReservationSystem;
import cz2002.util.ScannerUtil;

/**
 * ReservationSystem class
 * 
 * @author Ong Kong Tat
 * @version 1.0
 * @since 2020-11-01
 */

public class ReservationUI {
	/**
	 * Scanner
	 */
	private Scanner sc;
	/**
	 * List of tables
	 */
	private List<Table> tList;
	/**
	 * ReservationSystem
	 */
	private ReservationSystem rSystem;

	/**
	 * Constructor
	 * 
	 * @param scanner scanner for input
	 * @param tables  List of tables in restaurant
	 */
	public ReservationUI(Scanner scanner, List<Table> tables) {
		sc = scanner;
		rSystem = new ReservationSystem(tables);
		tList = tables;
	}

	/**
	 * Runs Reservation UI routine
	 */
	public void run() {

		rSystem.removeExpiredReservations(LocalDate.now());
		int option = ScannerUtil.Prompt(sc, "Check Reservation", "Make Reservation", "Remove Reservation", "Back");
		sc.nextLine();
		switch (option) {
		case 1 -> checkReservationUI();
		case 2 -> makeReservationUI();
		case 3 -> removeReservationUI();
		}
	}

	/**
	 * This method is to get the reservations of a specified date from the file with
	 * the date as its name
	 *
	 * @param dateIn          date of the reservations we want to validate
	 * @param makeReservation to specify if it is invoked by the makeReservationUI
	 * @return a date in LocalDate format based on dateIn
	 */
	public LocalDate validateDate(String dateIn, boolean makeReservation) {
		LocalDate reservationDate;
		LocalDate currentDate = LocalDate.now();
		while (true) {
			try {
				reservationDate = LocalDate.parse(dateIn,
						DateTimeFormatter.ofPattern("dd/MM/yyyy").withResolverStyle(ResolverStyle.SMART));
				if (reservationDate.isBefore(currentDate)) {
					throw new Exception("Sorry! You are entering a date that has already passed!");
				} else if (makeReservation && reservationDate.isBefore(currentDate.plusDays(1))) {
					throw new Exception("Sorry! You are only allowed to make a reservation 1 day in advance");
				} else if (reservationDate.isAfter(currentDate.plusMonths(1))) {
					throw new Exception(
							"Sorry! You are only allowed to process a reservation made within the next 30 days");
				} else
					break;
			} catch (DateTimeParseException e) {
				System.out.println("You have entered an invalid date!");
				System.out.println("Enter reservation date (dd/mm/yyyy): ");
				dateIn = sc.nextLine();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println("Enter reservation date (dd/mm/yyyy): ");
				dateIn = sc.nextLine();
			}
		}
		return reservationDate;
	}

	/**
	 * This method is to display the System messages for reservations and get the
	 * and getting the required input
	 *
	 * 
	 */
	public void checkReservationUI() {
		System.out.println("Which date would you like to view the reservations for? Please enter in dd/MM/yyyy");
		String dateIn = sc.nextLine();
		LocalDate reservationDate = validateDate(dateIn, false);
		ArrayList<Reservation> rList = rSystem.getReservationsByDate(reservationDate);
		for (int j = 0; j < rList.size(); j++) {
			for (int i = 0; i < tList.size(); i++) {
				if (rList.get(j).getTableNo() == tList.get(i).getTableNo()) {
					System.out.println("Date: " + dateIn);
					System.out.println("Time: " + rList.get(j).getTime());
					System.out.println("Name: " + rList.get(j).getName());
					System.out.println("ReservationID: " + rList.get(j).getId());
					System.out.println("NoOfPax: " + rList.get(j).getNoOfPax());
					System.out.println("TableNo: " + rList.get(j).getTableNo());
					System.out.println("");
				}
			}
		}
		if (rList.size() == 0)
			System.out.println("There are no reservations for this date.");
	}

	/**
	 * This method is to display the System messages for removing the reservation
	 * and getting the required input
	 * 
	 */
	public void removeReservationUI() {
		System.out.println("Date of reservation to be removed (dd/mm/yyyy):");
		String dateIn = sc.nextLine();
		LocalDate reservationDate = validateDate(dateIn, false);
		System.out.println("ID of reservation to be removed:");
		String rID = sc.nextLine();
		if (rSystem.removeReservation(rID, reservationDate)) {
			System.out.println("Reservation has been removed successfully");
		} else {
			System.out.println("Unable to remove reservation. Invalid reservation ID");
		}
	}

	/**
	 * This method is to display the System messages for making the reservations and
	 * getting the required input
	 * 
	 * 
	 */
	public void makeReservationUI() {
		int choiceInterval;
		int session;
		LocalTime reservationTime = LocalTime.now();
		boolean continueOn = true;

		System.out.println("Enter your name: ");
		String nameIn = sc.nextLine();

		System.out.println("Enter your 8 digit contact number: ");
		String contactIn = sc.nextLine();
		while (contactIn.length() != 8) {
			System.out.println("Invalid contact no! Please enter a 8 digit contact number.");
			System.out.println("Enter your 8 digit contact number: ");
			contactIn = sc.nextLine();
		}
		System.out.println("Enter reservation date (dd/mm/yyyy): ");
		String dateIn = sc.nextLine();
		LocalDate reservationDate = validateDate(dateIn, true);
		session = 0;
		System.out.println("Enter the session for which you wish to dine at our restaurant" + "\n" + "1) 1100-1330 \n"
				+ "2) 1800-2030\n");
		session = sc.nextInt();
		sc.nextLine();
		while (session != 1 && session != 2) {
			System.out.println("An invalid session has been chosen.\n"
					+ "Enter the session for which you wish to dine at our restaurant" + "\n" + "1) 1100-1330 \n"
					+ "2) 1800-2030\n");
			session = sc.nextInt();
			sc.nextLine();
		}
		while (session == 1 && continueOn) {
			System.out.println("Please enter your choice based on the reservation time intervals available shown.\n");
			System.out.println("1) 1100 	2) 1115		3) 1130		4) 1145\n"
					+ "5) 1200		6) 1215		7) 1230		8) 1245\n" + "9) 1300		10) 1315		11) 1330\n");
			choiceInterval = sc.nextInt();
			switch (choiceInterval) {
			case 1: {
				reservationTime = LocalTime.parse("11:00", DateTimeFormatter.ofPattern("HH:mm"));
				continueOn = false;
				break;
			}
			case 2: {
				reservationTime = LocalTime.parse("11:15", DateTimeFormatter.ofPattern("HH:mm"));
				continueOn = false;
				break;
			}
			case 3: {
				reservationTime = LocalTime.parse("11:30", DateTimeFormatter.ofPattern("HH:mm"));
				continueOn = false;
				break;
			}
			case 4: {
				reservationTime = LocalTime.parse("11:45", DateTimeFormatter.ofPattern("HH:mm"));
				continueOn = false;
				break;
			}
			case 5: {
				reservationTime = LocalTime.parse("12:00", DateTimeFormatter.ofPattern("HH:mm"));
				continueOn = false;
				break;
			}
			case 6: {
				reservationTime = LocalTime.parse("12:15", DateTimeFormatter.ofPattern("HH:mm"));
				continueOn = false;
				break;
			}
			case 7: {
				reservationTime = LocalTime.parse("12:30", DateTimeFormatter.ofPattern("HH:mm"));
				continueOn = false;

			}
			case 8: {
				reservationTime = LocalTime.parse("12:45", DateTimeFormatter.ofPattern("HH:mm"));
				continueOn = false;
				break;
			}
			case 9: {
				reservationTime = LocalTime.parse("13:00", DateTimeFormatter.ofPattern("HH:mm"));
				continueOn = false;
				break;
			}
			case 10: {
				reservationTime = LocalTime.parse("13:15", DateTimeFormatter.ofPattern("HH:mm"));
				continueOn = false;
				break;
			}
			case 11: {
				reservationTime = LocalTime.parse("13:30", DateTimeFormatter.ofPattern("HH:mm"));
				continueOn = false;
				break;
			}
			default: {
				System.out.println("Invalid choice\n");
			}
			}

		}
		continueOn = true;
		while (session == 2 && continueOn) {
			System.out.println("Please enter your choice based on the reservation time intervals available shown.\n");
			System.out.println("1) 1800 	2) 1815		3) 1830		4) 1845\n"
					+ "5) 1900		6) 1915		7) 1930		8) 1945\n" + "9) 2000		10) 2015		11) 2030\n");
			choiceInterval = sc.nextInt();
			switch (choiceInterval) {
			case 1: {
				reservationTime = LocalTime.parse("18:00", DateTimeFormatter.ofPattern("HH:mm"));
				continueOn = false;
				break;
			}
			case 2: {
				reservationTime = LocalTime.parse("18:15", DateTimeFormatter.ofPattern("HH:mm"));
				continueOn = false;
				break;
			}
			case 3: {
				reservationTime = LocalTime.parse("18:30", DateTimeFormatter.ofPattern("HH:mm"));
				continueOn = false;
				break;
			}
			case 4: {
				reservationTime = LocalTime.parse("18:45", DateTimeFormatter.ofPattern("HH:mm"));
				continueOn = false;
				break;
			}

			case 5: {
				reservationTime = LocalTime.parse("19:00", DateTimeFormatter.ofPattern("HH:mm"));
				continueOn = false;
				break;
			}
			case 6: {
				reservationTime = LocalTime.parse("19:15", DateTimeFormatter.ofPattern("HH:mm"));
				continueOn = false;
				break;
			}
			case 7: {
				reservationTime = LocalTime.parse("19:30", DateTimeFormatter.ofPattern("HH:mm"));
				continueOn = false;

			}
			case 8: {
				reservationTime = LocalTime.parse("19:45", DateTimeFormatter.ofPattern("HH:mm"));
				continueOn = false;
				break;
			}
			case 9: {
				reservationTime = LocalTime.parse("20:00", DateTimeFormatter.ofPattern("HH:mm"));
				continueOn = false;
				break;
			}
			case 10: {
				reservationTime = LocalTime.parse("20:15", DateTimeFormatter.ofPattern("HH:mm"));
				continueOn = false;
				break;
			}
			case 11: {
				reservationTime = LocalTime.parse("20:30", DateTimeFormatter.ofPattern("HH:mm"));
				continueOn = false;
				break;
			}
			default: {
				System.out.println("Invalid choice\n");
			}
			}
		}

		System.out.println("Enter number of pax (1-10): ");
		int paxNo = sc.nextInt();
		while (paxNo > 10 || paxNo <= 0) {
			if (paxNo > 10) {
				System.out.println(
						"We only allow up to 10 people in a group at our restaurant. Please enter a smaller number\nEnter number of pax: ");
			} else {
				System.out.println(
						"Number of pax cannot be less than or equal to 0. Please enter a valid number\nEnter number of pax: ");
			}
			paxNo = sc.nextInt();
		}
		String rID;
		rID = rSystem.makeReservation(nameIn, paxNo, contactIn, reservationDate, reservationTime);
		if (rID.isEmpty())
			System.out.println("We are fully booked");
		else {
			System.out.println("Success! The reservation has been booked!");
			System.out.println("Your reservation ID is " + rID);
		}

	}

}
