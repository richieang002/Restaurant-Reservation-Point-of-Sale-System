package cz2002.system;

import cz2002.entity.Reservation;
import cz2002.entity.Table;

import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.*;

/**
 * ReservationSystem class
 * 
 * @author Ong Kong Tat
 * @version 1.0
 * @since 2020-11-01
 */

public class ReservationSystem {
	/**
	 * List of tables
	 */
	private ArrayList<Table> tList;

	// Each reservation has a table allocated
	/**
	 * Constructor
	 * 
	 * @param tables List of tables in the restaurant
	 */
	public ReservationSystem(List<Table> tables) {
		tList = new ArrayList<>(tables);
	}

	/**
	 * This method is to get Reservation object
	 * 
	 * @param Id This Reservation's Id
	 * @return reservation object
	 */
	public Reservation getReservation(String Id) {
		LocalDate d = LocalDate.parse(Id.substring(0, 8), DateTimeFormatter.ofPattern("ddMMyyyy"));
		ArrayList<Reservation> rList = getReservationsByDate(d);
		int i;
		for (i = 0; i < rList.size(); i++) {
			if (rList.get(i).getId().equals(Id)) {
				return rList.get(i);
			}
		}
		System.out.println("This reservation ID does not exist!");
		return null;
	}

	/**
	 * This method is to remove a Reservation object based on specified id and date
	 * 
	 * @param id The id of the reservation that we want to remove
	 * @param d  The date of the reservation that we want to remove
	 * @return boolean for removing reservation status
	 */
	public boolean removeReservation(String id, LocalDate d) {
		ArrayList<Reservation> rList;
		int i;
		boolean status = false;
		String fileName = "reservation" + d.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + ".ser";
		rList = getReservationsByDate(d);
		for (i = 0; i < rList.size(); i++) {
			if (rList.get(i).getId().equals(id)) {
				status = true;
				break;
			}
		}
		if (status) {
			rList.remove(i);
			writeReservationToFile(rList, fileName);
			return true;
		}
		return false;
	}

	/**
	 * This method is to remove a Reservation Object as the person who booked the
	 * reservation has arrived
	 * 
	 * @param Id The id of the reservation that we want to remove
	 */
	public void reservationArrival(String Id) {
		LocalDate d = LocalDate.parse(Id.substring(0, 8), DateTimeFormatter.ofPattern("ddMMyyyy"));
		String fileName = "reservation" + d.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + ".ser";
		ArrayList<Reservation> rList = getReservationsByDate(d);
		int i;
		for (i = 0; i < rList.size(); i++) {
			if (rList.get(i).getId().equals(Id)) {
				break;
			}
		}
		rList.remove(i);
		writeReservationToFile(rList, fileName);
	}

	/**
	 * This method is to remove all expired Reservation Objects
	 * 
	 * @param d The date that we want to check for expired reservations
	 */
	public void removeExpiredReservations(LocalDate d) {
		LocalTime reservationExpiry;
		ArrayList<Reservation> rList;
		rList = getReservationsByDate(d);
		String fileName = "reservation" + d.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + ".ser";
		ArrayList<Reservation> removeList = new ArrayList<Reservation>();
		for (int i = 0; i < rList.size(); i++) {
			reservationExpiry = rList.get(i).getTime().plusMinutes(15);
			if (reservationExpiry.isBefore(LocalTime.now())) {
				removeList.add(rList.get(i));
			}
		}
		for (int i = 0; i < removeList.size(); i++) {
			rList.remove(removeList.get(i));
		}
		writeReservationToFile(rList, fileName);
	}

