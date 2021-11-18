package cz2002.system;
import cz2002.entity.Table;

import java.util.ArrayList;

/**
 * This class is to manage all Tables
 * @author Tran Trung Dung
 * @version 1.0
 * @since 2021-11-06
 */
public class TableSystem {
	/**
	 * Table Counter
	 */
	public static int tableCounter=0;
	/**
	 * List of all tables
	 */
	private ArrayList<Table> tableList = new ArrayList<Table>();

	/**
	 * This method is to get all tables
	 * @return list of all tables
	 */
	public ArrayList<Table> getTableList() {
		return tableList;
	}

	/**
	 * This method is to add Table to Table list
	 * @param capacity This indicates the capacity of the table
	 * @return a message about status of this method to system
	 */
	public String addTable(int capacity) {
		if(capacity<2 || capacity>10 || capacity%2==1) return "Invalid capacity!";
		Table table = new Table(capacity, ++tableCounter);
		if(tableList.add(table)) return "Table added successfully!";
		else return "Failed to add table!";
	}

	/**
	 * This method is to remove a table from list of all tables
	 * @param tableNo tableNo of the table that want to remove
	 * @return a message about status of this method to system
	 */
	public String removeTable(int tableNo) {
		if(tableList.removeIf(table-> table.getTableNo() == tableNo))
			return "Table removed successfully!";

		return "Failed to removed table";
	}

	/**
	 * This method is to get all available tables
	 * @return list of all available tables
	 */
	public ArrayList<Table> getAvailableTables(){
		ArrayList<Table> availableTables=new ArrayList<Table>();
		for(int i=0;i<tableList.size();i++) {
			if(tableList.get(i).getStatus() == Table.Status.VACANT) {
				availableTables.add(tableList.get(i));
			}
		}
		return availableTables;
	}

	/**
	 * This method is to get table by the TableNo
	 * @param tableNo tableNo of the table that want to get
	 * @return table with that tableNo if there is any, otherwise return null
	 */
	public Table getTableByNo(int tableNo) {
		for(int i=0;i<tableList.size();i++) {
			if(tableList.get(i).getTableNo() == tableNo) {
				return tableList.get(i);
			}
		}
		return null;
	}
}
