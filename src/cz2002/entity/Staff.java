package cz2002.entity;

import java.io.Serializable;

/**
 * Person class
 * @author Tran Trung Dung
 * @version 1.0
 * @since 2020-11-01
 */

public class Staff extends Person {
	/**
	 * Staff ID Counter
	 */
	public static Integer StaffIDcounter=0;
	/**
	 * ID of this Staff
	 */
	private Integer id;
	/**
	 * Job title of this Staff
	 */
	private String jobTitle;

	/**
	 * Constructor of this class
	 * @param name name of this Staff
	 * @param gender gender of this Staff
	 * @param jobTitle job title of this Staff
	 */
	public Staff(String name, Gender gender, String jobTitle) {
		super(name,gender);
		id = StaffIDcounter++;
		this.jobTitle = jobTitle;
	}

	/**
	 *This method is to get ID of this Staff
	 * @return ID of this Staff
	 */
	public Integer getID(){
		return id;
	}

	/**
	 * This method is to get job title of this Staff
	 * @return job title of this Staff
	 */
	public String getJobTitle(){
		return jobTitle;
	}
	
}
