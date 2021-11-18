package cz2002.entity;

import java.io.Serializable;

/**
 * Person class
 * @author Tran Trung Dung
 * @version 1.0
 * @since 2020-11-01
 */
public class Person implements Serializable {
	/**
	 * Gender type
	 */
	public enum Gender {
		/**
		 * Person is Male
		 */
		Male,
		/**
		 * Person is Female
		 */
		Female
	}

	/**
	 * name of this person
	 */
	private String name;
	/**
	 * gender of this person
	 */
	private Gender gender;

	/**
	 * Constructor of this class
	 * @param name name of this person
	 * @param gender gender of this person
	 */
	public Person(String name, Gender gender) {
		this.name = name;
		this.gender = gender;
	}

	/**
	 * This method is to set name of this person
	 * @param newName new name of this person
	 */
	public void setName(String newName) {
		name = newName;
	}
	/**
	 * This method is to set gender of this person
	 * @param newGender new gender of this person
	 */
	public void setGender(Gender newGender) {
		gender = newGender;
	}

	/**
	 * This method is to get name of this person
	 * @return name of this person
	 */
	public String getName() {
		return name;
	}
	/**
	 * This method is to get gender of this person
	 * @return gender of this person
	 */
	public Gender getGender() {
		return gender;
	}
	
}
