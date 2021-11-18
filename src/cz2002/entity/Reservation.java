package cz2002.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Reservation class
 * 
 * @author Ong Kong Tat
 * @version 1.0
 * @since 2020-11-01
 */
public class Reservation implements Serializable {

	/**
	 * Reservation ID counter
	 */
	private static int reservationIdCounter = 0;
	/**
	 * Reservation ID
	 */
	private String id;
	/**
	 * Name
	 */
	private String name;
	/**
	 * No of people
	 */
	private int noOfPax;
	/**
	 * Contact number
	 */
	private String contact;
	/**
	 * Reservation date
	 */
	private LocalDate date;
	/**
	 * Reservation time
	 */
	private LocalTime time;
	/**
	 * Table number
	 */
	private int tableNo;

	/**
	 * Constructor
	 * 
	 * @param name    name of the person who wants to make a reservation
	 * @param noOfPax the number of people that is booked for this reservation
	 * @param contact the contact number of the person who wants to make a
	 *                reservation
	 * @param date    Reservation date
	 * @param time    Reservation time
	 * @param tableNo The table the reservation is assigned to
	 */
	public Reservation(String name, int noOfPax, String contact, LocalDate date, LocalTime time, int tableNo) {
		this.name = name;
		this.noOfPax = noOfPax;
		this.contact = contact;
		this.date = date;
		this.time = time;
		this.tableNo = tableNo;
		this.id = date.format(DateTimeFormatter.ofPattern("ddMMyyyy")) + ++reservationIdCounter;
	}

	/**
	 * This method is to get Reservation ID
	 * 
	 * @return id of this reservation
	 */
	public String getId() {
		return id;
	}

	/**
	 * This method is to get name
	 * 
	 * @return name of this reservation
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method is to get contact
	 * 
	 * @return contact of this reservation
	 */
	public String getContact() {
		return contact;
	}

	/**
	 * This method is to get date
	 * 
	 * @return date of this reservation
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * This method is to get time
	 * 
	 * @return time of this reservation
	 */
	public LocalTime getTime() {
		return time;
	}

	/**
	 * This method is to get noOfPax
	 * 
	 * @return noOfPax of this reservation
	 */
	public int getNoOfPax() {
		return noOfPax;
	}

	/**
	 * This method is to get tableNo
	 * 
	 * @return tableNo of this reservation
	 */
	public int getTableNo() {
		return tableNo;
	}
	
	/**
	 * This method is to set reservationIdCounter
	 * 
	 * @param counter New reservationIdCounter value
	 */
	public static void setReservationIdCounter(int counter)
	{
		reservationIdCounter = counter;
	}
}
