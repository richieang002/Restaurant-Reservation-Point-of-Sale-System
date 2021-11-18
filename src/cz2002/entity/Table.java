package cz2002.entity;

import java.io.Serializable;

/**
 * Table class
 * @author Tran Trung Dung
 * @version 1.0
 * @since 2020-11-01
 */
public class Table  implements Serializable {
	/**
	 * Status enum to indicate table availability status
	 */
	public enum Status {
		/**
		 * Table is Vacant
		 */
		VACANT,
		/**
		 * Table is occupied
		 */
		OCCUPIED
	}

	/**
	 * Table number for identification
	 */
	private int tableNo;
	/**
	 * Table capacity to note how many pax it can accommodate
	 */
	private int Capacity;
	/**
	 * Table type to indicate table availability status
	 */
	private Status type = Status.VACANT;

	/**
	 * Creates a table object with capacity and number assigned
	 * @param capacity Table capacity
	 * @param tableNo Table number
	 */
	public Table(int capacity, int tableNo) {
		if (capacity <= 10 && capacity >= 2)
			Capacity = capacity;
		else
			System.out.println("Invalid Capacity");
		this.tableNo = tableNo;
	}

	/**
	 * Gets table capacity
	 * @return int
	 */
	public int getCapacity() {
		return Capacity;
	}

	/**
	 * Gets table availability status
	 * @return Status
	 */
	public Status getStatus() {
		return type;
	}

	/**
	 * Gets table number
	 * @return int
	 */
	public int getTableNo() {
		return tableNo;
	}

	/**
	 * Sets table status to Vacant
	 */
	public void freeTable() {
		type = Status.VACANT;
	}

	/**
	 * Sets table status to Occupied
	 */
	public void reserveTable() {
		// Assuming that reservation system has checked the table and ensured that its
		// free
		type = Status.OCCUPIED;
	}

}
