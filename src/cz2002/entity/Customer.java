package cz2002.entity;

/**
 * Customer class
 * @author Tran Trung Dung
 * @version 1.0
 * @since 2020-11-01
 */
public class Customer extends Person {
	/**
	 * Customer ID counter
	 */
	public static Integer CustIDcounter=0;
	/**
	 * Customer ID
	 */
	private Integer id;
	/**
	 * Whether customer is a member
	 */
	private boolean membership;

	/**
	 * Constructor for this class
	 * @param name name of customer
	 * @param gender gender of customer
	 * @param membership whether of customer is a member
	 */
	public Customer(String name, Gender gender, boolean membership) {
		super(name,gender);
		id = CustIDcounter++;
		this.membership = membership;
	}

	/**
	 * This method is to get the id of a customer
	 * @return id of that customer
	 */
	public Integer getID(){
		return id;
	}

	/**
	 * This method is to get whether a customer is a member
	 * @return true if customer is a member, otherwise return false
	 */
	public boolean getMembership(){
		return membership;
	}
	
}