	/**
	 * This method is to check the specified table for any conflicting reservations
	 * 
	 * @param tableNo The table that we want to check for conflicting reservations
	 * @param d       The date which we want to check for conflicting reservations
	 * @param t       The time which we want to check for conflicting reservations
	 * @return boolean to indicate if there are any conflicting reservations
	 */
	// For walk in
	// Find an available table
	// Check the reservations for that table
	public boolean checkTableForReservation(int tableNo, LocalDate d, LocalTime t) {
		ArrayList<Reservation> rList = getReservationsByDate(d);
		for (int i = 0; i < rList.size(); i++) {
			if (rList.get(i).getTableNo() == tableNo) {
				// We need a method to force the customer to leave after 1hr 30mins for the next
				// reservation if there is a reservation
				// We cannot allow a reservation/walk-in to be within 1hr 30mins of another
				// reservation
				// Set tableNo to -1 to indicate there are no tables for this reservation as it
				// is fully booked
				if ((t.until(rList.get(i).getTime(), ChronoUnit.MINUTES) <= 90)
						|| (t.until(rList.get(i).getTime(), ChronoUnit.MINUTES) >= -90)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * This method is to get the upcoming reservations for the specified table
	 * 
	 * @param tableNo The table that we want to check for upcoming reservations
	 * @return LocalTime of the next upcoming reservations
	 */
	public LocalTime getUpcomingReservation(int tableNo) {
		LocalTime reservationTime;
		ArrayList<Reservation> rList = getReservationsByDate(LocalDate.now());
		for (var reservation : rList) {
			if (reservation.getTableNo() == tableNo) {
				reservationTime = reservation.getTime();

				if (reservationTime.isBefore(LocalTime.now()))
					continue;
				else
					return reservationTime;
			}
		}

		return null;
	}

	/**
	 * This method is to make a reservation object with its attributes
	 * 
	 * @param nameIn          name of the person who wants to make a reservation
	 * @param paxNo           the number of people that is booked for this
	 *                        reservation
	 * @param contactIn       the contact number of the person who wants to make a
	 *                        reservation
	 * @param reservationDate Reservation date
	 * @param reservationTime Reservation time
	 * @return id of the reservation that was just booked
	 */
	public String makeReservation(String nameIn, int paxNo, String contactIn, LocalDate reservationDate,
			LocalTime reservationTime) {

		ArrayList<Reservation> rList;
		String fileName = "reservation" + reservationDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + ".ser";
		int confirmedTableNo = -1, tableNo = -1;
		rList = getReservationsByDate(reservationDate);
		// Check if conflict with any existing reservation by filtering through the
		// table list
		if (!tList.isEmpty()) {
			for (int j = 0; j < tList.size(); j++) {
				if (tList.get(j).getCapacity() >= paxNo) {
					// Get the tableNo for those bigger than the paxNo
					tableNo = tList.get(j).getTableNo();
					// Check for reservations assigned to that table for any conflicts
					if (checkTableForReservation(tableNo, reservationDate, reservationTime)) {
						confirmedTableNo = tableNo;
						break;
					}
				}
			}
			// Fully booked
			if (confirmedTableNo == -1)
				return "";
		}

		Reservation r = new Reservation(nameIn, paxNo, contactIn, reservationDate, reservationTime, tableNo);
		rList.add(r);
		writeReservationToFile(rList, fileName);
		return r.getId();
	}

	/**
	 * This method is to write the list of reservations to a file
	 * 
	 * @param rList    name of the person who wants to make a reservation
	 * @param fileName the name of the file we are writing to
	 */
	// Create the file and serialize the list with its new addition into the file
	public void writeReservationToFile(ArrayList<Reservation> rList, String fileName) {
		try {
			if (!rList.isEmpty()) {
				FileOutputStream fout = new FileOutputStream(fileName);
				ObjectOutputStream out = new ObjectOutputStream(fout);
				out.writeObject(rList);
				out.flush();
				// closing the stream
				out.close();
			} else {
				File f = new File(fileName);
				if(f.exists()) {
					f.delete();
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * This method is to get the reservations of a specified date from the file with
	 * the date as its name
	 * 
	 * @param d date of the reservations we want to get
	 * @return an array list of reservations on that date
	 */
	// Get the past reservations on the date we are looking to make a reservation
	// by going through the arrayList for reservations on that date
	public ArrayList<Reservation> getReservationsByDate(LocalDate d) {
		// Deserialize current reservations for that date
		File f = new File("reservation" + d.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + ".ser");
		ArrayList<Reservation> rList = new ArrayList<Reservation>();
		if (f.exists()) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
				rList = (ArrayList<Reservation>) ois.readObject();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (!rList.isEmpty()) {
			String counter = rList.get(rList.size() - 1).getId().substring(8);
			Reservation.setReservationIdCounter(Integer.parseInt(counter));
		} else {
			Reservation.setReservationIdCounter(0);
		}
		return rList;
	}

}